package com.example.score.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.baomidou.mybatisplus.autoconfigure.SpringBootVFS;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;

/**
 * @author lhz
 */

@EnableTransactionManagement
@MapperScan(basePackages = {"com.example.score.mapper"},
        sqlSessionTemplateRef = "appSqlSessionTemplate"
        //Cannot use both: sqlSessionTemplate and sqlSessionFactory together. sqlSessionFactory is ignored
        //sqlSessionFactoryRef = "appSqlSessionFactory"
)
@Configuration
public class MybatisPlusConfig {

    @Autowired
    private MybatisPlusProperties properties;

    @Autowired
    private ResourceLoader resourceLoader = new DefaultResourceLoader();

    @Autowired(required = false)
    private Interceptor[] interceptors;

    @Autowired(required = false)
    private DatabaseIdProvider databaseIdProvider;

    @Autowired
    private Environment env;

    @Autowired
    private DruidProperties druidProperties;


    @Bean
    @Profile(value={"dev","prod","test"})
    @ConfigurationProperties("spring.datasource.druid.master")
    public DataSource masterDataSource() {
        DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
        return druidProperties.dataSource(dataSource);
    }


    @Bean(name = "appSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactoryBean(DataSource dataSource,
                                                   MybatisPlusProperties properties,
                                                   @Qualifier("webApplicationContext") ResourceLoader resourceLoader,
                                                   ApplicationContext applicationContext) throws Exception {
        return getSqlSessionFactory(dataSource,
                properties,
                resourceLoader,
                null,
                null,
                applicationContext);
    }

    @Bean(name = "appSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(MybatisPlusProperties properties,
                                                 @Qualifier("appSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return getSqlSessionTemplate(sqlSessionFactory, properties);
    }

    private SqlSessionFactory getSqlSessionFactory(
            DataSource dataSource,
            MybatisPlusProperties properties,
            ResourceLoader resourceLoader,
            Interceptor[] interceptors,
            DatabaseIdProvider databaseIdProvider,
            ApplicationContext applicationContext
    ) throws Exception {
        MybatisSqlSessionFactoryBean mybatisPlus = new MybatisSqlSessionFactoryBean();
        //全局设置
        GlobalConfig globalConfig = new GlobalConfig();
        //元对象字段填充控制器
        globalConfig.setMetaObjectHandler(new MyMetaObjectHandler());
        mybatisPlus.setGlobalConfig(globalConfig);

        //设置数据源
        mybatisPlus.setDataSource(dataSource);
        mybatisPlus.setVfs(SpringBootVFS.class);
        //configLocation配置
        if (StringUtils.hasText(properties.getConfigLocation())) {
            mybatisPlus.setConfigLocation(resourceLoader.getResource(properties.getConfigLocation()));
        }
        // Mybatis plugin list
        if (!ObjectUtils.isEmpty(interceptors)) {
            mybatisPlus.setPlugins(interceptors);
        }
        // 设置DatabaseIdProvider
        if (databaseIdProvider != null) {
            mybatisPlus.setDatabaseIdProvider(databaseIdProvider);
        }
        //原生 MyBatis 所支持的配置
        if (properties.getConfiguration() != null) {
            mybatisPlus.setConfiguration(properties.getConfiguration());
        }
        // 指定外部化 MyBatis Properties 配置，通过该配置可以抽离配置，实现不同环境的配置部署
        if (properties.getConfigurationProperties() != null) {
            mybatisPlus.setConfigurationProperties(properties.getConfigurationProperties());
        }
        // MyBaits 别名包扫描路径，通过该属性可以给包中的类注册别名，注册后在 Mapper 对应的 XML 文件中可以直接使用类名
        if (StringUtils.hasLength(properties.getTypeAliasesPackage())) {
            mybatisPlus.setTypeAliasesPackage(properties.getTypeAliasesPackage());
        }
        // 枚举类扫描路径，如果配置了该属性，会将路径下的枚举类进行注入，让实体类字段能够简单快捷的使用枚举属性
        if (StringUtils.hasLength(properties.getTypeEnumsPackage())) {
            mybatisPlus.setTypeEnumsPackage(properties.getTypeEnumsPackage());
        }
        // 该配置请和 typeAliasesPackage 一起使用，如果配置了该属性，则仅仅会扫描路径下以该类作为父类的域对象
        if (properties.getTypeAliasesSuperType() != null) {
            mybatisPlus.setTypeAliasesSuperType(properties.getTypeAliasesSuperType());
        }
        // TypeHandler 扫描路径，如果配置了该属性，SqlSessionFactoryBean 会把该包下面的类注册为对应的 TypeHandler
        if (StringUtils.hasLength(properties.getTypeHandlersPackage())) {
            mybatisPlus.setTypeHandlersPackage(properties.getTypeHandlersPackage());
        }
        // MyBatis Mapper 所对应的 XML 文件位置
        if (!ObjectUtils.isEmpty(properties.getMapperLocations())) {
            // 设置mapper.xml文件的路径，必须放在resources目录下
            //mybatisPlus.setMapperLocations(new PathMatchingResourcePatternResolver()
            //        .getResources("classpath*:mapper/*.xml"));
            mybatisPlus.setMapperLocations(properties.resolveMapperLocations());
        }
        return mybatisPlus.getObject();
    }
    private SqlSessionTemplate getSqlSessionTemplate(SqlSessionFactory sqlSessionFactory, MybatisPlusProperties properties) {
        ExecutorType executorType = properties.getExecutorType();
        if (executorType != null) {
            return new SqlSessionTemplate(sqlSessionFactory, executorType);
        } else {
            return new SqlSessionTemplate(sqlSessionFactory);
        }
    }
}
