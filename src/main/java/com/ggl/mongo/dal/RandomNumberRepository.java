package com.ggl.mongo.dal;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ggl.mongo.model.RandomNumber;



@Repository
public interface RandomNumberRepository extends MongoRepository<RandomNumber, String> {
}


