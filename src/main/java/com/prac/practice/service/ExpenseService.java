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
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;

    @Autowired
    public ExpenseService(ExpenseRepository expenseRepository, UserRepository userRepository) {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
    }

    private User getCurrentUserEntity() {
        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public ExpenseResponseDto findById(Long id) {
        User currentUser = getCurrentUserEntity();

        Expense e = expenseRepository.findById(id)
                .orElseThrow(() -> new ExpenseNotFoundException("Expense not found " + id));

        if (!e.getUser().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException("You are not allowed to access this expense");
        }

        return ExpenseMapper.toExpenseResponseDto(e);
    }



    public Page<ExpenseResponseDto> findAll(Pageable pageable) {
        User currentUser = getCurrentUserEntity();
        return expenseRepository.findByUser(currentUser, pageable)
                .map(ExpenseMapper::toExpenseResponseDto);
    }

    public ExpenseResponseDto save(ExpenseRequestDto expenseRequestDto) {
        User currentUser = getCurrentUserEntity();

        Expense expense = ExpenseMapper.toEntity(expenseRequestDto);
        expense.setUser(currentUser); // 🔑 attach owner

        Expense saved = expenseRepository.save(expense);
        return ExpenseMapper.toExpenseResponseDto(saved);
    }


    public void deleteById(Long id) {
        User currentUser = getCurrentUserEntity();

        Expense existing = expenseRepository.findById(id)
                .orElseThrow(() -> new ExpenseNotFoundException("Expense not found " + id));

        if (!existing.getUser().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException("You are not allowed to delete this expense");
        }

        expenseRepository.delete(existing);
    }


    public ExpenseResponseDto updateById(Long id, ExpenseRequestDto expenseRequestDto) {
        User currentUser = getCurrentUserEntity();

        Expense existing = expenseRepository.findById(id)
                .orElseThrow(() -> new ExpenseNotFoundException("Expense not found"));

        if (!existing.getUser().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException("You are not allowed to update this expense");
        }

        existing.setTitle(expenseRequestDto.getTitle());
        existing.setCategory(expenseRequestDto.getCategory());
        existing.setAmount(expenseRequestDto.getAmount());

        Expense updated = expenseRepository.save(existing);

        return ExpenseMapper.toExpenseResponseDto(updated);
    }


    public Map<String, Double> getTotalAmountByCategory() {
        User currentUser = getCurrentUserEntity();
        List<Expense> expenses = expenseRepository.findByUser(currentUser);

        Map<String, Double> totals = new HashMap<>();

        for (Expense expense : expenses) {
            String category = expense.getCategory();
            Double amount = expense.getAmount();

            if (totals.containsKey(category)) {
                Double currentTotal = totals.get(category);
                totals.put(category, currentTotal + amount);
            } else {
                totals.put(category, amount);
            }
        }

        return totals;
    }


    public long getExpenseCount(){
        User currentUser = getCurrentUserEntity();
        return expenseRepository.findByUser(currentUser).size();
    }


}
