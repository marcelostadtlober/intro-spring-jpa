package br.com.devmedia.curso.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 
 * @author Marcelo
 * 
 * Classe de Integração entre o Spring, o Hibernate e a Jpa.
 *
 */
@Configuration
@EnableTransactionManagement	// Informa ao Spring que ele gerenciará as transações da aplicação
public class SpringJpaConfig {
	
	/*
	 * Método que configura a conexão com o banco de dados
	 */
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUrl("jdbc:mysql://localhost:3306/test?createDatabaseIfNotExist=true");
		ds.setUsername("root");
		ds.setPassword("");
		
		return ds;
	}
	
	/*
	 * Método que cria um EntityManager para a aplicação
	 */
	@Bean
	public EntityManagerFactory entityManagerFactory(DataSource dataSource) {
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setDataSource(dataSource);
		factory.setPackagesToScan("br.com.devmedia.curso.domain");
		factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		factory.setJpaProperties(jpaProperties());
		factory.afterPropertiesSet();
		
		return factory.getObject();
	}

	@Bean
	public JpaTransactionManager transactionalManager(EntityManagerFactory factory) {
		JpaTransactionManager tx = new JpaTransactionManager();
		tx.setEntityManagerFactory(factory);
		tx.setJpaDialect(new HibernateJpaDialect());
		
		return tx;
	}
	
	/*
	 * Método que passa as configurações adicionais ao Jpa
	 */
	private Properties jpaProperties() {
		Properties props = new Properties();
		props.setProperty("hibernate.show_sql", "true");
		props.setProperty("hibernate.format_sql", "true");
		props.setProperty("hibernate.hbm2ddl.auto", "update");
		
		return props;
	}

}
