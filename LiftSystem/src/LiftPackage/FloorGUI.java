package LiftPackage;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class FloorGUI
{
	
	Image downArrowImg = new Image(getClass().getResourceAsStream("DownArrow.jpg"));
	Image upArrowImg = new Image(getClass().getResourceAsStream("UpArrow.jpg"));
	
	public ArrayList<Button> upBtns = new ArrayList<Button>();
	public ArrayList<Button> downBtns = new ArrayList<Button>();
	public TextField downQueueTextField = new TextField();
	public TextField upQueueTextField = new TextField();

	public FloorGUI(int numOfFloors)
	{
		for (int i = numOfFloors; i > 0; i--)
		{
			Button btn = new Button();
			btn.setPrefSize(40, 40);
			btn.setGraphic(new ImageView(upArrowImg));
			upBtns.add(btn);

			//TODO implement this functionality to allow buttons to effect lift queues, 
			//currently just gets lifts that don't contain specified floor, potentially pointless
			
			btn.setOnAction(new EventHandler<ActionEvent>()
			{
				public void handle(ActionEvent e)
				{
					ArrayList<Lift> liftCheck = new ArrayList<Lift>();
					
					for(Lift lift : LiftManager.lifts)
					{
						if(!lift.getUpQueue().contains(btn.getText()))
						{
							liftCheck.add(lift);
						}
						else
						{
							liftCheck.add(lift);	
						}
					}
					for(int i = 0; i < liftCheck.size(); i++)
					{
						
					}
				}	
			});
		}

		for (int i = numOfFloors; i > 0; i--)
		{
			Button btn = new Button();
			btn.setPrefSize(40, 40);
			btn.setGraphic(new ImageView(downArrowImg));
			downBtns.add(btn);
		}
	}

	public HBox SetupGUI()
	{
		VBox upBtnsVBox = new VBox();

		VBox downBtnsVBox = new VBox();
		
		VBox floorTextVBox = new VBox();
		
		upBtnsVBox.setAlignment(Pos.CENTER);
		downBtnsVBox.setAlignment(Pos.CENTER);
		floorTextVBox.setAlignment(Pos.CENTER);
		upBtnsVBox.setSpacing(10);
		downBtnsVBox.setSpacing(10);
		floorTextVBox.setSpacing(34);

		for (Button button : upBtns)
		{
			upBtnsVBox.getChildren().add(button);
		}

		for (Button button : downBtns)
		{
			downBtnsVBox.getChildren().add(button);
		}
		
		for (Integer i = upBtns.size(); i > 0; i--)
		{
			Text floorText = new Text();
			floorText.setText("Floor: " + i.toString());
			floorTextVBox.getChildren().add(floorText);
		}

		HBox combinedHBox = new HBox();

		combinedHBox.getChildren().addAll(upBtnsVBox, downBtnsVBox, floorTextVBox);

		combinedHBox.setSpacing(10);
		
		combinedHBox.setAlignment(Pos.CENTER);

		return combinedHBox;
	}

	//TODO implement this functionality
	
	/*public void resetButton(String floorNum, String color)
	{
		for (Button btn : floorBtns)
		{
			if (btn.getText().equals(floorNum))
			{
				for (RadioButton button : floorRadioBtns)
				{
					if (btn.getText().equals(button.getText()))
					{
						button.setSelected(false);
						btn.setStyle("-fx-background-color:#" + color);
					}
				}
			}
		}
	}*/
}
