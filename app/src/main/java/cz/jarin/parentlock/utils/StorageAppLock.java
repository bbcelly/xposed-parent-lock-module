package cz.jarin.parentlock.utils;

public interface StorageAppLock<A, T> {

	void enableAppLock(A appId);

	void disableAppLock(A appId);

	boolean isEnabledAppLock(A appId);

	void setAppTimePerDay(A appId, T time);

	T getAppTimePerDay(A appId);

	void setRemainingTimePerDay(A appId, T time);

	T getRemainingTimePerDay(A appId);

}
