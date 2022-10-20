package com.demo_api.controller;

import com.demo_api.assembler.ProductDetailModelAssembler;
import com.demo_api.entity.ProductDetailEntity;
import com.demo_api.model.ProductDetail;
import com.demo_api.service.ProductDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/details")
public class ProductDetailsController {
    @Autowired
    PagedResourcesAssembler<ProductDetailEntity> pagedResourcesAssembler;
    @Autowired
    ProductDetailModelAssembler assembler;
    @Autowired
    ProductDetailService detailService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<EntityModel<ProductDetail>> getOne(@PathVariable Long id) {
        ProductDetailEntity detail = detailService.get(id);
        if(detail.getId() == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(assembler.toModel(detail), HttpStatus.OK);
    }

    //Get all details of provided product
    //stock: -1: Các chi tiết hết hàng, 1: Các chi tiết còn hàng (mặc định), 0: Lấy tất cả
    @GetMapping()
    public ResponseEntity<CollectionModel<EntityModel<ProductDetail>>> getAll(@RequestParam Long product,
                                                             @RequestParam(defaultValue = "*") String size,
                                                             @RequestParam(defaultValue = "1") int stock) {
        List<EntityModel<ProductDetail>> details = detailService.getAll(product, size, stock).stream()
                .map(assembler::toModel).collect(Collectors.toList());
        return new ResponseEntity<>(
                CollectionModel.of(details,linkTo(methodOn(ProductDetailsController.class).getAll(product, size,stock)).withSelfRel()),
                HttpStatus.OK);
    }

    @GetMapping(value = "/product/{id}")
    public ResponseEntity<CollectionModel<EntityModel<ProductDetail>>> getByProductId(@PathVariable Long id) {
        List<EntityModel<ProductDetail>> details = detailService.getByProductId(id).stream()
                .map(assembler::toModel).collect(Collectors.toList());
        return new ResponseEntity<>(
                CollectionModel.of(details, linkTo(methodOn(ProductDetailsController.class).getByProductId(id)).withSelfRel()),
                HttpStatus.OK
        );
    }

    //Add new
    //Test with:
    //In request body: {"size": "40","color": "Đen","price": 10000,"stock": 22,"product": {"id":1}}
    //In request body: {"size": "38","color": "Đen", "material": "Vải","price": 10000,"stock": 10,"product": {"id":1}}
    //In request body: {"size": "41","color": "Trắng","price": 10000,"stock": 22,"product": {"id":1}}
    //Sản phẩm id = 1 sẽ có 3 size là 40, 38 và 41
    @PostMapping()
    public ResponseEntity<EntityModel<ProductDetail>> addProductDetail(@RequestBody ProductDetailEntity newDetail){
        try{
            return new ResponseEntity<>(
                    assembler.toModel(detailService.save(newDetail)),
                    HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //Update
    //Test with: http://localhost:8060/details/1/update
    //In request body: {"product":{"id":1}}
    //In request body: {"size":"45"}
    //In request body: {"price":10000}
    //In request body: {"stock":102}
    //In request body: {"size":"33", "color":"Black"}
    //In request body: {"size":"33", "color":"Black", "material": "Paper"}
    @PutMapping(value = "/{id}/update")
    public ResponseEntity<EntityModel<ProductDetail>> updateProductDetail(@RequestBody ProductDetailEntity newDetail, @PathVariable Long id) {
        ProductDetailEntity detail = detailService.get(id);
        if(detail.getId() == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        try{
            detailService.update(detail, newDetail);
            return new ResponseEntity<>(assembler.toModel(detail), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //Delete
    //Test with: http://localhost:8060/details/1/delete
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteProductDetail(@PathVariable Long id) {
        if(detailService.get(id).getId() == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        detailService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
