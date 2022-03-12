package Utility;

public class WaitUtility {
	
	public static final int minWait=5;
	public static int mediumwait=10;
	public static int maxWait=15;
	
	public static enum WaitFor
	{
		ElementWait,
		VisibilityOfElement,
	}
	
	public static void FixWait(int timeSeconds) throws InterruptedException
	{
		Thread.sleep(timeSeconds*1000);
		
	}

}
