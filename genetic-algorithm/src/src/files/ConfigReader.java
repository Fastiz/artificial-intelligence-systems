package src.files;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ConfigReader {
    private final Map<String, String> properties;

    public ConfigReader(String path) throws IOException {
        this.properties = new HashMap<>();
        try(BufferedReader bf = new BufferedReader(new FileReader(path))){

            for(String line=bf.readLine(); line != null; line=bf.readLine()){
                if (line.toCharArray()[0] != '#') {
                    String[] values = line.split("=");
                    properties.put(values[0], values[1]);
                }
            }
        }
    }

    private String getValue(String key){
        return properties.get(key);
    }
}
