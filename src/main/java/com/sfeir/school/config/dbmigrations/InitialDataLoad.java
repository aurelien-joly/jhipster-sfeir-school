package com.sfeir.school.config.dbmigrations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import com.sfeir.school.domain.People;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.util.StreamUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

@ChangeLog(order = "002")
public class InitialDataLoad {


    @ChangeSet(order = "01", author = "initiator", id = "01-addPeople")
    public void loadInitialData(MongoTemplate mongoTemplate) throws IOException {
        System.out.println(FileUtils.readFileToString(new File(this.getClass().getResource("/data/defaultData.json").getFile()), Charset.defaultCharset()));
        List<People> peoples = new ObjectMapper().readValue(this.getClass().getResourceAsStream("/data/defaultData.json"),new TypeReference<List<People>>(){});
        System.out.println(peoples);
        mongoTemplate.insertAll(peoples);
    }
}
