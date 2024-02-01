package org.configTest;
import org.gym.config.AppConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import java.lang.reflect.Field;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Import(AppConfig.class)
public class AppConfigTest {


    @Mock
    private Environment environment;
    @Spy
    private AppConfig appConfig;
    @Mock
    private EntityManagerFactory entityManagerFactory;

    @InjectMocks
    private LocalContainerEntityManagerFactoryBean entityManagerFactoryBean;
    @Mock
    private DataSource dataSource;

    @Mock
    private Properties hibernateProperties;



    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDataSource() throws NoSuchFieldException, IllegalAccessException {
        // Mocking properties
        Environment environment = mock(Environment.class);
        when(environment.getProperty("spring.datasource.url")).thenReturn("jdbc:postgresql://localhost:5432/postgres");
        when(environment.getProperty("spring.datasource.driverClassName")).thenReturn("org.postgresql.Driver");
        when(environment.getProperty("spring.datasource.username")).thenReturn("postgres");
        when(environment.getProperty("spring.datasource.password")).thenReturn("gamer120");

        // Testing dataSource method
        AppConfig appConfig = new AppConfig();
        setPrivateField(appConfig, "url", "jdbc:postgresql://localhost:5432/postgres");
        setPrivateField(appConfig, "className", "org.postgresql.Driver");
        setPrivateField(appConfig, "username", "postgres");
        setPrivateField(appConfig, "password", "gamer120");

        DataSource dataSource = appConfig.dataSource();
        assertTrue(dataSource instanceof DriverManagerDataSource);

        // Add additional assertions as needed based on your specific requirements
    }



    @Test
    public void testTransactionManager() {
        EntityManagerFactory entityManagerFactory = mock(EntityManagerFactory.class);

        AppConfig appConfig = new AppConfig();
        PlatformTransactionManager transactionManager = appConfig.transactionManager(entityManagerFactory);
        assertTrue(transactionManager instanceof JpaTransactionManager);

    }

    @Test
    void testHibernateProperties() throws NoSuchFieldException, IllegalAccessException {
        // Создаем экземпляр AppConfig
        AppConfig appConfig = new AppConfig();

        // Устанавливаем значения полей напрямую
        setPrivateField(appConfig, "url", "jdbc:postgresql://localhost:5432/postgres");
        setPrivateField(appConfig, "className", "org.postgresql.Driver");
        setPrivateField(appConfig, "username", "postgres");
        setPrivateField(appConfig, "password", "gamer120");
        setPrivateField(appConfig, "hibernateDialect", "org.hibernate.dialect.PostgreSQLDialect");
        setPrivateField(appConfig, "show_sql", "true");
        setPrivateField(appConfig, "auto_sql", "update");

        // Вызываем метод, который хотим протестировать
        Properties result = appConfig.hibernateProperties();

        // Проверяем, что возвращенные свойства соответствуют установленным значениям
        assertEquals("org.hibernate.dialect.PostgreSQLDialect", result.getProperty("hibernate.dialect"));
        assertEquals("true", result.getProperty("hibernate.show_sql"));
        assertEquals("update", result.getProperty("hibernate.hbm2ddl.auto"));
    }
    private void setPrivateField(Object object, String fieldName, Object value)
            throws NoSuchFieldException, IllegalAccessException {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(object, value);
    }




}
