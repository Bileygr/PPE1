package controllers;

import java.io.IOException;
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
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.dao.ConfigurationDAO;

public class ConfigurationController {
	@FXML
	private TextField bdd;
	@FXML
	private TextField hostname;
	@FXML
	private TextField port;
	@FXML
	private TextField utilisateur;
	@FXML
	private TextField mdp;
	@FXML
	private CheckBox email;
	@FXML
	private Button enregistrer;
	@FXML
	private Button fermer;
	@FXML
	private Button retour;
	@FXML 
	private AnchorPane mainpane;
	@FXML
	private Scene main;
	
	private double xOffset;
	private double yOffset;
	
	@FXML
	private void retour(ActionEvent actionEvent) {
		try {
			mainpane.getChildren().clear();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getClassLoader().getResource("views/fxml/Connexion.fxml"));
			AnchorPane userFrame = (AnchorPane) loader.load();
			Scene sc = mainpane.getScene();
			sc.setRoot(userFrame);
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void fermeture(ActionEvent actionEvent) {
		Platform.exit();
        System.exit(0);
	}
	
	@FXML
	private void enregistrer(ActionEvent actionEvent) throws NumberFormatException, SQLException {	
		int email;
		if(this.email.isSelected()) {
			email = 1;
		}else{
			email = 0;
		}
		
		ConfigurationDAO.modifier(hostname.getText(), Integer.parseInt(port.getText()), bdd.getText(), utilisateur.getText(), mdp.getText(), email);
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Alerte!");
		alert.setHeaderText("Configuration");
		alert.setContentText("L'application va redémarrer afin de prendre en compte les changements.");
		alert.showAndWait();
		
		Main.getPrimaryStage().close();
		Platform.runLater( () -> new Main().start( new Stage() ) );
	}
	
	@FXML
    private void initialize() throws ClassNotFoundException, SQLException {
		bdd.setText(ConfigurationDAO.getBDD());
		hostname.setText(ConfigurationDAO.getHostname());
		port.setText(Integer.toString(ConfigurationDAO.getPort()));
		utilisateur.setText(ConfigurationDAO.getUtilisateur());
		mdp.setText(ConfigurationDAO.getMDP());
		
		int email = ConfigurationDAO.getEmail(); 
		
		if(email == 1) {
			this.email.setSelected(true);
		}else {
			this.email.setSelected(false);
		}
		
		mainpane.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				 xOffset = event.getSceneX();
				 yOffset = event.getSceneY();
				 
				 System.out.println(xOffset);
				 System.out.println(yOffset);
			}
		});
		
		mainpane.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Main.getPrimaryStage().setX(event.getScreenX()- xOffset);
				Main.getPrimaryStage().setY(event.getScreenY()- yOffset);
			}
		});
    }
}