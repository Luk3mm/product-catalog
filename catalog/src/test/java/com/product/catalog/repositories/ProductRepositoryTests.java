package com.product.catalog.repositories;

import com.product.catalog.dto.ProductDTO;
import com.product.catalog.entities.Product;
import com.product.catalog.services.ProductService;
import com.product.catalog.services.exceptions.DatabaseException;
import com.product.catalog.services.exceptions.ResourceNotFoundException;
import com.product.catalog.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class ProductRepositoryTests {
    @Autowired
    private ProductRepository repository;
    private long existingId;
    private long noExistingId;
    private long countTotalProducts;

    @BeforeEach
    void setUp() throws Exception{
        existingId = 1L;
        noExistingId = 1000L;
        countTotalProducts = 25L;
    }

    @Test
    public void saveShouldPersistWithAutoincrementWhenIdIsNull(){
        Product product = Factory.createProduct();
        product.setId(null);
        product = repository.save(product);
        Optional<Product> result = repository.findById(product.getId());

        Assertions.assertNotNull(product.getId());
        Assertions.assertEquals(countTotalProducts + 1L, product.getId());
        Assertions.assertTrue(result.isPresent());
        Assertions.assertSame(result.get(), product);
    }

    @Test
    public void deleteShouldDeleteObjectWhenExists(){
        repository.deleteById(existingId);

        Optional<Product> result = repository.findById(existingId);
        Assertions.assertFalse(result.isPresent());
    }

    @Test
    public void deleteShouldThrowEmptyResultDataAccessExceptionWhenIdDoesNotExist(){
        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
           repository.deleteById(noExistingId);
        });
    }
}
