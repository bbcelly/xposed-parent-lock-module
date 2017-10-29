package cz.jarin.parentlock.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Date;

import static android.content.Context.MODE_WORLD_READABLE;

public class StorageAppLockPrefs implements StorageAppLock<String, Integer> {

	private static final String prefsName = "lock";
	private static final String prefAppLockedSuffix = "_lck";
	private static final String prefTimeSuffix = "_time";
	private static final String prefTimeRemainingSuffix = "_timeR";
	private static final String prefAppLastDayStartTime = "_ldst";

	private final SharedPreferences sharedPreferences;

	public StorageAppLockPrefs(Context context) {
		sharedPreferences = context.getSharedPreferences(prefsName, Context.MODE_PRIVATE);
	}

	@Override
	public void enableAppLock(String appId) {
		setPrefBoolean(appId + prefAppLockedSuffix, true);
	}

	@Override
	public void disableAppLock(String appId) {
		setPrefBoolean(appId + prefAppLockedSuffix, false);
	}

	@Override
	public boolean isEnabledAppLock(String appId) {
		return sharedPreferences.getBoolean(appId + prefAppLockedSuffix, false);
	}

	@Override
	public void setAppTimePerDay(String appId, Integer time) {
		setPrefInt(appId + prefTimeSuffix, time);
	}

	@Override
	public Integer getAppTimePerDay(String appId) {
		return sharedPreferences.getInt(appId + prefTimeSuffix, 0);
	}

	@Override
	public void setRemainingTimePerDay(String appId, Integer time) {
		setPrefInt(appId + prefTimeRemainingSuffix, time);
	}

	@Override
	public Integer getRemainingTimePerDay(String appId) {
		long todayStartTime = getTodayStartTime();
		if (todayStartTime > sharedPreferences.getLong(appId + prefAppLastDayStartTime, 0)) {
			setPrefLong(appId + prefAppLastDayStartTime, todayStartTime);
			return getAppTimePerDay(appId);
		}
		return sharedPreferences.getInt(appId + prefTimeRemainingSuffix, 0);
	}

	private void setPrefString(String prefName, String prefValue) {
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(prefName, prefValue);
		editor.apply();
	}

	private void setPrefLong(String prefName, Long prefValue) {
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putLong(prefName, prefValue);
		editor.apply();
	}

	private void setPrefInt(String prefName, int prefValue) {
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putInt(prefName, prefValue);
		editor.apply();
	}

	private void setPrefBoolean(String prefName, Boolean prefValue) {
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putBoolean(prefName, prefValue);
		editor.apply();
	}

	private long getTodayStartTime() {
		long currentTime = new Date().getTime();
		long todayTimeRest = currentTime % (24 * 3600 * 1000);
		return currentTime - todayTimeRest;
	}
}
