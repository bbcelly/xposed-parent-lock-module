package cz.jarin.parentlock.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by celly on 29/10/2017.
 */

public class GetAppInfoService extends IntentService {

	private static final String TAG = "GetAppInfoService";

	public GetAppInfoService() {
		super("GetAppInfoService");
	}

	@Override
	protected void onHandleIntent(@Nullable Intent intent) {
		Log.d(TAG, "onHandleIntent: ");
	}
}
