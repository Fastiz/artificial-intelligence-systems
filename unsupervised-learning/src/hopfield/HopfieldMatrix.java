package hopfield;

import java.util.Arrays;
import java.util.List;

public class HopfieldMatrix {
    private final double[][] weights;
    private int[] status;

    public HopfieldMatrix(int nodes, List<int[]> values){
        weights = new double[nodes][nodes];
        status = new int[nodes];
        
        matrixInitialization(nodes, values);
    }

    private void matrixInitialization(int nodes, List<int[]> values){
        for(int i=0; i<nodes; i++){
            for(int j=0; j<nodes; j++){
                if(i==j){
                    weights[i][j] = 0;
                }else{
                    int finalI = i;
                    int finalJ = j;
                    weights[i][j] = (1.0/nodes) * values
                            .stream()
                            .mapToInt(p -> p[finalI]*p[finalJ])
                            .sum();
                }
            }
        }
    }

    public void initialize(int[] pattern){
        System.arraycopy(pattern, 0, status, 0, status.length);
    }

    public boolean step(){
        int[] newStatus = new int[status.length];

        for(int i=0; i<status.length; i++){
            double sum=0;
            for(int j=0; j<status.length; j++){
                sum += weights[i][j]*status[j];
            }
            newStatus[i] = sgn(sum);
        }

        boolean aux = Arrays.equals(status, newStatus);

        status = newStatus;

        return aux;
    }

    public int[] getStatus() {
        return status;
    }

    private int sgn(double val){
        if(val>=0)
            return 1;
        else
            return -1;
    }
}
