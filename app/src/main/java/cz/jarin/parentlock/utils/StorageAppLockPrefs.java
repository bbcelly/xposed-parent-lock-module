package cz.jarin.parentlock.utils;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_WORLD_READABLE;

public class StorageAppLockPrefs implements StorageAppLock<String, Integer> {

	private static final String prefsName = "lock";
	private static final String prefAppLockedSuffix = "_lck";
	private static final String prefTimeSuffix = "_time";
	private static final String prefTimeRemainingSuffix = "_timeR";

	private final SharedPreferences sharedPreferences;

	public StorageAppLockPrefs(Context context) {
		sharedPreferences = context.getSharedPreferences(prefsName, MODE_WORLD_READABLE);
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
		return sharedPreferences.getInt(appId + prefTimeSuffix, Integer.MAX_VALUE);
	}

	@Override
	public void setRemainingTimePerDay(String appId, Integer time) {
		setPrefInt(appId + prefTimeRemainingSuffix, time);
	}

	@Override
	public Integer getRemainingTimePerDay(String appId) {
		return sharedPreferences.getInt(appId + prefTimeRemainingSuffix, Integer.MAX_VALUE);
	}

	private void setPrefString(String prefName, String prefValue) {
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(prefName, prefValue);
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

}
