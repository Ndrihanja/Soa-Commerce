package com.soa.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class CategoryDto {
    private Long categoryId;
    private String categoryName;
    private Long numberOfProduct;
}
