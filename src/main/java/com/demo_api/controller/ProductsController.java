package com.demo_api.controller;

import com.demo_api.assembler.ProductModelAssembler;
import com.demo_api.entity.ProductDetailEntity;
import com.demo_api.entity.ProductEntity;
import com.demo_api.entity.RoleEntity;
import com.demo_api.model.Product;
import com.demo_api.model.Role;
import com.demo_api.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/products")
public class ProductsController {
    @Autowired
    ProductService productService;
    @Autowired
    ProductModelAssembler assembler;
    @Autowired
    PagedResourcesAssembler<ProductEntity> pagedResourcesAssembler;
    @GetMapping(value = "/{id}")
    public ResponseEntity<EntityModel<Product>> getOne(@PathVariable Long id) {
        ProductEntity product = productService.get(id);
        if(product.getId() == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(assembler.toModel(product), HttpStatus.OK);
    }

    //Get all with paging
    //Test with: http://localhost:8060/products?name=nike&status=1&page=0&size=2&sort=id,asc
    //Test with: http://localhost:8060/products?status=0&page=0&size=2&sort=id,asc
    //Test with: http://localhost:8060/products?page=0&size=2&sort=id,asc
    @GetMapping()
    public ResponseEntity<PagedModel<EntityModel<Product>>> getAll(@RequestParam(defaultValue = "-1") int status,
                                                                   @RequestParam(defaultValue = "*") String name,
                                                                   Pageable pageable) {
        Page<ProductEntity> products = productService.getAll(status, name, pageable);
        PagedModel<EntityModel<Product>> page = pagedResourcesAssembler.toModel(products, assembler);
        page.add(linkTo(methodOn(ProductsController.class).getAll(status,name,pageable)).withSelfRel());
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    //Add new
    //Test with: http://localhost:8060/products
    //In request body: {"name": "VANS"}
    @PostMapping()
    public ResponseEntity<EntityModel<Product>> addProduct(@RequestBody ProductEntity newProduct){
        try{
            return new ResponseEntity<>(
                    assembler.toModel(productService.save(newProduct)),
                    HttpStatus.CREATED
            );
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //Update
    //Test with: http://localhost:8060/products/update/1
    //In request body:
    //To set role with no privileges: {"name": "NIKE"}
    @PutMapping(value = "/update/{id}")
    public ResponseEntity<EntityModel<Product>> updateProduct(@RequestBody ProductEntity newProduct, @PathVariable Long id) {
        ProductEntity product = productService.get(id);
        if(product.getId() == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        productService.update(product, newProduct);
        return new ResponseEntity<>(assembler.toModel(product), HttpStatus.OK);
    }

    //Delete
    //Test with: http://localhost:8060/products/delete/1
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        if(productService.get(id).getId() == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        productService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
