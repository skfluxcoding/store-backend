package com.coding.flux.sk.core.controller;

import com.coding.flux.sk.core.dto.*;
import com.coding.flux.sk.core.service.CategoryService;
import jakarta.validation.Valid;
import net.sf.jasperreports.engine.JRException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryRestController {

    private final CategoryService categoryService;

    public CategoryRestController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<CategoryGetAll>> findAll() {
        var categories = categoryService.findAll();
        return ResponseEntity.ok(categories);
    }

    @PostMapping
    public ResponseEntity<CategoryCreated> create(@Valid @RequestBody CategoryCreate dto) {
        var category = categoryService.create(dto);
        var location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(category.categoryId()).toUri();
        return ResponseEntity.created(location).body(category);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> findById(@PathVariable String id) {
        var category = categoryService.findById(id);
        return ResponseEntity.ok(category);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryUpdated> update(@PathVariable String id,
                                                   @Valid @RequestBody CategoryUpdate dto) {
        var category = categoryService.update(id, dto);
        return ResponseEntity.ok(category);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        categoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/reports/generateReportGetAllCategory")
    public ResponseEntity<byte[]> generateReportGetAllCategory() throws JRException {
        byte[] pdf = categoryService.generateReportGetAllCategory();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=categories.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

}
