package application;

import java.awt.*;

public class App {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Window();
            }
        });
    }

}