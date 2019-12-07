package com.ggl.mongo.dal;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ggl.mongo.model.Publictree;



@Repository
public interface PublicTreeRepository extends MongoRepository<Publictree, String> {
}
