package com.sfeir.school.repository;

import com.sfeir.school.domain.People;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the People entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PeopleRepository extends MongoRepository<People, String> {

}
