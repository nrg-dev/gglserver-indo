package com.ggl.mongo.dal;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ggl.mongo.model.OwnTree;



@Repository
public interface OwnTreeRepository extends MongoRepository<OwnTree, String> {
}


