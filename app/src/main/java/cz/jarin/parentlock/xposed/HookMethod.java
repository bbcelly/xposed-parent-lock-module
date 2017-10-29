package cz.jarin.parentlock.xposed;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import cz.jarin.parentlock.LoginActivity;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

public class HookMethod implements IXposedHookLoadPackage {

	@Override
	public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {

		XposedBridge.log("+++++ Hook we are in SystemUI!21: " + lpparam.packageName);
		if (!lpparam.packageName.equals("eu.livesport.FlashScores_co_uk_plus"))  //tested app
			return;

		XposedBridge.log("+++++ we are in SystemUI!");

		findAndHookMethod("android.app.Activity", lpparam.classLoader, "onCreate", Bundle.class, new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//				XposedBridge.log("+++++ before: ");
//				// this will be called before the clock was updated by the original method
////				TextView tv = (TextView) param.thisObject;
////				String text = tv.getText().toString();
////				tv.setText(text + " :)");
////				tv.setTextColor(Color.RED);
//				XSharedPreferences pref = new XSharedPreferences(LoginActivity.class.getPackage().getName()); // load the preferences using Xposed (necessary to be accessible from inside the hook, SharedPreferences() won't work)
////				pref.makeWorldReadable();
////				pref.reload();
//				String prefString = pref.getString("xxx", "fail");
//				XposedBridge.log("+++++ xxx3: " + prefString);
				tst();

			}
			@Override
			protected void afterHookedMethod(MethodHookParam param) throws Throwable {
				// this will be called after the clock was updated by the original method
			}
		});
	}

	void tst() {
		XposedBridge.log("+++++ before: ");
		// this will be called before the clock was updated by the original method
//				TextView tv = (TextView) param.thisObject;
//				String text = tv.getText().toString();
//				tv.setText(text + " :)");
//				tv.setTextColor(Color.RED);
		XSharedPreferences pref = new XSharedPreferences(LoginActivity.class.getPackage().getName()); // load the preferences using Xposed (necessary to be accessible from inside the hook, SharedPreferences() won't work)
//				pref.makeWorldReadable();
//				pref.reload();
		String prefString = pref.getString("xxx", "fail");
		XposedBridge.log("+++++ xxx34: " + prefString);

		XSharedPreferences pref2 = new XSharedPreferences(LoginActivity.class.getPackage().getName(), "asd"); // load the preferences using Xposed (necessary to be accessible from inside the hook, SharedPreferences() won't work)
//				pref.makeWorldReadable();
//				pref.reload();
		String prefString2 = pref2.getString("xxx", "fail");
		XposedBridge.log("+++++ xxx35: " + prefString2);

		XSharedPreferences pref3 = new XSharedPreferences(LoginActivity.class.getPackage().getName(), "asdr"); // load the preferences using Xposed (necessary to be accessible from inside the hook, SharedPreferences() won't work)
//				pref.makeWorldReadable();
//				pref.reload();
		String prefString3 = pref3.getString("xxx", "fail");
		XposedBridge.log("+++++ xxx36: " + prefString3);


	}

}
