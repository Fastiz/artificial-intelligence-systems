package src;

import java.io.IOException;

public class Main {
    public static void main(String[] args){
        try {
            PipelineAdministrator pa = new PipelineAdministrator(10);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
