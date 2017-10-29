package cz.jarin.parentlock.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by celly on 29/10/2017.
 */

public class GetAppInfoBroadcastReceiver extends BroadcastReceiver {
	private static final String TAG = "GetAppInfoBR";

	public static final String START_GET_APP_INFO_SERVICE = "cz.jarin.parentlock.action.START_GET_APP_INFO_SERVICE";

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(TAG, "receive: +++++");
		Intent getAppInfoService = new Intent(context, GetAppInfoService.class);
		getAppInfoService.putExtra(GetAppInfoService.TIME_EXTRA, intent.getLongExtra(GetAppInfoService.TIME_EXTRA, 0));
		getAppInfoService.putExtra(GetAppInfoService.PACKAGE_NAME_EXTRA, intent.getStringExtra(GetAppInfoService.PACKAGE_NAME_EXTRA));
		context.startService(getAppInfoService);
	}
}

