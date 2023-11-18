package com.example.ecommerceproject;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import javafx.beans.binding.StringBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.*;

import java.io.*;
import java.net.URL;

import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import  com.example.ecommerceproject.Porudct;
import  com.example.ecommerceproject.Categorie;
import  com.example.ecommerceproject.Path;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.json.JSONArray;
import org.json.JSONObject;

public class ProductController implements Initializable {

    @FXML
    private Button btn_chose;

    @FXML
    private Button logout_btn;

    @FXML
    private Button btn_add;
    @FXML
    private Button btn_delete;
    @FXML
    private Button btn_update;
    @FXML
    private StackPane route;
    @FXML
    private Button btn_clear;

    @FXML
    private ComboBox<String> filied_categorie;

    @FXML
    private TextArea filied_details;

    @FXML
    private TextField filied_name;

    @FXML
    private TextField filied_prix;

    @FXML
    private ImageView image;

    @FXML
    private Button btn_products;

    @FXML
    private ComboBox<String> combox_categories;

    @FXML
    private Button btn_commandes;

    @FXML
    private Button btn_home;

    @FXML
    private TableColumn<Porudct,String > column_add_at;

    @FXML
    private TableColumn<Porudct,String > column_details;

    @FXML
    private TableColumn<Porudct,String > column_name;

    @FXML
    private TableColumn<Porudct,String > column_prix;

    @FXML
    private TableView<Porudct> table_product;

    private ObservableList<Porudct> data;

    private ObservableList<Categorie> data2;

    @FXML
    private Button add_catergorie_btn;

    @FXML
    private TextField fillied_add_categorie;

    @FXML
    private TableView<Categorie> table_categorie;


    @FXML
    private TableColumn<Categorie, String> categorie_name_column;

    @FXML
    private Button btn_delete_categorie;
    @FXML
    private Button update_categorie_name;

