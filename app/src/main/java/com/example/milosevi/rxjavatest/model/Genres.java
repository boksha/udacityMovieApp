package com.example.milosevi.rxjavatest.model;

import java.util.List;

/**
 * Created by milosevi on 9/29/17.
 */

public class Genres {
    List<Genre> genres;
    public Genres(List<Genre> genres){
        this.genres = genres;
    }

    @Override
    public String toString() {
        return "Genres{" +
                "genres=" + genres +
                '}';
    }
}
