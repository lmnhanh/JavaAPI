package com.demo_api.service;

import com.demo_api.entity.ProductDetailEntity;
import com.demo_api.entity.ProductEntity;
import com.demo_api.entity.RoleEntity;
import com.demo_api.entity.UserEntity;
import com.demo_api.repository.ProductDetailRepository;
import com.demo_api.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    ProductRepository repository;
    @Autowired ProductDetailRepository productDetailRepository;

    public ProductEntity get(Long id){
        return repository.findById(id).orElse(new ProductEntity());
    }

    public Page<ProductEntity> getAll(int status, String name, Pageable pageable){
        if(name.compareTo("*") == 0 && status == -1)
            return repository.findAll(pageable);
        if(name.compareTo("*") != 0 && status != -1)
            return repository.findByStatusAndNameContainingIgnoreCase(status, name, pageable);
        if(status != -1)
            return repository.findByStatus(status, pageable);
        return repository.findByNameContainingIgnoreCase(name,pageable);
    }

    public ProductEntity update(ProductEntity oldProduct, ProductEntity newProduct){
        oldProduct.setName(newProduct.getName());
        return repository.save(oldProduct);
    }

    public String delete(Long id){
        if(repository.findById(id).isPresent()){
            ProductEntity product = repository.findById(id).get();
            product.setStatus(0);
            for(ProductDetailEntity detail : product.getDetails()){
                detail.setStatus(0);
                productDetailRepository.save(detail);
            }
            repository.save(product);
            return "DELETED";
        }
        return "ERROR";
    }
    public ProductEntity save(ProductEntity product){
        return repository.save(product);
    }
}
