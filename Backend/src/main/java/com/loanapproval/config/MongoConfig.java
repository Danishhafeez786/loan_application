package com.loanapproval.config;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Configuration;

import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;


import static org.bson.UuidRepresentation.STANDARD;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Override
    protected String getDatabaseName() {
        return "loan_db";
    }

    @Override
    public MongoClient mongoClient() {

        ConnectionString connectionString =
                new ConnectionString("mongodb://localhost:27017/loan_db");

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .uuidRepresentation(STANDARD)   // 🔥 IMPORTANT FIX
                .build();

        return MongoClients.create(settings);
    }

}