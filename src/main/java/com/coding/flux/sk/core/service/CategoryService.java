package com.coding.flux.sk.core.service;

import com.coding.flux.sk.core.dto.*;
import net.sf.jasperreports.engine.JRException;

import java.util.List;

public interface CategoryService {

    public List<CategoryGetAll> findAll();

    public CategoryCreated create(CategoryCreate dto);

    public CategoryUpdated update(String id, CategoryUpdate dto);

    public CategoryResponse findById(String id);

    public void deleteById(String id);

    // reports
    public byte[] generateReportGetAllCategory() throws JRException;

}
