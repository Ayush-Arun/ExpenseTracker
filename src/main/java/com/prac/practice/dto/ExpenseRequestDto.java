package com.prac.practice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class ExpenseRequestDto {

    @NotBlank(message="Category must not be empty")
    private String category;

    @NotBlank(message = "Title must not be empty")
    private String title;

    @Min(value=1,message = "Amount must be atleast 1")
    private double amount;

    public ExpenseRequestDto(String category, String title, double amount) {
        this.category = category;
        this.title = title;
        this.amount = amount;
    }

    public ExpenseRequestDto() {
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
