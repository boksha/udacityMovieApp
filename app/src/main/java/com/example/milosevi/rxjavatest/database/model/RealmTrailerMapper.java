package com.example.milosevi.rxjavatest.database.model;

import com.example.milosevi.rxjavatest.Mapper;
import com.example.milosevi.rxjavatest.details.model.Trailer;

/**
 * Created by miodrag.milosevic on 12/4/2017.
 */

public class RealmTrailerMapper extends Mapper<RealmTrailer, Trailer> {

    @Override
    public RealmTrailer map(Trailer trailer) {
        RealmTrailer realmTrailer = new RealmTrailer();
        realmTrailer.setId(trailer.getId());
        realmTrailer.setKey(trailer.getKey());
        realmTrailer.setName(trailer.getName());
        realmTrailer.setSite(trailer.getSite());
        realmTrailer.setSize(trailer.getSize());
        realmTrailer.setType(trailer.getType());
        return realmTrailer;
    }

    @Override
    public Trailer reverseMap(RealmTrailer realmTrailer) {
        Trailer trailer = new Trailer();
        trailer.setId(realmTrailer.getId());
        trailer.setKey(realmTrailer.getKey());
        trailer.setName(realmTrailer.getName());
        trailer.setSite(realmTrailer.getSite());
        trailer.setSize(realmTrailer.getSize());
        trailer.setType(realmTrailer.getType());
        return trailer;
    }
}
