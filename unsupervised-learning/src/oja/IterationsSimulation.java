package oja;

import utils.CsvReader;
import utils.Vector;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class IterationsSimulation {
    public static void run() {
        TreeMap<String, Vector> categories;
        try {
            categories = (new CsvReader(
                    "./unsupervised-learning/europe.csv",
                    ",",
                    true)
            )
                    .read()
                    .standardizeValues()
                    .getCategories();

        } catch (
                IOException e) {
            e.printStackTrace();
            return;
        }

        List<Vector> values = new ArrayList<>(categories.values());

        OjaAlgorithm ojaAlgorithm = new OjaAlgorithm(0.000025, values);

        (new File("./unsupervised-learning/results/")).mkdir();
        File folder = new File("./unsupervised-learning/results/iterations");
        folder.mkdir();
        File[] files = folder.listFiles();
        for(File file : files) {
            file.delete();
        }

        int run = 1;
        for (int iterations = 1; iterations <= 10000; iterations += 10) {
            ojaAlgorithm.resetWeights();
            for (int i = 0; i < iterations; i++) {
                ojaAlgorithm.step();
            }
            try (BufferedWriter bf = new BufferedWriter(new FileWriter("./unsupervised-learning/results/iterations/principalComponents" + run))) {
                bf.write(Double.toString(iterations) + '\n');
                for (String key : categories.keySet()) {
                    bf.write(Double.toString(ojaAlgorithm.getPrincipalComponent(categories.get(key))) + '\n');
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            run++;
        }

    }


}
