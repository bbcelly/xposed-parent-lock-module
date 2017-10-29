package cz.jarin.parentlock;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
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
		holder.appTime.setText(model.getTimeRemaining());
//		holder.appEnable.setEnabled(model.isEnabled());
		holder.appEnable.setEnabled(storageAppLock.isEnabledAppLock(model.getAppPackage()));
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

			appEnable.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					boolean isEnabled = appEnable.isEnabled();
					appModel.setEnabled(isEnabled);
					if (isEnabled) {
						storageAppLock.enableAppLock(appModel.getAppPackage());
					} else {
						storageAppLock.disableAppLock(appModel.getAppPackage());
					}
//					appModel = new A
				}
			});

			view.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					//show dialog for modify time
//					appModel.
				}
			});
		}
	}

}
