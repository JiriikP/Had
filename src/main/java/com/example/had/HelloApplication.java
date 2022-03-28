package com.example.had;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 800);



        stage.setTitle("Hadi a zebriky!");
        stage.setScene(scene);
        stage.getIcons().add(new Image("https://cdn.discordapp.com/attachments/644113506383298562/949786451158106142/had_icon.png"));
        stage.show();
        stage.setMaximized(false); // mozna pak true


    }

    public static void main(String[] args) {
        launch();
    }
}