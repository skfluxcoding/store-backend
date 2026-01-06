package com.coding.flux.sk.core.service;

import com.coding.flux.sk.core.dto.*;
import net.sf.jasperreports.engine.JRException;

import java.util.List;

public interface CategoryService {

    List<CategoryFindAll> findAll();

    CategoryCreated create(CategoryCreate dto);

    CategoryUpdated update(String id, CategoryUpdate dto);

    CategoryFindById findById(String id);

    void deleteById(String id);

    // reports
    byte[] generateReportGetAllCategory() throws JRException;

}
