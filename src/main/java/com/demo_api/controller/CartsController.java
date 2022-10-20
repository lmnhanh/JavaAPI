package com.demo_api.controller;

import com.demo_api.assembler.CartModelAssembler;
import com.demo_api.entity.CartEntity;
import com.demo_api.model.Cart;
import com.demo_api.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping(value = "/{id}")
    public ResponseEntity<EntityModel<Cart>> getOne(@PathVariable Long id){
        CartEntity cart = cartService.get(id);
        if(cart.getId() == null)
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(assembler.toModel(cart), HttpStatus.OK);
    }

    //Get all
    //status: 0 -> chưa thanh toán (mặc định), 1: Đã thanh toán
    @GetMapping("/ofUser/{user}")
    public ResponseEntity<PagedModel<EntityModel<Cart>>> getAll(@PathVariable Long user, @RequestParam(defaultValue = "0") int status, Pageable pageable){
        Page<CartEntity> carts = cartService.getAll(user, status, pageable);
        PagedModel<EntityModel<Cart>> page = pagedResourcesAssembler.toModel(carts, assembler);
        page.add(linkTo(methodOn(CartsController.class).getAll(user, status, pageable)).withSelfRel());
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    //Add new
    //Test with: http://localhost:8060/carts
    //In request body: {"name": "VANS"}
    @PostMapping()
    public ResponseEntity<EntityModel<Cart>> addCart(@RequestBody CartEntity newCart){
        try{
            CartEntity cart = cartService.save(newCart);
            if(cart.getId() == null)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(
                    assembler.toModel(cart), HttpStatus.CREATED
            );
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
