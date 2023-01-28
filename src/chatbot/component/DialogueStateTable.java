package chatbot.component;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

public class DialogueStateTable {


    public DialogueStateTable() {

    }

    public static String execute(String dialogueStateName, List<Hashtable<String, String>> slotHistory) {

        String response = "I am not sure. Could you say more?";

        switch (dialogueStateName) {


            case "CHIT-CHAT":
                response = "go crazy go stupid";
                break;


            case "GREETING":
                response = "Hello! How can I help you?";
                break;

			/*
		 	case "START-STATE":
		 		response = "Hello! How can I help you?";
		 	break;
			*/

            //Dialogue States that are independent from domains/intents

            case "ASK-LOCATION":
                response = "Where are you?";
                break;

            //Dialogue States in the color domain

            case "ANSWER-COLOR":
                response = "Here is your requested color!";
                break;

            case "MODIFY-COLOR":
                response = "Color has been modified";
                break;

            case "MODIFY-REJECTED":
                response = "There is no color to be modified";
                break;

            case "COMPLEMENT-REJECTED":
                response = "What color would you like the complement of";
                break;

            case "FIND-COMPLEMENTARY":
                response = "Here is the complement of your color";
                break;

            case "ASK-COLOR":
                response = "Could you repeat that color? Perhaps with a more common name.";
                break;

            case "ANSWER-RANDOM-COLOR":
                response = "Here is a random color!";
                break;


            //Dialogue States in the Food domain

            /*
        	case "ASK-LOCATION-ORDER-FOOD":
        		response = "Where do you want to order from?";
            break;

        	case "ASK-LOCATION-FIND-FOOD":
        		response = "I will find some restaurants near you. Which area are you in?";
            break;

        	case "ANSWER-ORDER-FOOD":
        		response = "I will find some restaurants near you. Which area are you in?";
            break;

        	case "ANSWER-FIND-FOOD":
        		response = "I will find some restaurants near you. Which area are you in?";
            break;
        	*/

            default:
                System.err.println("Invalid dialogueStateName: " + dialogueStateName);
                System.exit(1);
                //throw new IllegalArgumentException("Invalid dialogueStateName: " + dialogueStateName);

        }

        return response;

    }



}

