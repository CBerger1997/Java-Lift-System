package LiftPackage;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FloorButton extends Button
{
	boolean isUp;
	int floorNum;
	
	public FloorButton(Image btnImage, boolean isUp, int floorNum)
	{
		this.setPrefSize(40, 40);
		this.setGraphic(new ImageView(btnImage));
		this.isUp = isUp;
		this.floorNum = floorNum;
	}
}
