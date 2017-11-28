package test;

import machinelearningdemo.dao.MongoDao;
import machinelearningdemo.dto.Kkflow;
import machinelearningdemo.joone.Traffic_NeuralNet;
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
        LocalDateTime localDateTime = LocalDateTime.of(2014,9,1,0,1);
        LocalDateTime localDateTime2 = LocalDateTime.of(2014,9,2,0,1);
        LocalDateTime testTime = LocalDateTime.of(2014,9,7,0,1);
        LocalDateTime testTime2 = LocalDateTime.of(2014,9,8,0,1);
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        Instant instant2 = localDateTime2.atZone(ZoneId.systemDefault()).toInstant();
        Date date = Date.from(instant);
        Date date2 = Date.from(instant2);
        Date date3 = new Date(Date.from(instant).getTime()+3600000);
        Date date4 = new Date(Date.from(instant2).getTime()+3600000);
        List<Kkflow> kkflowinput = new ArrayList<>(10000);
        List<Kkflow> kkflowoutput = new ArrayList<>(10000);
        DocBuilder docBuilder = new DocBuilder();
//        docBuilder.addkkidlimit("310003000054");
        for (int i=0;i<7;i++){
            docBuilder.addTimerange(date,date2);
            kkflowinput.addAll(mongoDao.selectGroupBykkCountByDoc(docBuilder.getDocument(),date,date2).get(0));
            docBuilder.addTimerange(date3,date4);
            kkflowoutput.addAll(mongoDao.selectGroupBykkCountByDoc(docBuilder.getDocument(),date3,date4).get(1));
            date = new Date(date.getTime() + 86400000);
            date2 = new Date(date2.getTime() + 86400000);
            date3 = new Date(date3.getTime() + 86400000);
            date4 = new Date(date4.getTime() + 86400000);

        }
        double[][] inputArray  = new double[kkflowinput.size()][24];
        double[][] outputArray  = new double[kkflowoutput.size()][24];
        for(int i=0;i<7;i++){
            inputArray[i] = kkflowinput.get(i).todoublearray();
        }
        for(int i=0;i<7;i++){
            outputArray[i] = kkflowoutput.get(i).todoublearray();
        }
        List<double[][]> testdatalist = gettestdata(testTime,testTime2);
        Traffic_NeuralNet traffic_neuralNet = new Traffic_NeuralNet();
        traffic_neuralNet.setDesiredOutputArray(outputArray);
        traffic_neuralNet.setInputArray(inputArray);
        traffic_neuralNet.setTestArray(testdatalist.get(0));
        traffic_neuralNet.initNeuralNet();
        traffic_neuralNet.train();
        traffic_neuralNet.interrogate();
    }
    private List<double[][]> gettestdata(LocalDateTime localDateTime1,LocalDateTime localDateTime2){
        List<double[][]> datalist = new ArrayList<>();
        Instant instant = localDateTime1.atZone(ZoneId.systemDefault()).toInstant();
        Instant instant2 = localDateTime2.atZone(ZoneId.systemDefault()).toInstant();
        Date date = Date.from(instant);
        Date date2 = Date.from(instant2);
        Date date3 = new Date(Date.from(instant).getTime()+86400000);
        Date date4 = new Date(Date.from(instant2).getTime()+86400000);
        List<Kkflow> kkflowinput = new ArrayList<>(10000);
        List<Kkflow> kkflowoutput = new ArrayList<>(10000);
        DocBuilder docBuilder = new DocBuilder();
//        docBuilder.addkkidlimit("310003000054");
        for (int i=0;i<7;i++){
            docBuilder.addTimerange(date,date2);
            kkflowinput.addAll(mongoDao.selectGroupBykkCountByDoc(docBuilder.getDocument(),date,date2).get(0));
            docBuilder.addTimerange(date3,date4);
            kkflowoutput.addAll(mongoDao.selectGroupBykkCountByDoc(docBuilder.getDocument(),date3,date4).get(1));
            date = new Date(date.getTime() + 86400000);
            date2 = new Date(date2.getTime() + 86400000);
            date3 = new Date(date3.getTime() + 86400000);
            date4 = new Date(date4.getTime() + 86400000);

        }
        double[][] inputArray  = new double[kkflowinput.size()][24];
        double[][] outputArray  = new double[kkflowoutput.size()][24];
        for(int i=0;i<7;i++){
            inputArray[i] = kkflowinput.get(i).todoublearray();
        }
        for(int i=0;i<7;i++){
            outputArray[i] = kkflowoutput.get(i).todoublearray();
        }
        datalist.add(inputArray);
        datalist.add(outputArray);
        return datalist;
    }

}
