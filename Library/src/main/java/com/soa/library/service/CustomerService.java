package com.soa.library.service;

import com.soa.library.dto.CustomerDto;
import com.soa.library.model.Customer;

public interface CustomerService {

    CustomerDto save(CustomerDto customerDto);

    Customer findByUsername(String username);

    Customer saveInfor(Customer customer);

    long count();
}
