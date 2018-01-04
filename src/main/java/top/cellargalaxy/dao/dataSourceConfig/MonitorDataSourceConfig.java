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
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * Created by cellargalaxy on 17-12-7.
 * 这里记录两个坑
 * 第一个是配置多数据源的时候要且只能配置一个叫主数据源的数据源，配置这个数据源是为了当没有被指定使用那个数据源的时候，程序可以默认使用主数据源
 * 配置主数据源的方法为添加@Primary注解，所以可以对比一下其他的数据源都没有次注解
 * 第二个是自己傻逼。例如本类上面有@Configuration，表明此类以默认的monitorDataSourceConfig名的bean
 * 但是一开始的时候monitorSqlSessionTemplate()方法的bean声明的名称却是monitorDataSourceConfig
 * 别说冲突了，连类关系都不一样，就报了奇怪的错误。
 */
@Configuration
@MapperScan(basePackages = "top.cellargalaxy.dao.monitor", sqlSessionTemplateRef = "monitorSqlSessionTemplate")
public class MonitorDataSourceConfig {
	
	@Primary
	@Bean(name = "monitorDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.monitor")
	public DataSource monitorDataSource() {
		return DataSourceBuilder.create().build();
	}
	
	@Bean(name = "monitorTransactionManager")
	@Primary
	public DataSourceTransactionManager monitorTransactionManager(@Qualifier("monitorDataSource") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
	
	@Bean(name = "monitorSqlSessionFactory")
	@Primary
	public SqlSessionFactory monitorSqlSessionFactory(@Qualifier("monitorDataSource") DataSource dataSource) throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
		return bean.getObject();
	}
	
	@Bean(name = "monitorSqlSessionTemplate")
	@Primary
	public SqlSessionTemplate monitorSqlSessionTemplate(@Qualifier("monitorSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
	
}
