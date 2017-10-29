package cz.jarin.parentlock.xposed;

import android.app.Activity;
import android.os.Handler;

import java.util.HashMap;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import static de.robv.android.xposed.XposedHelpers.getAdditionalInstanceField;
import static de.robv.android.xposed.XposedHelpers.setAdditionalInstanceField;

public class HookMethod implements IXposedHookLoadPackage {

	public static final String INSTANCE_FIELD_HANDLER = "handler";
	public static final String INSTANCE_FIELD_RUNNABLE = "runnable";
	final HashMap<String, Long> tsPerPackage = new HashMap<>();

	@Override
	public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {

		final String packageName = lpparam.packageName;
		tsPerPackage.put(packageName, -1L);

		final Runnable mainRunnable = new Runnable() {

			@Override
			public void run() {
				long ts = tsPerPackage.get(packageName);
				long timeInApp = 0;
				if (ts != -1L) {
					timeInApp = System.currentTimeMillis() - ts;
				}
				tsPerPackage.put(packageName, System.currentTimeMillis());
				XposedBridge.log("+++++: RUN, " + packageName + ", " + timeInApp + "ms");
				//TODO: send info to our module
			}
		};

		findAndHookMethod(android.app.Activity.class.getName(), lpparam.classLoader, "onResume", new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(final MethodHookParam param) throws Throwable {
				final Handler h = new Handler(((Activity)param.thisObject).getMainLooper());
				setAdditionalInstanceField(param.thisObject, INSTANCE_FIELD_HANDLER, h);
				Runnable r = new Runnable() {
					@Override
					public void run() {
						Handler h = (Handler)getAdditionalInstanceField(param.thisObject, INSTANCE_FIELD_HANDLER);
						if (h == null) {
							return;
						}
						mainRunnable.run();
						h.postDelayed(this, 1000);
					}
				};
				setAdditionalInstanceField(param.thisObject, INSTANCE_FIELD_RUNNABLE, r);
				h.post(r);

			}

		});

		findAndHookMethod(android.app.Activity.class.getName(), lpparam.classLoader, "onPause", new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				Handler h = (Handler)getAdditionalInstanceField(param.thisObject, INSTANCE_FIELD_HANDLER);
				Runnable r = (Runnable)getAdditionalInstanceField(param.thisObject, INSTANCE_FIELD_RUNNABLE);

				if (h != null && r != null) {
					h.post(r);
				}
				setAdditionalInstanceField(param.thisObject, INSTANCE_FIELD_HANDLER, null);
				setAdditionalInstanceField(param.thisObject, INSTANCE_FIELD_RUNNABLE, null);
				tsPerPackage.put(packageName, -1L);
			}

		});
	}

}
