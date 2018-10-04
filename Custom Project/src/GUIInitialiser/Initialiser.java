package GUIInitialiser;

import LiftPackage.LiftManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class Initialiser extends Application
{
	LiftManager liftManager = new LiftManager(3);

	public static void main(String[] args)
	{
		launch(args);
	}

	public void stop()
	{
		liftManager.stopLifts();
	}
	
	public void start(Stage primaryStage)
	{
		primaryStage.setTitle("Welcome");
		
		primaryStage.setScene(liftManager.LiftGuiBuilder());
		
		primaryStage.show();
	}
}