package top.cellargalaxy.dao.dataSourceConfig;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * Created by cellargalaxy on 17-12-8.
 */
@Configuration
@MapperScan(basePackages = "top.cellargalaxy.dao.personnel", sqlSessionTemplateRef = "personnelSqlSessionTemplate")
public class PersonnelDataSourceConfig {
	
	@Bean(name = "personnelDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.personnel")
	public DataSource personnelDataSource() {
		return DataSourceBuilder.create().build();
	}
	
	@Bean(name = "personnelTransactionManager")
	public DataSourceTransactionManager personnelTransactionManager(@Qualifier("personnelDataSource") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
	
	@Bean(name = "personnelSqlSessionFactory")
	public SqlSessionFactory personnelSqlSessionFactory(@Qualifier("personnelDataSource") DataSource dataSource) throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
		return bean.getObject();
	}
	
	@Bean(name = "personnelSqlSessionTemplate")
	public SqlSessionTemplate personnelSqlSessionTemplate(@Qualifier("personnelSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
	
}
