package com.sfeir.school.web.rest;

import com.sfeir.school.JhipsterSfeirSchoolApp;
import com.sfeir.school.domain.People;
import com.sfeir.school.repository.PeopleRepository;
import com.sfeir.school.repository.search.PeopleSearchRepository;
import com.sfeir.school.service.PeopleService;
import com.sfeir.school.web.rest.errors.ExceptionTranslator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

import static com.sfeir.school.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PeopleResource REST controller.
 *
 * @see PeopleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSfeirSchoolApp.class)
public class PeopleResourceIntTest {

    private static final String DEFAULT_PHOTO = "AAAAAAAAAA";
    private static final String UPDATED_PHOTO = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_ENTRY_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ENTRY_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_BIRTH_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BIRTH_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_GENDER = "AAAAAAAAAA";
    private static final String UPDATED_GENDER = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_MANAGER = false;
    private static final Boolean UPDATED_IS_MANAGER = true;

    private static final String DEFAULT_MANAGER = "AAAAAAAAAA";
    private static final String UPDATED_MANAGER = "BBBBBBBBBB";

    private static final String DEFAULT_MANAGER_ID = "AAAAAAAAAA";
    private static final String UPDATED_MANAGER_ID = "BBBBBBBBBB";

    @Autowired
    private PeopleRepository peopleRepository;

    @Autowired
    private PeopleService peopleService;

    /**
     * This repository is mocked in the com.sfeir.school.repository.search test package.
     *
     * @see com.sfeir.school.repository.search.PeopleSearchRepositoryMockConfiguration
     */
    @Autowired
    private PeopleSearchRepository mockPeopleSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restPeopleMockMvc;

