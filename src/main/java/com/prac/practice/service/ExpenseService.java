package com.prac.practice.service;

import com.prac.practice.dto.ExpenseRequestDto;
import com.prac.practice.dto.ExpenseResponseDto;
import com.prac.practice.entity.Expense;
import com.prac.practice.exception.ExpenseNotFoundException;
import com.prac.practice.mapper.ExpenseMapper;
import com.prac.practice.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class ExpenseService {
    private final ExpenseRepository expenseRepository;

    @Autowired
    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public ExpenseResponseDto findById(Long id) {
        Expense e= expenseRepository.findById(id).orElseThrow(()->new ExpenseNotFoundException("Expense not found "+id));
        return ExpenseMapper.toExpenseResponseDto(e);
    }

    public Page<ExpenseResponseDto> findAll(Pageable pageable) {
        return expenseRepository.findAll(pageable).
                map(expense -> ExpenseMapper.toExpenseResponseDto(expense));
    }

    public ExpenseResponseDto save(ExpenseRequestDto expenseRequestDto) {
        Expense expense = ExpenseMapper.toEntity(expenseRequestDto);
        Expense saved = expenseRepository.save(expense);
        return ExpenseMapper.toExpenseResponseDto(saved);
    }

    public void deleteById(Long id) {
        if(!expenseRepository.existsById(id)){
            throw new ExpenseNotFoundException("Expense not found "+id);
        }
        expenseRepository.deleteById(id);
    }

    public ExpenseResponseDto updateById(Long id, ExpenseRequestDto expenseRequestDto) {

        Expense existing = expenseRepository.findById(id)
                .orElseThrow(() -> new ExpenseNotFoundException("Expense not found"));

        existing.setTitle(expenseRequestDto.getTitle());
        existing.setCategory(expenseRequestDto.getCategory());
        existing.setAmount(expenseRequestDto.getAmount());

        Expense updated = expenseRepository.save(existing);

        return ExpenseMapper.toExpenseResponseDto(updated);
    }

    public Map<String, Double> getTotalAmountByCategory() {
        List<Expense> expenses = expenseRepository.findAll();
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
        return expenseRepository.count();
    }

}
