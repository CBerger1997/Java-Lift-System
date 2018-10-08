package LiftPackage;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FloorButton
{
	Button floorBtn = new Button();
	boolean isUp;
	int floorNum;
	
	public FloorButton(Image btnImage, boolean isUp, int floorNum)
	{
		floorBtn.setPrefSize(40, 40);
		floorBtn.setGraphic(new ImageView(btnImage));
		this.isUp = isUp;
		this.floorNum = floorNum;
	}
}
