import java.util.Arrays;
import java.util.Scanner;
import javax.swing.JOptionPane;
class RobotControl
{
	private Robot r;
	public static StringBuilder sb;
	
	public RobotControl(Robot r)
	{
		this.r = r;
	}

	public void control(int barHeights[], int blockHeights[])
	{
		//sampleControlMechanism(barHeights, blockHeights);
		run(barHeights, blockHeights);

	}

	public void sampleControlMechanism(int barHeights[], int blockHeights[])
	{
		// Internally the Robot object maintains the value for Robot height(h),
		// arm-width (w) and picker-depth (d).

		// These values are displayed for your convenience
		// These values are initialised as h=2 w=1 and d=0

		// When you call the methods up() or down() h will be changed
		// When you call the methods extend() or contract() w will be changed
		// When you call the methods lower() or raise() d will be changed

		// sample code to get you started
		// Try running this program with obstacle 555555 and blocks of height
		// 2222 (default)
		// It will work for fisrt block only
		// You are free to introduce any other variables

		int h = 2; // Initial height of arm 1
		int w = 1; // Initial width of arm 2
		int d = 0; // Initial depth of arm 3

		int sourceHt = 12;

		// For Parts (a) and (b) assume all four blocks are of the same height
		// For Part (c) you need to compute this from the values stored in the
		// array blockHeights
		// i.e. sourceHt = blockHeights[0] + blockHeights[1] + ... use a loop!

		//int targetCol1Ht = 0; // Applicable only for part (c) - Initially empty
		//int targetCol2Ht = 0; // Applicable only for part (c) - Initially empty

		// height of block just picked will be 3 for parts A and B
		// For part (c) this value must be extracing the topmost unused value
		// from the array blockHeights

		int blockHt = 3;

		// clearance should be based on the bars, the blocks placed on them,
		// the height of source blocks and the height of current block

		// Initially clearance will be determined by the blocks at source
		// (3+3+3+3=12)
		// as they are higher than any bar and block-height combined

		int clearence = 12;

		// Raise it high enough - assumed max obstacle = 4 < sourceHt

		// this makes sure robot goes high enough to clear any obstacles
		while (h < clearence + 1)
		{
			// Raising 1
			r.up();

			// Current height of arm1 being incremented by 1
			h++;
		}

		System.out.println("Debug 1: height(arm1)= " + h + " width (arm2) = "
				+ w + " depth (arm3) =" + d);

		// this will need to be updated each time a block is dropped off
		int extendAmt = 10;

		// Bring arm 2 to column 10
		while (w < extendAmt)
		{
			// moving 1 step horizontally
			r.extend();

			// Current width of arm2 being incremented by 1
			w++;
		}

		System.out.println("Debug 2: height(arm1)= " + h + " width (arm2) = "
				+ w + " depth (arm3) =" + d);

		// lowering third arm - the amount to lower is based on current height
		// and the top of source blocks

		// the position of the picker (bottom of third arm) is determined by h
		// and d
		while (h - d > sourceHt + 1)
		{
			// lowering third arm
			r.lower();

			// current depth of arm 3 being incremented
			d++;
		}

		// picking the topmost block
		r.pick();

		// topmost block is assumed to be 3 for parts (a) and (b)
		blockHt = 3;

		// When you pick the top block height of source decreases
		sourceHt -= blockHt;

		// raising third arm all the way until d becomes 0
		while (d > 0)
		{
			r.raise();
			d--;
		}

		System.out.println("Debug 3: height(arm1)= " + h + " width (arm2) = "
				+ w + " depth (arm3) =" + d);

		// why not see the effect of changing contractAmt to 6 ?
		int contractAmt = 7;

		// Must be a variable. Initially contract by 3 units to get to column 3
		// where the first bar is placed (from column 10)

		while (contractAmt > 0)
		{
			r.contract();
			contractAmt--;
		}

		System.out.println("Debug 4: height(arm1)= " + h + " width (arm2) = "
				+ w + " depth (arm3) =" + d);

		// You need to lower the third arm so that the block sits just above the
		// bar
		// For part (a) all bars are initially set to 7
		// For Parts (b) and (c) you must extract this value from the array
		// barHeights

		int currentBar = 0;

		// lowering third arm
		while ((h - 1) - d - blockHt > barHeights[currentBar])
		{
			r.lower();
			d++;
		}

		System.out.println("Debug 5: height(arm1)= " + h + " width (arm2) = "
				+ w + " depth (arm3) =" + d);

		// dropping the block
		r.drop();

		// The height of currentBar increases by block just placed
		barHeights[currentBar] += blockHt;

		// raising the third arm all the way
		while (d > 0)
		{
			r.raise();
			d--;
		}
		System.out.println("Debug 6: height(arm1)= " + h + " width (arm2) = "
				+ w + " depth (arm3) =" + d);

		// This just shows the message at the end of the sample robot run -
		// you don't need to duplicate (or even use) this code in your program.

		JOptionPane.showMessageDialog(null,
				"You have moved one block from source "
						+ "to the first bar position.\n"
						+ "Now you may modify this code or "
						+ "redesign the program and come up with "
						+ "your own method of controlling the robot.",
				"Helper Code Execution", JOptionPane.INFORMATION_MESSAGE);
		// You have moved one block from source to the first bar position.
		// You should be able to get started now.
	}
	
