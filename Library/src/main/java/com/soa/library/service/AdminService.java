package com.soa.library.service;

import com.soa.library.dto.AdminDto;
import com.soa.library.model.Admin;

public interface AdminService {
    Admin findByUsername(String username);
    Admin save(AdminDto adminDto);
}
