package com.prac.practice.controller;

import com.prac.practice.dto.ExpenseRequestDto;
import com.prac.practice.dto.ExpenseResponseDto;
import com.prac.practice.dto.PagedResponse;
import com.prac.practice.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public ResponseEntity<PagedResponse<ExpenseResponseDto>>
    findAllExpenses(@RequestParam(defaultValue = "0")  int page,
                    @RequestParam(defaultValue = "5") int size,
                    @RequestParam(defaultValue = "id") String sortBy,
                    @RequestParam(defaultValue = "asc") String direction)
    {
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size,sort);

        Page<ExpenseResponseDto> resultPage = expenseService.findAll(pageable);

        PagedResponse<ExpenseResponseDto> response = new PagedResponse<>(
                resultPage.getContent(),
                resultPage.getNumber(),
                resultPage.getSize(),
                resultPage.getTotalElements(),
                resultPage.getTotalPages(),
                resultPage.hasNext(),
                resultPage.hasPrevious()
        );
        return ResponseEntity.ok(response);

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
