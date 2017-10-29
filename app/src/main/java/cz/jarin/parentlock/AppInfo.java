package cz.jarin.parentlock;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import cz.jarin.parentlock.utils.StorageAppLock;
import cz.jarin.parentlock.utils.StorageAppLockPrefs;
import cz.jarin.parentlock.utils.TimeLockCounter;
import cz.jarin.parentlock.utils.TimeLockCounterImpl;
import cz.jarin.parentlock.utils.TimeRemaining;
import cz.jarin.parentlock.utils.TimeRemainingImpl;

public class AppInfo {

	private final Context context;

	private StorageAppLock<String, Integer> storageAppLockPrefs;

	public AppInfo(Context context) {
		this.context = context;
		storageAppLockPrefs = new StorageAppLockPrefs(context);
//		TimeRemaining<Integer> tr = new TimeRemainingImpl();
//		TimeLockCounter<Integer> t = new TimeLockCounterImpl<>("a", storageAppLockPrefs, tr);
	}

//	public HashMap<String, String> getApps() {
	public List<AppModel> getApps() {
		final PackageManager packageManager = context.getPackageManager();
		Intent intent = new Intent(Intent.ACTION_MAIN, null);
		intent.addCategory(Intent.CATEGORY_LAUNCHER);
		List<ResolveInfo> resInfos = packageManager.queryIntentActivities(intent, 0);
		//using hashset so that there will be no duplicate packages,
		//if no duplicate packages then there will be no duplicate apps
//		HashMap<String, String> packageNames = new HashMap<>();
//		List<ApplicationInfo> appInfos = new ArrayList<ApplicationInfo>(0);
		ArrayList<AppModel> apps = new ArrayList<>();

		//getting package names and adding them to the hashset
		for(ResolveInfo resolveInfo : resInfos) {
//			if (resolveInfo.activityInfo.packageName.contains("livesport")) {
//				System.out.println("e");
//			}
//			packageNames.put(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name);
			AppModel appModel = new AppModelImpl(
					resolveInfo.activityInfo.name,
					resolveInfo.activityInfo.packageName,
					storageAppLockPrefs.getRemainingTimePerDay(resolveInfo.activityInfo.packageName),
					storageAppLockPrefs.isEnabledAppLock(resolveInfo.activityInfo.packageName));
			apps.add(appModel);
//			System.out.println("XXX: " + resolveInfo.activityInfo.packageName);
		}

//		return packageNames;
		return apps;
	}

}
