package src;

import src.multilayerPerceptron.MultiLayerPerceptronTest;
import src.multilayerPerceptron.UtilsTest;


public class MainTest {

    public static void main(String[] args){
        utilsTest();

        multiLayerPerceptronTest();

    }



    private static void multiLayerPerceptronTest(){
        MultiLayerPerceptronTest.and();

        MultiLayerPerceptronTest.checkIfAllWeightsAreUpdated();
    }

    private static void utilsTest(){
        UtilsTest.checkIfMatrixIsTransposed();

        UtilsTest.transposeTwoTimes();

        UtilsTest.dotProductCases();

        UtilsTest.elementwiseOperationCases();
    }
}
