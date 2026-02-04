package com.prac.practice.service;

import com.prac.practice.entity.Expense;
import com.prac.practice.exception.ExpenseNotFoundException;
import com.prac.practice.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class ExpenseService {
    private final ExpenseRepository expenseRepository;

    @Autowired
    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public Expense findById(Long id) {
        return expenseRepository.findById(id).orElseThrow(()->new ExpenseNotFoundException("Expense not found "+id));
    }

    public List<Expense> findAll(){
        return expenseRepository.findAll();
    }

    public Expense save(Expense expense) {
        return expenseRepository.save(expense);
    }

    public void deleteById(Long id) {
        expenseRepository.deleteById(id);
    }
    public Expense updateById(Long id, Expense newExpense) {

        Expense existing = expenseRepository.findById(id)
                .orElseThrow(() -> new ExpenseNotFoundException("Expense not found"));

        existing.setTitle(newExpense.getTitle());
        existing.setCategory(newExpense.getCategory());
        existing.setAmount(newExpense.getAmount());

        return expenseRepository.save(existing);
    }

    public Map<String,Double> getTotalAmountByCategory(){
        List<Expense> expenses = expenseRepository.findAll();
        Map<String,Double> totals = new HashMap<>();
        for(Expense expense : expenses){
            totals.merge(expense.getCategory(),expense.getAmount(),Double::sum);
        }
        return totals;
    }

    public long getExpenseCount(){
        return expenseRepository.count();
    }

}
