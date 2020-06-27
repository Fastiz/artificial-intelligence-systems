package src.multilayerPerceptron;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.stream.IntStream;

public class UtilsTest {
    public static void checkIfMatrixIsTransposed(){
        List<List<Double>> matrix = generateMatrix();

        List<List<Double>> transposed = Utils.transpose(matrix);

        for(int i=0; i<matrix.size(); i++){
            for(int j=0; j<matrix.get(0).size(); j++){
                assert matrix.get(i).get(j).equals(transposed.get(j).get(i));
            }
        }

    }

    public static void transposeTwoTimes(){
        List<List<Double>> matrix = generateMatrix();

        List<List<Double>> twoTimesTransposed = Utils.transpose(Utils.transpose(matrix));

        for(int i=0; i<matrix.size(); i++){
            for(int j=0; j<matrix.get(0).size(); j++){
                assert matrix.get(i).get(j).equals(twoTimesTransposed.get(i).get(j));
            }
        }
    }

    public static void dotProductCases(){
        assert Double.valueOf(Utils.dotProduct(
                Arrays.asList(1.0, 1.0, 1.0, 1.0), Arrays.asList(1.0, 1.0, 1.0, 1.0)
        )).equals(4.0);

        assert Double.valueOf(Utils.dotProduct(
                Arrays.asList(2.0, 1.0, 8.0, 1.0), Arrays.asList(1.0, 1.0, 1.0, 1.0)
        )).equals(12.0);

        assert Double.valueOf(Utils.dotProduct(
                Arrays.asList(2.0, 1.0, 3.0, 6.0), Arrays.asList(2.0, 1.0, 3.0, 1.0)
        )).equals(20.0);
    }

    public static void elementwiseOperationCases(){
        List<Double> a = Arrays.asList(1.0, 2.0, 3.0), b = Arrays.asList(2.0, 3.0, 1.0);

        BinaryOperator<Double> op;

        op = Double::sum;
        List<Double> result = Utils.elementwiseOperation(a, b, op);

        assert IntStream.range(0, result.size()).mapToObj(
                i->result.get(i).equals(op.apply(a.get(i), b.get(i)))
        ).reduce(true, (acc, newValue)->acc&&newValue);

    }

    private static List<List<Double>> generateMatrix(){
        List<List<Double>> matrix = new ArrayList<>(4);

        matrix.addAll(
                Arrays.asList(
                    Arrays.asList(
                            1.0, 2.0, 3.0, 4.0
                    ),
                    Arrays.asList(
                            3.0, 2.2, 1.3, 0.1
                    ),
                    Arrays.asList(
                            2.0, 1.0, 12.0, 7.0
                    ),
                    Arrays.asList(
                            0.1, 2.01, 3.03, 4.04
                    )
                )
        );

        return matrix;
    }
}
