package com.sfeir.school.service.impl;

import com.sfeir.school.service.PeopleService;
import com.sfeir.school.domain.People;
import com.sfeir.school.repository.PeopleRepository;
import com.sfeir.school.repository.search.PeopleSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing People.
 */
@Service
public class PeopleServiceImpl implements PeopleService {

    private final Logger log = LoggerFactory.getLogger(PeopleServiceImpl.class);

    private final PeopleRepository peopleRepository;

    private final PeopleSearchRepository peopleSearchRepository;

    public PeopleServiceImpl(PeopleRepository peopleRepository, PeopleSearchRepository peopleSearchRepository) {
        this.peopleRepository = peopleRepository;
        this.peopleSearchRepository = peopleSearchRepository;
    }

    /**
     * Save a people.
     *
     * @param people the entity to save
     * @return the persisted entity
     */
    @Override
    public People save(People people) {
        log.debug("Request to save People : {}", people);
        People result = peopleRepository.save(people);
        peopleSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the people.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<People> findAll(Pageable pageable) {
        log.debug("Request to get all People");
        return peopleRepository.findAll(pageable);
    }


    /**
     * Get one people by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public Optional<People> findOne(String id) {
        log.debug("Request to get People : {}", id);
        return peopleRepository.findById(id);
    }

    /**
     * Delete the people by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete People : {}", id);
        peopleRepository.deleteById(id);
        peopleSearchRepository.deleteById(id);
    }

    /**
     * Search for the people corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<People> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of People for query {}", query);
        return peopleSearchRepository.search(queryStringQuery(query), pageable);    }
}
