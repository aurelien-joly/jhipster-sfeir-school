package com.sfeir.school.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.sfeir.school.config.CustomDateTimeDeserializer;
import com.sfeir.school.config.CustomDateTimeSerializer;
import io.swagger.annotations.ApiModel;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * The Employee entity.
 */
@ApiModel(description = "The Employee entity.")
@Document(collection = "people")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "people")
public class People implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword,fielddata = true)
    private String id;

    @Field("photo")
    private String photo;

    @Field("first_name")
    @JsonProperty("firstname")
    @org.springframework.data.elasticsearch.annotations.Field(fielddata = true, type = FieldType.Text)
    private String firstName;

    @Field("last_name")
    @JsonProperty("lastname")
    @org.springframework.data.elasticsearch.annotations.Field(fielddata = true, type = FieldType.Text)
    private String lastName;

    @Field("company_name")
    @JsonProperty("entity")
    private String companyName;

    @Field("entry_date")
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    private LocalDate entryDate;

    @Field("birth_date")
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    private LocalDate birthDate;

    @Field("gender")
    private String gender;

    @Field("email")
    private String email;

    @Field("phone_number")
    @JsonProperty("phone")
    private String phoneNumber;

    @Field("is_manager")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Boolean)
    private Boolean isManager;

    @Field("manager")
    private String manager;

    @Field("manager_id")
    private String managerId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhoto() {
        return photo;
    }

    public People photo(String photo) {
        this.photo = photo;
        return this;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getFirstName() {
        return firstName;
    }

    public People firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public People lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public People companyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public People entryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
        return this;
    }

    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public People birthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public People gender(String gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public People email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public People phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean isIsManager() {
        return isManager;
    }

    public People isManager(Boolean isManager) {
        this.isManager = isManager;
        return this;
    }

    public void setIsManager(Boolean isManager) {
        this.isManager = isManager;
    }

    public String getManager() {
        return manager;
    }

    public People manager(String manager) {
        this.manager = manager;
        return this;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getManagerId() {
        return managerId;
    }

    public People managerId(String managerId) {
        this.managerId = managerId;
        return this;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        People people = (People) o;
        if (people.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), people.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "People{" +
            "id=" + getId() +
            ", photo='" + getPhoto() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", companyName='" + getCompanyName() + "'" +
            ", entryDate='" + getEntryDate() + "'" +
            ", birthDate='" + getBirthDate() + "'" +
            ", gender='" + getGender() + "'" +
            ", email='" + getEmail() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", isManager='" + isIsManager() + "'" +
            ", manager='" + getManager() + "'" +
            ", managerId='" + getManagerId() + "'" +
            "}";
    }
}
