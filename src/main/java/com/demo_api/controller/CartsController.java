package com.demo_api.controller;

import com.demo_api.assembler.CartModelAssembler;
import com.demo_api.entity.CartEntity;
import com.demo_api.model.Cart;
import com.demo_api.service.CartService;
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
@RequestMapping(value = "/carts")
public class CartsController {
    @Autowired
    CartService cartService;
    @Autowired
    PagedResourcesAssembler<CartEntity> pagedResourcesAssembler;
    @Autowired
    CartModelAssembler assembler;

    //Get one
    //Test with: http://localhost:8060/carts/1
    @GetMapping(value = "/{id}")
    public ResponseEntity<EntityModel<Cart>> getOne(@PathVariable Long id){
        CartEntity cart = cartService.get(id);
        if(cart.getId() == null)
            return  new ResponseEntity<>(assembler.toModel(new CartEntity()),HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(assembler.toModel(cart), HttpStatus.OK);
    }

    //Get all
    //Test with: http://localhost:8060/carts
    //Find all cart of user: http://localhost:8060/carts/ofUser/1
    //Find all cart of user with paging: http://localhost:8060/carts/ofUser/1?page=0&size=2&sort=id,asc
    @GetMapping(value = "/ofUser/{user}")
    public ResponseEntity<PagedModel<EntityModel<Cart>>> getAll(@PathVariable Long user, @RequestParam(defaultValue = "0") int status, Pageable pageable){
        Page<CartEntity> carts = cartService.getAll(user, status, pageable);
        PagedModel<EntityModel<Cart>> page = pagedResourcesAssembler.toModel(carts, assembler);
        page.add(linkTo(methodOn(CartsController.class).getAll(user, status, pageable)).withSelfRel());
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    //Tìm tất cả món trong một đơn hàng của một user(1 đơn hàng mua nhiều món)
    //Trả về danh sách các món trong đơn hàng "2" của user "1": http://localhost:8060/carts/ofUser/1/ofOrder/2
    @GetMapping(value = "/ofUser/{user}/ofOrder/{order_id}")
    public ResponseEntity<CollectionModel<EntityModel<Cart>>> getAll(@PathVariable Long user, @PathVariable Long order_id){
        List<EntityModel<Cart>> carts = cartService.getByUserIdAndOrderId(user,order_id).stream()
                .map(assembler::toModel).collect(Collectors.toList());
        return new ResponseEntity<>(
                CollectionModel.of(carts,linkTo(methodOn(CartsController.class).getAll(user,order_id)).withSelfRel()),
                HttpStatus.OK
        );
    }

    //Add new
    //Test with: http://localhost:8060/carts
    //In request body:
    //User "1" thêm một món "2" vào giỏ hàng vơi số lượng là 9 -> {"user":{"id":1}, "detail":{"id":2}, "quantity": 9}
    //Chú ý kết quả trả về: stock -> số lượng tối đa có thể thêm
    @PostMapping()
    public ResponseEntity<EntityModel<Cart>> addCart(@RequestBody CartEntity newCart){
        try{
            CartEntity cart = cartService.add(newCart);
            if(cart.getId() == null)
                return new ResponseEntity<>(assembler.toModel(new CartEntity()), HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(
                    assembler.toModel(cart),HttpStatus.CREATED
            );
        }catch (Exception e){
            return new ResponseEntity<>(assembler.toModel(new CartEntity()), HttpStatus.BAD_REQUEST);
        }
    }

    //Update
    //Test with: http://localhost:8060/carts/1/update
    //In request body:
    //Tăng số lượng trong giỏ hàng có id "1" lên thành 4: {"quantity": 4}
    @PutMapping(value = "/{id}/update")
    public ResponseEntity<EntityModel<Cart>> updateCart(@RequestBody CartEntity newCart, @PathVariable Long id) {
        CartEntity cart = cartService.get(id);
        if(cart.getId() == null){
            return new ResponseEntity<>(assembler.toModel(new CartEntity()), HttpStatus.NOT_FOUND);
        }
        try{
            return new ResponseEntity<>(assembler.toModel(cartService.update(cart, newCart)), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //Delete
    //Test with: http://localhost:8060/carts/1/delete
    //Chỉ xóa các item trong giỏ hàng khi chưa thanh toán.
    //Trả về "ERROR" nếu xóa thât bại: Do item đã thanh toán rồi hoặc id không hợp lệ
    //Trể về "DELETED" nếu xóa thành công.
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteCart(@PathVariable Long id) {
        if(cartService.get(id).getId() == null){
            return new ResponseEntity<>(assembler.toModel(new CartEntity()),HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cartService.delete(id),HttpStatus.OK);
    }
}
