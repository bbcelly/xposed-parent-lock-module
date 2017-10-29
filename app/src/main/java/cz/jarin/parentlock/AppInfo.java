package cz.jarin.parentlock;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cz.jarin.parentlock.utils.StorageAppLock;
import cz.jarin.parentlock.utils.StorageAppLockPrefs;

public class AppInfo {

	private final Context context;

	private StorageAppLock<String, Integer> storageAppLockPrefs;

	public AppInfo(Context context) {
		this.context = context;
		storageAppLockPrefs = new StorageAppLockPrefs(context);
	}

	public List<AppModel> getApps() {
		final PackageManager packageManager = context.getPackageManager();
		Intent intent = new Intent(Intent.ACTION_MAIN, null);
		intent.addCategory(Intent.CATEGORY_LAUNCHER);
		List<ResolveInfo> resInfos = packageManager.queryIntentActivities(intent, 0);
		ArrayList<AppModel> apps = new ArrayList<>();

		for(ResolveInfo resolveInfo : resInfos) {
			AppModel appModel = new AppModelImpl(
					packageManager.getApplicationLabel(resolveInfo.activityInfo.applicationInfo).toString(),
					resolveInfo.activityInfo.packageName,
					storageAppLockPrefs.getRemainingTimePerDay(resolveInfo.activityInfo.packageName),
					storageAppLockPrefs.isEnabledAppLock(resolveInfo.activityInfo.packageName));
			apps.add(appModel);
		}

		Collections.sort(apps, new Comparator<AppModel>() {
			@Override
			public int compare(AppModel o1, AppModel o2) {
				return o1.getAppName().compareTo(o2.getAppName());
			}
		});

		return apps;
	}

}
