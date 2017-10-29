package cz.jarin.parentlock.utils;

public interface TimeLockCounter<T> {

	void enableAppLock();

	void disableAppLock();

	boolean isEnabledAppLock();

	void setAppTimePerDay(T time);

	T getAppTimePerDay();

	void setRemainingTimePerDay(T time);

	void subRemainingTimePerDay(T time);

	T getRemainingTimePerDay();
}
