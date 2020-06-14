package org.webshop.cart.relations;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import org.testcontainers.shaded.com.fasterxml.jackson.annotation.JsonIgnore;
import org.webshop.cart.Cart;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@Entity
public class CartProduct extends PanacheEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cart_id")
    @JsonIgnore
   public Cart cart;

   public long productId;

   public int quantity;
   public BigDecimal price;

    public CartProduct() {
    }

    public CartProduct(Cart cart, long productId, int quantity, BigDecimal price) {
        this.cart = cart;
       this.productId = productId;
       this.quantity = quantity;
        this.price = price;
    }
}
