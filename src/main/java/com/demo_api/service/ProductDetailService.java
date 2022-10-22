package com.demo_api.service;

import com.demo_api.entity.ProductDetailEntity;
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
        if(size.compareTo("*") == 0) {
            if (stock > 0)
                return repository.findInStock(product);
            if (stock == 0)
                return repository.findOutStock(product);
            return repository.findByProductId(product);
        } else{
            if(stock > 0)
                return repository.findInStockBySize(size, product);
            if(stock == 0)
                return repository.findOutStockBySize(size, product);
            return repository.findBySizeAndProductId(size, product);
        }
    }

    public void update (ProductDetailEntity oldDetail, ProductDetailEntity newDetail){
        oldDetail.setSize(newDetail.getSize());
        oldDetail.setOrigin(newDetail.getOrigin());
        oldDetail.setGender(newDetail.getGender());
        oldDetail.setImage(newDetail.getImage());
        oldDetail.setStock(newDetail.getStock());
        oldDetail.setPrice(newDetail.getPrice());
        oldDetail.setProduct(newDetail.getProduct());
        repository.save(oldDetail);
    }

    public String delete(Long id){
        if(repository.findById(id).isPresent()){
            ProductDetailEntity detail = repository.findById(id).get();
            detail.setStatus(0);
            repository.save(detail);
            return "DELETED";
        }
        return "ERROR";
    }

    public ProductDetailEntity save(ProductDetailEntity detail){
        return repository.save(detail);
    }
}
