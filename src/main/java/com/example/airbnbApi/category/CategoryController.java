package com.example.airbnbApi.category;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
public class CategoryController {

        private final CategoryService categoryService;

        @GetMapping
        public ResponseEntity<List<Category>> getCategoryAll(){
            return ResponseEntity.ok().body(categoryService.getAllCategory());
        }




}
