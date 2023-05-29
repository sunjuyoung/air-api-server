package com.example.airbnbApi.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final String[] categories =
            {
                    "Beach"
                    ,"Windmills"
                    ,"Modern"
                    ,"Countryside"
                    ,"Pools"
                    ,"Islands"
                    ,"Lake"
                    ,"Skiing"
                    ,"Castles"
                    ,"Caves"
                    ,"Camping"
                    ,"Arctic"
                    ,"Desert"
                    ,"Barns"
                    ,"Lux"
    };



    public void createCategory(){
        for(String c : categories){
            Category newCategory = Category.builder()

                    .name(c)
                    .build();
            categoryRepository.save(newCategory);
        }

    }
    public List<Category> getAllCategory(){
        return categoryRepository.findAll();
    }
}
