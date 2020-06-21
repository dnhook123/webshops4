package org.webshop.order;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import javax.persistence.*;
import java.math.BigDecimal;


@Entity
public class Orders extends PanacheEntity {

    public String orderDate;
    public String deliveryTime;
    public String shippingAddress;
    public String orderReference;
    public String orderStatus;
    public BigDecimal totalPrice;
    public String customerName;
    public String customerEmail;
    public String productIds;

}

