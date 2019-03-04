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
	private static Stage primaryStage;
	private AnchorPane rootL;
	
	public static Stage obtenir_le_primaryStage() {
		return primaryStage;
	}

	@Override
	public void start(Stage primaryStage) 
	{
		Main.primaryStage  = primaryStage;
		 
		try 
		{
			FXMLLoader acc = new FXMLLoader() ;
			acc.setLocation(Main.class.getClassLoader().getResource("views/fxml/Connexion.fxml"));
			primaryStage.initStyle(StageStyle.TRANSPARENT);
			rootL = (AnchorPane)acc.load();
			Scene scene = new Scene(rootL);
			Main.primaryStage.setScene(scene);
			Main.primaryStage.show();
			
		} catch (IOException e) 
		  {
			e.printStackTrace();
		  }	
	}

	public static void main(String[] args) throws IOException
	{
		launch(args);
	}
}