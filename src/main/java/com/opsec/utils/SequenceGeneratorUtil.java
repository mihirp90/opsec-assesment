package com.opsec.utils;

import com.opsec.model.DatabaseSequence;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Component
public class SequenceGeneratorUtil {
    private MongoOperations mongoOperations;

    public SequenceGeneratorUtil(MongoOperations mongoOperations){
        this.mongoOperations = mongoOperations;
    }

    public long generateSequence(String sequence) {
        DatabaseSequence counter = mongoOperations.findAndModify(
                query(where("_id").is(sequence)),
                new Update().inc("seq",1),
                options().returnNew(true).upsert(true),
                DatabaseSequence.class
        );
        return !Objects.isNull(counter)?counter.getSeq():1;
    }
}
