package hopfield;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Simulation {
    public static void run(){
        List<int[]> patterns = new ArrayList<>();

        patterns.add(new int[]{
                1,  1,  1,  1,  1,
                1, -1, -1, -1,  1,
                1,  1,  1,  1,  1,
                1, -1, -1, -1,  1,
                1, -1, -1, -1,  1
        });
        /*patterns.add(new int[]{
                1,  1,  1,  1, -1,
                1, -1, -1, -1,  1,
                1,  1,  1,  1, -1,
                1, -1, -1, -1,  1,
                1,  1,  1,  1, -1
        });*/
        /*patterns.add(new int[]{
                1,  1,  1,  1,  1,
                1, -1, -1, -1, -1,
                1, -1, -1, -1, -1,
                1, -1, -1, -1, -1,
                1,  1,  1,  1,  1
        });*/
        patterns.add(new int[]{
                1,  1,  1,  1, -1,
                1, -1, -1, -1,  1,
                1, -1, -1, -1,  1,
                1, -1, -1, -1,  1,
                1,  1,  1,  1, -1
        });

        //Printing letters
        System.out.println("\nPrinting the provided letters");
        patterns.forEach(Simulation::printPattern);

        //Creating the Hopfield model
        HopfieldMatrix hm = new HopfieldMatrix(patterns.get(0).length, patterns);
        System.out.println("\nCreated the hopfield model and supplied the letters");

        //Getting a random letter, dirtying it and running hopfield
        Random rnd = new Random();
        for(int t=0; t<5; t++){
            System.out.println("--------------------");
            int index = rnd.nextInt(patterns.size());
            int[] dirtyPattern = dirtyPattern(patterns.get(index), 0.05);
            runHopfieldForPattern(hm, dirtyPattern);
        }
    }

    private static void runHopfieldForPattern(HopfieldMatrix hm, int[] pattern){
        System.out.println("\nRunning Hopfield for pattern:");
        printPattern(pattern);

        //Initializing hm
        hm.initialize(pattern);

        //Running iterations
        System.out.println("\nIterating...");
        int i = 0;
        while(!hm.step()){
            System.out.println("Running iteration #"+ i++ + ":");
            printPattern(hm.getStatus());
        }

        //Printing result
        System.out.println("\nReached convergence:");
        printPattern(hm.getStatus());
    }

    private static int[] dirtyPattern(int[] pattern, double flipChance){
        int[] dirtyPattern = new int[pattern.length];
        for(int i=0; i<5; i++) {
            for (int j = 0; j < 5; j++) {
                if(Math.random() < flipChance){
                    dirtyPattern[i*5 + j] = pattern[i*5 + j] == 1 ? -1 : 1;
                }else{
                    dirtyPattern[i*5 + j] = pattern[i*5 + j];
                }
            }
        }
        if(Arrays.equals(dirtyPattern, pattern))
            dirtyPattern = dirtyPattern(pattern, flipChance);
        return dirtyPattern;
    }

    private static void printPattern(int[] pattern){
        for(int i=0; i<5; i++){
            for(int j=0; j<5; j++){
                String c = pattern[i*5 + j] == 1 ? "*" : " ";
                System.out.print(c + " ");
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }
}
