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
		for (int i = 10; i > 0; i--)
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
						if (!btn.getText().equals(floorNum.toString()) && btn.getText().equals(button.getText()) && !lift.getUpQueue().contains(btn.getText()))
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
		}

		for (int i = 10; i > 0; i--)
		{
			Integer nameInt = i;
			RadioButton btn = new RadioButton();
			btn.setText(nameInt.toString());
			btn.setPrefSize(40, 40);
			btn.setDisable(true);
			floorRadioBtns.add(btn);
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
		VBox floorBtnsVBox = new VBox();

		VBox floorIndicatorsVBox = new VBox();
		
		floorBtnsVBox.setSpacing(10);
		
		floorIndicatorsVBox.setSpacing(10);

		for (Button button : floorBtns)
		{
			floorBtnsVBox.getChildren().add(button);
		}

		for (RadioButton button : floorRadioBtns)
		{
			floorIndicatorsVBox.getChildren().add(button);
		}

		// --------Queue Text GUI Start--------

		Text upQueueText = new Text("Up Queue");
		Text downQueueText = new Text("Down Queue");
		Text movingText = new Text("Moving: ");

		movingText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

		upQueueText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

		downQueueText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

		VBox queueTextVBox = new VBox();

		VBox queueTextFieldVBox = new VBox();

		queueTextVBox.getChildren().addAll(movingText, upQueueText, downQueueText);

		isMovingText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

		queueTextFieldVBox.getChildren().addAll(isMovingText, upQueueTextField, downQueueTextField);

		HBox combinedQueueHBox = new HBox();

		combinedQueueHBox.getChildren().addAll(queueTextVBox, queueTextFieldVBox);

		combinedQueueHBox.setSpacing(40);

		queueTextVBox.setSpacing(100);

		queueTextVBox.setAlignment(Pos.CENTER);

		queueTextFieldVBox.setSpacing(100);

		queueTextFieldVBox.setAlignment(Pos.CENTER);

		// --------Queue Text GUI End--------

		// --------Current Floor GUI Start--------

		HBox floorButtonsHBox = new HBox();

		floorButtonsHBox.setAlignment(Pos.CENTER);

		floorButtonsHBox.getChildren().addAll(floorBtnsVBox, floorIndicatorsVBox);

		// --------Current Floor GUI End--------

		HBox combinedHBox = new HBox();

		combinedHBox.getChildren().addAll(floorButtonsHBox, combinedQueueHBox);

		combinedHBox.setAlignment(Pos.CENTER);
		
		VBox combinedFloorButtonTextVBox = new VBox();

		combinedFloorButtonTextVBox.setAlignment(Pos.CENTER);
		
		combinedFloorButtonTextVBox.setSpacing(30);

		combinedFloorButtonTextVBox.getChildren().addAll(currentFloorText, combinedHBox);
		
		String cssLayout = "-fx-padding: 10;" + 
                "-fx-border-style: solid inside;" + 
                "-fx-border-width: 5;" +
                "-fx-border-insets: 5;" + 
                "-fx-border-radius: 5;" + 
                "-fx-border-color: black;";
		
		combinedFloorButtonTextVBox.setStyle(cssLayout);;
		
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