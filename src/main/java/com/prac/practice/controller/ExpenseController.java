package com.prac.practice.controller;

import com.prac.practice.dto.ExpenseRequestDto;
import com.prac.practice.dto.ExpenseResponseDto;
import com.prac.practice.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ExpenseResponseDto> createExpense(@Valid @RequestBody ExpenseRequestDto dto) {
        ExpenseResponseDto expenseResponseDto = expenseService.save(dto);
        return new ResponseEntity<>(expenseResponseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ExpenseResponseDto>> findAllExpenses() {
        return new ResponseEntity<>(expenseService.findAll(),HttpStatus.OK);
    }

    @GetMapping("/id/{myid}")
    public ResponseEntity<ExpenseResponseDto> findExpenseById(@PathVariable("myid") Long myid){
        return new ResponseEntity<>(expenseService.findById(myid),HttpStatus.OK);
    }

    @PutMapping("/id/{myid}")
    public ResponseEntity<ExpenseResponseDto> updateExpense(@PathVariable("myid") Long myid,@Valid @RequestBody ExpenseRequestDto dto){
        return new ResponseEntity<>(expenseService.updateById(myid,dto),HttpStatus.OK);
    }

    @DeleteMapping("/id/{myid}")
    public ResponseEntity<Void> deleteExpenseById(@PathVariable("myid") Long myid){
        expenseService.deleteById(myid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
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
