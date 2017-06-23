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
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
        return null;
    }

    public List<Kkflow> selectGroupBykkCountByDoc(Document document){
        MongoCollection mongoCollection = mongodb.getCollection();
        List<Kkflow> kkflowList = new ArrayList<>();
        List<String> kklist = getkklist(document);
        Document hour = new Document("hour", new Document("$hour", "$date"));
        List<Kkflow> synList = Collections.synchronizedList(new ArrayList<>());
        kklist.parallelStream().forEach(kk->{
            Document kkdoc = new Document("kkid",kk);
            MongoCursor<Document> cursor = mongoCollection.aggregate(

                    Arrays.asList(
                            Aggregates.match(kkdoc),
                            Aggregates.group(hour, Accumulators.sum("flow", 1),Accumulators.avg("avgSpeed","$speed"))
                    )

            ).allowDiskUse(true).iterator();
            try {
                int i = 0;
                int timeflow[] = new int[24];
                double timespeed[] = new double[24];
                String kkid="";
                while (cursor.hasNext()) {
                    Document temp = cursor.next();
                    if(kkid==""){
                        kkid = temp.getString("_id");
                    }
                    timeflow[(i+8)%24] = temp.getInteger("count");
                    timespeed[(i+8)%24] = temp.getDouble("avgSpeed");
                    //生成每个kk的流量对象
                    System.out.println(temp);
                }
                Kkflow kkflow = new Kkflow(kkid,timeflow,timespeed);
                kkflowList.add(kkflow);

            } finally {
                cursor.close();
            }

        });
        return kkflowList;
    }

}
