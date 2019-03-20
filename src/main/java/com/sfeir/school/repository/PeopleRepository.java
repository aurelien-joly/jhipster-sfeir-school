package com.sfeir.school.repository;

import com.sfeir.school.domain.People;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data MongoDB repository for the People entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PeopleRepository extends MongoRepository<People, String> {

    Page<People> findAllByOwnerIdIgnoreCase(Pageable pageable, String ownerId);

    Optional<People> findByIdAndOwnerIdIgnoreCase(String id, String ownerId);
}
