package LiftPackage;

import java.util.ArrayList;
import LiftPackage.Lift;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class LiftGUI
{
	public ArrayList<Button> floorBtns = new ArrayList<Button>();
	public ArrayList<RadioButton> floorRadioBtns = new ArrayList<RadioButton>();
	public TextField downQueueTextField = new TextField();
	public TextField upQueueTextField = new TextField();
	public Text currentFloorText = new Text("CurrentFloor:");
	public Text isMovingText = new Text("Moving:");

	public LiftGUI(Lift lift)
	{
		upQueueTextField.setEditable(false);
		downQueueTextField.setEditable(false);

		for (int i = LiftManager.floors.size() - 1; i >= 0; i--)
		{
			Integer nameInt = i;
			Button btn = new Button(nameInt.toString());
			btn.setPrefSize(40, 40);
			btn.setStyle("-fx-background-color:#C0C0C0");
			floorBtns.add(btn);

			btn.setOnAction(new EventHandler<ActionEvent>()
			{
				public void handle(ActionEvent e)
				{
					Integer floorNum = lift.getFloor();
					for (RadioButton button : floorRadioBtns)
					{
						if (!btn.getText().equals(floorNum.toString()) && btn.getText().equals(button.getText()) 
							&& !lift.getUpQueue().contains(	Integer.valueOf(btn.getText())) && !lift.getDownQueue().contains(Integer.valueOf(btn.getText())))
						{
							if (button.isSelected())
							{
								button.setSelected(false);
								btn.setStyle("-fx-background-color:#C0C0C0");
								lift.removeFloorFromQueue(Integer.valueOf(button.getText()));
							}
							else
							{
								button.setSelected(true);
								btn.setStyle("-fx-background-color:#FF3333");
								lift.addNewFloorToQueue(Integer.valueOf(button.getText()));
							}
						}
					}
				}
			});
			
			if(i == 0)
			{
				btn.setStyle("-fx-background-color:#90EE90");
			}
		}

		for (int i = LiftManager.floors.size() - 1; i >= 0; i--)
		{
			Integer nameInt = i;
			RadioButton btn = new RadioButton();
			btn.setText(nameInt.toString());
			btn.setPrefSize(40, 40);
			btn.setDisable(true);
			floorRadioBtns.add(btn);
			
			if(i == 0)
			{
				btn.setSelected(true);
			}
		}

		lift.setFloor(0);
		currentFloorText.setText("Current Floor: " + lift.getFloor());
		currentFloorText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

		lift.setMoving(false);
		isMovingText.setText(String.valueOf(lift.getIsMoving()));
		lift.setIsUp(true);
	}

	public VBox SetupGUI()
	{
		Text upQueueText = new Text("Up Queue");
		upQueueText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

		Text downQueueText = new Text("Down Queue");
		downQueueText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

		Text movingText = new Text("Moving: ");
		movingText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

		VBox floorBtnsVBox = new VBox();
		floorBtnsVBox.setSpacing(10);

		VBox floorIndicatorsVBox = new VBox();
		floorIndicatorsVBox.setSpacing(10);

		VBox queueTextVBox = new VBox();
		queueTextVBox.setAlignment(Pos.CENTER);
		queueTextVBox.setSpacing(100);

		VBox queueTextFieldVBox = new VBox();
		queueTextFieldVBox.setAlignment(Pos.CENTER);
		queueTextFieldVBox.setSpacing(100);

		VBox combinedFloorButtonTextVBox = new VBox();
		combinedFloorButtonTextVBox.setAlignment(Pos.CENTER);
		combinedFloorButtonTextVBox.setSpacing(30);

		HBox combinedQueueHBox = new HBox();
		combinedQueueHBox.setSpacing(40);

		HBox combinedHBox = new HBox();
		combinedHBox.setAlignment(Pos.CENTER);

		HBox floorButtonsHBox = new HBox();
		floorButtonsHBox.setAlignment(Pos.CENTER);

		isMovingText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		
		String cssLayout = "-fx-padding: 10;" + 
                "-fx-border-style: solid inside;" + 
                "-fx-border-width: 5;" +
                "-fx-border-insets: 5;" + 
                "-fx-border-radius: 5;" + 
                "-fx-border-color: black;";
		
		combinedFloorButtonTextVBox.setStyle(cssLayout);;
		
		floorBtnsVBox.getChildren().addAll(floorBtns);
		
		floorIndicatorsVBox.getChildren().addAll(floorRadioBtns);

		// --------Queue Text GUI Start--------

		queueTextVBox.getChildren().addAll(movingText, upQueueText, downQueueText);

		queueTextFieldVBox.getChildren().addAll(isMovingText, upQueueTextField, downQueueTextField);

		combinedQueueHBox.getChildren().addAll(queueTextVBox, queueTextFieldVBox);

		// --------Queue Text GUI End--------

		// --------Current Floor GUI Start--------

		floorButtonsHBox.getChildren().addAll(floorBtnsVBox, floorIndicatorsVBox);

		// --------Current Floor GUI End--------
		
		combinedHBox.getChildren().addAll(floorButtonsHBox, combinedQueueHBox);

		combinedFloorButtonTextVBox.getChildren().addAll(currentFloorText, combinedHBox);
		
		// --------Current Floor GUI End--------
		
		return combinedFloorButtonTextVBox;
	}

	public void resetButton(String floorNum, String color)
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
	}
}