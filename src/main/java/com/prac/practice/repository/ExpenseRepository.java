package com.prac.practice.repository;

import com.prac.practice.entity.Expense;
import com.prac.practice.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    Page<Expense> findByUser(User user, Pageable pageable);

    List<Expense> findByUser(User user);
}
