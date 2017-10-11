package com.example.milosevi.rxjavatest.model;

/**
 * Created by milosevi on 9/29/17.
 */

public class Genre {
    int id;
    String name;
    public Genre(int id,String name){
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
