package com.coding.flux.sk.core.service.impl;

import com.coding.flux.sk.common.exception.AlreadyExistsException;
import com.coding.flux.sk.common.exception.NotFoundException;
import com.coding.flux.sk.core.dto.CategoryRequest;
import com.coding.flux.sk.core.dto.CategoryResponse;
import com.coding.flux.sk.core.dto.RepCategoryGetAll;
import com.coding.flux.sk.core.entity.Category;
import com.coding.flux.sk.core.repository.CategoryMongoRepository;
import com.coding.flux.sk.core.service.CategoryService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryMongoRepository categoryMongoRepository;

    public CategoryServiceImpl(CategoryMongoRepository categoryMongoRepository) {
        this.categoryMongoRepository = categoryMongoRepository;
    }

    @Override
    public List<CategoryResponse> findAll() {
        return categoryMongoRepository.findAllByEnabledTrueOrderByCreatedAtDesc()
                .stream()
                .map(item -> new CategoryResponse(
                        item.getIdCategory(),
                        item.getName(),
                        item.getDescription()))
                .toList();
    }

    @Override
    public CategoryResponse create(CategoryRequest dto) {
        if (categoryMongoRepository.existsByEnabledTrueAndNameIgnoreCase(dto.name())) {
            throw new AlreadyExistsException("Name " + dto.name() + " already exists");
        }
        var category = Category.builder()
                .name(dto.name())
                .description(dto.description())
                .enabled(true)
                .createdAt(LocalDateTime.now())
                .build();
        var saved = categoryMongoRepository.save(category);
        return new CategoryResponse(
                saved.getIdCategory(),
                saved.getName(),
                saved.getDescription()
        );
    }

    @Override
    public CategoryResponse update(String id, CategoryRequest dto) {
        var category = categoryMongoRepository.findByEnabledTrueAndIdCategory(id)
                .orElseThrow(() -> new NotFoundException("Category not found"));
        if (categoryMongoRepository.existsByEnabledTrueAndNameIgnoreCaseAndIdCategoryNot(dto.name(), id)) {
            throw new AlreadyExistsException("Name " + dto.name() + " already exists");
        }
        category.setName(dto.name());
        category.setDescription(dto.description());
        category.setUpdatedAt(LocalDateTime.now());
        var saved = categoryMongoRepository.save(category);
        return new CategoryResponse(
                saved.getIdCategory(),
                saved.getName(),
                saved.getDescription()
        );
    }

    @Override
    public CategoryResponse findById(String id) {
        var category = categoryMongoRepository.findByEnabledTrueAndIdCategory(id)
                .orElseThrow(() -> new NotFoundException("Category " + id + " not found"));
        return new CategoryResponse(
                category.getIdCategory(),
                category.getName(),
                category.getDescription()
        );
    }

    @Override
    public void deleteById(String id) {
        var category = categoryMongoRepository.findByEnabledTrueAndIdCategory(id)
                .orElseThrow(() -> new NotFoundException("Category not found"));
        category.setEnabled(false);
        category.setUpdatedAt(LocalDateTime.now());
        categoryMongoRepository.delete(category);
    }

    @Override
    public byte[] generateReportGetAllCategory() throws JRException {
        var data = categoryMongoRepository.findAllByEnabledTrueOrderByCreatedAtDesc()
                .stream()
                .map(item -> new RepCategoryGetAll(
                        item.getIdCategory(),
                        item.getName(),
                        item.getDescription()))
                .toList();
        InputStream jasperStream = getClass().getResourceAsStream("/reports/GetAllCategory.jasper");
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(data);
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("REPORT_TITLE", "Listado de Categorías");
        return JasperRunManager.runReportToPdf(jasperReport, parameters, dataSource);
    }
}
