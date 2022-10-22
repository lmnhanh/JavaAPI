package com.demo_api.controller;

import com.demo_api.assembler.OrderModelAssembler;
import com.demo_api.entity.OrderEntity;
import com.demo_api.model.Order;
import com.demo_api.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/orders")
public class OrdersController {
    @Autowired
    OrderService orderService;
    @Autowired
    PagedResourcesAssembler<OrderEntity> pagedResourcesAssembler;
    @Autowired
    OrderModelAssembler assembler;

    //Get one
    //Test with: http://localhost:8060/orders/1
    //Trả về: total-> tổng giá trị đơn hàng
    @GetMapping(value = "/{id}")
    public ResponseEntity<EntityModel<Order>> getOne(@PathVariable Long id){
        OrderEntity order = orderService.get(id);
        if(order.getId() == null)
            return new ResponseEntity<>(assembler.toModel(new OrderEntity()), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(assembler.toModel(order), HttpStatus.OK);
    }

    //Get all
    //Tất cả đơn hàng của user "1": http://localhost:8060/orders/ofUser/1
    //Tất cả đơn hàng của user "1" nếu nhiều quá thì phân trang: http://localhost:8060/orders/ofUser/1?page=0&size=5&sort=id,asc
    //Trang 1 -> page 0, size=5 -> Mỗi trang chứa 5 đơn hàng, sort=id,asc -> xắp sếp tăng dần theo id đơn hàng.
    @GetMapping(value = "/ofUser/{id}")
    public ResponseEntity<PagedModel<EntityModel<Order>>> getAll(@PathVariable(name = "id") Long user, Pageable pageable){
//        getAll(user, start, end, pageable) --> tìm theo thời gian lập -->Đang lỗi :((
        Page<OrderEntity> orders = orderService.getAll(user, pageable);
        PagedModel<EntityModel<Order>> page = pagedResourcesAssembler.toModel(orders, assembler);
        page.add(linkTo(methodOn(OrdersController.class).getAll(user, pageable)).withSelfRel());
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    //Thêm đơn hàng tui chưa làm kịp


    //Nếu muốn test đơn hàng
    //Tạo đơn hàng mới thủ công trong mysql, nhớ gán user_id.
    //Gán mã đơn hàng vào cart (bảng carts) và đặt status = 1 -> đã thanh toán
    //Gọi lại  http://localhost:8060/orders/1 sẽ thấy tổng giá trị đơn hàng được trả về.

}
