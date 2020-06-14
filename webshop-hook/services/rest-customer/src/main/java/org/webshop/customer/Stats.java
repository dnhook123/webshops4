package org.webshop.customer;

import javax.persistence.Column;
import javax.persistence.Transient;
import javax.validation.constraints.Null;
import java.util.OptionalDouble;


public class Stats {

    public Double average;

    public int countReviews;


    public Double getAverage() {
        return average;
    }

    public void setAverage(Double average) {
        this.average = average;
    }

    public int getCountReviews() {
        return countReviews;
    }

    public void setCountReviews(int countReviews) {
        this.countReviews = countReviews;
    }
}
