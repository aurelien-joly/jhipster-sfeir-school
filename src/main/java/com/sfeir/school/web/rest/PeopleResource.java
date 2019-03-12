package com.sfeir.school.web.rest;
import com.sfeir.school.domain.People;
import com.sfeir.school.service.PeopleService;
import com.sfeir.school.web.rest.errors.BadRequestAlertException;
import com.sfeir.school.web.rest.util.HeaderUtil;
import com.sfeir.school.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing People.
 */
@RestController
@RequestMapping("/api")
public class PeopleResource {

    private final Logger log = LoggerFactory.getLogger(PeopleResource.class);

    private static final String ENTITY_NAME = "people";

    private final PeopleService peopleService;

    public PeopleResource(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    /**
     * POST  /people : Create a new people.
     *
     * @param people the people to create
     * @return the ResponseEntity with status 201 (Created) and with body the new people, or with status 400 (Bad Request) if the people has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/people")
    public ResponseEntity<People> createPeople(@RequestBody People people) throws URISyntaxException {
        log.debug("REST request to save People : {}", people);
        if (people.getId() != null) {
            throw new BadRequestAlertException("A new people cannot already have an ID", ENTITY_NAME, "idexists");
        }
        People result = peopleService.save(people);
        return ResponseEntity.created(new URI("/api/people/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /people : Updates an existing people.
     *
     * @param people the people to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated people,
     * or with status 400 (Bad Request) if the people is not valid,
     * or with status 500 (Internal Server Error) if the people couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/people")
    public ResponseEntity<People> updatePeople(@RequestBody People people) throws URISyntaxException {
        log.debug("REST request to update People : {}", people);
        if (people.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        People result = peopleService.save(people);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, people.getId().toString()))
            .body(result);
    }

    /**
     * GET  /people : get all the people.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of people in body
     */
    @GetMapping("/people")
    public ResponseEntity<List<People>> getAllPeople(Pageable pageable) {
        log.debug("REST request to get a page of People");
        Page<People> page = peopleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/people");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /people/:id : get the "id" people.
     *
     * @param id the id of the people to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the people, or with status 404 (Not Found)
     */
    @GetMapping("/people/{id}")
    public ResponseEntity<People> getPeople(@PathVariable String id) {
        log.debug("REST request to get People : {}", id);
        Optional<People> people = peopleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(people);
    }

    /**
     * DELETE  /people/:id : delete the "id" people.
     *
     * @param id the id of the people to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/people/{id}")
    public ResponseEntity<Void> deletePeople(@PathVariable String id) {
        log.debug("REST request to delete People : {}", id);
        peopleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /**
     * SEARCH  /_search/people?query=:query : search for the people corresponding
     * to the query.
     *
     * @param query the query of the people search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/people")
    public ResponseEntity<List<People>> searchPeople(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of People for query {}", query);
        Page<People> page = peopleService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/people");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
