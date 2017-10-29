package cz.jarin.parentlock;

class AppModelImpl implements AppModel {

	private String appName;
	private String appPackage;
	private int timePerDay;
	private boolean isEnabled;

	public AppModelImpl(String appName, String appPackage, int timePerDay, boolean isEnabled) {
		this.appName = appName;
		this.appPackage = appPackage;
		this.timePerDay = timePerDay;
		this.isEnabled = isEnabled;
	}

	@Override
	public String getAppName() {
		return appName;
	}

	@Override
	public String getAppPackage() {
		return appPackage;
	}

	@Override
	public void setTimePerDay(int timePerDay) {
		this.timePerDay = timePerDay;
	}

	@Override
	public int getTimePerDay() {
		return timePerDay;
	}

	@Override
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	@Override
	public boolean isEnabled() {
		return isEnabled;
	}

}
