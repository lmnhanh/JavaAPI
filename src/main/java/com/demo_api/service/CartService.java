package com.demo_api.service;

import com.demo_api.entity.CartEntity;
import com.demo_api.entity.ProductDetailEntity;
import com.demo_api.model.Cart;
import com.demo_api.model.ProductDetail;
import com.demo_api.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    @Autowired
    CartRepository repository;
    @Autowired
    ProductDetailService detailService;


    public CartEntity get(Long id){
        return repository.findById(id).orElse(new CartEntity());
    }

    public Page<CartEntity> getAll(Long user, int status, Pageable pageable){
        return repository.findByStatusAndUserId(user, (status == 0)? 0:1, pageable);
    }
    public List<CartEntity> getByUserIdAndOrderId(Long user, Long id){
        return repository.findByUserIdAndOrderId(user, id);
    }

    public CartEntity getByUserIdAndDetailId(Long user_id, Long detail_id){
        return repository.findByUserIdAndDetailId(user_id, detail_id);
    }

    public CartEntity add(CartEntity cart){
        if(cart.getDetail().getId() == null){
            return new CartEntity();
        }
        int stock = detailService.get(cart.getDetail().getId()).getStock();

        CartEntity oldCart = getByUserIdAndDetailId(cart.getUser().getId(), cart.getDetail().getId());
        if(oldCart != null){
            int newQuantity = oldCart.getQuantity() + cart.getQuantity();
            if(stock <= 0 || cart.getQuantity() <= 0 || stock < newQuantity){
                return cart;
            }
            oldCart.setQuantity(newQuantity);
            return save(oldCart);
        }
        if(stock <= 0 || cart.getQuantity() <= 0 || stock < cart.getQuantity()){
            return cart;
        }
        cart.setDetail(detailService.get(cart.getDetail().getId()));
        return save(cart);
    }

    public Boolean checkOut(List<CartEntity> carts){
        Boolean isSuccess = true;
        try{
            for(CartEntity cart : carts){
                if(cart.getDetail().getStock() >= cart.getQuantity()){
                    cart.getDetail().setStock(cart.getDetail().getStock() - cart.getQuantity());
                }
            }
        }
        catch (Exception e){
            isSuccess = false;
        }
        return isSuccess;
    }

    public CartEntity update(CartEntity oldCart, CartEntity newCart){
        if(detailService.get(oldCart.getDetail().getId()).getStock() < newCart.getQuantity()){
            return new CartEntity();
        }
        oldCart.setQuantity(newCart.getQuantity());
        return save(oldCart);
    }

    public String delete(Long id){
        CartEntity cart = get(id);
       if(cart.getStatus() != 0 || cart.getOrder() != null){
           return "ERROR";
       }
       repository.delete(cart);
       return "DELETED";
    }

    public CartEntity save(CartEntity cart){
        System.out.println(cart.getDetail().getStock() + "-" + cart.getDetail().getPrice());
        return repository.save(cart);
    }
}
