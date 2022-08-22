package ib.api.validate.config;

import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PreDestroy;
import javax.sql.DataSource;

@Configuration
@MapperScan(value = "ib.api.validate", sqlSessionFactoryRef = "RestSqlSessionFactory")
@RequiredArgsConstructor
@EnableTransactionManagement
public class DBConfig {

    private final AppConfig appConfig;

    @Primary
    @Bean(name="RestDataSource", destroyMethod = "close")
    @Qualifier("RestDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.hikari.rest")
    public DataSource restDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Primary
    @Bean(name = "RestSqlSessionFactory")
    public SqlSessionFactory restSqlSessionFactory(@Qualifier("RestDataSource") DataSource restDataSource, ApplicationContext applicationContext) throws Exception {
        String mapperPath = appConfig.getMapperPath() == null ? "classpath*:mapper/rest/*.xml" : appConfig.getMapperPath();
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(restDataSource);
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources(mapperPath));
        return sqlSessionFactoryBean.getObject();
    }

    @Primary
    @Bean(name = "RestSqlSessionTemplate")
    public SqlSessionTemplate restSqlSessionTemplate(SqlSessionFactory restSqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(restSqlSessionFactory);
    }
}