	/**************************************************
	 * 
	 * my project starts here
	 * DARCY MORGAN  S3659847
	 * 
	**************************************************/
	Scanner keyboard=new Scanner(System.in);//importing Scanner
	//so that i don't have to exit the code when the blocks have finished moving
	//so i can look at the final state to compare moves to the minimum count 

	final int STARTING_HEIGHT=2;
	final int STARTING_WIDTH=1;		//some constants for changing 
	final int STARTING_DEPTH=0;		//the robot class if you want to
	final int SOURCE_COLUMN=9;		//i count from 0 here, from 1 everywhere else *lol*
	public int h=STARTING_HEIGHT;
	public int w=STARTING_WIDTH;
	public int d=STARTING_DEPTH;
	public int clearance;				//highest y coordinate
	public int sourceHt;				//to track this column's y coordinate
	public int c1Ht=0; 					//part C only for these two
	public int c2Ht=0;		
	public int blockSize;				//size of block current picked by the arm (for B and C)
	public int destinationColumn=10;
	public int lowestAvailableColumn=3;	//rather than merging variables c1Ht and c2Ht into a new array of blockHt 
	//incorporating all y values, i have chosen to keep them separately and use lowestAvailableColumn to increment
	//the columns that don't have both a block and a bar on them
	
	public void run(int barHt[], int blockHt[])
	{
		sourceHt=setSourceHt(blockHt);
		clearance=setClearance(barHt, sourceHt); //i could of rearranged the functions so they don't return the value
		//and internally update, but since i had trouble changing blockHt[] to a public state i stuck with this
		
		while(sourceHt!=0)//when sourceHt is empty, the program is finished and stops 
		{
			
			moveRight(SOURCE_COLUMN,sourceHt);//moving a block in one full navigation requires varied moves based on 
			//clearance, blockSize and destinationColumn. the initial movement is always to the source column,
			//since h, w and d have defaults that aren't ideal
			
			blockSize=blockHt[blockHt.length-1]; //counting from 0, always have to -1 when array indexing like this
			blockHt = Arrays.copyOf(blockHt, blockHt.length-1);
	
			sourceHt=setSourceHt(blockHt);//update sourceHt after r.pick() only 
			switch(blockSize){
			case 1:
				destinationColumn=1;
				break;
			case 2:
				destinationColumn=2;
				break;
			case 3:
				destinationColumn=lowestAvailableColumn;
				lowestAvailableColumn++;
				break;
			default:
				//exception handler
				break;
			}
			clearance=setClearance(barHt, sourceHt); 
			
			if(destinationColumn>2){
			moveLeft(destinationColumn,barHt[destinationColumn-3] );
			barHt[destinationColumn-3]+=3; //in future, if the design is changed to allow blocks of size>3 then 
			//this is where to start with implementation
			}
			else if(blockSize==1){
				moveLeft(destinationColumn, c1Ht);
				c1Ht+=blockSize; //always going to be adding 1, but this is more clear
			}
			else if(blockSize==2){
				moveLeft(destinationColumn, c2Ht);
				c2Ht+=blockSize; //same as above
			}
			else{
				//exception handler
			}
		}

		//System.exit(0);
		//all blocks have been moved at this point
		//@SuppressWarnings("unused")
		//String pause = keyboard.nextLine();
		//what is the java equivalent of System("pause") ? google tells me it's this so i guess this is fine
	}

	public void moveRight(int x,int y){
		//maximum of five actions 
		//up, raise, right(extend), lower, pick
		while(h<=clearance){
			r.up();
			h++;
		}
		while(h-d<=clearance){
			r.raise();
			d--;
		}
		while(w<=x){
			r.extend();
			w++;
		}
		while((h-d)>y+1){
			r.lower();
			d++;
		}
		r.pick();
	}
	public void moveLeft(int x,int y){
		//maximum of four actions
		//raise, left(contract, lower, drop (excluding the special conditional below)
		while(d==0&&h-(blockSize)<clearance+1){
			r.up();//this while loop is for the event that a two or one-size block needs h to raise instead of d
			h++; //this isn't triggered in any of the default setups, but testing revealed this as an issue
			//a copy of this is also nested in the following loop, in the event that d is not 0 when this
			//method is called.
			//an example configuration that triggers one of these two loops is 7743 12321
		}
		while(h-(d+blockSize)<clearance+1){
		r.raise();
		d--;
		while(d==0&&h-(blockSize)<clearance+1){
			r.up();
			h++;
		}
		}
		while(w>x)
		{
			r.contract();
			w--;
		}
		while(h-(d+blockSize)>y+1)
		{
			r.lower();
			d++;
		}
		r.drop();
	}
	
	
	public int setSourceHt(int blockHt[])
	{
		//simple logic to determine current sourceHt
		int tempSourceHt=0;//hard decision on what to call this, i could of stuck with just temp
		for(int i=0;i<blockHt.length;i++){
			tempSourceHt+=blockHt[i];
		}
		return tempSourceHt;
	}
	public int setClearance(int barHt[], int sourceHt){
		//looks through all the columns to set current clearance
		//updated to only look through columns that the arm will pass over (destinationColumn)
		//when blockSize=1 or 2 it has to read all of barHt
		//but if it is size 3 it has to read all the values that it passes over 
		int clearance=0;
		int i=0;
		if(blockSize==3){
			i=destinationColumn-3;

		}
		while(i<barHt.length){
			if(clearance<barHt[i]){
				clearance=barHt[i];
			}
			i++;
		}
		if(clearance<sourceHt){
			clearance=sourceHt;
		}
		if(clearance>12){
			//exception handler
		}
		
		return clearance;
	}
}

