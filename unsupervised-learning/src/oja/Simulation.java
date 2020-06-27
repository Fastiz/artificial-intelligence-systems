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

public class Simulation {
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

        OjaAlgorithm ojaAlgorithm = new OjaAlgorithm(1, values);

        (new File("./unsupervised-learning/results/")).mkdir();
        File folder = new File("./unsupervised-learning/results/principalComponents");
        folder.mkdir();
        File[] files = folder.listFiles();
        for(File file : files) {
            file.delete();
        }

        int iteration = 1;
        for (double learnFactor = 0.00005; learnFactor <= 0.005; learnFactor += 0.000025) {
            ojaAlgorithm.setLearnFactor(learnFactor);
            ojaAlgorithm.resetWeights();
            for (int i = 0; i < 10000; i++) {
                ojaAlgorithm.step();
            }
            if(iteration == 1) {
                try (BufferedWriter bf = new BufferedWriter(new FileWriter("./unsupervised-learning/results/weights"))) {
                    Vector weights = ojaAlgorithm.getWeights();
                    for (int i = 0; i < weights.getDimension(); i++) {
                        bf.write(Double.toString(weights.get(i)) + '\n');
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            try (BufferedWriter bf = new BufferedWriter(new FileWriter("./unsupervised-learning/results/principalComponents/principalComponents" + iteration))) {
                bf.write(Double.toString(learnFactor) + '\n');
                for (String key : categories.keySet()) {
                    bf.write(Double.toString(ojaAlgorithm.getPrincipalComponent(categories.get(key))) + '\n');
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            iteration++;
        }

        System.out.println();
    }


}
