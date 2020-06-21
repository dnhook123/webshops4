package org.webshop.product;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.Random;

@Entity
public class Product extends PanacheEntity {

    @NotNull
    @Size(min = 3, max = 50)
    public String name;

    public String description;

    @NotNull
    @Min(1)
    public BigDecimal price;
    public String picture;

    @Column(columnDefinition = "TEXT")
    public String sauce;

    public String imageSource;

    public String vendor;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", level=" + price +
                ", picture='" + picture + '\'' +
                ", sauce='" + sauce + '\'' +
                '}';
    }

        public static Product findRandomProduct() {
            long productCount = Product.count();
            Random random = new Random();
            int randomProduct = random.nextInt((int) productCount);
            return Product.findAll().page(randomProduct, 1).firstResult();
        }

}
