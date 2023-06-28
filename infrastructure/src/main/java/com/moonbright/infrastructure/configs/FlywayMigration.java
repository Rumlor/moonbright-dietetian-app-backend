package com.moonbright.infrastructure.configs;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import org.flywaydb.core.Flyway;


import javax.sql.DataSource;
import java.util.Objects;

@Startup
@Singleton
public class FlywayMigration {

    private Flyway flyway ;
    @Resource(lookup = "mysql_local_resource")
    private DataSource dataSource;


    @PostConstruct
    public void initFlyway() {
        if (Objects.isNull(flyway)){
            System.out.println("Starting to migrate the database schema with Flyway");
            Flyway flyway = Flyway.configure().dataSource(dataSource).load();
            flyway.migrate();
            System.out.println("Successfully applied latest schema changes");
        }

    }
}
