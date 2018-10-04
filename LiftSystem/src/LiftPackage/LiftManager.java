package LiftPackage;

import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class LiftManager
{
	ArrayList<Lift>lifts = new ArrayList<Lift>();
	
	public LiftManager(int numOfLIfts)
	{
		for(int i = 0; i < numOfLIfts; i++)
		{
			Lift lift = new Lift();
			lifts.add(lift);
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
		TabPane liftTabPane = new TabPane();

		int count = 1;
		
		for (Lift lift : lifts)
		{
			Tab tab = new Tab();
			tab.setClosable(false);
			tab.setText("Lift: " + count);
			tab.setContent(lift.getLiftGUI().SetupGUI());
			liftTabPane.getTabs().add(tab);
			
			count++;
		}
		
		Scene scene = new Scene(liftTabPane, 700, 700);

		return scene;
	}
}
