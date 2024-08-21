package org.hospital.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;


public class DatabaseConfig {


    public static DataSource getDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(System.getenv("DB_URL"));
        config.setUsername(System.getenv("DB_USERNAME"));
        config.setPassword(System.getenv("DB_PASSWORD"));
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        config.setMaximumPoolSize(10);
        config.setAutoCommit(true);
        config.setIdleTimeout(30000);
        config.setMaxLifetime(60000);
        config.setConnectionTimeout(30000);

        return new HikariDataSource(config);
    }
}
