package com.demo_api.assembler;

import com.demo_api.controller.ProductDetailsController;
import com.demo_api.controller.ProductsController;
import com.demo_api.entity.ProductDetailEntity;
import com.demo_api.model.ProductDetail;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProductDetailModelAssembler implements RepresentationModelAssembler<ProductDetailEntity, EntityModel<ProductDetail>> {
    @Override
    public EntityModel<ProductDetail> toModel(ProductDetailEntity entity) {
        if(entity.getId() == null)
            return EntityModel.of(new ProductDetail(entity.getId(), entity.getSize(), entity.getColor(), entity.getMaterial(), entity.getImage(),entity.getStock(), entity.getPrice(), entity.getStatus()));
        return EntityModel.of(new ProductDetail(entity.getId(), entity.getSize(), entity.getColor(), entity.getMaterial(), entity.getImage(), entity.getStock(), entity.getPrice(), entity.getStatus()),
                linkTo(methodOn(ProductDetailsController.class).getOne(entity.getId())).withSelfRel(),
                linkTo(methodOn(ProductsController.class).getOne(entity.getProduct().getId())).withRel("product")
        );
    }
}
