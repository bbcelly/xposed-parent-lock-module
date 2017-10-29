package cz.jarin.parentlock.utils;

public interface TimeRemaining<T> {

	void setRemainingTime(T time);

	T getRemainingTime();

	void subRemainingTime(T time);

	boolean hasRemainingTime();

}
