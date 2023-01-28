package chatbot.component;

import java.awt.Color;
import java.util.HashMap;
import java.util.Random;
import javax.swing.*;

public class Colors {

    private int r;
    private int g;
    private int b;

    public Colors (int r, int g, int b){
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public Colors(){}

    // java.awt.Color has standard roygbiv colors built in, however this is just to demonstrate how new
    //standard colors are added
    private HashMap<String, Color> standardColors(){
        HashMap<String , Color> standardColorsMap = new HashMap<String, Color>();

        Color red = new Color(255, 0, 0);
        Color orange = new Color(255, 127, 0);
        Color yellow = new Color(255, 255, 0);
        Color green = new Color(0, 255, 0);
        Color blue = new Color(0, 0, 255);
        Color purple = new Color(75, 0, 130);

        standardColorsMap.put("red", red);
        standardColorsMap.put("orange", orange);
        standardColorsMap.put("yellow", yellow);
        standardColorsMap.put("green", green);
        standardColorsMap.put("blue", blue);
        standardColorsMap.put("purple", purple);

        return standardColorsMap;
    }

    private Color generateRandomColor(){
        Random rand = new Random();
        int randomR = rand.nextInt(255);
        int randomG = rand.nextInt(255);
        int randomB = rand.nextInt(255);

        Color newRand = new Color(randomR, randomG, randomB);
        return newRand;
    }


    public Color modifyColor(Color c, String x){
        Color modified = c;
        if(x.equals("lighter")){
            modified = c.brighter();
        }
        if(x.equals("darker")){
            modified = c.darker();
        }
        return modified;
    }

    public Color getComplementary(Color c){
        Color complement = new Color((255 - c.getRed()), (255 - c.getGreen()), (255 - c.getBlue()));
        return complement;
    }

    public HashMap<String, Color> getStandardColors(){return standardColors();}
    public Color getRandomColor(){return generateRandomColor();}


}
