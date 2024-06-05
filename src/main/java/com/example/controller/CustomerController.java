package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.example.model.Customer;
import com.example.service.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    
    
    @PostMapping("/register")
    public Customer registerCustomer(@RequestBody Customer customer) {
        return customerService.registerCustomer(customer);
    }

    @GetMapping("/{username}")
    public Customer getCustomerByUsername(@PathVariable String username) {
        return customerService.getCustomerByUsername(username);
    }

    @PostMapping("/login")
    public boolean login(@RequestParam String username, @RequestParam String password) {
        return customerService.login(username, password);
    }

    @GetMapping("/{username}/balance")
    public double getBalance(@PathVariable String username) {
        return customerService.getBalance(username);
    }

    @PostMapping("/{username}/deposit")
    public void deposit(@PathVariable String username, @RequestParam double amount) {
        customerService.deposit(username, amount);
    }

    @PostMapping("/{username}/withdraw")
    public void withdraw(@PathVariable String username, @RequestParam double amount) {
        customerService.withdraw(username, amount);
    }

    // Add other endpoints as needed
}
