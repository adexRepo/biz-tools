package ecnic.service.common.database;

import static ecnic.service.common.constants.DatabaseConstant.DB_SCHEMA_DOCUMENT;
import static ecnic.service.common.constants.DatabaseConstant.DB_SCHEMA_MONEY;
import static ecnic.service.common.constants.DatabaseConstant.USER;

import javax.sql.DataSource;
import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.stereotype.Component;

@Component
public class MultiModuleFlywayMigrationStrategy implements FlywayMigrationStrategy {
    
    @Override
    public void migrate(Flyway flyway) {
        var dataSource = flyway.getConfiguration().getDataSource();
        
        Flyway userModule = configFlyway(USER, "db/user", dataSource);
        
        Flyway moneyModule = configFlyway(DB_SCHEMA_MONEY, "db/money", dataSource);
        
        Flyway documentModule = configFlyway(DB_SCHEMA_DOCUMENT, "db/document", dataSource);
        
        documentModule.migrate();
        moneyModule.migrate();
        userModule.migrate();
        
    }
    
    private static Flyway configFlyway(String user, String x, DataSource dataSource) {
        return Flyway.configure()
                .schemas(user)
                .locations(x)
                .dataSource(dataSource).load();
    }
}
