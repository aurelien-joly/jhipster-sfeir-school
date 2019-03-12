package com.sfeir.school.repository.search;

import com.sfeir.school.domain.People;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the People entity.
 */
public interface PeopleSearchRepository extends ElasticsearchRepository<People, String> {
}
