package cz.jarin.parentlock.services;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import de.robv.android.xposed.XposedBridge;

/**
 * Created by celly on 29/10/2017.
 */

public class GetAppInfoService extends IntentService {

	private static final String TAG = "GetAppInfoService";

	public static class Receiver extends BroadcastReceiver {

		public Receiver() {
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

	public GetAppInfoService() {
		super("GetAppInfoService");
	}

	@Override
	protected void onHandleIntent(@Nullable Intent intent) {
		Log.d(TAG, "onHandleIntent: ");
	}
}
