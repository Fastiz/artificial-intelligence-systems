package oja;

import utils.CsvReader;
import utils.Vector;

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

        OjaAlgorithm ojaAlgorithm = new OjaAlgorithm(0.0005, values);

        for(int i = 0; i < 1000000; i++) {
            ojaAlgorithm.step();
        }

        for (String key : categories.keySet()) {
            System.out.println(ojaAlgorithm.getPrincipalComponent(categories.get(key)));
        }

        System.out.println(ojaAlgorithm.getWeights());
    }

}
