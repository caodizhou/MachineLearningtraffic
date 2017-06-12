package test;

import machinelearningdemo.dao.MongoDao;
import machinelearningdemo.tools.DocBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by cdz on 2017/6/12.
 */
public class Test {

    @Autowired
    private MongoDao mongoDao;

    public void test(){
        DocBuilder docBuilder = new DocBuilder();
        LocalDateTime localDateTime = LocalDateTime.of(2016,1,3,0,1);
        LocalDateTime localDateTime2 = LocalDateTime.of(2016,1,4,0,1);
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        Instant instant2 = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        Date date = Date.from(instant);
        Date date2 = Date.from(instant2);
        docBuilder.addTimerange(date,date2);

    }
}
