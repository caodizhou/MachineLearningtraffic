package machinelearningdemo.service;

import machinelearningdemo.dto.Coordinate;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.HashMap;

@Component
public class Kkinfo {

    private HashMap<String, Coordinate> coordinatemap;


    public Kkinfo(){
        File ff = new File("d:/kk_info.txt");
        coordinatemap = new HashMap<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(ff));
            String templine = br.readLine();
//        StringBuffer sf = new StringBuffer("");
            while ((templine = br.readLine()) != null) {
                String split[] = templine.split(";");
                String id = split[1];
                if (split[7].equals("") || split[8].equals("")) continue;
                Coordinate c = new Coordinate(Double.valueOf(split[8]), Double.valueOf(split[7]));
                coordinatemap.put(id,c);
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}
