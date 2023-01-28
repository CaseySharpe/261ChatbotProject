/*
 * chatbot.component is added for Assignment 3 (Language Understanding)
 *
 * DomainClassifier.java is added for Assignment 3 (Language
 * Understanding)
 */

package chatbot.component;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

public class DomainClassifier {
    Colors c = new Colors();
    private static String[] domainDictionary;

    public DomainClassifier() {
        initializeDomainDictionary();
    }


    private void initializeDomainDictionary() {
        //list all the domains
        domainDictionary = new String[]{"Single Color", "Other"};
        //create the display string
        System.out.print("Domains: (");
        for(int i=0;i<domainDictionary.length;i++) {
            System.out.print(domainDictionary[i]);
            if(i!=domainDictionary.length-1) {
                System.out.print(", ");
            }
        }
        System.out.println(")");
    }


    private Double[] calculateDomainScores(String nowInputText) {

        //DO NOT change the following 4 lines
        //initiate all the scores to 0.0
        Double[] scoreArray = new Double[domainDictionary.length];
        for(int i=0;i<scoreArray.length;i++) {
            scoreArray[i] = 0.0;
        }

        //============= Please Modify Here (begins) ===============
        Set<String> keySet = c.getStandardColors().keySet();
        ArrayList<String> listOfKeys = new ArrayList<>(keySet);
        HashMap<String, Double> singleColorDictionary = new HashMap<>();
        for (String x : listOfKeys){
            singleColorDictionary.put(x, .2);
        }
        singleColorDictionary.put("random", .1);
        singleColorDictionary.put("complement", .1);
        singleColorDictionary.put("complementary", .1);
        singleColorDictionary.put("lighter", .1);
        singleColorDictionary.put("darker", .1);
        singleColorDictionary.put("generate", .1);
        singleColorDictionary.put("color", .2);
        singleColorDictionary.put("show", .2);
        singleColorDictionary.put("whatever", .1);
        singleColorDictionary.put("new", .1);


        ArrayList<String> commonWords = new ArrayList<>();
        commonWords.add("i");
        commonWords.add("that");
        commonWords.add("the");
        commonWords.add("please");
        commonWords.add("a");
        commonWords.add("is");
        commonWords.add("it");
        commonWords.add("all");
        commonWords.add("what");
        commonWords.add("should");
        commonWords.add("for");
        commonWords.add("do");
        commonWords.add("need");
        commonWords.add("an");
        commonWords.add("bring");
        commonWords.add("where");
        commonWords.add("go");
        commonWords.add("to");
        commonWords.add("something");
        commonWords.add("want");
        commonWords.add("the");
        commonWords.add("at");
        commonWords.add("where");
        commonWords.add("in");
        commonWords.add("will");
        commonWords.add("be");




        ArrayList<String> userInputList = new ArrayList<>();
        for(String word : nowInputText.toLowerCase().split(" ")) {
            userInputList.add(word);
        }

        int i = 0;

        for(String x : userInputList){
            if(singleColorDictionary.containsKey(x)){
                scoreArray[0] = scoreArray[0] + singleColorDictionary.get(x);
            }
            else if(!commonWords.contains(x)){
                scoreArray[1] = scoreArray[1] + .10;
            }
        }


        //============= Please Modify Here (ends) ===============

        //Do not change the following lines
        //Check before returning the scoreArray
        if(scoreArray.length!=domainDictionary.length) {
            System.err.println("The score array size does not equal to the domain array size.");
            System.exit(1);
        }
        for(Double nowValue: scoreArray) {
            if(nowValue==null) {
                System.err.println("The score array contains null values.");
                System.exit(1);
            }
        }
        return scoreArray;
    }


    /**
     * Input:
     * 	nowInputText: the message that the user sent to your chatbot
     *
     * Output:
     * 	the label (domain) name string
     *
     * @param nowInputText	An English message sent from the user.
     * @return 				The name of the domain.
     *
     */
    public String getLabel(String nowInputText) {

        //get the score array
        Double[] intentScores = calculateDomainScores(nowInputText);

        //print the scores of each domain
        Double nowMaxScore = null;
        int nowMaxIndex = -1;
        System.out.print("Domain Scores: (");
        for(int i=0;i<intentScores.length;i++){
            System.out.print(intentScores[i].doubleValue());
            if(i!=intentScores.length-1) {
                System.out.print(", ");
            }
            if(nowMaxScore==null||nowMaxIndex==-1||intentScores[i].doubleValue()>nowMaxScore.doubleValue()) {
                nowMaxIndex = i;
                nowMaxScore = intentScores[i].doubleValue();
            }
        }
        System.out.println(")");

        return domainDictionary[nowMaxIndex];
    }

}

