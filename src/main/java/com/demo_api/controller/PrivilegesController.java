package com.demo_api.controller;

import com.demo_api.assembler.PrivilegeModelAssembler;
import com.demo_api.entity.RoleEntity;
import com.demo_api.model.Privilege;
import com.demo_api.entity.PrivilegeEntity;
import com.demo_api.model.Role;
import com.demo_api.repository.RoleRepository;
import com.demo_api.service.PrivilegeService;
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
@RequestMapping(value = "/privileges")
public class PrivilegesController {
    @Autowired
    PrivilegeService privilegeService;
    @Autowired
    PagedResourcesAssembler<PrivilegeEntity> pagedResourcesAssembler;
    @Autowired
    PrivilegeModelAssembler assembler;

    //Get one
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Privilege>> getOne(@PathVariable Long id) {
        PrivilegeEntity privilege = privilegeService.get(id);
        if(privilege.getId() == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(assembler.toModel(privilege), HttpStatus.OK);
    }

    //Get all
    @GetMapping()
    public ResponseEntity<PagedModel<EntityModel<Privilege>>> getAll(@RequestParam(defaultValue = "-1") Long role, Pageable pageable){
        Page<PrivilegeEntity> privileges = privilegeService.getAll(role, pageable);
        PagedModel<EntityModel<Privilege>> page = pagedResourcesAssembler.toModel(privileges, assembler);
        page.add(linkTo(methodOn(PrivilegesController.class).getAll(role, pageable)).withSelfRel());
        return new ResponseEntity<>(page, HttpStatus.OK);
    }
}
