package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Customer;
import com.example.repository.CustomerRepository;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer registerCustomer(Customer customer) {
        // Perform any validation logic here
        return customerRepository.save(customer);
    }

    public Customer getCustomerByUsername(String username) {
        return customerRepository.findByUsername(username);
    }

    public boolean login(String username, String password) {
        Customer customer = customerRepository.findByUsername(username);
        return customer != null && customer.getPassword().equals(password);
    }

    public double getBalance(String username) {
        Customer customer = customerRepository.findByUsername(username);
        return customer != null ? customer.getBalance() : -1;
    }

    public void deposit(String username, double amount) {
        Customer customer = customerRepository.findByUsername(username);
        if (customer != null) {
            double currentBalance = customer.getBalance();
            customer.setBalance(currentBalance + amount);
            customerRepository.save(customer);
        }
    }

    public void withdraw(String username, double amount) {
        Customer customer = customerRepository.findByUsername(username);
        if (customer != null) {
            double currentBalance = customer.getBalance();
            if (currentBalance >= amount) {
                customer.setBalance(currentBalance - amount);
                customerRepository.save(customer);
            } else {
                // Handle insufficient balance
            }
        }
    }

    // Add other methods as needed
}