package cz.jarin.parentlock;

public interface AppModel {

	String getAppName();

	String getAppPackage();

	void setTimeRemaining(int timeRemaining);

	int getTimeRemaining();

	void setEnabled(boolean isEnabled);

	boolean isEnabled();
}
