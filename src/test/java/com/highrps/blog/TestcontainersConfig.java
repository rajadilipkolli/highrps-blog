package com.highrps.blog;

import static org.testcontainers.utility.DockerImageName.parse;

import ch.martinelli.oss.testcontainers.mailpit.MailpitContainer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.grafana.LgtmStackContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;
import org.testcontainers.rabbitmq.RabbitMQContainer;

@TestConfiguration(proxyBeanMethods = false)
@Testcontainers
public class TestcontainersConfig {

    @Container
    MailpitContainer mailpit = new MailpitContainer("axllent/mailpit:v1.31");

    @Container
    static RabbitMQContainer rabbitmq = new RabbitMQContainer(parse("rabbitmq:4-management"));

    @Container
    static GenericContainer<?> redis = new GenericContainer<>(parse("redis:8-alpine")).withExposedPorts(6379);

    @Container
    static LgtmStackContainer lgtm = new LgtmStackContainer(parse("grafana/otel-lgtm:0.33.0"));

    @Bean
    @ServiceConnection
    PostgreSQLContainer postgres() {
        return new PostgreSQLContainer(parse("postgres:18-alpine"));
    }

    @Bean
    @ServiceConnection
    MailpitContainer mailpit() {
        return mailpit;
    }

    @Bean
    @ServiceConnection
    RabbitMQContainer rabbitmq() {
        return rabbitmq;
    }

    @Bean
    @ServiceConnection(name = "redis")
    GenericContainer<?> redis() {
        return redis;
    }

    @Bean
    @ServiceConnection
    LgtmStackContainer lgtm() {
        return lgtm;
    }
}
