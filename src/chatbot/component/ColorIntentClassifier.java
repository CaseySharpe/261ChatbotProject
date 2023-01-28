package chatbot.component;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

import static java.awt.Color.white;

public class ColorIntentClassifier {
    boolean colorExist = false;
    private static String[] intentDictionary;

    public ColorIntentClassifier() {
        this.initializeIntentDictionary();
    }

    private void initializeIntentDictionary() {
        intentDictionary = new String[]{"Generate Specific Color", "Generate Random Color", "Modify Color",
                "Color Complement", "ColorX Complement", "Random Complement"};
        System.out.print("Intents: (");

        for(int i = 0; i < intentDictionary.length; ++i) {
            System.out.print(intentDictionary[i]);
            if (i != intentDictionary.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println(")");
    }

    private Double[] calculateIntentScores(String nowInputText) {
        Double[] scoreArray = new Double[intentDictionary.length];

        for(int i = 0; i < scoreArray.length; ++i) {
            scoreArray[i] = 0.0;
        }

        ArrayList<String> specificColorDictionary = new ArrayList();
        specificColorDictionary.add("red");
        specificColorDictionary.add("orange");
        specificColorDictionary.add("yellow");
        specificColorDictionary.add("green");
        specificColorDictionary.add("blue");
        specificColorDictionary.add("purple");
        specificColorDictionary.add("white");
        specificColorDictionary.add("black");
        ArrayList<String> randomColorDictionary = new ArrayList();
        randomColorDictionary.add("random");
        randomColorDictionary.add("new");
        randomColorDictionary.add("whatever");
        ArrayList<String> modifyColorDictionary = new ArrayList();
        modifyColorDictionary.add("darker");
        modifyColorDictionary.add("lighter");
        ArrayList<String> complementaryColorDictionary = new ArrayList();
        complementaryColorDictionary.add("complementary");
        complementaryColorDictionary.add("complement");

        ArrayList<String> userInputList = new ArrayList();
        String[] var6 = nowInputText.toLowerCase().split(" ");
        int var7 = var6.length;

        int var8;
        for(var8 = 0; var8 < var7; ++var8) {
            String word = var6[var8];
            userInputList.add(word);
        }

        Iterator var11 = userInputList.iterator();

        while(var11.hasNext()) {
            String x = (String)var11.next();
            if (specificColorDictionary.contains(x)) {
                scoreArray[0] = scoreArray[0] + 0.1;
            } else if (randomColorDictionary.contains(x)) {
                scoreArray[1] = scoreArray[1] + 0.1;
            }
            else if (modifyColorDictionary.contains(x)) {
                scoreArray[2] = scoreArray[2] + 0.1;
            }
            else if (complementaryColorDictionary.contains(x)){
                scoreArray[3] = scoreArray[3] + 0.1;
                scoreArray[4] = scoreArray[4] + 0.1;
                scoreArray[5] = scoreArray[5] + 0.1;
                for(String s : userInputList){
                    if (specificColorDictionary.contains(s)){
                        scoreArray[4] = scoreArray[4] + 0.1;
                    }
                    if (randomColorDictionary.contains(s)){
                        scoreArray[5] = scoreArray[5] + 0.1;
                    }
                }
            }
        }

        if (scoreArray.length != intentDictionary.length) {
            System.err.println("The score array size does not equal to the intent array size.");
            System.exit(1);
        }

        Double[] var12 = scoreArray;
        var7 = scoreArray.length;

        for(var8 = 0; var8 < var7; ++var8) {
            Double nowValue = var12[var8];
            if (nowValue == null) {
                System.err.println("The score array contains null values.");
                System.exit(1);
            }
        }

        return scoreArray;
    }

    public String getLabel(String nowInputText) {
        Double[] intentScores = this.calculateIntentScores(nowInputText);
        Double nowMaxScore = null;
        int nowMaxIndex = -1;
        System.out.print("Intent Scores: (");

        for(int i = 0; i < intentScores.length; ++i) {
            System.out.print(intentScores[i]);
            if (i != intentScores.length - 1) {
                System.out.print(", ");
            }

            if (nowMaxScore == null || nowMaxIndex == -1 || intentScores[i] > nowMaxScore) {
                nowMaxIndex = i;
                nowMaxScore = intentScores[i];
            }
        }

        System.out.println(")");









        return intentDictionary[nowMaxIndex];
    }


}
