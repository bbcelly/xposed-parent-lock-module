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
	public GetAppInfoBroadcastReceiver() {
		super();
//			XposedBridge.log("+++++ create: ");
		Log.d(TAG, "create: +++++");
	}

	@Override
	public void onReceive(Context context, Intent intent) {
//			XposedBridge.log("+++++ receive: ");
		Log.d(TAG, "receive: +++++");
	}
}

