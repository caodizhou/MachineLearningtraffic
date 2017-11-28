package machinelearningdemo.dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import machinelearningdemo.config.Mongodb;
import machinelearningdemo.dto.Kkflow;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * Created by cdz on 2017/6/12.
 */
@Repository
public class MongoDao {

    @Autowired
    private Mongodb mongodb;



    public long selectCountByDoc(Document document){
        MongoCollection mongoCollection = mongodb.getCollection();
        return mongoCollection.count(document);

    }

    public List<String> getkklist(Document document){
        MongoCollection mongoCollection = mongodb.getCollection();
        List<String> kklist = new ArrayList<>();
        MongoCursor<Document> cursor = mongoCollection.aggregate(

                Arrays.asList(
                        Aggregates.match(document),
                        Aggregates.group("$kkid")
                )

        ).allowDiskUse(true).iterator();
        try {
            while (cursor.hasNext()) {
                Document temp = cursor.next();
                kklist.add(temp.getString("_id"));
            }
        } finally {
            cursor.close();
        }
        return kklist;
    }

    public List<List<Kkflow>> selectGroupBykkCountByDoc(Document document,Date date1,Date date2){
        MongoCollection mongoCollection = mongodb.getCollection();
        List<Kkflow> inputlist = new ArrayList<>();
        List<Kkflow> outputlist = new ArrayList<>();
        List<String> kklist = getkklist(document);
        Document hour = new Document("hour", new Document("$hour", "$date"));
        List<Kkflow> synList = Collections.synchronizedList(new ArrayList<>());
        Document copydocument = new Document(document);
        kklist.stream().forEach(kk->{
//            Document kkdoc = new Document("kkid",kk);
            copydocument.append("kkid",kk);
            MongoCursor<Document> cursor = mongoCollection.aggregate(

                    Arrays.asList(
                            Aggregates.match(copydocument),
                            Aggregates.group(hour, Accumulators.sum("flow", 1),Accumulators.avg("avgSpeed","$speed"))
                    )

            ).allowDiskUse(true).iterator();
            try {
                int i = 0;
                double timeflow[] = new double[24];
                double timespeed[] = new double[24];
                String kkid="";
                while (cursor.hasNext()) {
                    Document temp = cursor.next();
                    timeflow[(i+8)%24] = temp.getInteger("flow")/5000.0;
                    timespeed[(i+8)%24] = temp.getDouble("avgSpeed");
                    //生成每个kk的流量对象
//                    System.out.println(temp);
                    i++;
                }
                Kkflow kkflow = new Kkflow(kk,timeflow,timespeed);
                inputlist.add(kkflow);

            } finally {
                cursor.close();
            }

//            Document date = new Document();
            copydocument.append("date",new Document("$gte",new Date(date1.getTime()+86400000))
                    .append("$lte",new Date(date2.getTime()+86400000)));

            cursor = mongoCollection.aggregate(

                    Arrays.asList(
                            Aggregates.match(copydocument),
                            Aggregates.group(hour, Accumulators.sum("flow", 1),Accumulators.avg("avgSpeed","$speed"))
                    )

            ).allowDiskUse(true).iterator();
            try {


                int i = 0;
                double timeflow[] = new double[24];
                double timespeed[] = new double[24];
                String kkid="";
                while (cursor.hasNext()) {
                    Document temp = cursor.next();
                    timeflow[temp.getInteger("hour")] = temp.getInteger("flow")/5000.0;
                    timespeed[temp.getInteger("hour")] = temp.getDouble("avgSpeed");
                    //生成每个kk的流量对象
//                    System.out.println(temp);
                    i++;
                }
                Kkflow kkflow = new Kkflow(kk,timeflow,timespeed);
               outputlist.add(kkflow);

            }
            finally {
                cursor.close();
            }
        });
        List<List<Kkflow>> pair = new ArrayList<>();
        pair.add(inputlist);
        pair.add(outputlist);
        return pair;
    }

}
