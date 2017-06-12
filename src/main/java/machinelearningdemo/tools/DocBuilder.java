package machinelearningdemo.tools;

import org.bson.Document;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by cdz on 2017/6/12.
 */
public class DocBuilder {
    private Document document;

    public Document getDocument() {
        return document;
    }

    public void addTimerange(Date starttime, Date endtime){
        if(document==null)
            document = new Document();
        document.append("date",new Document("$gte",starttime).append("$lte",endtime));
    }

    public void addgeolimit(double lng1,double lat1,double lng2,double lat2){
        List<List<Double>> points = new ArrayList<>(4);
        Double[] p1 = {lat1,lng1};
        Double[] p2 = {lat2,lng1};
        Double[] p3 = {lat2,lng2};
        Double[] p4 = {lat1,lng2};
        points.add(Arrays.asList(p1));
        points.add(Arrays.asList(p2));
        points.add(Arrays.asList(p3));
        points.add(Arrays.asList(p4));
        points.add(Arrays.asList(p1));
//        List<Double> p1 = new ArrayList<>();
//        p1.add(lat1);
//        p1.add(lng1);
//        List<Double> p2 = new ArrayList<>();
//        p2.add(lat2);
//        p2.add(lng1);
//        List<Double> p3 = new ArrayList<>();
//        p3.add(lat2);
//        p3.add(lng2);
//        List<Double> p4 = new ArrayList<>();
//        p4.add(lat1);
//        p4.add(lng2);
        ArrayList<List<List<Double>>> geo = new ArrayList<>(1);
        geo.add(points);
        if(document==null)
            document = new Document();
        Document geoDoc =
                new Document("$geoWithin",
                        new Document("$geometry", new Document("type", "Polygon")
                                .append("coordinates", geo)));
        document.append("geom", geo);
    }

    public void addspeedlimit(int s1,int s2){
        if(document==null)
            document = new Document();
        document.append("clsd",new Document("$gte",s1).append("$lte",s2));
    }


}