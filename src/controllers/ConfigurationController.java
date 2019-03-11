package controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import application.Main;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import models.dao.ConfigurationConnexionBaseDeDonneesDAO;

public class ConfigurationController {
	@FXML
	private Scene main;
	@FXML 
	private AnchorPane mainPane;
	@FXML
	private TextField baseDeDonneesInput;
	@FXML
	private TextField nomHoteInput;
	@FXML
	private TextField numeroDePortInput;
	@FXML
	private TextField nomUtilisateurInput;
	@FXML
	private PasswordField motDePasseInput;
	@FXML
	private CheckBox emailInput;
	@FXML
	private Button enregistrerButton;
	@FXML
	private Button fermetureButton;
	@FXML
	private Button retourButton;
	
	private double xOffset;
	private double yOffset;
	
	@FXML
	private void retourner_dans_la_page_precedente(ActionEvent actionEvent) {
		try {
			mainPane.getChildren().clear();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getClassLoader().getResource("views/fxml/Connexion.fxml"));
			AnchorPane userFrame = (AnchorPane) loader.load();
			Scene sc = mainPane.getScene();
			sc.setRoot(userFrame);
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void fermeture_de_l_application(ActionEvent actionEvent) {
		Platform.exit();
        System.exit(0);
	}
	
	@FXML
	private void enregistrer_les_informations_modifie(ActionEvent actionEvent) throws NumberFormatException, SQLException, FileNotFoundException, UnsupportedEncodingException {	
		int statusOptionEnvoiEmail;
		
		if(this.emailInput.isSelected()) {
			statusOptionEnvoiEmail = 1;
		}else{
			statusOptionEnvoiEmail = 0;
		}
		
		ConfigurationConnexionBaseDeDonneesDAO.modifier_les_modalites_de_connexion_a_la_base_de_donnees(nomHoteInput.getText(), Integer.parseInt(numeroDePortInput.getText()), baseDeDonneesInput.getText(), nomUtilisateurInput.getText(), motDePasseInput.getText(), statusOptionEnvoiEmail);
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Alerte!");
		alert.setHeaderText("Configuration");
		alert.setContentText("L'application va fermer, réouvrez la après pour prendre en compte les changements.");
		alert.showAndWait();
		
		Platform.exit();
        System.exit(0);
		
		/*
		Main.obtenir_le_primaryStage().close();
		Platform.runLater( () -> new Main().start( new Stage() ) );
		*/
	}
	
	@FXML
    private void initialize() throws ClassNotFoundException, SQLException, IOException {
		baseDeDonneesInput.setText(ConfigurationConnexionBaseDeDonneesDAO.obtenir_le_nom_de_la_base_de_donnees());
		nomHoteInput.setText(ConfigurationConnexionBaseDeDonneesDAO.obtenir_le_nom_d_hote());
		numeroDePortInput.setText(Integer.toString(ConfigurationConnexionBaseDeDonneesDAO.obtenir_le_numero_de_port()));
		nomUtilisateurInput.setText(ConfigurationConnexionBaseDeDonneesDAO.obtenir_le_nom_de_l_utilisateur());
		motDePasseInput.setText(ConfigurationConnexionBaseDeDonneesDAO.obtenir_le_mot_de_passe());
		
		boolean statusOptionEnvoiEmail = ConfigurationConnexionBaseDeDonneesDAO.obtenir_le_status_de_l_option_d_envoi_d_email(); 
		
		if(statusOptionEnvoiEmail == true) {
			this.emailInput.setSelected(true);
		}else {
			this.emailInput.setSelected(false);
		}
		
		mainPane.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				 xOffset = event.getSceneX();
				 yOffset = event.getSceneY();
			}
		});
		
		mainPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Main.obtenir_le_primaryStage().setX(event.getScreenX()- xOffset);
				Main.obtenir_le_primaryStage().setY(event.getScreenY()- yOffset);
			}
		});
    }
}