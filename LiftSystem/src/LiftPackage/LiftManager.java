package LiftPackage;

import java.util.ArrayList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;

public class LiftManager
{
	public static ArrayList<Lift> lifts = new ArrayList<Lift>();
	ArrayList<Floor> floors = new ArrayList<Floor>();
	FloorGUI floorGUI;
	
	public LiftManager(int numOfLIfts, int numOfFloors)
	{
		floorGUI = new FloorGUI(numOfFloors);
		
		for(int i = 0; i < numOfLIfts; i++)
		{
			Lift lift = new Lift();
			lifts.add(lift);
		}
		for(int i = 1; i <= numOfFloors; i++)
		{
			Floor floor = new Floor(i);
			floors.add(floor);
		}
		
		for (Lift lift : lifts)
		{
			lift.start();
		}
	}
	
	public void stopLifts()
	{
		for (Lift lift : lifts)
		{
			lift.stopRunFlag();
		}
	}
	
	public Scene LiftGuiBuilder()
	{
		HBox combinedGUIs = new HBox();
		combinedGUIs.setSpacing(80);
		combinedGUIs.setAlignment(Pos.CENTER);
		combinedGUIs.getChildren().add(floorGUI.SetupGUI());
		
		for (Lift lift : lifts)
		{
			combinedGUIs.getChildren().add(lift.getLiftGUI().SetupGUI());
		}
		
		Scene scene = new Scene(combinedGUIs, 1900, 600);

		return scene;
	}
}
