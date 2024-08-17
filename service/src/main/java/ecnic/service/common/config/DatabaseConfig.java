//package ecnic.service.common.config;
//
//import static ecnic.service.common.config.DatabaseConstants.DOCUMENT;
//import static ecnic.service.common.config.DatabaseConstants.ECNIC_SERVICE_DOCUMENT_DOMAIN;
//import static ecnic.service.common.config.DatabaseConstants.ECNIC_SERVICE_MONEY_DOMAIN;
//import static ecnic.service.common.config.DatabaseConstants.ECNIC_SERVICE_USER_DOMAIN;
//import static ecnic.service.common.config.DatabaseConstants.HIBERNATE_DEFAULT_SCHEMA;
//import static ecnic.service.common.config.DatabaseConstants.HIBERNATE_DIALECT;
//import static ecnic.service.common.config.DatabaseConstants.MONEY;
//import static ecnic.service.common.config.DatabaseConstants.SPRING_JPA_DATABASE_PLATFORM;
//import static ecnic.service.common.config.DatabaseConstants.USER;
//
//import jakarta.persistence.EntityManager;
//import java.util.HashMap;
//import java.util.Map;
//import javax.sql.DataSource;
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//import org.springframework.stereotype.Component;
//
///**
// * The type Database config.
// */
//@Configuration
//@RequiredArgsConstructor
//@Component
//public class DatabaseConfig {
//
//    private final Environment env;
//
//    /**
//     * Data source properties data source properties.
//     *
//     * @return the data source properties
//     */
//    @Bean
//    @ConfigurationProperties("spring.datasource")
//    public DataSourceProperties dataSourceProperties() {
//        return new DataSourceProperties();
//    }
//
//    /**
//     * Data source data source.
//     *
//     * @return the data source
//     */
//    @Bean
//    public DataSource dataSource() {
//        DataSourceProperties dataSourceProperties = dataSourceProperties();
//        return DataSourceBuilder.create()
//                .driverClassName(dataSourceProperties.getDriverClassName())
//                .username(dataSourceProperties.getUsername())
//                .password(dataSourceProperties.getPassword())
//                .build();
//    }
//
//    /**
//     * User entity manager local container entity manager factory bean.
//     *
//     * @return the local container entity manager factory bean
//     */
//    @Bean
//    public LocalContainerEntityManagerFactoryBean userEntityManager() {
//        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
//        em.setDataSource(dataSource());
//        em.setPackagesToScan(ECNIC_SERVICE_USER_DOMAIN);
//        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
//        em.setJpaPropertyMap(buildProperties(USER));
//        return em;
//    }
//
//    /**
//     * Document entity manager local container entity manager factory bean.
//     *
//     * @return the local container entity manager factory bean
//     */
//    @Bean
//    public LocalContainerEntityManagerFactoryBean documentEntityManager() {
//        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
//        em.setDataSource(dataSource());
//        em.setPackagesToScan(ECNIC_SERVICE_DOCUMENT_DOMAIN);
//        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
//        em.setJpaPropertyMap(buildProperties(DOCUMENT));
//        return em;
//    }
//
//    /**
//     * Money entity manager local container entity manager factory bean.
//     *
//     * @return the local container entity manager factory bean
//     */
//    @Bean
//    public LocalContainerEntityManagerFactoryBean moneyEntityManager() {
//        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
//        em.setDataSource(dataSource());
//        em.setPackagesToScan(ECNIC_SERVICE_MONEY_DOMAIN);
//        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
//        em.setJpaPropertyMap(buildProperties(MONEY));
//        return em;
//    }
//
//    /**
//     * Every schema has same dialect coz of this same database.
//     *
//     * @return the map string object
//     */
//    private Map<String, Object> buildProperties(String schemeName) {
//        Map<String, Object> properties = new HashMap<>();
//        properties.put(HIBERNATE_DIALECT, env.getProperty(SPRING_JPA_DATABASE_PLATFORM)); // Replace with your actual dialect
//        properties.put(HIBERNATE_DEFAULT_SCHEMA, schemeName);
//        return properties;
//    }
//}
