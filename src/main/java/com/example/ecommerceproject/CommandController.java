package com.example.ecommerceproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Scanner;


public class CommandController implements Initializable {

    @FXML
    private Button btn_commandes;

    @FXML
    private Button btn_home;

    @FXML
    private Button btn_products;

    @FXML
    private TableColumn<Command, String> column__livraison;

    @FXML
    private TableColumn<Command, String> column_created_at;

    @FXML
    private TableColumn<Command, String> column_payment;

    @FXML
    private TableColumn<?, ?> column_price;

    @FXML
    private TableColumn<?, ?> column_product;

    @FXML
    private TableColumn<?, ?> column_quantite;

    @FXML
    private TableColumn<Command, String> column_statut;

    @FXML
    private TableColumn<Command, String> column_user;
    @FXML
    private Button delete_btn;

    @FXML
    private Button logout_btn;

    @FXML
    private ComboBox<String> statut_combox;

    @FXML
    private TableView<Detail> table_dtails;

    @FXML
    private TableView<Command> tavle_command;

    @FXML
    private Button update;

    ObservableList<Command> data;
    ObservableList<Detail> data2;
   ObservableList<User> data3;
    private String base_Url="http://127.0.0.1:8000/api/";
    @FXML
    private ImageView image;
    @FXML
    private TableColumn<User, String> column_user_name;
    @FXML
    private TableView<User> table_user;

    @FXML
    private TableColumn<User, String> column_address;

