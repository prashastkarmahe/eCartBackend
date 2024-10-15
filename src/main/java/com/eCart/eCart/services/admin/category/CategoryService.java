package com.eCart.eCart.services.admin.category;

import com.eCart.eCart.dto.CategoryDto;
import com.eCart.eCart.entity.Category;

import java.util.List;

public interface CategoryService {
    Category createCategory(CategoryDto categoryDto);
    List<Category> getAllCategory();
}
