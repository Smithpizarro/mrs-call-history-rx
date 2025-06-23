package pe.business.app.calls.infrastructure.config;

import io.r2dbc.spi.ConnectionFactory;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.R2dbcTransactionManager;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.reactive.TransactionalOperator;

@Configuration(proxyBeanMethods = true)
@EnableR2dbcRepositories
public class R2dbcConfiguration extends AbstractR2dbcConfiguration {

    @Value("${database.name}")
    private String database;

    @Value("${database.host}")
    private String host;

    @Value("${database.port:5432}")
    private int port;

    @Value("${database.username}")
    private String username;

    @Value("${database.password}")
    private String password;

    @Override
    @Bean
    public ConnectionFactory connectionFactory() {

        return new PostgresqlConnectionFactory(PostgresqlConnectionConfiguration.builder()
                .host(host)
                .port(port)
                .username(username)
                .password(password)
                .database(database)
                .build());
    }

    @Bean
    public ReactiveTransactionManager reactiveTransactionManager() {
        return new R2dbcTransactionManager(connectionFactory());
    }

    @Bean
    public TransactionalOperator transactionalOperator() {
        return TransactionalOperator.create(reactiveTransactionManager());
    }

}