    @FXML
    private TableColumn<User, String> column_phone;
    @FXML
    private StackPane route;
    @FXML
    void delete(ActionEvent event) {
      if(Path.idCommand==0){
          Alert alert=new Alert(Alert.AlertType.ERROR);
          alert.setTitle("Error");
          alert.setContentText("select a commande to update it");
          alert.setHeaderText(null);
          alert.showAndWait();
      }
      else{
             String path=base_Url+"delete/commandes/"+Path.idCommand;
             Path.idCommand=0;
         try {
             URL url=new URL(path);
             HttpURLConnection con=(HttpURLConnection) url.openConnection();
             con.setRequestMethod("DELETE");
             con.connect();
             try (BufferedReader bf = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                String line;
                while ((line = bf.readLine()) != null) {
                }
             }
             debut();
         }catch (Exception e){
              System.out.println(e.getMessage());
         }
        }
    }
    void setColumnUsers(){
              column_user_name.setCellValueFactory(new PropertyValueFactory<>("name"));
              column_address.setCellValueFactory(new PropertyValueFactory<>("address"));
              column_phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
    }
    ObservableList<User> getUsers(int id){
        ObservableList<User>  list= FXCollections.observableArrayList();
        try{
            URL url=new URL(base_Url+"user/"+id);
            HttpURLConnection urlConnection=(HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            int code=urlConnection.getResponseCode();
            if(code!=200){
                throw new Exception();
            }
            else {
                StringBuilder stringBuilder=new StringBuilder();
                Scanner scanner=new Scanner(url.openStream());
                while (scanner.hasNext()){
                    stringBuilder.append(scanner.nextLine());
                }
                scanner.close();
                JSONObject json=new JSONObject(stringBuilder.toString());
                list.add(new User(json.get("name").toString(),json.get("phone").toString(),json.get("address").toString()));
                return list;
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    @FXML
    void getRowSelected(MouseEvent event) {
        Command command=tavle_command.getSelectionModel().getSelectedItem();
        int num=tavle_command.getSelectionModel().getSelectedIndex();
        if((num-1)<-1){
            return;
        }
        image.setImage(null);
        Path.idCommand=command.getId();
        statut_combox.setValue(command.getStatut());
        setColumnDetails();
        data2=getDetails(command.getId());
        table_dtails.setItems(data2);
        setColumnUsers();
        data3=getUsers(command.getUser_id());
        table_user.setItems(data3);
    }
    void setComboxValue(){
        ObservableList <String> list=FXCollections.observableArrayList();
        list.add("traiter");
        list.add("en attent");
        statut_combox.setItems(list);
    }
    private double x=0,y=0;
    @FXML
    void switchEvent(ActionEvent event) {
                 if(event.getSource()==btn_commandes){
                     try {
                           Parent root=FXMLLoader.load(getClass().getResource("fxml/commande.fxml"));
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
                 } else if (event.getSource()==logout_btn) {
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
    @FXML
    void update(ActionEvent event) {
        if(Path.idCommand==0){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("select a commande to update it");
            alert.setHeaderText(null);
            alert.showAndWait();
        }else {
            try {
                String select = statut_combox.getSelectionModel().getSelectedItem();
                int statut = 0;
                if (select.equals("en attent")) {
                    statut = 1;
                } else {
                    statut = 2;
                }
                String body = "{\"statut\":" + statut + "}";
                URL url = new URL(base_Url + "update/commandes/" + Path.idCommand);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("User-Agent", "Mozilla/5.0");
                try (DataOutputStream dos = new DataOutputStream(conn.getOutputStream())) {
                    dos.writeBytes(body);
                }
                try (BufferedReader bf = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                    String line;
                    while ((line = bf.readLine()) != null) {
                    }
                }
                debut();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
     void setColumnDetails(){
        column_product.setCellValueFactory(new PropertyValueFactory<>("product_name"));
        column_quantite.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        column_price.setCellValueFactory(new PropertyValueFactory<>("price"));
     }
     ObservableList<Detail> getDetails(int id){
         ObservableList<Detail>  list= FXCollections.observableArrayList();
         try{
             URL url=new URL(base_Url+"details/"+id);
             HttpURLConnection urlConnection=(HttpURLConnection) url.openConnection();
             urlConnection.setRequestMethod("GET");
             urlConnection.connect();
             int code=urlConnection.getResponseCode();
             if(code!=200){
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
                 for(int i=0;i<json.length();i++){
                     double total=(double)(int) json.getJSONObject(i).get("total");
                     list.add(new Detail( (int) json.getJSONObject(i).get("id"),(int) json.getJSONObject(i).get("quantite") ,json.getJSONObject(i).get("name").toString(),total));
                 }
                 return list;
             }
         }catch (Exception e){
             System.out.println(e.getMessage());
             return null;
         }
     }
     ObservableList<Command> getDataCommandes(){

        ObservableList<Command>  list= FXCollections.observableArrayList();
        try{
            URL url=new URL(base_Url+"commandes");
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
                for(int i=0;i<json.length();i++){
                    double total=(double)(int) json.getJSONObject(i).get("total");
                    list.add(new Command(json.getJSONObject(i).get("livraison").toString(),json.getJSONObject(i).get("statut").toString(),json.getJSONObject(i).get("payment").toString(),total,(int) json.getJSONObject(i).get("id"),(int) json.getJSONObject(i).get("user_id"),json.getJSONObject(i).get("created_at").toString()));
                }
                return list;
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    void setColumnCommand(){

           column_created_at.setCellValueFactory(new PropertyValueFactory<>("created_at"));
           column_user.setCellValueFactory(new PropertyValueFactory<>("total"));
           column__livraison.setCellValueFactory(new PropertyValueFactory<>("livraison"));
           column_payment.setCellValueFactory(new PropertyValueFactory<>("payment"));
           column_statut.setCellValueFactory(new PropertyValueFactory<>("statut"));

    }
    void debut(){

        setColumnCommand();

        data=getDataCommandes();

        tavle_command.setItems(data);
    }
    @FXML
    void getRowDetail(MouseEvent event) {
                   Detail detail=table_dtails.getSelectionModel().getSelectedItem();
                   int num=table_dtails.getSelectionModel().getSelectedIndex();
                   if((num-1)<-1){
                       return;
                   }
                   String url = base_Url +"get_image/" +detail.getId();
                   Image image1 = new Image(url, 230, 168, false, true);
                   image.setImage(image1);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
           debut();
           setComboxValue();
        btn_commandes.setStyle("-fx-background-color:linear-gradient(to Bottom Right,#272b3f,#256b51);-fx-border-color:white;-fx-border-width:0.1px;");
    }
}
