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
			
			upBtn.setOnAction(new EventHandler<ActionEvent>()
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
			
			downBtn.setOnAction(new EventHandler<ActionEvent>()
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
			upBtnsVBox.getChildren().add(button);
		}

		for (FloorButton button : downBtns)
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
	
	public void LiftQueueUpdate(FloorButton btn)
	{
		ArrayList<Lift> liftCheck = new ArrayList<Lift>();
		
		for(Lift lift : LiftManager.lifts)
		{
			if(lift.getUpQueue().contains(btn.floorNum) || lift.getDownQueue().contains(btn.floorNum) || lift.getFloor() == btn.floorNum && lift.getIsUp() == btn.isUp)
			{
				return;
			}
			else if(btn.isUp && (lift.getIsUp() || lift.getIsMoving() == false && lift.getFloor() < btn.floorNum))
			{
				liftCheck.add(lift);
			}
			else if(!btn.isUp && (!lift.getIsUp() || lift.getIsMoving() == false && lift.getFloor() > btn.floorNum))
			{
				liftCheck.add(lift);
			}
		}
						
		if(btn.isUp)
		{
			UpButtonChecks(liftCheck, btn);
		}
		else
		{
			DownButtonChecks(liftCheck, btn);
		}
	}
	
	public void UpButtonChecks(ArrayList<Lift> lifts, FloorButton btn)
	{
		ArrayList<Integer> floorDistances = new ArrayList<Integer>();
		
		for(int i = 0; i < lifts.size(); i++)
		{
			floorDistances.add(Math.abs(btn.floorNum - lifts.get(i).getFloor()));
		}
		
		Integer smallestDist = 100;
		int distIndex = 0;
		
		for (int i = 0; i < floorDistances.size(); i++)
		{
			if(floorDistances.get(i) < smallestDist)
			{
				smallestDist = floorDistances.get(i);
				distIndex = i;
			}
		}
		
		for(int i = 0; i < LiftManager.lifts.size(); i++)
		{
			if(lifts.get(distIndex).equals(LiftManager.lifts.get(i)))
			{
				LiftManager.lifts.get(i).addNewFloorToQueue(btn.floorNum);
				return;
			}
		}
	}
	
	public void DownButtonChecks(ArrayList<Lift> lifts, FloorButton btn)
	{	
		ArrayList<Integer> floorDistances = new ArrayList<Integer>();
		
		for(Lift lift : lifts)
		{
			floorDistances.add(Math.abs(lift.getFloor() - btn.floorNum));
		}
		
		Integer smallestDist = 100;
		int distIndex = 0;
		
		for (int i = 0; i < floorDistances.size(); i++)
		{
			if(floorDistances.get(i) < smallestDist)
			{
				smallestDist = floorDistances.get(i);
				distIndex = i;
			}
		}
		
		for(int i = 0; i < LiftManager.lifts.size(); i++)
		{
			if(lifts.get(distIndex).equals(LiftManager.lifts.get(i)))
			{				
				LiftManager.lifts.get(i).addNewFloorToQueue(btn.floorNum);
				return;
			}
		}
	}
}