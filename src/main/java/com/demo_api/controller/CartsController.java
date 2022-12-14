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

    //T??m t???t c??? m??n trong m???t ????n h??ng c???a m???t user(1 ????n h??ng mua nhi???u m??n)
    //Tr??? v??? danh s??ch c??c m??n trong ????n h??ng "2" c???a user "1": http://localhost:8060/carts/ofUser/1/ofOrder/2
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
    //User "1" th??m m???t m??n "2" v??o gi??? h??ng v??i s??? l?????ng l?? 9 -> {"user":{"id":1}, "detail":{"id":2}, "quantity": 9}
    //Ch?? ?? k???t qu??? tr??? v???: stock -> s??? l?????ng t???i ??a c?? th??? th??m
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
    //T??ng s??? l?????ng trong gi??? h??ng c?? id "1" l??n th??nh 4: {"quantity": 4}
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
    //Ch??? x??a c??c item trong gi??? h??ng khi ch??a thanh to??n.
    //Tr??? v??? "ERROR" n???u x??a th??t b???i: Do item ???? thanh to??n r???i ho???c id kh??ng h???p l???
    //Tr??? v??? "DELETED" n???u x??a th??nh c??ng.
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteCart(@PathVariable Long id) {
        if(cartService.get(id).getId() == null){
            return new ResponseEntity<>(assembler.toModel(new CartEntity()),HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cartService.delete(id),HttpStatus.OK);
    }
}
