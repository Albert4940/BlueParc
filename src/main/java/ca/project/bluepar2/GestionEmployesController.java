package ca.project.bluepar2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

public class GestionEmployesController implements Initializable {
    DateTimeFormatter dateFormateur = DateTimeFormatter.ofPattern("dd/MM/YYYY");
    @FXML
    private TextField txtNomUsager;
    @FXML
    private TextField txtNomFamille;
    @FXML
    private TextField txtPrenom;
    @FXML
    private Button btnAjout;
    @FXML
    private Button btnRecherche;
    @FXML
    private TextField txtNoEmploye;
    @FXML
    private DatePicker datePickerDebut;
    @FXML
    private DatePicker datePickerFin;
    @FXML
    private Button btnSupprime;
    @FXML
    private Button btnRetourFenPrincipale;
    @FXML
    private TableView<Employe> tableVue;
    @FXML
    private TableColumn<Employe,String> nomUsager;
    @FXML
    private TableColumn<Employe,String> nomFamille;
    @FXML
    private TableColumn<Employe,String> prenom;
    @FXML
    private TableColumn<Employe,String> dateDebut;
    @FXML
    private TableColumn<Employe,String> dateFin;
    @FXML
    private TableColumn<Employe,String> noEmploye;
    @FXML
    private Label lblErreur;
    @FXML
    private Button btnEffacer;



    ObservableList<Employe> listEmploye = FXCollections.observableArrayList(
            new Employe("Emp1", "Doe", "John", new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime(),  new GregorianCalendar(2018, Calendar.JUNE, 5).getTime(), "001"),
            new Employe("Emp2", "Deer", "Jane", new GregorianCalendar(2016, Calendar.DECEMBER, 19).getTime(),  new GregorianCalendar(2020, Calendar.JANUARY, 30).getTime(), "002")
    );

    private void rechercher(){
        tableVue.getSelectionModel().clearSelection();
           for (int i=0; i<listEmploye.size(); i++){
               if (txtNoEmploye.getText().contentEquals(listEmploye.get(i).getNoEmploye() )){
                   tableVue.getSelectionModel().select(i);
               }
           }
    }


    private boolean verificationChampsAjout(){
        boolean condition = false;

        if(txtNomUsager.getText() !="" & txtNomFamille.getText() !="" & txtPrenom.getText() !="" & txtNoEmploye.getText() !="" & datePickerDebut.getEditor().getText() !="" & datePickerFin.getEditor().getText() !="")  {
            condition = true;
        }
        return condition;
    }

    private boolean verificationChampsRecherche(){
        boolean condition = false;

        if(txtNomUsager.getText().length() >0 || txtNomFamille.getText().length() >0 || txtPrenom.getText().length() >0 || txtNoEmploye.getText().length() >0 || datePickerDebut.getEditor().getText() !="" || datePickerFin.getEditor().getText() !="") {
            condition = true;
        }
        return condition;
    }

    private boolean verificationSiEmployeExistant(){
        boolean condition = false;
        return condition;
    }

    private void switchToHub(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("application.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    private void effacerFormulaire(){
        txtNomUsager.setText("");
        txtNomFamille.setText("");
        txtNoEmploye.setText("");
        txtPrenom.setText("");
        datePickerDebut.getEditor().setText("");
        datePickerFin.getEditor().setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*
        //Ajout de la date d'aujourd'hui dans les cases de dates (début et fin).
        datePickerDebut.setValue(LocalDate.now());
        datePickerFin.setValue(LocalDate.now());
         */

        //Placeholder lorsque la table est vide
        tableVue.setPlaceholder(
                new Label("Aucune information"));
        //Model de sélection multiple Source: https://jenkov.com/tutorials/javafx/tableview.html
        TableView.TableViewSelectionModel<Employe> selectionModel =
                tableVue.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.MULTIPLE);
        //Efface le formulaire
        effacerFormulaire();
        //TableView aide via ce tuto: https://www.youtube.com/watch?v=fnU1AlyuguE
        nomUsager.setCellValueFactory(new PropertyValueFactory<Employe, String>("nomUsager"));
        nomFamille.setCellValueFactory(new PropertyValueFactory<Employe, String>("nomFamille"));
        prenom.setCellValueFactory(new PropertyValueFactory<Employe, String>("prenom"));
        dateDebut.setCellValueFactory(new PropertyValueFactory<Employe, String>("dateDebut"));
        dateFin.setCellValueFactory(new PropertyValueFactory<Employe, String>("dateFin"));
        noEmploye.setCellValueFactory(new PropertyValueFactory<Employe, String>("noEmploye"));

        //Lien avec la liste observable
        tableVue.setItems(listEmploye);

        //Ajout des events handler pour les boutons
        btnAjout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                lblErreur.setText(""); //Vide les erreurs
                if(verificationChampsAjout()){
                    //Collecte de la date de début
                    LocalDate localDateDebut = datePickerDebut.getValue();
                    Instant instantDebut = Instant.from(localDateDebut.atStartOfDay(ZoneId.systemDefault()));
                    Date dateDebut = Date.from(instantDebut);

                    //Collecte de la date de fin
                    LocalDate localDateFin = datePickerFin.getValue();
                    Instant instantFin = Instant.from(localDateFin.atStartOfDay(ZoneId.systemDefault()));
                    Date dateFin = Date.from(instantFin);
                    //Ajout de l'employé dans la liste observable.
                    listEmploye.add(new Employe(txtNomUsager.getText(), txtNomFamille.getText(), txtPrenom.getText(), dateDebut, dateFin, txtNoEmploye.getText() ));
                } else{
                    lblErreur.setText("Veuillez remplir tout les champs!");
                }
            }
        });
        btnRecherche.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                lblErreur.setText(""); //Vide les erreurs
                if(verificationChampsRecherche()){
                    rechercher();
                } else{
                    lblErreur.setText("Veuillez remplir au moins 1 champs!");
                }
            }
        });
        btnSupprime.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                lblErreur.setText(""); //Vide les erreurs
                System.out.println("Suppression des éléments");
            }
        });
        btnRetourFenPrincipale.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    switchToHub(actionEvent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        btnEffacer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                effacerFormulaire();
            }
        });
    }

//    public void close(ActionEvent event) {
//        Parent root;
//        try {
//            root = FXMLLoader.load(getClass().getResource("application.fxml"));
//            Stage stage = new Stage();
//            stage.setTitle("Parc Aquatique");
//            stage.setScene(new Scene(root, 800, 600));
//            stage.show();
//            // Hide this current window (if this is what you want)
//            ((Node)(event.getSource())).getScene().getWindow().hide();
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}