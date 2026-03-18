package com.devjoliveira.appointmentmanagementapi.service;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devjoliveira.appointmentmanagementapi.domain.Product;
import com.devjoliveira.appointmentmanagementapi.dto.ProductDTO;
import com.devjoliveira.appointmentmanagementapi.dto.ProductMinDTO;
import com.devjoliveira.appointmentmanagementapi.repository.ProductRepository;
import com.devjoliveira.appointmentmanagementapi.service.exceptions.DatabaseException;
import com.devjoliveira.appointmentmanagementapi.service.exceptions.ResourceNotFoundException;

@Service
public class ProductService {

  private final ProductRepository productRepository;

  public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Transactional(readOnly = true)
  public Page<ProductDTO> findAllPaged(Pageable pageable) {
    Page<Product> prods = productRepository.findAll(pageable);
    return prods.map(ProductDTO::new);
  }

  @Transactional(readOnly = true)
  public ProductDTO findById(UUID id) {
    return productRepository.findById(id).map(ProductDTO::new).orElseThrow(
        () -> new ResourceNotFoundException("Product not found with id: " + id));
  }

  @Transactional(readOnly = true)
  public List<ProductDTO> findByName(String name) {
    var products = productRepository.findByNameContainingIgnoreCase(name);

    if (products == null || products.isEmpty()) {
      throw new ResourceNotFoundException("Product not found with name: " + name);
    }

    return products.stream().map(ProductDTO::new).toList();
  }

  @Transactional
  public ProductDTO save(ProductMinDTO request) {

    productRepository.findByName(request.name()).ifPresent(product -> {
      throw new DuplicateKeyException("Product with name " + product.getName() + " already exists");
    });

    Product entity = new Product();
    entity.setName(request.name());
    entity.setDurationInSeconds(Duration.ofSeconds(request.durationInSeconds()));
    entity.setPrice(request.price());
    entity.setPrice(request.price());

    var fromDB = productRepository.save(entity);
    return new ProductDTO(fromDB);
  }

  @Transactional
  public ProductDTO change(UUID id, ProductMinDTO request) {

    var fromDB = productRepository.findById(id).orElseThrow(
        () -> new ResourceNotFoundException("Product with id " + id + " not found"));

    if (!fromDB.getName().equals(request.name())) {
      productRepository.findByName(request.name()).ifPresent(product -> {
        throw new DuplicateKeyException("Product with name " + product.getName() + " already exists");
      });
    }
    fromDB.setName(request.name());
    fromDB.setDurationInSeconds(Duration.ofSeconds(request.durationInSeconds()));
    fromDB.setPrice(request.price());
    fromDB.setPrice(request.price());

    var updateProduct = productRepository.save(fromDB);
    return new ProductDTO(updateProduct);
  }

  @Transactional
  public void delete(UUID id) {

    var fromDB = productRepository.findById(id).orElseThrow(
        () -> new ResourceNotFoundException("Product with id " + id + " not found"));

    try {
      productRepository.delete(fromDB);
    } catch (DataIntegrityViolationException e) {
      throw new DatabaseException("Fail in reference integrity");
    }

  }

}
