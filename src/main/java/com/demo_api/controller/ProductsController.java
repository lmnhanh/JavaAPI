package com.demo_api.controller;

import com.demo_api.assembler.ProductDetailModelAssembler;
import com.demo_api.assembler.ProductModelAssembler;
import com.demo_api.entity.ProductEntity;
import com.demo_api.model.Product;
import com.demo_api.model.ProductDetail;
import com.demo_api.service.ProductDetailService;
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
import org.springframework.security.access.prepost.PreAuthorize;
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
    ProductDetailService detailService;
    @Autowired
    ProductModelAssembler assembler;
    @Autowired
    ProductDetailModelAssembler detailAssembler;
    @Autowired
    PagedResourcesAssembler<ProductEntity> pagedResourcesAssembler;

    //Get one
    //Lấy sản phẩm có id là "1": http://localhost:8060/products/1
    @GetMapping(value = "/{id}")
    public ResponseEntity<EntityModel<Product>> getOne(@PathVariable Long id) {
        ProductEntity product = productService.get(id);
        if(product.getId() == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(assembler.toModel(product), HttpStatus.OK);
    }

    //Get all details of provided product
    //Lấy tất cả chi tiết của sản phẩm có id là 1: http://localhost:8060/products/1/details
    @GetMapping(value = "/{id}/details")
    public ResponseEntity<CollectionModel<EntityModel<ProductDetail>>> getDetails(@PathVariable Long id) {
        List<EntityModel<ProductDetail>> details = detailService.getByProductId(id).stream()
                .map(detailAssembler::toModel).collect(Collectors.toList());
        return new ResponseEntity<>(
                CollectionModel.of(details, linkTo(methodOn(ProductDetailsController.class).getAll(id,"*",0)).withSelfRel()),
                HttpStatus.OK
        );
    }

    //Get all with paging
    //Tìm sản phẩm theo tên "nike" và trạng thái "1"(đang kinh doanh): http://localhost:8060/products?name=nike&status=1&page=0&size=2&sort=id,asc
    //Tìm sản phẩm theo trạng thái "0"(ngừng kinh doanh): http://localhost:8060/products?status=0&page=0&size=2&sort=id,asc
    //Chỉ tìm theo tên sản phẩm đang kinh doanh: http://localhost:8060/products?name=vans
    //Tìm hết: http://localhost:8060/products?&status=-1&page=0&size=5&sort=id,asc
    //Chú ý: status:    0-> ngừng kinh doanh,
    //                  1-> đang kinh doanh (mặc định),
    //                  -1-> lấy hết
    @GetMapping()
    public ResponseEntity<PagedModel<EntityModel<Product>>> getAll(@RequestParam(defaultValue = "1") int status,
                                                                   @RequestParam(defaultValue = "*") String name,
                                                                   Pageable pageable) {
        Page<ProductEntity> products = productService.getAll(status, name, pageable);
        PagedModel<EntityModel<Product>> page = pagedResourcesAssembler.toModel(products, assembler);
        page.add(linkTo(methodOn(ProductsController.class).getAll(status,name,pageable)).withSelfRel());
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    //Add new
    //Test with: http://localhost:8060/products
    //Thêm sản phẩm mới: {"name": "Giày FAKE"}
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
    //Update sản phẩm có id là "1": http://localhost:8060/products/1/update
    //In request body:
    //Đặt lại tên sản phẩm: {"name": "Giày giả"}
    @PutMapping(value = "/{id}/update")
    public ResponseEntity<EntityModel<Product>> updateProduct(@RequestBody ProductEntity newProduct, @PathVariable Long id) {
        ProductEntity product = productService.get(id);
        if(product.getId() == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        productService.update(product, newProduct);
        return new ResponseEntity<>(assembler.toModel(product), HttpStatus.OK);
    }

    //Delete
    //Test with: http://localhost:8060/products/1/delete
    //Trả về "DELETED" nêu xóa thành công (thực ra là set status = 0);
    @DeleteMapping("/{id}/delete")
//    @PreAuthorize(value = "hasRole('Admin')")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        if(productService.get(id).getId() == null){
            return new ResponseEntity<>(assembler.toModel(new ProductEntity()),HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(productService.delete(id),HttpStatus.OK);
    }
}
