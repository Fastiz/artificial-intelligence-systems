package src;

import src.multilayerPerceptron.MultiLayerPerceptronTest;
import src.multilayerPerceptron.UtilsTest;

public class MainTest {

    public static void main(String[] args){
        utilsTest();

        MultiLayerPerceptronTest();
    }

    private static void MultiLayerPerceptronTest(){
        //MultiLayerPerceptronTest.evenBetween0And10();

        MultiLayerPerceptronTest.and();
    }

    private static void utilsTest(){
        UtilsTest.checkIfMatrixIsTransposed();

        UtilsTest.dotProductCases();

        UtilsTest.elementwiseOperationCases();
    }
}
