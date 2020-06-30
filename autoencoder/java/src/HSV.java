package src;

import src.multilayerPerceptron.AutoEncoder;
import src.multilayerPerceptron.MultiLayerPerceptron;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class HSV {

    private float hueMax, hueMin;
    private float saturation, alpha;

    private final Random rnd;

    private boolean fromImage;

    private String image;

    public HSV(float hueMax, float hueMin, float saturation, float alpha){
        this.hueMax = hueMax;
        this.hueMin = hueMin;
        this.saturation = saturation;
        this.alpha = alpha;
        this.rnd = new Random();
        this.fromImage = false;
    }

    public HSV(String image){
        this.fromImage = true;
        this.rnd = new Random();
        this.image = image;
    }

    public void run(){
        double alpha = 0.001;

        AutoEncoder autoEncoder = new AutoEncoder(
                (new MultiLayerPerceptron.Builder())
                        .setActivationFunction(Math::tanh)
                        .setActivationFunctionDerivative(p->(1-Math.pow(Math.tanh(p), 2)))
                        .setAlpha(alpha)
                        .setInnerLayersDimensions(Arrays.asList(5, 1, 5))
                        .setInDim(3)
                        .setOutDim(3)
                        .create()
        );

        int trainingSize = 30;
        List<List<Double>> training = new ArrayList<>(trainingSize);
        if(image != null){
            try{
                BufferedImage bi = ImageIO.read(new File(this.image));
                int height = bi.getHeight(), width = bi.getWidth();
                for(int it=0; it<trainingSize; it++){
                    int clr = bi.getRGB(rnd.nextInt(width), rnd.nextInt(height));
                    int red =   (clr & 0x00ff0000) >> 16;
                    int green = (clr & 0x0000ff00) >> 8;
                    int blue =   clr & 0x000000ff;

                    List<Double> rgb = new ArrayList<>(3);

                    rgb.add(red / 255.0 * 2 - 1);
                    rgb.add(green / 255.0 * 2 - 1);
                    rgb.add(blue / 255.0 * 2 - 1);

                    training.add(rgb);
                }
            }catch (IOException e){
                System.err.println(e);
                return;
            }
        }else{
            for(int it=0; it<trainingSize; it++){
                Color color = colorGenerator();
                List<Double> rgb = new ArrayList<>(3);

                rgb.add(color.getRed() / 255.0 * 2 - 1);
                rgb.add(color.getGreen() / 255.0 * 2 - 1);
                rgb.add(color.getBlue() / 255.0 * 2 - 1);

                training.add(rgb);
            }
        }

        int itNum = 2000000;
        for(int it=0; it<itNum; it++){
            int rndIndex = rnd.nextInt(trainingSize);

            autoEncoder.step(training.get(rndIndex));
        }

        try(BufferedWriter bf = new BufferedWriter(new FileWriter("colors"))){
            for(List<Double> col : training){
                List<Double> newColor = col.stream()
                        .mapToDouble(v->(v+1)/2*255.0).boxed().collect(Collectors.toList());
                bf.write(newColor.get(0) + " " + newColor.get(1) + " " + newColor.get(2) + "\n");
            }
            bf.write("\n");
            double step = 0.01;
            for(double pos=-1.0; pos<1.0; pos+=step){
                List<Double> newColor = autoEncoder.decode(Collections.singletonList(pos)).stream()
                        .mapToDouble(v->(v+1)/2*255.0).boxed().collect(Collectors.toList());

                bf.write(newColor.get(0) + " " + newColor.get(1) + " " + newColor.get(2) + "\n");
            }
        }catch (IOException e){
            System.err.println(e);
        }
    }

    public Color colorGenerator(){
        float hue = rnd.nextFloat()*(hueMax-hueMin) + hueMin;
        return Color.getHSBColor(hue, saturation, alpha);
    }
}
