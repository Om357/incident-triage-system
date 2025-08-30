package com.incident.triage.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "com.incident.triage.repository")
@EnableTransactionManagement
public class DatabaseConfig {

    // H2 Database configuration is handled by Spring Boot auto-configuration
    // This class can be used for custom database configurations if needed

    /*
     * For production environment, you can add custom DataSource beans here
     * Example:
     *
     * @Bean
     * @Profile("prod")
     * public DataSource productionDataSource() {
     *     HikariConfig config = new HikariConfig();
     *     config.setJdbcUrl("jdbc:postgresql://localhost:5432/incident_db");
     *     config.setUsername("your_username");
     *     config.setPassword("your_password");
     *     config.setDriverClassName("org.postgresql.Driver");
     *     return new HikariDataSource(config);
     * }
     */
}
