package machinelearningdemo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by cdz on 2017/6/9.
 */
@Configuration
@EnableWebMvc
public class DataSourceConfig {
    @Value("${mongodb.url}")
    private String url;

    @Value("${mongodb.port}")
    private int port;

    @Value("${mongodb.username}")
    private String username;

    @Value("${mongodb.database}")
    private String database;

    @Value("${mongodb.password}")
    private String password;

    @Value("${mongodb.collection}")
    private String collectionname;

    @Bean(name = "dataSource")
    public  Mongodb getDataSource() {

            Mongodb mongodb = new Mongodb();
            mongodb.setUrl(url);
            mongodb.setPort(port);
            mongodb.setUsername(username);
            mongodb.setPassword(password);
            mongodb.setDatabase(database);
            mongodb.setCollectionname(collectionname);
            mongodb.mongodbconnect();
            return mongodb;
    }
}
