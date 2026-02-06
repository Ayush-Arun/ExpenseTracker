package com.prac.practice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseRequestDto {

    @NotBlank(message="Category must not be empty")
    private String category;

    @NotBlank(message = "Title must not be empty")
    private String title;

    @Min(value=1,message = "Amount must be atleast 1")
    private double amount;
}
