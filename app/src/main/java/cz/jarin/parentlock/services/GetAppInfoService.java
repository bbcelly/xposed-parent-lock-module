package cz.jarin.parentlock.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import cz.jarin.parentlock.utils.StorageAppLock;
import cz.jarin.parentlock.utils.StorageAppLockPrefs;

/**
 * Created by celly on 29/10/2017.
 */

public class GetAppInfoService extends IntentService {

	private static final String TAG = "GetAppInfoService";
	public static final String PACKAGE_NAME_EXTRA = "packageName";
	public static final String TIME_EXTRA = "time";

	private static StorageAppLock<String, Integer> storageAppLock;

	public GetAppInfoService() {
		super("GetAppInfoService");
		storageAppLock = new StorageAppLockPrefs(getApplicationContext());
	}

	@Override
	protected void onHandleIntent(@Nullable Intent intent) {
		Log.d(TAG, "onHandleIntent: ");
		if (intent == null) {
			return;
		}
		boolean isAppOk = true;
		String appPackage = intent.getStringExtra(PACKAGE_NAME_EXTRA);
		if (storageAppLock.isEnabledAppLock(appPackage)) {
			int timeSec = Math.round(intent.getLongExtra(TIME_EXTRA, 0) / 1000f);
			int timeRemainingSeconds = storageAppLock.getRemainingTimePerDay(appPackage) - timeSec;
			if (timeRemainingSeconds <= 0) {
				timeRemainingSeconds = 0;
				isAppOk = false;
			}
			storageAppLock.setRemainingTimePerDay(appPackage, timeRemainingSeconds);
		}
	}
}
