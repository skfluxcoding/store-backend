package com.coding.flux.sk.core.service.impl;

import com.coding.flux.sk.common.exception.AlreadyExistsException;
import com.coding.flux.sk.common.exception.NotFoundException;
import com.coding.flux.sk.core.dto.*;
import com.coding.flux.sk.core.mapper.CategoryMapper;
import com.coding.flux.sk.core.repository.CategoryRepository;
import com.coding.flux.sk.core.service.CategoryService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryFindAll> findAll() {
        return categoryRepository.findAllByEnabledTrueOrderByCreatedAtDesc()
                .stream()
                .map(CategoryMapper::toFindAll)
                .toList();
    }

    @Override
    public CategoryCreated create(CategoryCreate dto) {
        if (categoryRepository.existsByEnabledTrueAndNameIgnoreCase(dto.name())) {
            throw new AlreadyExistsException("Name " + dto.name() + " already exists");
        }
        var category = CategoryMapper.toCreate(dto);
        var saved = categoryRepository.save(category);
        return CategoryMapper.toCreated(saved);
    }

    @Override
    public CategoryUpdated update(String id, CategoryUpdate dto) {
        var category = categoryRepository.findByEnabledTrueAndIdCategory(id)
                .orElseThrow(() -> new NotFoundException("Category not found"));
        if (categoryRepository.existsByEnabledTrueAndNameIgnoreCaseAndIdCategoryNot(dto.name(), id)) {
            throw new AlreadyExistsException("Name " + dto.name() + " already exists");
        }
        var update = CategoryMapper.toUpdate(category, dto);
        var saved = categoryRepository.save(update);
        return CategoryMapper.toUpdated(saved);
    }

    @Override
    public CategoryFindById findById(String id) {
        var category = categoryRepository.findByEnabledTrueAndIdCategory(id)
                .orElseThrow(() -> new NotFoundException("Category " + id + " not found"));
        return CategoryMapper.toFindById(category);
    }

    @Override
    public void deleteById(String id) {
        var category = categoryRepository.findByEnabledTrueAndIdCategory(id)
                .orElseThrow(() -> new NotFoundException("Category not found"));
        CategoryMapper.toDeleteById(category);
        categoryRepository.delete(category);
    }

    @Override
    public byte[] generateReportGetAllCategory() throws JRException {
        var data = categoryRepository.findAllByEnabledTrueOrderByCreatedAtDesc()
                .stream()
                .map(CategoryMapper::toRepCategoryGetAll)
                .toList();
        InputStream jasperStream = getClass().getResourceAsStream("/reports/GetAllCategory.jasper");
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(data);
        HashMap<String, Object> parameters = new HashMap<>();
        return JasperRunManager.runReportToPdf(jasperReport, parameters, dataSource);
    }
}
