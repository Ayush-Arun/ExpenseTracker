package com.prac.practice.controller;

import com.prac.practice.service.AdminService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/demote/{userId}")
    public String demote(@PathVariable Long userId) {

        adminService.demoteToUser(userId);

        return "User demoted to USER ✅";
    }

    @PostMapping("/promote/{userId}")
    public String promote(@PathVariable Long userId) {

        adminService.promoteToAdmin(userId);

        return "User promoted to ADMIN ✅";
    }
}