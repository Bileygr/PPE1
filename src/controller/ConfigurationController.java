package controller;

import java.sql.SQLException;
import dao.ConfigurationDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class ConfigurationController {
	@FXML
	private TextField 		bdd;
	@FXML
	private TextField 		hostname;
	@FXML
	private TextField 		port;
	@FXML
	private TextField 		utilisateur;
	@FXML
	private TextField 		mdp;
	@FXML
	private CheckBox		email;
	@FXML
	private Button 			enregistrer;
	@FXML 
	private AnchorPane  	mainPane;
	
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
		alert.setContentText("Veuillez redémarrer l'application pour que les changements prennent effet.");
		alert.showAndWait();
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
    }
}
