package chatbot.main;

import chatbot.infra.Chatbot;
import chatbot.infra.ChatbotGUI;

public class Main {

    public static void main(String[] args)  {

        //create your chatbot (user name, bot name)
        Chatbot nowChatbot = new Chatbot("Casey", "ColorBot");

        //create a GUI that connects to your chatbot
        ChatbotGUI nowChatbotGUI = new ChatbotGUI(nowChatbot);

    }

}
