package cz.jarin.parentlock.utils;

public class TimeRemainingImpl implements TimeRemaining<Integer> {

	private Integer timeRemaining;

	@Override
	public void setRemainingTime(Integer time) {
		timeRemaining = time;
	}

	@Override
	public Integer getRemainingTime() {
		return timeRemaining;
	}

	@Override
	public void subRemainingTime(Integer time) {
		timeRemaining -= time;
	}

	@Override
	public boolean hasRemainingTime() {
		return timeRemaining > 0;
	}

}
