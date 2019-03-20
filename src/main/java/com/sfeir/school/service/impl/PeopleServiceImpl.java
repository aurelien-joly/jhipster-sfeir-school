package com.sfeir.school.service.impl;

import com.sfeir.school.domain.People;
import com.sfeir.school.repository.PeopleRepository;
import com.sfeir.school.repository.search.PeopleSearchRepository;
import com.sfeir.school.service.PeopleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing People.
 */
@Service
public class PeopleServiceImpl implements PeopleService {

    private final Logger log = LoggerFactory.getLogger(PeopleServiceImpl.class);

    private final PeopleRepository peopleRepository;

    private final PeopleSearchRepository peopleSearchRepository;

    private final CacheManager cacheManager;

    public PeopleServiceImpl(PeopleRepository peopleRepository, PeopleSearchRepository peopleSearchRepository, CacheManager cacheManager) {
        this.peopleRepository = peopleRepository;
        this.peopleSearchRepository = peopleSearchRepository;
        this.cacheManager = cacheManager;
    }

    /**
     * Save a people.
     *
     * @param people the entity to save
     * @param userId
     * @return the persisted entity
     */
    @Override
    public People save(People people, String userId) {
        log.debug("Request to save People : {} for {}", people, userId);
        people.setOwnerId(userId);
        People result = peopleRepository.save(people);
        peopleSearchRepository.save(result);
        return result;
    }


    /**
     * Update a people. Only modified non null, non blank fields are updated.
     *
     * @param people the entity to update
     * @return the persisted entity
     */
    @Override
    public People update(People people) {
        log.debug("Request to save People : {}", people);
        People peopleEntity = peopleRepository.findById(people.getId()).orElseThrow(NoSuchElementException::new);
        People result = peopleRepository.save(patchEntity(peopleEntity, people));
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
    public Page<People> findAll(Pageable pageable, String userId) {
        log.debug("Request to get all People");
        return peopleRepository.findAllByOwnerIdIgnoreCase(pageable, userId);
    }


    /**
     * Get one people by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public Optional<People> findOne(String id, String ownerId) {
        log.debug("Request to get People : {}", id);
        return peopleRepository.findByIdAndOwnerIdIgnoreCase(id, ownerId);
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
        return peopleSearchRepository.search(queryStringQuery(query), pageable);
    }

    private People patchEntity(People peopleEntity, People people) {
        Arrays.stream(People.class.getDeclaredFields()).filter(field -> {
            field.setAccessible(true);
            if (StringUtils.equals(field.getName(), "id") || StringUtils.equals(field.getName(), "serialVersionUID")) {
                return false;
            }
            try {
                return Objects.nonNull(field.get(people));
            } catch (IllegalAccessException e) {
                return false;
            }
        }).forEach(field -> {
            try {
                field.set(peopleEntity, field.get(people));
            } catch (IllegalAccessException e) {
                log.error("Can't update field", e);
            }
        });
        return peopleEntity;
    }

}
