package com.example.milosevi.rxjavatest;

/**
 * Created by miodrag.milosevic on 11/10/2017.
 */

public interface Mapper<From, To> {

    To map(From from);
}

