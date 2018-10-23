package application;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application
{
	private Stage primaryStage;
	private AnchorPane rootL;

	@Override
	public void start(Stage primaryStage) 
	{
		primaryStage.setTitle("Administrateur");
		this.primaryStage  = primaryStage;
		 
		try 
		{
			FXMLLoader acc = new FXMLLoader() ;
			acc.setLocation(Main.class.getClassLoader().getResource("view/Connexion.fxml"));
			primaryStage.initStyle(StageStyle.TRANSPARENT);
			rootL = (AnchorPane)acc.load();
			Scene scene = new Scene(rootL);
			this.primaryStage.setScene(scene);
			this.primaryStage.show();
			
		} catch (IOException e) 
		  {
			e.printStackTrace();
		  }	
	}

	public static void main(String[] args) 
	{
		launch(args);
	}
}
