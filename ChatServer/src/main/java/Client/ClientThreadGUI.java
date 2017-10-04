package Client;

import GUI.MainGUI;

public class ClientThreadGUI {

    public static void main(String[] args) {

        MainGUI mg = new MainGUI();
        mg.display();
        mg.updateChatBox("Bob", "Hej");
        mg.updateChatBox("boB", "Jeh");

    }
}