    private People people;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PeopleResource peopleResource = new PeopleResource(peopleService);
        this.restPeopleMockMvc = MockMvcBuilders.standaloneSetup(peopleResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static People createEntity() {
        People people = new People()
            .photo(DEFAULT_PHOTO)
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .companyName(DEFAULT_COMPANY_NAME)
            .entryDate(DEFAULT_ENTRY_DATE)
            .birthDate(DEFAULT_BIRTH_DATE)
            .gender(DEFAULT_GENDER)
            .email(DEFAULT_EMAIL)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .isManager(DEFAULT_IS_MANAGER)
            .manager(DEFAULT_MANAGER)
            .managerId(DEFAULT_MANAGER_ID);
        return people;
    }

    @Before
    public void initTest() {
        peopleRepository.deleteAll();
        people = createEntity();
    }

    @Test
    public void createPeople() throws Exception {
        int databaseSizeBeforeCreate = peopleRepository.findAll().size();

        // Create the People
        restPeopleMockMvc.perform(post("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(people)))
            .andExpect(status().isCreated());

        // Validate the People in the database
        List<People> peopleList = peopleRepository.findAll();
        assertThat(peopleList).hasSize(databaseSizeBeforeCreate + 1);
        People testPeople = peopleList.get(peopleList.size() - 1);
        assertThat(testPeople.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testPeople.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testPeople.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testPeople.getCompanyName()).isEqualTo(DEFAULT_COMPANY_NAME);
        assertThat(testPeople.getEntryDate()).isEqualTo(DEFAULT_ENTRY_DATE);
        assertThat(testPeople.getBirthDate()).isEqualTo(DEFAULT_BIRTH_DATE);
        assertThat(testPeople.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testPeople.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testPeople.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testPeople.isIsManager()).isEqualTo(DEFAULT_IS_MANAGER);
        assertThat(testPeople.getManager()).isEqualTo(DEFAULT_MANAGER);
        assertThat(testPeople.getManagerId()).isEqualTo(DEFAULT_MANAGER_ID);

        // Validate the People in Elasticsearch
        verify(mockPeopleSearchRepository, times(1)).save(testPeople);
    }

    @Test
    public void createPeopleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = peopleRepository.findAll().size();

        // Create the People with an existing ID
        people.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restPeopleMockMvc.perform(post("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(people)))
            .andExpect(status().isBadRequest());

        // Validate the People in the database
        List<People> peopleList = peopleRepository.findAll();
        assertThat(peopleList).hasSize(databaseSizeBeforeCreate);

        // Validate the People in Elasticsearch
        verify(mockPeopleSearchRepository, times(0)).save(people);
    }

    @Test
    public void getAllPeople() throws Exception {
        // Initialize the database
        peopleRepository.save(people);

        // Get all the peopleList
        restPeopleMockMvc.perform(get("/api/people?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(people.getId())))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(DEFAULT_PHOTO)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME)))
            .andExpect(jsonPath("$.[*].entryDate").value(hasItem(DEFAULT_ENTRY_DATE.toString())))
            .andExpect(jsonPath("$.[*].birthDate").value(hasItem(DEFAULT_BIRTH_DATE.toString())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].isManager").value(hasItem(DEFAULT_IS_MANAGER.booleanValue())))
            .andExpect(jsonPath("$.[*].manager").value(hasItem(DEFAULT_MANAGER)))
            .andExpect(jsonPath("$.[*].managerId").value(hasItem(DEFAULT_MANAGER_ID)));
    }
    
    @Test
    public void getPeople() throws Exception {
        // Initialize the database
        peopleRepository.save(people);

        // Get the people
        restPeopleMockMvc.perform(get("/api/people/{id}", people.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(people.getId()))
            .andExpect(jsonPath("$.photo").value(DEFAULT_PHOTO))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.companyName").value(DEFAULT_COMPANY_NAME))
            .andExpect(jsonPath("$.entryDate").value(DEFAULT_ENTRY_DATE.toString()))
            .andExpect(jsonPath("$.birthDate").value(DEFAULT_BIRTH_DATE.toString()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER))
            .andExpect(jsonPath("$.isManager").value(DEFAULT_IS_MANAGER.booleanValue()))
            .andExpect(jsonPath("$.manager").value(DEFAULT_MANAGER))
            .andExpect(jsonPath("$.managerId").value(DEFAULT_MANAGER_ID));
    }

    @Test
    public void getNonExistingPeople() throws Exception {
        // Get the people
        restPeopleMockMvc.perform(get("/api/people/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updatePeople() throws Exception {
        // Initialize the database
        peopleService.save(people);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockPeopleSearchRepository);

        int databaseSizeBeforeUpdate = peopleRepository.findAll().size();

        // Update the people
        People updatedPeople = peopleRepository.findById(people.getId()).get();
        updatedPeople
            .photo(UPDATED_PHOTO)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .companyName(UPDATED_COMPANY_NAME)
            .entryDate(UPDATED_ENTRY_DATE)
            .birthDate(UPDATED_BIRTH_DATE)
            .gender(UPDATED_GENDER)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .isManager(UPDATED_IS_MANAGER)
            .manager(UPDATED_MANAGER)
            .managerId(UPDATED_MANAGER_ID);

        restPeopleMockMvc.perform(put("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPeople)))
            .andExpect(status().isOk());

        // Validate the People in the database
        List<People> peopleList = peopleRepository.findAll();
        assertThat(peopleList).hasSize(databaseSizeBeforeUpdate);
        People testPeople = peopleList.get(peopleList.size() - 1);
        assertThat(testPeople.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testPeople.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testPeople.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testPeople.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testPeople.getEntryDate()).isEqualTo(UPDATED_ENTRY_DATE);
        assertThat(testPeople.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
        assertThat(testPeople.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testPeople.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testPeople.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testPeople.isIsManager()).isEqualTo(UPDATED_IS_MANAGER);
        assertThat(testPeople.getManager()).isEqualTo(UPDATED_MANAGER);
        assertThat(testPeople.getManagerId()).isEqualTo(UPDATED_MANAGER_ID);

        // Validate the People in Elasticsearch
        verify(mockPeopleSearchRepository, times(1)).save(testPeople);
    }

    @Test
    public void updateNonExistingPeople() throws Exception {
        int databaseSizeBeforeUpdate = peopleRepository.findAll().size();

        // Create the People

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPeopleMockMvc.perform(put("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(people)))
            .andExpect(status().isBadRequest());

        // Validate the People in the database
        List<People> peopleList = peopleRepository.findAll();
        assertThat(peopleList).hasSize(databaseSizeBeforeUpdate);

        // Validate the People in Elasticsearch
        verify(mockPeopleSearchRepository, times(0)).save(people);
    }

    @Test
    public void deletePeople() throws Exception {
        // Initialize the database
        peopleService.save(people);

        int databaseSizeBeforeDelete = peopleRepository.findAll().size();

        // Delete the people
        restPeopleMockMvc.perform(delete("/api/people/{id}", people.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<People> peopleList = peopleRepository.findAll();
        assertThat(peopleList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the People in Elasticsearch
        verify(mockPeopleSearchRepository, times(1)).deleteById(people.getId());
    }

    @Test
    public void searchPeople() throws Exception {
        // Initialize the database
        peopleService.save(people);
        when(mockPeopleSearchRepository.search(queryStringQuery("id:" + people.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(people), PageRequest.of(0, 1), 1));
        // Search the people
        restPeopleMockMvc.perform(get("/api/_search/people?query=id:" + people.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(people.getId())))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(DEFAULT_PHOTO)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME)))
            .andExpect(jsonPath("$.[*].entryDate").value(hasItem(DEFAULT_ENTRY_DATE.toString())))
            .andExpect(jsonPath("$.[*].birthDate").value(hasItem(DEFAULT_BIRTH_DATE.toString())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].isManager").value(hasItem(DEFAULT_IS_MANAGER.booleanValue())))
            .andExpect(jsonPath("$.[*].manager").value(hasItem(DEFAULT_MANAGER)))
            .andExpect(jsonPath("$.[*].managerId").value(hasItem(DEFAULT_MANAGER_ID)));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(People.class);
        People people1 = new People();
        people1.setId("id1");
        People people2 = new People();
        people2.setId(people1.getId());
        assertThat(people1).isEqualTo(people2);
        people2.setId("id2");
        assertThat(people1).isNotEqualTo(people2);
        people1.setId(null);
        assertThat(people1).isNotEqualTo(people2);
    }
}
