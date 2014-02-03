package utility;

public class IntervalTimer {
	public long rootTime;
	public long interval;
	
	public IntervalTimer(long newRootTime, long newInterval) {
		rootTime = newRootTime;
		interval = newInterval;
	}
	
	public boolean isInterval() {
		return ((System.currentTimeMillis() - rootTime) > interval);
	}
	
}
