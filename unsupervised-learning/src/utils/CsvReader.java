package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.DoubleSummaryStatistics;

import static utils.Statistics.mean;
import static utils.Statistics.std;

public class CsvReader {
    private final String filePath;
    private final String separator;
    private final Map<String, Vector> categories;
    private final boolean ignoreFistLine;
    private int vectorDim;

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

                vectorDim = values.length - 1;
            }
        }
        return this;
    }

    public CsvReader normalizeValues(){
        List<Double> maxs = new ArrayList<>(vectorDim);
        for(int vectorIndex = 0; vectorIndex < vectorDim; vectorIndex++){
            double max = 0;
            for(String key: categories.keySet()){
                double newVal = categories.get(key).get(vectorIndex);
                if(newVal > max){
                    max = newVal;
                }
            }
            maxs.add(max);
        }

        for(String key: categories.keySet()){
            Vector v = categories.get(key);
            List<Double> newValues = new ArrayList<>(vectorDim);
            for(int vectorIndex = 0; vectorIndex < vectorDim; vectorIndex++){
                newValues.add(v.get(vectorIndex) / maxs.get(vectorIndex));
            }
            Vector newVector = new Vector(newValues);
            categories.put(key, newVector);
        }

        return this;
    }

    public CsvReader standardizeValues(){
        List<Double> means = new ArrayList<>(vectorDim);
        List<Double> stds = new ArrayList<>(vectorDim);

        for(int vectorIndex = 0; vectorIndex < vectorDim; vectorIndex++){
            List<Double> values = new ArrayList<>();
            for(String key: categories.keySet()){
                values.add(categories.get(key).get(vectorIndex));
            }

            means.add(mean(values));
            stds.add(std(values));
        }

        for(String key: categories.keySet()){
            Vector v = categories.get(key);
            List<Double> newValues = new ArrayList<>(vectorDim);
            for(int vectorIndex = 0; vectorIndex < vectorDim; vectorIndex++){
                newValues.add((v.get(vectorIndex) - means.get(vectorIndex))/stds.get(vectorIndex));
            }
            Vector newVector = new Vector(newValues);
            categories.put(key, newVector);
        }

        return this;
    }

    public Map<String, Vector> getCategories() {
        return categories;
    }
}
