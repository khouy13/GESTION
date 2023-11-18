package com.example.ecommerceproject;

import com.itextpdf.text.api.Indentable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.net.URL;
import javafx.scene.input.MouseEvent;
import javafx.stage.StageStyle;

import java.util.Objects;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    private double x=0;
    private double y=0;

    @FXML
    private Button btn_enter;

    @FXML
    private PasswordField password;

    @FXML
    private StackPane route;
    @FXML
    private Label error;
    @FXML
    private Label incorrete;
    @FXML
    void enter(ActionEvent event) {
             if(password.getText().isEmpty()){
                 error.setVisible(true);
                 error.setStyle("-fx-text-fill:red");
                 password.setStyle("-fx-border-color:red");
             }
             else{

                 if(password.getText().equals("123")){
                     try {
                         btn_enter.getScene().getWindow().hide();
                         Parent root= FXMLLoader.load(getClass().getResource("fxml/home.fxml"));
                         Stage stage=new Stage();
                         Scene scene=new Scene(root,1200,620);
                         stage.setScene(scene);
                         stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource("Image/icon1.PNG")).openStream()));
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
                     }catch (Exception e){
                         System.out.println(e.getMessage());
                     }
                 }
                 else{
                     incorrete.setVisible(true);
                     incorrete.setStyle("-fx-text-fill:red;");
                 }
             }

    }

    @FXML
    void vider(MouseEvent event) {
                incorrete.setVisible(false);
                error.setVisible(false);
                password.setStyle("-fx-border-color:aqua;");
    }

    @FXML
    private Button close_btn;

    @FXML
    void close(ActionEvent event) {
          close_btn.getScene().getWindow().hide();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

