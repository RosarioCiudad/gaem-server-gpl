
package coop.tecso.demoda.iface.helper;



public class DemodaTimer {
	private long timeStart = 0L;
	
	public DemodaTimer() {
		timeStart = System.currentTimeMillis();
	}

	public void reset() {
		timeStart = System.currentTimeMillis();
	}
	
	public long stop() {
		long time = System.currentTimeMillis() - timeStart; 
		reset();
		return time;
	}

	public String stop(String msg) {
		return "DemodaTimer(ms):" + msg + ":" + stop();
	}

}
