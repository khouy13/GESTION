package com.example.ecommerceproject;

import javafx.application.Application;

import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.json.JSONArray;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;
import java.util.Scanner;

public class Main extends Application {
    private double x=0;
    private  double y=0;
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/login.fxml"));
        Parent root=fxmlLoader.load();
        Scene scene =new Scene(root);
        stage.setTitle("Shop Management");
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource("Image/icon1.PNG")).openStream()));
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.initStyle(StageStyle.TRANSPARENT);
        root.setOnMousePressed(
                e->{
                    x=e.getSceneX();
                    y=e.getSceneY();
                }
        );
        root.setOnMouseDragged(e->{
                stage.setY(e.getScreenY()-y);
                stage.setX(e.getScreenX()-x);
        });
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}