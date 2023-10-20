package com.product.catalog.repositories;

import com.product.catalog.entities.Product;
import com.product.catalog.services.ProductService;
import com.product.catalog.services.exceptions.ResourceNotFoundException;
import com.product.catalog.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

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
    public void findByIdShouldReturnNonEmptyOptionalWhenIdExists(){
        Optional<Product> result = repository.findById(existingId);
        Assertions.assertTrue(result.isPresent());
    }

    @Test
    public void findByIdShouldReturnEmptyOptionalWhenIdDoesNotExists(){
        Optional<Product> result = repository.findById(noExistingId);
        Assertions.assertTrue(result.isEmpty());
    }

  /*  @Test
    public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists(){
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.delete(noExistingId);
        });

        Mockito.verify(repository, Mockito.times(1)).deleteById(noExistingId);
    }*/

    @Test
    public void saveShouldPersistWithAutoincrementWhenIdIsNull(){
        Product product = Factory.createProduct();
        product.setId(null);
        product = repository.save(product);

        Assertions.assertNotNull(product.getId());
        Assertions.assertEquals(countTotalProducts + 1, product.getId());
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

   /* @Test
    public void deleteShouldDoNothingWhenIdExists(){
        Assertions.assertDoesNotThrow(() -> {
            service.delete(existingId);
        });

        Mockito.verify(repository, Mockito.times(1)).deleteById(existingId);
    }*/

}
