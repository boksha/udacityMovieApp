package com.example.milosevi.rxjavatest.database.model;

import com.example.milosevi.rxjavatest.Mapper;
import com.example.milosevi.rxjavatest.details.model.Review;

/**
 * Created by miodrag.milosevic on 12/4/2017.
 */

public class RealmReviewMapper extends Mapper<RealmReview, Review> {

    @Override
    public RealmReview map(Review review) {
        RealmReview realmReview = new RealmReview();
        realmReview.setId(review.getId());
        realmReview.setAuthor(review.getAuthor());
        realmReview.setContent(review.getContent());
        realmReview.setUrl(review.getUrl());
        return realmReview;
    }

    @Override
    public Review reverseMap(RealmReview m) {
        Review review = new Review();
        review.setId(m.getId());
        review.setContent(m.getContent());
        review.setAuthor(m.getAuthor());
        review.setUrl(m.getUrl());
        return review;
    }
}

