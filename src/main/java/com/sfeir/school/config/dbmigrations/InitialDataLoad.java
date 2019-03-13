package com.sfeir.school.config.dbmigrations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import com.sfeir.school.domain.People;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.IOException;
import java.util.List;

@ChangeLog(order = "002")
public class InitialDataLoad {


    @ChangeSet(order = "01", author = "initiator", id = "01-addPeople")
    public void loadInitialData(MongoTemplate mongoTemplate) throws IOException {
        List<People> peoples = new ObjectMapper().readValue(this.getClass().getResourceAsStream("/data/defaultData.json"),new TypeReference<List<People>>(){});
        mongoTemplate.insertAll(peoples);
    }
}
