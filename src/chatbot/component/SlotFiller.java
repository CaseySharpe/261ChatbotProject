package chatbot.component;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;
import java.awt.Color;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.awt.Color.*;

public class SlotFiller {
    Colors c = new Colors();

    /*
     * Task 1: Extract slot values from the input user message
     *
     * [Input]
     * One user message (e.g., "What's the weather in State College?")
     *
     * [Output]
     * A hash table that contains a set of (key, value) tuples, where the "key"
     * is the name of the slot (e.g., "location") and "value" is the extracted
     * value (e.g., "State College").
     *
     */
    public Hashtable<String, String> extractSlotValues(String nowInputText) {

        //initialize the hash table. You do not need to change this line of code.
        Hashtable<String, String> result = new Hashtable<String, String>();
        Set<String> keySet = c.getStandardColors().keySet();
        ArrayList<String> listOfKeys = new ArrayList<>(keySet);
        //adds user input to a list
        ArrayList<String> userInputList = new ArrayList<>();
        for (String word : nowInputText.toLowerCase().split(" ")) {
            userInputList.add(word);
        }

        //modify the following code to implement your own slot extractor


        for (String s : userInputList) {
            if (s.equals("complementary") || s.equals("complement")){
                boolean specific = false;
                for(String x :userInputList){
                    if(keySet.contains(x)){
                        result.put("ColorX Complement", x);
                        specific = true;
                    }
                    else if(x.equals("random")){
                        result.put("Random Complement", x);
                        specific = true;
                    }
                }
                if (!specific){
                    result.put("Color Complement", s);
                }
                break;
            }
            else if (keySet.contains(s)) {
                //adding value to the result hash table
                result.put("Specific Color", s);
            }
            else if (s.equals("random")){
                result.put("Random Color", s);
            }
            else if(s.equals("lighter") || s.equals("darker")){
                result.put("Modify Color", s);
            }

        }
        return result;
        }


    }







