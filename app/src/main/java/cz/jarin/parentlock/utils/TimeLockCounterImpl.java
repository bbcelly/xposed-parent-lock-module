package cz.jarin.parentlock.utils;

public class TimeLockCounterImpl<A, T> implements TimeLockCounter<T> {

	private final A appId;

	private final TimeRemaining<T> timeRemaining;

	private final StorageAppLock<A, T> storageAppLock;

	public TimeLockCounterImpl(A appId, StorageAppLock<A, T> storageAppLock, TimeRemaining<T> timeRemaining) {
		this.appId = appId;
		this.storageAppLock = storageAppLock;
		this.timeRemaining = timeRemaining;
	}

	@Override
	public void enableAppLock() {
		storageAppLock.enableAppLock(appId);
	}

	@Override
	public void disableAppLock() {
		storageAppLock.disableAppLock(appId);
	}

	@Override
	public boolean isEnabledAppLock() {
		return storageAppLock.isEnabledAppLock(appId);
	}

	@Override
	public void setAppTimePerDay(T time) {
		storageAppLock.setAppTimePerDay(appId, time);
	}

	@Override
	public T getAppTimePerDay() {
		return storageAppLock.getAppTimePerDay(appId);
	}

	@Override
	public void setRemainingTimePerDay(T time) {
		timeRemaining.setRemainingTime(time);
		storageAppLock.setRemainingTimePerDay(appId, time);
	}

	@Override
	public void subRemainingTimePerDay(T time) {
		timeRemaining.subRemainingTime(time);
		storageAppLock.setRemainingTimePerDay(appId, timeRemaining.getRemainingTime());
	}

	@Override
	public T getRemainingTimePerDay() {
		return storageAppLock.getRemainingTimePerDay(appId);
	}

}
