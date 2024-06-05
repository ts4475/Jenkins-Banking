package com.example.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.model.Customer;
import com.example.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setName("John Doe");
        customer.setUsername("johndoe");
        customer.setPassword("password");
        customer.setBalance(100.0);
    }

    @Test
    void testRegisterCustomer() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String customerJson = objectMapper.writeValueAsString(customer);

        mockMvc.perform(post("/customer/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(customerJson))
                .andExpect(status().isOk());

        verify(customerService).registerCustomer(customer);
    }

    @Test
    void testGetCustomerByUsername() throws Exception {
        when(customerService.getCustomerByUsername("johndoe")).thenReturn(customer);

        mockMvc.perform(get("/customer/johndoe"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("johndoe"))
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    void testLogin() throws Exception {
        when(customerService.login("johndoe", "password")).thenReturn(true);

        mockMvc.perform(post("/customer/login")
                .param("username", "johndoe")
                .param("password", "password")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
    }

    @Test
    void testGetBalance() throws Exception {
        when(customerService.getBalance("johndoe")).thenReturn(100.0);

        mockMvc.perform(get("/customer/johndoe/balance"))
                .andExpect(status().isOk())
                .andExpect(content().string("100.0"));
    }

    @Test
    void testDeposit() throws Exception {
        mockMvc.perform(post("/customer/johndoe/deposit")
                .param("amount", "50.0"))
                .andExpect(status().isOk());

        verify(customerService).deposit("johndoe", 50.0);
    }

    @Test
    void testWithdraw() throws Exception {
        mockMvc.perform(post("/customer/johndoe/withdraw")
                .param("amount", "50.0"))
                .andExpect(status().isOk());

        verify(customerService).withdraw("johndoe", 50.0);
    }
}
