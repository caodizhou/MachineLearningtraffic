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

import java.util.ArrayList;
import java.util.Arrays;
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

    public List<Kkflow> selectCountGroupByDoc(Document document){
        MongoCollection mongoCollection = mongodb.getCollection();
        List<Kkflow> kkflowList = new ArrayList<>();
        MongoCursor<Document> cursor = mongoCollection.aggregate(

                Arrays.asList(
                        Aggregates.match(document),
                        Aggregates.group("$kkid", Accumulators.sum("count", 1))
//                                Aggregates.sort(Sorts.ascending("count"))
                )

        ).allowDiskUse(true).iterator();
        try {
            while (cursor.hasNext()) {
                Document temp = cursor.next();
                //生成每个kk的流量对象
                Kkflow kkflow = new Kkflow(temp.getString("_id"),temp.getInteger("count"));
                kkflowList.add(kkflow);
            }
        } finally {
            cursor.close();
        }
        return kkflowList;
    }

}
