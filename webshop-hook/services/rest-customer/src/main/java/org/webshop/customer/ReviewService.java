package org.webshop.customer;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static javax.transaction.Transactional.TxType.REQUIRED;
import static javax.transaction.Transactional.TxType.SUPPORTS;

@ApplicationScoped
@Transactional(REQUIRED)
public class ReviewService {

    @Transactional(SUPPORTS)
    public List<Review> findAllProducts() {
        return Review.listAll();
    }

    @Transactional(SUPPORTS)
    public List<Review> findReviewsByProduct(Long id) {

        List<Review> allPersons = Review.listAll();
        return allPersons.stream().
                filter(p -> p.productId == id).
                collect(Collectors.toList());
    }

    public Stats getReviewStats(Long id) {

        List<Review> allPersons = Review.listAll();
        List<Review> relevantReviews = allPersons.stream().
                filter(p -> p.productId == id).
                collect(Collectors.toList());

        Stats stats = new Stats();
        stats.average = relevantReviews.stream().mapToInt(a -> a.stars)
                .average().orElse(0);
        stats.average = (double) Math.round(stats.average * 100) / 100;
        stats.countReviews = (int)relevantReviews.stream().mapToInt(a -> a.stars)
                .count();
        return stats;
    }


    public Review persistReview(@Valid Review review) {
        Review.persist(review);
        return review;
    }

    public Review updateCustomer(@Valid Review review) {
        Review entity = Review.findById(review.id);
        entity.name = review.name;
        entity.experience = review.experience;
        entity.productId = review.productId;
        entity.stars = review.stars;
        return entity;
    }

    public void deleteCustomer(Long id) {
        Review review = Review.findById(id);
        review.delete();
    }
}