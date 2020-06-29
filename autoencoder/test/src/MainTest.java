package src;

import src.multilayerPerceptron.EncoderTest;
import src.multilayerPerceptron.MultiLayerPerceptronTest;
import src.multilayerPerceptron.UtilsTest;


public class MainTest {

    public static void main(String[] args){
        utilsTest();

        multiLayerPerceptronTest();

        autoEncoderTest();
    }

    public static void autoEncoderTest(){
        EncoderTest.testEncodeDecodeForFont(1);

        EncoderTest.testIfEncodeDecodeIsTheSameAsClassifyOfThePerceptron();
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
