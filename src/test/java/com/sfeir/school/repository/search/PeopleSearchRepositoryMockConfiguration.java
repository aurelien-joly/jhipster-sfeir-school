package com.sfeir.school.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of PeopleSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class PeopleSearchRepositoryMockConfiguration {

    @MockBean
    private PeopleSearchRepository mockPeopleSearchRepository;

}
