package org.webshop.cart;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import org.testcontainers.shaded.com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.testcontainers.shaded.com.fasterxml.jackson.annotation.JsonBackReference;
import org.testcontainers.shaded.com.fasterxml.jackson.annotation.JsonIgnore;
import org.testcontainers.shaded.com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.webshop.cart.client.Product;
import org.webshop.cart.relations.CartProduct;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Cart extends PanacheEntity {


    public long customerId;
    public String name;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL,
            mappedBy = "cart", orphanRemoval = true)
    public Set<CartProduct> cartProducts = new HashSet<>();


    public BigDecimal totalPrice;


    public static Cart findByCustomerId(long customerId) {
        return find("customerId", customerId).firstResult();
    }

    // 1 cart has 1 owner.
//    @OneToOne
//    public CartOwner cartOwner;
}
