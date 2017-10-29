package cz.jarin.parentlock;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cz.jarin.parentlock.utils.StorageAppLock;

public class AppListAdapter extends RecyclerView.Adapter<AppListAdapter.RowViewHolder> {

	List<AppModel> apps = new ArrayList<>();

	private StorageAppLock<String, Integer> storageAppLock;

	public AppListAdapter(StorageAppLock<String, Integer> storageAppLock, List<AppModel> appModels) {
		this.storageAppLock = storageAppLock;
		apps = appModels;
	}

	@Override
	public RowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View itemView = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.row_app, parent, false);

		return new RowViewHolder(itemView);
	}

	@Override
	public void onBindViewHolder(RowViewHolder holder, int position) {
		AppModel model = apps.get(position);
		holder.appModel = model;
		holder.appTitle.setText(model.getAppName());
		holder.appTime.setText("Time per day: " + getTime(getTime(storageAppLock.getAppTimePerDay(model.getAppPackage()))));
		holder.appEnable.setChecked(storageAppLock.isEnabledAppLock(model.getAppPackage()));
	}

	@Override
	public int getItemCount() {
		return apps.size();
	}

	public void setData(List<AppModel> appModels) {
		apps = appModels;
	}

	public class RowViewHolder extends RecyclerView.ViewHolder {
		TextView appTitle, appTime;
		CheckBox appEnable;
		AppModel appModel;

		public RowViewHolder(View view) {
			super(view);
			appTitle = view.findViewById(R.id.app_title);
			appTime = view.findViewById(R.id.app_time);
			appEnable = view.findViewById(R.id.app_enabled);

			appEnable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					appModel.setEnabled(isChecked);
					if (isChecked) {
						storageAppLock.enableAppLock(appModel.getAppPackage());
					} else {
						storageAppLock.disableAppLock(appModel.getAppPackage());
					}
				}
			});

			view.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Context context = v.getContext();
					AlertDialog.Builder builder = new AlertDialog.Builder(context);
					builder.setTitle("Set H:M:S usage per day");

					LinearLayout llDialog = new LinearLayout(context);
					llDialog.setOrientation(LinearLayout.HORIZONTAL);

					LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

					int timeSeconds = appModel.getTimePerDay();
					final int[] time = getTime(timeSeconds);

					final NumberPicker npHours = new NumberPicker(context);
					npHours.setMinValue(0);
					npHours.setMaxValue(23);
					npHours.setValue(time[0]);
					npHours.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
						@Override
						public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
							time[0] = newVal;
						}
					});
					llDialog.addView(npHours, llp);

					final NumberPicker npMinutes = new NumberPicker(context);
					npMinutes.setMinValue(0);
					npMinutes.setMaxValue(59);
					npMinutes.setValue(time[1]);
					npMinutes.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
						@Override
						public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
							time[1] = newVal;
						}
					});
					llDialog.addView(npMinutes, llp);

					final NumberPicker npSeconds = new NumberPicker(context);
					npSeconds.setMinValue(0);
					npSeconds.setMaxValue(59);
					npSeconds.setValue(time[2]);
					npSeconds.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
						@Override
						public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
							time[2] = newVal;
						}
					});
					llDialog.addView(npSeconds, llp);


					builder.setView(llDialog);
					builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							int timeOld = appModel.getTimePerDay();
							int timeNew = getTimeInt(time);
							if (timeNew != timeOld) {
								appModel.setTimePerDay(timeNew);
								storageAppLock.setAppTimePerDay(appModel.getAppPackage(), timeNew);
								appTime.setText("Time per day: " + getTime(time));
							}
							dialog.cancel();
						}
					});
					builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
						}
					});
					builder.show();
				}
			});
		}
	}

	private String getTime(int[] timeHMS) {
		String time = "";
		if (timeHMS[0] < 10) time += "0";
		time += timeHMS[0] + ":";
		if (timeHMS[1] < 10) time += "0";
		time += timeHMS[1] + ":";
		if (timeHMS[2] < 10) time += "0";
		time += timeHMS[2];
		return time;
	}

	private int[] getTime(int timeSeconds) {
		int timeH = timeSeconds / 3600;
		int timeM = (timeSeconds - (timeH * 3600)) / 60;
		int timeS = timeSeconds % 60;
		return new int[]{timeH, timeM, timeS};
	}

	private int getTimeInt(int[] timeHMS) {
		return timeHMS[0] * 3600 + timeHMS[1] * 60 + timeHMS[2];
	}

}
