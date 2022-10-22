package com.demo_api.controller;

import com.demo_api.assembler.ProductDetailModelAssembler;
import com.demo_api.entity.ProductDetailEntity;
import com.demo_api.model.MessageResponse;
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

    //Get one
    //Test with:  http://localhost:8060/details/1
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id) {
        ProductDetailEntity detail = detailService.get(id);
        if(detail.getId() == null){
            return new ResponseEntity<>(new MessageResponse("NOT FOUND product detail provided!"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(assembler.toModel(detail), HttpStatus.OK);
    }

    //Get all details of provided product
    //stock:   -1-> Tất cả
    //         1-> Các chi tiết còn hàng (mặc định)
    //         0: Lấy tất cả
    //Lấy các chi tiết còn hàng của sản phẩm có id "1":  http://localhost:8060/details/ofProduct/1
    //Lấy tất cả chi tiết của sản phẩm có id "1":  http://localhost:8060/details/ofProduct/1?stock=-1
    //Lấy các chi tiết còn hàng có size 38 của sản phẩm có id "1":  http://localhost:8060/details/ofProduct/1?size=38
    @GetMapping(value = "/ofProduct/{id}")
    public ResponseEntity<CollectionModel<EntityModel<ProductDetail>>> getAll(@PathVariable Long id,
                                                             @RequestParam(defaultValue = "*") String size,
                                                             @RequestParam(defaultValue = "1") int stock) {
        List<EntityModel<ProductDetail>> details = detailService.getAll(id, size, stock).stream()
                .map(assembler::toModel).collect(Collectors.toList());
        return new ResponseEntity<>(
                CollectionModel.of(details,linkTo(methodOn(ProductDetailsController.class).getAll(id, size,stock)).withSelfRel()),
                HttpStatus.OK);
    }

    //Add new
    //Test with:
    //In request body: {"size": "40","color": "Đen","price": 10000,"stock": 22,"product": {"id":1}}
    //In request body: {"size": "38","color": "Đen", "material": "Vải","price": 10000,"stock": 10,"product": {"id":1}}
    //In request body: {"size": "41","color": "Trắng","price": 10000,"stock": 22,"product": {"id":1}}
    //Sản phẩm id = 1 sẽ có 3 size là 40, 38 và 41
    @PostMapping()
    public ResponseEntity<?> addProductDetail(@RequestBody ProductDetailEntity newDetail){
        try{
            return new ResponseEntity<>(
                    assembler.toModel(detailService.save(newDetail)),
                    HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(new MessageResponse("ERROR when creating new product detail!"),HttpStatus.BAD_REQUEST);
        }
    }

    //Update
    //Update chi tiết có mã là "1": http://localhost:8060/details/1/update
    //Đặt lại sản phẩm của chi tiết: {"product":{"id":1}}
    //Đặt lại size: {"size":"45"}
    //Đặt lại giá: {"price":10000}
    //Đặt lại số lượng tồn: {"stock":102}
    //Đặt lại size và màu: {"size":"33", "color":"Black"}
    //Đặt lại size, màu và chất liệu: {"size":"33", "color":"Black", "material": "Paper"}
    @PutMapping(value = "/{id}/update")
    public ResponseEntity<?> updateProductDetail(@RequestBody ProductDetailEntity newDetail, @PathVariable Long id) {
        ProductDetailEntity detail = detailService.get(id);
        if(detail.getId() == null){
            return new ResponseEntity<>(new MessageResponse("NOT FOUND product detail provided!"), HttpStatus.NOT_FOUND);
        }
        try{
            detailService.update(detail, newDetail);
            return new ResponseEntity<>(assembler.toModel(detail), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new MessageResponse("ERROR when updating the product detail!"),HttpStatus.BAD_REQUEST);
        }
    }

    //Delete
    //Test with: http://localhost:8060/details/1/delete
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteProductDetail(@PathVariable Long id) {
        if(detailService.get(id).getId() == null){
            return new ResponseEntity<>(assembler.toModel(new ProductDetailEntity()), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new MessageResponse(detailService.delete(id)), HttpStatus.OK);
    }
}
