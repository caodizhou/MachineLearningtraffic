package test;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import machinelearningdemo.config.Mongodb;
import machinelearningdemo.dao.MongoDao;
import machinelearningdemo.dto.Kkflow;
import machinelearningdemo.tools.DocBuilder;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;


@Component
public class test2 {

    @Autowired
    private Mongodb mongodb;

    void generateTrainData(){
        MongoDatabase mongoDatabase =  mongodb.getMongoDatabase();
        mongoDatabase.createCollection("trainData");
        MongoCollection<Document> collection = mongoDatabase.getCollection("trainData");
        LocalDateTime localDateTime = LocalDateTime.of(2014,9,1,0,1);
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        Date date = Date.from(instant);
        DocBuilder docBuilder = new DocBuilder();
        Document hour = new Document("hour", new Document("$hour", "$date"));
        docBuilder.addkkidlimit("310003000054");
        for(int i=0;i<20;i++){
            Date date2 = new Date(date.getTime() + 86400000);
            docBuilder.addTimerange(date,date2);
            Document document = docBuilder.getDocument();
            MongoCursor<Document> cursor = mongodb.getCollection().aggregate(

                    Arrays.asList(
                            Aggregates.match(document),
                            Aggregates.group(hour, Accumulators.sum("flow", 1))
                    )

            ).allowDiskUse(true).iterator();
            try {
                int j = 0;
                double timeflow[] = new double[24];
                String kkid="";
                while (cursor.hasNext()) {
                    Document temp = cursor.next();
                    timeflow[j] = temp.getInteger("flow");
                    j++;
                }
//                Document doc = new Document("");


            } finally {
                cursor.close();
            }
        }
    }
}
