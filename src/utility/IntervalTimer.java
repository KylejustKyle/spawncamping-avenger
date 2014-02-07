package utility;

public class IntervalTimer {
	public long rootTime;
	public long interval;
	public long stoppedProgress;
	public boolean isStopped;
	
	public IntervalTimer(long newInterval) {
		rootTime = System.currentTimeMillis();
		interval = newInterval;
		stoppedProgress = 0;
		isStopped = false;
	}
	
	public boolean isInterval() {
		if (isStopped) {
			return false;
		}
		
		if((System.currentTimeMillis() - rootTime) + stoppedProgress > interval) {
			stoppedProgress = 0;
			return true;
		} else {
			return false;
		}
	}
	
	public void stop() {
		isStopped = true;
		stoppedProgress = System.currentTimeMillis() - rootTime;
	}
	
	public void start() {
		isStopped = false;
		rootTime = System.currentTimeMillis();
	}
}
