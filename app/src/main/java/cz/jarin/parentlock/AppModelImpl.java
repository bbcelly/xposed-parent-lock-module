package cz.jarin.parentlock;

class AppModelImpl implements AppModel {

	private String appName;
	private String appPackage;
	private int timeRemaining;
	private boolean isEnabled;

	public AppModelImpl(String appName, String appPackage, int timeRemaining, boolean isEnabled) {
		this.appName = appName;
		this.appPackage = appPackage;
		this.timeRemaining = timeRemaining;
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
	public void setTimeRemaining(int timeRemaining) {
		this.timeRemaining = timeRemaining;
	}

	@Override
	public int getTimeRemaining() {
		return timeRemaining;
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
