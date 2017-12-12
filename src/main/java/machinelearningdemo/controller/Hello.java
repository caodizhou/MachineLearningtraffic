package machinelearningdemo.controller;

import machinelearningdemo.dao.MongoDao;
import machinelearningdemo.service.Testservice;
import machinelearningdemo.tools.DocBuilder;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import test.Test;
import test.test2;

/**
 * Created by cdz on 2017/6/8.
 */
@Controller
@ComponentScan("machinelearningdemo.service")
@ComponentScan("test")
public class Hello {
    @Autowired
    Testservice testservice;

    @Autowired
    private MongoDao mongoDao;

    @Value("${test}")
    private String haha;

    @Autowired
    private Test test;

    @Autowired
    private test2 test2;

    @RequestMapping(value = "/lssb", method = RequestMethod.GET)
    @ResponseBody
    public String test() {
        System.out.println(testservice.getHahaha());
//        test.test();
//        test2 test2 = new test2();
        test2.generateTrainData();
        return testservice.getHahaha();
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {

        return "index";
    }

}
