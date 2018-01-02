package test;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import machinelearningdemo.config.Mongodb;
import machinelearningdemo.dao.MongoDao;
import machinelearningdemo.dto.Kkflow;
import machinelearningdemo.joone.Traffic_NeuralNet;
import machinelearningdemo.tools.DocBuilder;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


@Component
public class test2 {

    @Autowired
    private Mongodb mongodb;

    public void generateTrainData() {
        MongoDatabase mongoDatabase = mongodb.getMongoDatabase();
//        mongoDatabase.createCollection("trainData");
        MongoCollection<Document> collection = mongoDatabase.getCollection("trainData");
        LocalDateTime localDateTime = LocalDateTime.of(2014, 9, 1, 0, 1);
        int day = localDateTime.getDayOfMonth();
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        Date date = Date.from(instant);
//        long sDate = date.getTime();
        DocBuilder docBuilder = new DocBuilder();
        Document hour = new Document("hour", new Document("$hour", "$date"));
        docBuilder.addkkidlimit("310003000054");
//        collection.insertOne(new Document().append("ls","sb"));
        for (int i = 0; i < 20; i++) {
            Date date2 = new Date(date.getTime() + 86400000);
            docBuilder.addTimerange(date, date2);
            Document document = docBuilder.getDocument();
            MongoCursor<Document> cursor = mongodb.getCollection().aggregate(

                    Arrays.asList(
                            Aggregates.match(document),
                            Aggregates.group(hour, Accumulators.sum("flow", 1))
                    )

            ).allowDiskUse(true).iterator();
            Document doc = new Document("day", day);
            try {
                int j = 0;
                double timeflow[] = new double[24];
                String kkid = "";
//                List<Integer> hourlist = new ArrayList<>(24);
                int[] hourarray = new int[24];
                while (cursor.hasNext()) {
                    Document temp = cursor.next();
                    int flow = temp.getInteger("flow");
                    Document _id = (Document) (temp.get("_id"));

//                    hourlist.set(_id.getInteger("hour"), flow);
                    hourarray[(_id.getInteger("hour") + 8) % 24] = flow;

                }
                List<Integer> list = new ArrayList<>();
                for (int t : hourarray) {
                    list.add(t);
                }
                doc.append("datetime", date.getTime());
                doc.append("hourlist", list);
                System.out.println(doc);
            } finally {
                cursor.close();
            }
            collection.insertOne(doc);
            day++;
            date = date2;
        }
    }

    public void test() {
        LocalDateTime localDateTime = LocalDateTime.of(2014, 9, 1, 0, 1);
        int day = localDateTime.getDayOfMonth();
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        Date date = Date.from(instant);
        long time = date.getTime();
        MongoDatabase mongoDatabase = mongodb.getMongoDatabase();
//        mongoDatabase.createCollection("trainData");
        MongoCollection<Document> collection = mongoDatabase.getCollection("trainData");
        List<Kkflow> kkflowinput = new ArrayList<>(100);
        List<Kkflow> kkflowoutput = new ArrayList<>(100);
        double[][] inputArray  = new double[15][24];
        double[][] outputArray  = new double[15][24];
        double[][] testArray  = new double[20][24];

        for (int i = 0; i < 15; i++) {
            Document document = new Document("day", i+1);
            MongoCursor<Document> cursor = collection.find(document).iterator();
            try {
                while (cursor.hasNext()) {
                    Document temp = cursor.next();
                    List<Integer> list = (ArrayList<Integer>) temp.get("hourlist");
                    long datetime  = temp.getLong("datetime");
//                    inputArray[i] = new double[24];
//                    outputArray[i] = new double[24];
                    for(int j=0;j<24;j++){

                        inputArray[i][j] = (datetime+j*3600000-time)/3600000d/1000;
                        outputArray[i][j] = list.get(j)/5000d;
                    }


                }
            } finally {
                cursor.close();
            }
        }
//        for (int i = 15; i < 20; i++) {
//            Document document = new Document("day", i+1);
//            MongoCursor<Document> cursor = collection.find(document).iterator();
//            try {
//                while (cursor.hasNext()) {
//                    Document temp = cursor.next();
//                    List<Integer> list = (ArrayList<Integer>) temp.get("hourlist");
//                    long datetime  = temp.getLong("datetime");
////                    testArray[i] = new double[24];
//                    for(int j=0;j<24;j++){
//                        testArray[i-15][j] = (datetime+j*3600000-time)/time;
//                    }
//
//                }
//            } finally {
//                cursor.close();
//            }
//        }
        for (int i = 0; i < 20; i++) {
            Document document = new Document("day", i+1);
            MongoCursor<Document> cursor = collection.find(document).iterator();
            try {
                while (cursor.hasNext()) {
                    Document temp = cursor.next();
                    List<Integer> list = (ArrayList<Integer>) temp.get("hourlist");
                    long datetime  = temp.getLong("datetime");
//                    testArray[i] = new double[24];
                    for(int j=0;j<24;j++){
                        testArray[i][j] = (datetime+j*3600000-time)/3600000d/1000;
                    }

                }
            } finally {
                cursor.close();
            }
        }
        Traffic_NeuralNet traffic_neuralNet = new Traffic_NeuralNet();
        traffic_neuralNet.setDesiredOutputArray(outputArray);
        traffic_neuralNet.setInputArray(inputArray);
        traffic_neuralNet.setTestArray(testArray);
        traffic_neuralNet.initNeuralNet();
        traffic_neuralNet.train();
        traffic_neuralNet.interrogate();
    }
}
