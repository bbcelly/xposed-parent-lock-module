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
import android.widget.EditText;
import android.widget.LinearLayout;
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
		holder.appTime.setText("Time remaining: " + model.getTimeRemaining());
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
					builder.setTitle("Set max usage time per day");

					LinearLayout llDialog = new LinearLayout(context);
					llDialog.setOrientation(LinearLayout.VERTICAL);

					final EditText etTime = new EditText(context);
					etTime.setText(String.valueOf(appModel.getTimeRemaining()), TextView.BufferType.EDITABLE);
					etTime.setTextColor(0xff000000);
					LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
					llDialog.addView(etTime, llp);

					builder.setView(llDialog);
					builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							String textOld = appTime.getText().toString();
							String textNew = etTime.getText().toString();
							if (!textNew.equals(textOld)) {
								int timePerDay = Integer.parseInt(textNew);
								appModel.setTimeRemaining(timePerDay);
								storageAppLock.setAppTimePerDay(appModel.getAppPackage(), timePerDay);
								appTime.setText("Time remaining: " + timePerDay);
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

}
