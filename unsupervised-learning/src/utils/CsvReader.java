package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class CsvReader {
    private final String filePath;
    private final String separator;
    private final Map<String, Vector> categories;
    private final boolean ignoreFistLine;

    public CsvReader(String filePath, String separator, boolean ignoreFirstLine){
        this.filePath = filePath;
        this.separator = separator;
        this.ignoreFistLine = ignoreFirstLine;
        this.categories = new HashMap<>();
    }

    public CsvReader read() throws IOException {
        try(BufferedReader bf = new BufferedReader(new FileReader(filePath))){
            if(ignoreFistLine)
                bf.readLine();

            for(String line = bf.readLine(); line != null ; line = bf.readLine()){
                String[] values = line.split(separator);

                categories.put(values[0], new Vector(Arrays.asList(values).subList(1, values.length)
                        .stream()
                        .map(Double::valueOf)
                        .collect(Collectors.toList()))
                );
            }
        }
        return this;
    }

    public Map<String, Vector> getCategories() {
        return categories;
    }
}