    @FXML
    private Label message;
    @FXML
    void updateCategroire(ActionEvent event) {
        if(fillied_add_categorie.getText().isEmpty() || Path.id_cat==0){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("fill the fillied");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
        else{
            try {
                String body = "{\"catagorie_name\": \""+fillied_add_categorie.getText()+"\"}";
                URL url = new URL(base_Url+"update/categories/"+Path.id_cat);
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

                    }}
                message.setText("categorie name update successfully !!!");
                fillied_add_categorie.setText("");
                setCombox();

            }catch (Exception e){
                e.getStackTrace();
            }
        }
    }
    @FXML
    void deleteCategorie(ActionEvent event) {
        if(Path.id_cat==0){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error message");
            alert.setContentText("select a categorie to delete it");
            alert.showAndWait();
        }else{
            String path=base_Url+"categorie/"+Path.id_cat;
            Path.id_cat=0;
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
                viderLesChamps();
                fillied_add_categorie.setText("");
                setCombox();
                message.setText("categorie deleted successfully !!!");
            }catch (Exception e){
                e.getStackTrace();
            }
        }
    }

    void setCategorieColumn(){
        categorie_name_column.setCellValueFactory(new PropertyValueFactory<>("name"));
    }


    @FXML
    void getCategorieCllicked(MouseEvent event) {
         Categorie categorie=table_categorie.getSelectionModel().getSelectedItem();
         int num=table_categorie.getSelectionModel().getSelectedIndex();
         if ((num - 1) < -1) {
            return;
         }
         else{
             fillied_add_categorie.setText(categorie.getName());
             Path.id_cat= categorie.getId();
         }
    }

    @FXML
    void add_categorie(ActionEvent event) {
       if(fillied_add_categorie.getText().isEmpty()){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("fill the fillied");
            alert.setHeaderText(null);
            alert.showAndWait();
       }
       else{
           try {
           String body = "{\"catagorie_name\": \""+fillied_add_categorie.getText()+"\"}";
           URL url = new URL(base_Url+"categorie");
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
//                System.out.println(line);
            }}

            message.setText("categorie added successfully !!!");
            fillied_add_categorie.setText("");
            setCombox();

           }catch (Exception e){
               e.getStackTrace();
           }
       }
    }

    private  File file;
    @FXML
    private TextField search;
    @FXML
    void search(MouseEvent event) {
        FilteredList<Porudct> filter = new FilteredList<>(data, e -> true);
        search.textProperty().addListener((Observable, oldValue, newValue) -> {
            filter.setPredicate(predecteData -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String searchKey = newValue.toLowerCase();
                if (predecteData.getName().toString().contains(searchKey)) {
                    return true;
                }else {
                    return false;
                }
            });
            SortedList<Porudct> sortedList = new SortedList<>(filter);
            sortedList.comparatorProperty().bind(table_product.comparatorProperty());
            setColumn();
            table_product.setItems(sortedList);
        });
    }
    private String base_Url="http://127.0.0.1:8000/api/";
    private double x=0,y=0;
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
 //------------------->>>>>>
    public ObservableList<Porudct> getData(){
        ObservableList<Porudct> list= FXCollections.observableArrayList();
        try{
          URL url=new URL(base_Url+"products");
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
                  double prix=(double)(int) json.getJSONObject(i).get("prix");
                  list.add(new Porudct((int) json.getJSONObject(i).get("id"),(int) json.getJSONObject(i).get("categorie_id"),json.getJSONObject(i).get("name").toString(),json.getJSONObject(i).get("image").toString(),prix,json.getJSONObject(i).get("details").toString(),json.getJSONObject(i).get("created_at").toString()));
              }
              return list;
          }
        }catch (Exception e){
            return null;
        }
    }

    public  void setColumn(){
        column_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        column_prix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        column_details.setCellValueFactory(new PropertyValueFactory<>("details"));
        column_add_at.setCellValueFactory(new PropertyValueFactory<>("created_at"));
    }

    public void debut(){
        data=getData();
        setColumn();
        table_product.setItems(data);
    }

    public void setCombox(){

        ObservableList<Categorie> list=FXCollections.observableArrayList();
        ObservableList<String> observableList=FXCollections.observableArrayList();
        try {

            URL url=new URL(base_Url+"categories");
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
                   list.add(new Categorie((int) json.getJSONObject(i).get("id"),json.getJSONObject(i).get("catagorie_name").toString()));
                }
             for (Categorie categorie:list){
                    observableList.add(categorie.getName());
             }
             combox_categories.setItems(observableList);
             filied_categorie.setItems(observableList);
             data2=list;
             setCategorieColumn();
             table_categorie.setItems(data2);
            }
        }catch (Exception e){
             e.getStackTrace();
        }

    }
    public void selectionCategorie(ActionEvent event){
           String select=combox_categories.getSelectionModel().getSelectedItem();
           for (Categorie categorie:data2){
                 if(categorie.getName()==select){
                     FilteredList<Porudct> filter = new FilteredList<>(data, e -> true);
                     filter.setPredicate(predecteData -> {
                         int searchKey = categorie.getId();
                         if (predecteData.getCategorie_id()==searchKey){
                             return true;
                         }
                         else{
                             return false;
                         }
                      });
                     SortedList<Porudct> sortedList = new SortedList<>(filter);
                     sortedList.comparatorProperty().bind(table_product.comparatorProperty());
                     setColumn();
                     table_product.setItems(sortedList);
                     break;
                 }
           }

    }
    public  void viderLesChamps(){
            filied_name.setText("");
            filied_prix.setText("");
            filied_details.setText("");
            filied_categorie.setPromptText("choose");
            filied_categorie.setValue("choose");
            image.setImage(null);
            Path.id=0;
            Path.path="";
    }
    @FXML
    void choose(ActionEvent event) {
       FileChooser fileChooser=new FileChooser();
       file=fileChooser.showOpenDialog(route.getScene().getWindow());
       if (file!=null){
           String path=file.getAbsolutePath();
           Path.path=path;
           Image image1 = new Image(Path.path, 230, 168, false, true);
           image.setImage(image1);
           System.out.println(Path.path);
       }
    }
    @FXML
    void getRowClicked(MouseEvent event)throws IOException, DocumentException {
               Porudct porudct=table_product.getSelectionModel().getSelectedItem();

                int num = table_product.getSelectionModel().getSelectedIndex();
                          if ((num - 1) < -1) {
                              return;
                          }
                          Path.id=porudct.getId();
                          filied_name.setText(porudct.getName());
                          filied_prix.setText(" "+porudct.getPrix());
                          filied_details.setText(porudct.getDetails());
                          for (Categorie categorie : data2) {
                                   if(categorie.getId()==porudct.getCategorie_id()){
                                       filied_categorie.setValue(categorie.getName());
                                       break;
                                   }
                          }

                              String url = base_Url +"get_image/" + porudct.getId();
                              Image image1 = new Image(url, 230, 168, false, true);
                              image.setImage(image1);
    }
    void updateImage(){
        if(filied_categorie.getSelectionModel().getSelectedItem()==null
                || filied_details.getText().isEmpty()
                || filied_name.getText().isEmpty()
                || filied_prix.getText().isEmpty()
                || Path.path==null
                || Path.path==""
        ){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Massage");
            alert.setContentText("fill all filieds");
            alert.setHeaderText(null);
            alert.showAndWait();
        }else {
            double prix = Double.parseDouble(filied_prix.getText());
            String select = filied_categorie.getSelectionModel().getSelectedItem();
            int categorie_id = 0;
            for (Categorie categorie : data2) {
                if (categorie.getName() == select) {
                    categorie_id = categorie.getId();
                }
            }// JSON data
            try {
                String name = filied_name.getText();
                String details = filied_details.getText();
                // Image file path
                String imagePath = Path.path; // Replace with your image file path

                // Boundary string for multipart/form-data
                String boundary = "---------------------------" + System.currentTimeMillis();

                // Construct the multipart/form-data request body
                StringBuilder requestBody = new StringBuilder();
                requestBody.append("--").append(boundary).append("\r\n");
                requestBody.append("Content-Disposition: form-data; name=\"name\"\r\n\r\n");
                requestBody.append(name).append("\r\n");
                requestBody.append("--").append(boundary).append("\r\n");
                requestBody.append("Content-Disposition: form-data; name=\"details\"\r\n\r\n");
                requestBody.append(details).append("\r\n");
                requestBody.append("--").append(boundary).append("\r\n");
                requestBody.append("Content-Disposition: form-data; name=\"prix\"\r\n\r\n");
                requestBody.append(prix).append("\r\n");
                requestBody.append("--").append(boundary).append("\r\n");
                requestBody.append("Content-Disposition: form-data; name=\"categorie_id\"\r\n\r\n");
                requestBody.append(categorie_id).append("\r\n");
                requestBody.append("--").append(boundary).append("\r\n");
                requestBody.append("Content-Disposition: form-data; name=\"image\"; filename=\"image.jpg\"\r\n");
                requestBody.append("Content-Type: image/jpeg\r\n\r\n");

                // Create a URL object and open the connection
                URL url = new URL(base_Url+"Update/"+Path.id);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

                // Get the output stream of the connection
                try (OutputStream os = conn.getOutputStream();
                     FileInputStream imageStream = new FileInputStream(imagePath)) {

                    // Write the multipart/form-data request body
                    os.write(requestBody.toString().getBytes(StandardCharsets.UTF_8));

                    // Write the image data
                    byte[] buffer = new byte[8192];
                    int bytesRead;
                    while ((bytesRead = imageStream.read(buffer)) != -1) {
                        os.write(buffer, 0, bytesRead);
                    }

                    // Write the closing boundary
                    os.write(("\r\n--" + boundary + "--\r\n").getBytes(StandardCharsets.UTF_8));
                }

//                // Read and process the server response
                try (BufferedReader bf = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                    String line;
                    while ((line = bf.readLine()) != null) {
                    }
                }

                // Close the connection
                conn.disconnect();
                debut();
                viderLesChamps();
                message.setText("product update successfully !!!");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
    void update2(){

        if(filied_categorie.getSelectionModel().getSelectedItem()==null
                || filied_details.getText().isEmpty()
                || filied_name.getText().isEmpty()
                || filied_prix.getText().isEmpty()
        ){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Massage");
            alert.setContentText("fill all filieds");
            alert.setHeaderText(null);
            alert.showAndWait();
        }else {
            double prix = Double.parseDouble(filied_prix.getText());
            String select = filied_categorie.getSelectionModel().getSelectedItem();
            int categorie_id = 0;
            for (Categorie categorie : data2) {
                if (categorie.getName() == select) {
                    categorie_id = categorie.getId();
                }
            }// JSON data
            try {
                String name = filied_name.getText();
                String details = filied_details.getText();
                String body = "{\"name\": \"" + filied_name.getText()
                              +"\",\"prix\":" + prix
                              +",\"details\":\""+filied_details.getText()
                              +"\",\"categorie_id\":"+categorie_id
                              +"}";
                URL url = new URL(base_Url+"Update/"+Path.id);
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

                   }}
                message.setText("product update successfully !!!");
                    viderLesChamps();
                   debut();

        }catch (Exception e){
            e.getStackTrace();
        }
        }
    }
    @FXML
    void update(ActionEvent event) {
       if( Path.path==null || Path.path==""){
               update2();
       }
       else{
               updateImage();
       }
    }

    @FXML
    void delete(ActionEvent event) {
        if(Path.id==0){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error message");
            alert.setContentText("select a product to delete it");
            alert.showAndWait();
        }else{
        String path=base_Url+"products/"+Path.id;
        Path.id=0;
        try {
          URL url=new URL(path);
          HttpURLConnection con=(HttpURLConnection) url.openConnection();
          con.setRequestMethod("DELETE");
          con.connect();
          viderLesChamps();
          try (BufferedReader bf = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
              String line;
              while ((line = bf.readLine()) != null) {
              }
          }
            message.setText("product deleted successfully !!!");
          debut();
      }catch (Exception e){
          e.getStackTrace();
      }
    }
    }
    @FXML
    void add(ActionEvent event) {
        if(filied_categorie.getSelectionModel().getSelectedItem()==null
                || filied_details.getText().isEmpty()
                || filied_name.getText().isEmpty()
                || filied_prix.getText().isEmpty()
                || Path.path==null
                || Path.path==""
        ){
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Massage");
                alert.setContentText("fill all filieds");
                alert.setHeaderText(null);
                alert.showAndWait();
        }else {
            double prix = Double.parseDouble(filied_prix.getText());
            String select = filied_categorie.getSelectionModel().getSelectedItem();
            int categorie_id = 0;
            for (Categorie categorie : data2) {
                if (categorie.getName() == select) {
                    categorie_id = categorie.getId();
                }
            }// JSON data
            try {
                String name = filied_name.getText();
                String details = filied_details.getText();
                // Image file path
                String imagePath = Path.path; // Replace with your image file path

                // Boundary string for multipart/form-data
                String boundary = "---------------------------" + System.currentTimeMillis();

                // Construct the multipart/form-data request body
                StringBuilder requestBody = new StringBuilder();
                requestBody.append("--").append(boundary).append("\r\n");
                requestBody.append("Content-Disposition: form-data; name=\"name\"\r\n\r\n");
                requestBody.append(name).append("\r\n");
                requestBody.append("--").append(boundary).append("\r\n");
                requestBody.append("Content-Disposition: form-data; name=\"details\"\r\n\r\n");
                requestBody.append(details).append("\r\n");
                requestBody.append("--").append(boundary).append("\r\n");
                requestBody.append("Content-Disposition: form-data; name=\"prix\"\r\n\r\n");
                requestBody.append(prix).append("\r\n");
                requestBody.append("--").append(boundary).append("\r\n");
                requestBody.append("Content-Disposition: form-data; name=\"categorie_id\"\r\n\r\n");
                requestBody.append(categorie_id).append("\r\n");
                requestBody.append("--").append(boundary).append("\r\n");
                requestBody.append("Content-Disposition: form-data; name=\"image\"; filename=\"image.jpg\"\r\n");
                requestBody.append("Content-Type: image/jpeg\r\n\r\n");

                // Create a URL object and open the connection
                URL url = new URL(base_Url + "products");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

                // Get the output stream of the connection
                try (OutputStream os = conn.getOutputStream();
                     FileInputStream imageStream = new FileInputStream(imagePath)) {

                    // Write the multipart/form-data request body
                    os.write(requestBody.toString().getBytes(StandardCharsets.UTF_8));

                    // Write the image data
                    byte[] buffer = new byte[8192];
                    int bytesRead;
                    while ((bytesRead = imageStream.read(buffer)) != -1) {
                        os.write(buffer, 0, bytesRead);
                    }

                    // Write the closing boundary
                    os.write(("\r\n--" + boundary + "--\r\n").getBytes(StandardCharsets.UTF_8));
                }

                // Read and process the server response
                try (BufferedReader bf = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                    String line;
                    while ((line = bf.readLine()) != null) {
                    }
                }

                // Close the connection
                message.setText("product added successfully !!!");
                conn.disconnect();
                debut();
                viderLesChamps();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
}

    @FXML
    void clear(ActionEvent event) {
            viderLesChamps();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
                 debut();
                 setCombox();
        btn_products.setStyle("-fx-background-color:linear-gradient(to Bottom Right,#272b3f,#256b51);-fx-border-color:white;-fx-border-width:0.1px;");
    }
}
