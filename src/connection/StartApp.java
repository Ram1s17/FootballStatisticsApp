package connection;

import ui.SignInForm;

public class StartApp {
    public static void main(String[] args) {
        DatabaseConnection.connectToDB();
        new SignInForm("Вход в \"Football Statistics\"");

    }
}
