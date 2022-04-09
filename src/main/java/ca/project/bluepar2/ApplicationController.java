package ca.project.bluepar2;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class ApplicationController {

    @FXML
    Button accueil;

    @FXML
    Button employe;

    @FXML
    Button activite;

    @FXML
    Button client;

    @FXML
    Button close;

    @FXML
    VBox homeVb;
    @FXML
    ImageView homeImg;
    @FXML
    Text homeTxt;

    @FXML
    VBox employeVb;
    @FXML
    ImageView employeImg;
    @FXML
    Text employeTxt;

    @FXML
    VBox activiteVb;
    @FXML
    ImageView activiteImg;
    @FXML
    Text activiteTxt;

    @FXML
    VBox deconnexionVb;
    @FXML
    ImageView deconnexionImg;
    @FXML
    Text deconnexionTxt;

    public void quitter(ActionEvent actionEvent) {
    }


    public void openWindow(Event event){
        if(employeVb.equals(event.getTarget()) || employeImg.equals(event.getTarget()) || employeTxt.equals(event.getTarget())){
            //System.out.println(event.getTarget());
            openPane("gestionEmploye", event);
        }else if(activiteVb.equals(event.getTarget()) || activiteImg.equals(event.getTarget()) || activiteTxt.equals(event.getTarget())){
            openPane("RechercheActivites", event);
        }else if(deconnexionVb.equals(event.getTarget()) || deconnexionImg.equals(event.getTarget()) || deconnexionTxt.equals(event.getTarget())){
            openPane("login-view", event);
        }else if(homeVb.equals(event.getTarget())){
            openPane("gestionEmploye", event);
        }
    }

    public void openPaneCallBack(Event event){
        openWindow(event);
        event.consume();
    }
    @FXML
    public void initialize(){

        employeVb.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> openPaneCallBack(event));
        employeImg.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> openPaneCallBack(event));
        employeTxt.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> openPaneCallBack(event));

        activiteVb.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> openPaneCallBack(event));
        activiteImg.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> openPaneCallBack(event));
        activiteTxt.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> openPaneCallBack(event));

        deconnexionVb.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> openPaneCallBack(event));
        deconnexionImg.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> openPaneCallBack(event));
        deconnexionTxt.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> openPaneCallBack(event));
    }
    public void openPane(String file, Event event){
        Parent root;
        try {
            System.out.println(((Node)(event.getSource())).getScene());
            root = FXMLLoader.load(getClass().getResource(file+".fxml"));
            Stage stage = new Stage();
            stage.setTitle("Parc Aquatique");
            stage.setScene(new Scene(root, 800, 600));
            stage.show();
            // Hide this current window (if this is what you want)
            ((Node)(event.getSource())).getScene().getWindow().hide();

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


}
