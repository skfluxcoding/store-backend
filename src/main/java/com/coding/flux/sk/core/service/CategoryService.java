package com.coding.flux.sk.core.service;

import com.coding.flux.sk.core.dto.CategoryRequest;
import com.coding.flux.sk.core.dto.CategoryResponse;
import net.sf.jasperreports.engine.JRException;

import java.util.List;

public interface CategoryService {

    public List<CategoryResponse> findAll();

    public CategoryResponse create(CategoryRequest dto);

    public CategoryResponse update(String id, CategoryRequest dto);

    public CategoryResponse findById(String id);

    public void deleteById(String id);

    // reports
    public byte[] generateReportGetAllCategory() throws JRException;

}
