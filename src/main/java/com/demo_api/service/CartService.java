package com.demo_api.service;

import com.demo_api.entity.CartEntity;
import com.demo_api.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    @Autowired
    CartRepository repository;

    public CartEntity get(Long id){
        return repository.findById(id).orElse(new CartEntity());
    }

    public Page<CartEntity> getAll(Long user, int status, Pageable pageable){
        return repository.findByStatusAndUserId(user, (status == 0)? 0:1, pageable);
    }

    public CartEntity getByUserIdAndDetailId(Long user_id, Long detail_id){
        return repository.findByUserIdAndDetailId(user_id, detail_id);
    }

    public CartEntity add(CartEntity cart){
        return repository.save(cart);
    }

    public CartEntity save(CartEntity cart){
        CartEntity oldCart;
        //SAI chổ này
//        if(cart.getDetail().getStock() < cart.getQuantity()){
//            return new CartEntity();
//        }
        if(getByUserIdAndDetailId(cart.getUser().getId(), cart.getDetail().getId()) != null){
            oldCart = getByUserIdAndDetailId(cart.getUser().getId(), cart.getDetail().getId());
            oldCart.setQuantity(oldCart.getQuantity() + cart.getQuantity());
            oldCart.getDetail().setStock( oldCart.getDetail().getStock() - cart.getQuantity());
            return repository.save(oldCart);
        }
        cart.getDetail().setStock( cart.getDetail().getStock() - cart.getQuantity());
        return repository.save(cart);
    }
}
