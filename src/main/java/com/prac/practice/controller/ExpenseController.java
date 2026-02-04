package com.prac.practice.controller;

import com.prac.practice.entity.Expense;
import com.prac.practice.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;
    @Autowired
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping
    public Expense createExpense(@RequestBody Expense expense) {
        return expenseService.save(expense);
    }

    @GetMapping
    public List<Expense> findAll(){
        return expenseService.findAll();
    }

    @GetMapping("/id/{myid}")
    public Expense findExpenseById(@PathVariable("myid") Long myid){

        return expenseService.findById(myid);
    }

    @PutMapping("/id/{myid}")
    public Expense updateExpense(@PathVariable("myid") Long myid,@RequestBody Expense expense){
        return expenseService.updateById(myid,expense);
    }

    @DeleteMapping("/id/{myid}")
    public void deleteById(@PathVariable("myid") Long myid){
        expenseService.deleteById(myid);
    }

    @GetMapping("/count")
    public long getExpenseCount() {
        return expenseService.getExpenseCount();
    }

    @GetMapping("/total-by-category")
    public Map<String,Double> getTotalByCategory() {
        return expenseService.getTotalAmountByCategory();
    }

}
