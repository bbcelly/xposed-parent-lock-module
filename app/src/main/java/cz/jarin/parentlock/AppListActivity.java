package cz.jarin.parentlock;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AbsListView;
import android.widget.ListView;

import cz.jarin.parentlock.utils.StorageAppLockPrefs;

public class AppListActivity extends AppCompatActivity {

	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_app_list);
		listView = findViewById(R.id.listApps);
//		new AppInfo(this).getApps();
//		listView.setAdapter(new AppListAdapter(new StorageAppLockPrefs(this), new AppInfo(this).getApps()));
	}
}
