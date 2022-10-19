package com.demo_api.service;

import com.demo_api.entity.ProductDetailEntity;
import com.demo_api.entity.RoleEntity;
import com.demo_api.entity.UserEntity;
import com.demo_api.repository.ProductDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductDetailService {
    @Autowired
    ProductDetailRepository repository;

    public ProductDetailEntity get(Long id){
        return repository.findById(id).orElse(new ProductDetailEntity());
    }

    public List<ProductDetailEntity> getByProductId(Long id){
        return repository.findByProductId(id);
    }
    public List<ProductDetailEntity> getAll(Long product, String size, int stock){
        return repository.findAll();
    }

    public void update (ProductDetailEntity oldDetail, ProductDetailEntity newDetail){
        if(newDetail.getSize() != null)
            oldDetail.setSize(newDetail.getSize());
        if(newDetail.getColor() != null)
            oldDetail.setColor(newDetail.getColor());
        if(newDetail.getImage() != null)
            oldDetail.setImage(newDetail.getImage());
        if(newDetail.getMaterial() != null)
            oldDetail.setMaterial(newDetail.getMaterial());
        if(newDetail.getStock() != -1)
            oldDetail.setStock(newDetail.getStock());
        if(newDetail.getPrice() != -1L)
            oldDetail.setPrice(newDetail.getPrice());
        if(newDetail.getProduct() != null)
            oldDetail.setProduct(newDetail.getProduct());
        repository.save(oldDetail);
    }

    public void delete(Long id){
        if(repository.findById(id).isPresent()){
            ProductDetailEntity detail = repository.findById(id).get();
            detail.setStatus(0);
            repository.save(detail);
        }
    }

    public ProductDetailEntity save(ProductDetailEntity detail){
        return repository.save(detail);
    }
}
