package com.product.catalog.services;

import com.product.catalog.dto.CategoryDTO;
import com.product.catalog.entities.Category;
import com.product.catalog.services.exceptions.DatabaseException;
import com.product.catalog.services.exceptions.ResourceNotFoundException;
import com.product.catalog.repositories.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository repository;

    @Transactional(readOnly = true)
    public Page<CategoryDTO> findAllPaged(PageRequest pageRequest){
        Page<Category> list = repository.findAll(pageRequest);

        return list.map(x -> new CategoryDTO(x));
    }
    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) {
        Optional<Category> obj = repository.findById(id);
        Category entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity Not Found!"));
        return new CategoryDTO(entity);
    }
    @Transactional
    public CategoryDTO insert(CategoryDTO dto) {
        Category entity = new Category();
        entity.setName(dto.getName());
        entity = repository.save(entity);
        return new CategoryDTO(entity);
    }
    @Transactional
    public CategoryDTO update(Long id, CategoryDTO dto) {
        try {
            Category entity = repository.getOne(id);
            entity.setName(dto.getName());
            entity = repository.save(entity);
            return new CategoryDTO(entity);
        }
        catch(EntityNotFoundException e){
            throw new ResourceNotFoundException("Id not found! " + id);
        }
    }

    public void delete(Long id) {
        try {
            repository.deleteById(id);
        }
        catch(EmptyResultDataAccessException e){
            throw new ResourceNotFoundException("Id not found to delete!" + id);
        }
        catch(DataIntegrityViolationException e){
            throw new DatabaseException("Integrity Violation, Its not possible to delete!");
        }
    }
}