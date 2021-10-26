package com.linkedinlerning.reactive.config;

import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

@Profile(value="local")
@Configuration
@Import(EmbeddedMongoAutoConfiguration.class)
public class DataConfig {
}
