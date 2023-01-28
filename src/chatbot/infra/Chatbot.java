package chatbot.infra;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Iterator;

import chatbot.component.*;

import javax.swing.*;

import static java.awt.Color.BLACK;
import static java.awt.Color.white;

public class Chatbot {

    private String userName = "YOUR NAME HERE";
    private String botName = "BOT NAME HERE";


    //=====Code Added for Assignment 3 (Language Understanding) Begins=====

    //one domain classifier
    private DomainClassifier nowDomainClassifier;

    //each domain has one intent classifier
    private ColorIntentClassifier colorIntentClassifier;
    ArrayList<Color> colorHistory = new ArrayList<>();
    ArrayList<JFrame> frameList = new ArrayList<>();
    private SlotFiller nowSlotFiller;
    private DialogueManager nowDialogueManager;
    //=====Code Added for Assignment 3 (Language Understanding) Ends=====


    public Chatbot(String userName, String botName) {

        this.userName = userName;
        this.botName = botName;

        //=====Code Added for Assignment 3 (Language Understanding) Begins=====

        this.nowDomainClassifier = new DomainClassifier();
        this.colorIntentClassifier = new ColorIntentClassifier();

        //=====Code Added for Assignment 4 =====

        this.nowSlotFiller = new SlotFiller();

        this.nowDialogueManager = new DialogueManager();

    }

    /*
     * Task 3: Add a response in Chatbot.java to respond to user message
     *
     * Please modify the getResponse() method in the Chatbot class to respond
     * to three or more different user messages meaningfully. I provided one
     * example in the getResponse().
     */

    public String getResponse(String nowInputText) {


        //=====Code Added for Assignment 3 (Language Understanding) Begins=====

        System.out.println("--------------");
        System.out.println("User Message: "+nowInputText);
        String nowDomain = nowDomainClassifier.getLabel(nowInputText);
        System.out.println("Domain: "+nowDomain);
        String nowIntent = "";
        Hashtable<String, String> extractedSlotValues = this.nowSlotFiller.extractSlotValues(nowInputText);
        if(!nowDomain.equals("Other")) {//in-domain message
                if(nowDomain.equals("Single Color")) {//Single Color domain
                nowIntent = colorIntentClassifier.getLabel(nowInputText);
                    if (nowIntent.equals("Modify Color")){
                        if(colorHistory.isEmpty()){
                            nowIntent = "Modify Rejected";
                        }
                    }
                    if (nowIntent.equals("Color Complement")){
                        if(colorHistory.isEmpty()){
                            nowIntent = "Complement Rejected";
                        }
                    }
            }else {//this shouldn't happen
                System.err.println("Domain name is incorrect!");
                System.exit(1);
                return null;
            }
        }
        String nowDialogueStatus = "Domain = "+nowDomain+"; Intent = "+nowIntent;
        nowDialogueStatus += slotTableToString(extractedSlotValues);
        System.out.println("nowDialogueStatus: "+nowDialogueStatus);

        //Dialogue Management
        String nextState = nowDialogueManager.getNextState(nowDomain, nowIntent, extractedSlotValues);
        System.out.println("nextState: "+nextState);
        String nowResponse = nowDialogueManager.executeStateAndGetResponse(nextState);
        System.out.println("nowResponse: "+nowResponse);
        for(JFrame frame : frameList){
            frame.dispose();
        }
        JFrame frame = popup(extractedSlotValues, nowIntent, nowResponse);
        frameList.add(frame);

       /*
        System.out.println("Intent: "+nowIntent);
        String nowResponse = "Domain = "+nowDomain+"; Intent = "+nowIntent+"; ";
        nowResponse = nowResponse + this.slotTableToString(extractedSlotValues);
        */
        return nowResponse;

        //=====Code Added for Assignment 3 (Language Understanding) Ends=====

    }

