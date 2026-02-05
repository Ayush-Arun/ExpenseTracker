package com.prac.practice.mapper;

import com.prac.practice.dto.ExpenseRequestDto;
import com.prac.practice.dto.ExpenseResponseDto;
import com.prac.practice.entity.Expense;

public class ExpenseMapper {
    public static Expense toEntity(ExpenseRequestDto expenseRequestDto) {
        Expense expense = new Expense();
        expense.setTitle(expenseRequestDto.getTitle());
        expense.setCategory(expenseRequestDto.getCategory());
        expense.setAmount(expenseRequestDto.getAmount());
        return expense;
    }

    public static ExpenseResponseDto toExpenseResponseDto(Expense expense) {
        ExpenseResponseDto expenseResponseDto = new ExpenseResponseDto(
                expense.getId(),
                expense.getCategory(),
                expense.getTitle(),
                expense.getAmount()
        );
        return expenseResponseDto;
    }
}
