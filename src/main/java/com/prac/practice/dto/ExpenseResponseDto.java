package com.prac.practice.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseResponseDto {
    private long id;
    private String category;
    private String title;
    private double amount;

}
