package com.example.ecommerceproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Scanner;

public class HomeController implements Initializable{
    private String base_Url="http://127.0.0.1:8000/api/";
    @FXML
    private Button btn_home;

    @FXML
    private Button btn_commandes;

    @FXML
    private Button logout_btn;

    @FXML
    private Label label_categories;

    @FXML
    private Label label_commandes;

    @FXML
    private Label label_products;

    @FXML
    private Label label_users;
     @FXML
     private StackPane route;

    @FXML
    private BarChart<String,Number> barchart;
    @FXML
    private BarChart<String,Number> barchart1;

    @FXML
    private Button btn_products;
    @FXML
    private AnchorPane container_anchor;

    @FXML
    private ImageView imagev;

    private  double x=0,y=0;
    @FXML
    void switchEvent(ActionEvent event) {
        if(event.getSource()==btn_commandes){
            try {
                Parent root= FXMLLoader.load(getClass().getResource("fxml/commande.fxml"));
                Stage stage=(Stage) route.getScene().getWindow();
                Scene scene=new Scene(root,1200,620);
                stage.setScene(scene);
                stage.centerOnScreen();
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
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        else if(event.getSource()==btn_home){
            try {
                Parent root=FXMLLoader.load(getClass().getResource("fxml/home.fxml"));
                Stage stage=(Stage) route.getScene().getWindow();
                Scene scene=new Scene(root,1200,620);
                stage.setScene(scene);
                stage.centerOnScreen();
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

            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        } else if (event.getSource()==btn_products) {
            try {
                Parent root=FXMLLoader.load(getClass().getResource("fxml/product.fxml"));
                Stage stage=(Stage) route.getScene().getWindow();
                Scene scene=new Scene(root,1200,620);
                stage.setScene(scene);
                stage.centerOnScreen();
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
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        else if (event.getSource()==logout_btn) {
            try {
                logout_btn.getScene().getWindow().hide();
                Parent root=FXMLLoader.load(getClass().getResource("fxml/login.fxml"));
                Stage stage=new Stage();
                Scene scene=new Scene(root,500,400);
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource("Image/icon1.PNG")).openStream()));
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
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }
    void chart(){
        try{
            URL url=new URL(base_Url+"chiffre_afaire");
            HttpURLConnection urlConnection=(HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            int code=urlConnection.getResponseCode();
            if(code!=200){
                System.out.println("!200");
                throw new Exception();
            }
            else {
                StringBuilder stringBuilder=new StringBuilder();
                Scanner scanner=new Scanner(url.openStream());
                while (scanner.hasNext()){
                    stringBuilder.append(scanner.nextLine());
                }
                scanner.close();
                JSONArray json=new JSONArray(stringBuilder.toString());

                XYChart.Series<String,Number> series=new XYChart.Series<>();
                series.setName("revenue/categorie");


                XYChart.Series<String,Number> series1=new XYChart.Series<>();
                series1.setName("number of products sould out/categorie");

                for(int i=0;i<json.length();i++){

                    double chaifre=(double)(int) json.getJSONObject(i).get("chifreAfaire");
                    double quantie=(double)(int) json.getJSONObject(i).get("quantite");
                    String name=json.getJSONObject(i).get("name").toString();

                    series.getData().add(new XYChart.Data<>(name,chaifre));
                    series1.getData().add(new XYChart.Data<>(name,quantie));
                }
                barchart.getData().add(series);
                barchart1.getData().add(series1);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
    void setLabel(){
        try{
            URL url=new URL(base_Url+"statistique");
            HttpURLConnection urlConnection=(HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            int code=urlConnection.getResponseCode();
            if(code!=200){
                System.out.println("!200");
                throw new Exception();
            }
            else {
                StringBuilder stringBuilder=new StringBuilder();
                Scanner scanner=new Scanner(url.openStream());
                while (scanner.hasNext()){
                    stringBuilder.append(scanner.nextLine());
                }
                scanner.close();
                JSONObject json=new JSONObject((stringBuilder.toString()));
                label_categories.setText(json.get("categories").toString());
                label_commandes.setText(json.get("commandes").toString());
                label_products.setText(json.get("products").toString());
                label_users.setText(json.get("users").toString());
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
          btn_home.setStyle("-fx-background-color:linear-gradient(to Bottom Right,#272b3f,#256b51);-fx-border-color:white;-fx-border-width:0.1px;");
          chart();
          setLabel();
    }
}
