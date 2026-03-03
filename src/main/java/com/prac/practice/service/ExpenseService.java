package com.prac.practice.service;

import com.prac.practice.dto.ExpenseRequestDto;
import com.prac.practice.dto.ExpenseResponseDto;
import com.prac.practice.entity.Expense;
import com.prac.practice.entity.User;
import com.prac.practice.exception.ExpenseNotFoundException;
import com.prac.practice.exception.UserNotFoundException;
import com.prac.practice.mapper.ExpenseMapper;
import com.prac.practice.repository.ExpenseRepository;
import com.prac.practice.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;

    @Autowired
    public ExpenseService(
            ExpenseRepository expenseRepository,
            UserRepository userRepository) {

        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
    }

    /* =====================================================
       CURRENT USER
     ===================================================== */

    private User getCurrentUserEntity() {

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));
    }

    /* =====================================================
       ROLE CHECK
     ===================================================== */

    private boolean isAdmin(User user) {
        return user.getRole().equals("ROLE_ADMIN")
                || user.getRole().equals("ROLE_SUPER_ADMIN");
    }

    /* =====================================================
       AUTHORIZATION CHECK (CENTRALIZED)
     ===================================================== */

    private void checkExpenseAccess(
            Expense expense,
            User currentUser,
            String message) {

        boolean owner =
                expense.getUser()
                        .getId()
                        .equals(currentUser.getId());

        if (!owner && !isAdmin(currentUser)) {
            throw new AccessDeniedException(message);
        }
    }

    /* =====================================================
       FIND BY ID
     ===================================================== */

    public ExpenseResponseDto findById(Long id) {

        User currentUser = getCurrentUserEntity();

        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() ->
                        new ExpenseNotFoundException(
                                "Expense not found " + id));

        checkExpenseAccess(
                expense,
                currentUser,
                "You are not allowed to access this expense"
        );

        return ExpenseMapper.toExpenseResponseDto(expense);
    }

    /* =====================================================
       FIND ALL
     ===================================================== */

    public Page<ExpenseResponseDto> findAll(Pageable pageable) {

        User currentUser = getCurrentUserEntity();

        Page<Expense> expenses;

        if (isAdmin(currentUser)) {
            expenses = expenseRepository.findAll(pageable);
        } else {
            expenses =
                    expenseRepository.findByUser(
                            currentUser,
                            pageable);
        }

        return expenses.map(
                ExpenseMapper::toExpenseResponseDto);
    }

    /* =====================================================
       CREATE
     ===================================================== */

    public ExpenseResponseDto save(
            ExpenseRequestDto dto) {

        User currentUser = getCurrentUserEntity();

        Expense expense =
                ExpenseMapper.toEntity(dto);

        expense.setUser(currentUser);

        Expense saved =
                expenseRepository.save(expense);

        return ExpenseMapper
                .toExpenseResponseDto(saved);
    }

    /* =====================================================
       UPDATE
     ===================================================== */

    public ExpenseResponseDto updateById(
            Long id,
            ExpenseRequestDto dto) {

        User currentUser = getCurrentUserEntity();

        Expense existing =
                expenseRepository.findById(id)
                        .orElseThrow(() ->
                                new ExpenseNotFoundException(
                                        "Expense not found"));

        checkExpenseAccess(
                existing,
                currentUser,
                "You are not allowed to update this expense"
        );

        existing.setTitle(dto.getTitle());
        existing.setCategory(dto.getCategory());
        existing.setAmount(dto.getAmount());

        Expense updated =
                expenseRepository.save(existing);

        return ExpenseMapper
                .toExpenseResponseDto(updated);
    }

    /* =====================================================
       DELETE
     ===================================================== */

    public void deleteById(Long id) {

        User currentUser = getCurrentUserEntity();

        Expense existing =
                expenseRepository.findById(id)
                        .orElseThrow(() ->
                                new ExpenseNotFoundException(
                                        "Expense not found " + id));

        checkExpenseAccess(
                existing,
                currentUser,
                "You are not allowed to delete this expense"
        );

        expenseRepository.delete(existing);
    }

    /* =====================================================
       ANALYTICS
     ===================================================== */

    public Map<String, Double> getTotalAmountByCategory() {

        User currentUser = getCurrentUserEntity();

        List<Expense> expenses =
                expenseRepository.findByUser(currentUser);

        Map<String, Double> totals =
                new HashMap<>();

        for (Expense expense : expenses) {

            totals.merge(
                    expense.getCategory(),
                    expense.getAmount(),
                    Double::sum
            );
        }

        return totals;
    }

    public long getExpenseCount() {

        User currentUser = getCurrentUserEntity();

        return expenseRepository
                .findByUser(currentUser)
                .size();
    }
}