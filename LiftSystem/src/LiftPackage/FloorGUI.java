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
	
	public ArrayList<FloorButton> upBtns = new ArrayList<FloorButton>();
	public ArrayList<FloorButton> downBtns = new ArrayList<FloorButton>();
	public TextField downQueueTextField = new TextField();
	public TextField upQueueTextField = new TextField();

	public FloorGUI(int numOfFloors)
	{
		for (int i = numOfFloors; i > 0; i--)
		{
			FloorButton upBtn = new FloorButton(upArrowImg, true, i);
			upBtns.add(upBtn);

			//TODO implement this functionality to allow buttons to effect lift queues, 
			//currently just gets lifts that don't contain specified floor, potentially pointless
			
			upBtn.floorBtn.setOnAction(new EventHandler<ActionEvent>()
			{
				public void handle(ActionEvent e)
				{
					LiftQueueUpdate(upBtn);
				}
			});
		}

		for (int i = numOfFloors; i > 0; i--)
		{
			FloorButton downBtn = new FloorButton(downArrowImg, false, i);
			downBtns.add(downBtn);
			
			downBtn.floorBtn.setOnAction(new EventHandler<ActionEvent>()
			{
				public void handle(ActionEvent e)
				{
					LiftQueueUpdate(downBtn);
				}
			});
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

		for (FloorButton button : upBtns)
		{
			upBtnsVBox.getChildren().add(button.floorBtn);
		}

		for (FloorButton button : downBtns)
		{
			downBtnsVBox.getChildren().add(button.floorBtn);
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
	
	public void LiftQueueUpdate(FloorButton btn)
	{
		ArrayList<Lift> liftCheck = new ArrayList<Lift>();
		
		for(Lift lift : LiftManager.lifts)
		{
			if(lift.getUpQueue().contains(btn.floorNum) || lift.getDownQueue().contains(btn.floorNum) || lift.getFloor() == btn.floorNum && lift.getIsUp() == btn.isUp)
			{
				return;
			}
			else
			{
				liftCheck.add(lift);
			}
		}
		
		boolean closeCheck = false;
		
		for(Lift lift : liftCheck)
		{
			if(lift.getIsUp() == true && btn.isUp == true || btn.isUp == true && lift.getIsMoving() == false && lift.getFloor() < btn.floorNum)
			{
				closeCheck = UpButtonChecks(lift, btn);
			}
			else if(lift.getIsUp() == false && btn.isUp == false || btn.isUp == false && lift.getIsMoving() == false && lift.getFloor() > btn.floorNum)
			{
				closeCheck = DownButtonChecks(lift, btn);
			}			
			if(closeCheck)
			{
				return;
			}
		}
	}
	
	public boolean UpButtonChecks(Lift lift, FloorButton btn)
	{
		if((lift.getFloor() == (btn.floorNum - 1)))
		{
			for(int i = 0; i < LiftManager.lifts.size(); i++)
			{
				if(lift == LiftManager.lifts.get(i))
				{
					LiftManager.lifts.get(i).addNewFloorToQueue(btn.floorNum);
					return true;
				}
			}
		}
		//else if()
		//{
			
		//}
		
		return false;		
	}
	
	public boolean DownButtonChecks(Lift lift, FloorButton btn)
	{
		if((lift.getFloor() == (btn.floorNum + 1)))
		{
			for(int i = 0; i < LiftManager.lifts.size(); i++)
			{
				if(lift.equals(LiftManager.lifts.get(i)))
				{
					LiftManager.lifts.get(i).addNewFloorToQueue(btn.floorNum);
					return true;
				}
			}
		}

		return false;
	}
}