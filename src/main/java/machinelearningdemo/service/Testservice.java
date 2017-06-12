package machinelearningdemo.service;

import machinelearningdemo.config.Mongodb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

/**
 * Created by cdz on 2017/6/9.
 */
@Service
@ComponentScan("machinelearningdemo")
//@Import(DataSourceConfig.class)
public class Testservice {

    @Value("${test}")
    private String hahaha;

    @Autowired
    private Mongodb mongodb;

    public String getHahaha() {
        return hahaha;
    }

//    @Value("#{test}")
//@Value("${test}")
public void setHahaha(String hahaha) {
    System.out.println(mongodb.toString());
        this.hahaha = hahaha;
    }
}
