package machinelearningdemo.service;

import com.mongodb.client.MongoCollection;
import machinelearningdemo.config.Mongodb;
import machinelearningdemo.dto.Coordinate;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class HeatmapService {
    @Autowired
    private Mongodb mongodb;

    @Autowired
    private Kkinfo kkinfo;

    public List<Coordinate> getHeatmapData(){
        List<Coordinate> flowlist = new ArrayList<>();
        Map<String,Coordinate> kkmap  =  kkinfo.getCoordinatemap();
        MongoCollection<Document> collection = mongodb.getCollection();
        for(Map.Entry<String, Coordinate> entry:kkmap.entrySet()){
            Document document = new Document("kkid",entry.getKey());

            Coordinate coordinate = entry.getValue();
            coordinate.setCount((int)collection.count(document));
            flowlist.add(coordinate);
        }
        return flowlist;
    }

}
