package test;

import machinelearningdemo.dao.MongoDao;
import machinelearningdemo.dto.Kkflow;
import machinelearningdemo.tools.DocBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

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

@Component
public class Test {

    @Autowired
    private MongoDao mongoDao;

    public void test(){
        DocBuilder docBuilder = new DocBuilder();
        LocalDateTime localDateTime = LocalDateTime.of(2016,1,3,0,1);
        LocalDateTime localDateTime2 = LocalDateTime.of(2016,1,4,0,1);
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        Instant instant2 = localDateTime2.atZone(ZoneId.systemDefault()).toInstant();
        Date date = Date.from(instant);
        Date date2 = Date.from(instant2);
        docBuilder.addTimerange(date,date2);
        List<Kkflow> kkflowList = mongoDao.selectGroupBykkCountByDoc(docBuilder.getDocument());
    }
}