    public JFrame popup(Hashtable<String, String> extractedSlotValues, String nowIntent, String nowResponse){
        if(nowIntent.equals("Generate Specific Color") && !nowResponse.equals("Could you repeat that color? Perhaps with a more common name.")){
            Color colorName;
            Colors c = new Colors();
            colorName = c.getStandardColors().get(extractedSlotValues.get("Specific Color"));
            colorHistory.add(colorName);
            JFrame frame = new JFrame();
            JLabel label = new JLabel(RGBValue(colorName));
            label.setForeground(labelColor(colorName));
            label.setVerticalAlignment(JLabel.TOP);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            frame.add(label);
            frame.setSize(200, 200);
            frame.setLocation(650, 100);
            frame.setVisible(true);
            frame.getContentPane().setBackground(colorName);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            return frame;
        }
        if (nowIntent.equals("Generate Random Color")){
            Colors c = new Colors();
            Color rand = c.getRandomColor();
            colorHistory.add(rand);
            JFrame frame = new JFrame();
            JLabel label = new JLabel(RGBValue(rand));
            label.setForeground(labelColor(rand));
            label.setVerticalAlignment(JLabel.TOP);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            frame.add(label);
            frame.setSize(200, 200);
            frame.setLocation(650, 100);
            frame.setVisible(true);
            frame.getContentPane().setBackground(rand);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            return frame;
        }
        if (nowIntent.equals("Modify Color")){
            Colors modified = new Colors();
            Color c = colorHistory.get(colorHistory.size() -1);
            Color x = modified.modifyColor(c, extractedSlotValues.get("Modify Color"));
            colorHistory.add(x);
            JFrame frame = new JFrame();
            JLabel label = new JLabel(RGBValue(x));
            label.setForeground(labelColor(x));
            label.setVerticalAlignment(JLabel.TOP);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            frame.add(label);
            frame.setSize(200, 200);
            frame.setLocation(650, 100);
            frame.setVisible(true);
            frame.getContentPane().setBackground(x);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            return frame;
        }
        if (nowIntent.equals("ColorX Complement")){
            Colors c = new Colors();
            Color x = c.getStandardColors().get(extractedSlotValues.get("ColorX Complement"));
            Color complement = c.getComplementary(x);
            colorHistory.add(complement);
            JFrame frame = new JFrame();
            frame.setLayout(new FlowLayout());
            frame.setLocation(650, 100);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.add(createPanel(x));
            frame.add(createPanel(complement));
            frame.pack();
            frame.setVisible(true);
            return frame;
        }
        if (nowIntent.equals("Random Complement")){
            Colors c = new Colors();
            Color x = c.getRandomColor();
            Color complement = c.getComplementary(x);
            colorHistory.add(complement);
            JFrame frame = new JFrame();
            frame.setLayout(new FlowLayout());
            frame.setLocation(650, 100);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.add(createPanel(x));
            frame.add(createPanel(complement));
            frame.pack();
            frame.setVisible(true);
            return frame;
        }
        if (nowIntent.equals("Color Complement")){
            Colors complement = new Colors();
            Color c = colorHistory.get(colorHistory.size() -1);
            Color x = complement.getComplementary(c);
            colorHistory.add(x);
            JFrame frame = new JFrame();
            frame.setLayout(new FlowLayout());
            frame.setLocation(650, 100);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.add(createPanel(c));
            frame.add(createPanel(x));
            frame.pack();
            frame.setVisible(true);
            return frame;

        }
        return new JFrame();
    }
    public JPanel createPanel(Color c){
        JPanel panel = new JPanel();
        JLabel label = new JLabel(RGBValue(c));
        label.setForeground(labelColor(c));
        label.setVerticalAlignment(JLabel.TOP);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panel.setBackground(c);
        panel.add(label);
        panel.setPreferredSize(new Dimension(200, 200));
        return panel;
    }

    public String RGBValue (Color c){
        StringBuilder sb = new StringBuilder();
        sb.append("RGB (" + c.getRed() + ", " + c.getGreen() + ", " + c.getBlue() + ")");
        return sb.toString();
    }

    public Color labelColor (Color c){
        Color label;
        if (c.getGreen() > 200){
           label = BLACK;
        }
        else{
            label = white;
        }
        return label;
    }


    private String slotTableToString(Hashtable<String, String> extractedSlotValues) {
        String result = "";
        String nowKey;
        String nowValue;
        String longValue = "";

        Iterator extractedSlotValuesIterator = extractedSlotValues.keySet().iterator();
		/*
		for(Iterator var4 = extractedSlotValues.keySet().iterator(); var4.hasNext(); result = result + nowKey + "=" + nowValue + "; ") {
			nowKey = (String)var4.next();
			nowValue = nowValue.concat((String)extractedSlotValues.get(nowKey));
			System.out.println(nowKey + "=" + nowValue);
		}
		 */

        while(extractedSlotValuesIterator.hasNext()){
            nowKey = (String)extractedSlotValuesIterator.next();
            nowValue = (String)extractedSlotValues.get(nowKey);
            longValue = longValue.concat(nowValue);
            result = nowKey + " = " + longValue;
            System.out.println(nowKey + " = " + longValue);
        }

        result = "Slot Filling= (" + result + ")";


        return result;
    }





    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBotName() {
        return botName;
    }

    public void setBotName(String botName) {
        this.botName = botName;
    }





}

