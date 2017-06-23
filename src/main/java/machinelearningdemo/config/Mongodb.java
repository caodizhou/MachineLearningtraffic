package machinelearningdemo.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cdz on 2017/6/9.
 */
public class Mongodb {
    private MongoDatabase mongoDatabase;

    private String url;

    private int port;

    private String username;

    private String database;

    private String password;

    private String collectionname;

    private MongoCollection collection;



    public void mongodbconnect() {


        ServerAddress serverAddress = new ServerAddress(url, port);
        List<ServerAddress> addrs = new ArrayList<ServerAddress>();
        addrs.add(serverAddress);
        MongoCredential credential =MongoCredential.createCredential(username, database, password.toCharArray());
        List<MongoCredential> credentials = new ArrayList<MongoCredential>();
        credentials.add(credential);
        MongoClient mongoClient = new MongoClient(addrs, credentials);
        // 连接到数据库
        mongoDatabase = mongoClient.getDatabase(database);

        System.out.println("Connect to database successfully");
        collection = mongoDatabase.getCollection(collectionname);
        System.out.println("集合选择成功");
    }



    public MongoCollection<Document> getCollection() {
        return collection;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCollectionname(String collectionname) {
        this.collectionname = collectionname;
    }

    @Override
    public String toString() {
        return "Mongodb{" +
                "mongoDatabase=" + mongoDatabase +
                ", collection='" + collectionname + '\'' +
                '}';
    }
}
