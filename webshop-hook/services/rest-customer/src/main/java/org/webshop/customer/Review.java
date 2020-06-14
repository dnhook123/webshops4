package org.webshop.customer;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.Random;

@Entity
public class Review extends PanacheEntity {

    @NotNull
    public String name;

    public String experience;

    public int stars;

    public int productId;

    public String timestamp;

}