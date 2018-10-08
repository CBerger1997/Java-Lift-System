package LiftPackage;

import java.util.ArrayList;
import java.util.Collections;

public class Lift extends Thread
{
	private Integer floor;
	private boolean moving = false;
	private boolean isUp = false;
	private boolean runFlag = true;
	private LiftGUI liftGUI;
	private Integer prevFloor;
	private ArrayList<Integer> upQueue = new ArrayList<Integer>();
	private ArrayList<Integer> downQueue = new ArrayList<Integer>();

	public Lift()
	{
		liftGUI = new LiftGUI(this);
	}

	// TODO make each lift depend on the same queue and prioritise lifts dependent
	// on distance from selected floors
	// TODO fix dreadful code...

	public void run()
	{
		while (runFlag)
		{
			if (moving == true && isUp == true)
			{
				if (upQueue.size() > 0)
				{
					for (int i = floor + 1; i < 11; i++)
					{
						if (upQueue.size() > 0)
						{
							threadSleep(500);
	
							prevFloor = floor;
	
							floor = i;
	
							setMovementGUI();
	
							if (upQueue.size() > 0 && floor == upQueue.get(0))
							{
								removeFloorFromQueue(floor);
								threadSleep(2141);
							} 
							else
							{
								moving = false;
								liftGUI.isMovingText.setText("false");
							}
						}
						else
						{
							continue;
						}
					}
				}
				else
				{
					moving = false;
					liftGUI.isMovingText.setText("false");
				}
			}
			else if (moving == true && isUp == false)
			{
				if (downQueue.size() > 0)
				{
					for (int i = floor - 1; i > 0; i--)
					{
						if (downQueue.size() > 0)
						{
							threadSleep(500);
	
							prevFloor = floor;
	
							floor = i;
	
							setMovementGUI();
	
							if (downQueue.size() > 0 && floor == downQueue.get(0))
							{
								removeFloorFromQueue(floor);
								threadSleep(2141);
							} 
							else
							{
								moving = false;
								liftGUI.isMovingText.setText("false");
							}
						} 
						else
						{
							continue;
						}
					}
				}
				else
				{
					moving = false;
					liftGUI.isMovingText.setText("false");
				}
			}
			else
			{
				threadSleep(500);
				
				if (upQueue.size() > 0)
				{
					moving = true;
					isUp = true;
					liftGUI.isMovingText.setText("true");
				} 
				else if (downQueue.size() > 0)
				{
					moving = true;
					isUp = false;
					liftGUI.isMovingText.setText("true");
				}
			}
		}
	}
	
	public void threadSleep(int time)
	{
		try
		{
			Thread.sleep(time);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	public void setMovementGUI()
	{
		liftGUI.currentFloorText.setText("Current Floor: " + String.valueOf(floor));

		liftGUI.resetButton(floor.toString(), "90EE90");
		liftGUI.resetButton(prevFloor.toString(), "C0C0C0");
	}

	public boolean getIsMoving()
	{
		return moving;
	}

	public void setMoving(boolean moving)
	{
		this.moving = moving;
	}

	public int getFloor()
	{
		return floor;
	}

	public void setFloor(int floor)
	{
		this.floor = floor;
	}

	public ArrayList<Integer> getUpQueue()
	{
		return upQueue;
	}

	public ArrayList<Integer> getDownQueue()
	{
		return downQueue;
	}

	public void stopRunFlag()
	{
		runFlag = false;
	}

	public void addNewFloorToQueue(Integer floor)
	{
		if (floor > this.floor)
		{
			upQueue.add(floor);

			Collections.sort(upQueue);

			liftGUI.upQueueTextField.setText(" ");

			for (Integer integer : upQueue)
			{
				liftGUI.upQueueTextField.appendText(integer + " ");
			}

		} 
		else if (floor < this.floor)
		{
			downQueue.add(floor);

			Collections.sort(downQueue);

			Collections.reverse(downQueue);

			liftGUI.downQueueTextField.setText(" ");

			for (Integer integer : downQueue)
			{
				liftGUI.downQueueTextField.appendText(integer + " ");
			}
		}
	}

	public void removeFloorFromQueue(Integer floorNum)
	{
		if (upQueue.contains(floorNum))
		{
			upQueue.remove(floorNum);

			liftGUI.upQueueTextField.setText(" ");

			for (Integer integer : upQueue)
			{
				if (integer != null)
				{
					liftGUI.upQueueTextField.appendText(integer + " ");
				}
			}

			Integer liftFloor = this.floor;

			liftGUI.resetButton(prevFloor.toString(), "C0C0C0");
			liftGUI.resetButton(liftFloor.toString(), "90EE90");
		} 
		else if (downQueue.contains(floorNum))
		{
			downQueue.remove(floorNum);

			liftGUI.downQueueTextField.setText(" ");

			for (Integer integer : downQueue)
			{
				if (integer != null)
				{
					liftGUI.downQueueTextField.appendText(integer + " ");
				}
			}
			Integer liftFloor = this.floor;

			liftGUI.resetButton(prevFloor.toString(), "C0C0C0");
			liftGUI.resetButton(liftFloor.toString(), "90EE90");
		}
	}

	public boolean getIsUp()
	{
		return isUp;
	}

	public void setIsUp(boolean isUp)
	{
		this.isUp = isUp;
	}

	public LiftGUI getLiftGUI()
	{
		return this.liftGUI;
	}
}
