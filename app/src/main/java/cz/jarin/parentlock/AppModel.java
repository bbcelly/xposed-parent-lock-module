package cz.jarin.parentlock;

public interface AppModel {

	String getAppName();

	String getAppPackage();

	void setTimePerDay(int timePerDay);

	int getTimePerDay();

	void setEnabled(boolean isEnabled);

	boolean isEnabled();
}
