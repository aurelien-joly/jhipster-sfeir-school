package com.sfeir.school.service.impl;

import com.sfeir.school.JhipsterSfeirSchoolApp;
import com.sfeir.school.domain.People;
import com.sfeir.school.service.PeopleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSfeirSchoolApp.class)
public class PeopleServiceImplTest {

    private static final String USER = "admin";

    private static final String DEFAULT_PHOTO = "AAAAAAAAAA";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";

    private static final String DEFAULT_COMPANY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_ENTRY_DATE = LocalDate.ofEpochDay(0L);

    private static final LocalDate DEFAULT_BIRTH_DATE = LocalDate.ofEpochDay(0L);

    private static final String DEFAULT_GENDER = "AAAAAAAAAA";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";

    private static final Boolean DEFAULT_IS_MANAGER = false;
    private static final Boolean UPDATED_IS_MANAGER = true;

    private static final String DEFAULT_MANAGER = "AAAAAAAAAA";

    private static final String DEFAULT_MANAGER_ID = "AAAAAAAAAA";

    @Autowired
    private PeopleService peopleService;


    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static People createEntity() {
        return new People()
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
    }

    public static People createWrongEntity() {
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
        people.setId("WrongID");
        return people;
    }

    @Test(expected = NoSuchElementException.class)
    public void patchPeople() {
        People oldPeople = peopleService.save(createEntity(), "admin");
        People newPeople = new People();
        newPeople.setId(oldPeople.getId());
        newPeople.setCompanyName(UPDATED_COMPANY_NAME);
        newPeople.setIsManager(UPDATED_IS_MANAGER);
        People patchedPeople = peopleService.update(newPeople);
        assertEquals(oldPeople.getLastName(), patchedPeople.getLastName());
        assertEquals(oldPeople.getBirthDate(), patchedPeople.getBirthDate());


        assertNotEquals(oldPeople.getCompanyName(), patchedPeople.getCompanyName());
        assertNotEquals(oldPeople.isIsManager(), patchedPeople.isIsManager());

        peopleService.update(createWrongEntity());
    }

}
