package com.aitian.salary.service;

import org.springframework.web.multipart.MultipartFile;

public interface SalaryService {

    boolean batchImport(String name, MultipartFile file);
}
