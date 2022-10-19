package com.demo_api.assembler;

import com.demo_api.controller.ProductDetailsController;
import com.demo_api.controller.ProductsController;
import com.demo_api.entity.ProductEntity;
import com.demo_api.model.Product;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProductModelAssembler implements RepresentationModelAssembler<ProductEntity, EntityModel<Product>> {
    @Override
    public EntityModel<Product> toModel(ProductEntity entity) {
        if(entity.getId() == null)
            return EntityModel.of(new Product(entity.getId(), entity.getName(), entity.getStatus()));
        return EntityModel.of(new Product(entity.getId(), entity.getName(), entity.getStatus()),
                linkTo(methodOn(ProductsController.class).getOne(entity.getId())).withSelfRel(),
                linkTo(methodOn(ProductsController.class).getDetails(entity.getId())).withRel("details")
        );
    }
}
