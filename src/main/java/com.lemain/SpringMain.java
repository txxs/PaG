package com.lemain;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/**
 * Created by jianghuimin on 2017/1/18.
 */

@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan
//指定扫描的mapper接口所在的包
@MapperScan("com.lemain.mapper")

public class SpringMain {

    private static final String TYPE_ALIASES_PACKAGE = "com.lemain.domain";
    private static final String MAPPER_LOCATION = "classpath:/mapper/*.xml";
    @Bean
    @Autowired
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        final SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        //mybatis.typeAliasesPackage：指定domain类的基包，即指定其在*Mapper.xml文件中可以使用简名来代替全类名（看后边的UserMapper.xml介绍）
        sqlSessionFactoryBean.setTypeAliasesPackage(TYPE_ALIASES_PACKAGE);
        /*
            mybatis.mapperLocations：指定*Mapper.xml的位置
            如果不加会报org.apache.ibatis.binding.BindingException: Invalid bound statement (not found): com.blog.mapper.MessageMapper.findMessageInfo异常
            因为找不到*Mapper.xml，也就无法映射mapper中的接口方法。
         */
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_LOCATION));
        return sqlSessionFactoryBean.getObject();
    }

    public static void main(String... arg){
        SpringApplication.run(SpringMain.class,arg);
    }
}
