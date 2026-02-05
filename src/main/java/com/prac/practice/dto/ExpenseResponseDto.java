package com.prac.practice.dto;

public class ExpenseResponseDto {
    private long id;
    private String category;
    private String title;
    private double amount;

    public ExpenseResponseDto(long id, String category, String title, double amount) {
        this.id = id;
        this.category = category;
        this.title = title;
        this.amount = amount;
    }

    public ExpenseResponseDto() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
