package machinelearningdemo.controller;

import machinelearningdemo.service.Testservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by cdz on 2017/6/8.
 */
@Controller
@ComponentScan("machinelearningdemo.service")
public class Hello {
    @Autowired
    Testservice testservice;

    @Value("${test}")
    private String haha;

    @RequestMapping(value = "/lssb", method = RequestMethod.GET)
    @ResponseBody
    public String index() {
        System.out.println(testservice.getHahaha());
//        System.out.println(haha);
        return testservice.getHahaha();
    }

}
