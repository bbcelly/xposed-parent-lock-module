package cz.jarin.parentlock;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import cz.jarin.parentlock.utils.StorageAppLockPrefs;

public class AppListActivity extends AppCompatActivity {

	private RecyclerView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_app_list);
		listView = findViewById(R.id.listApps);
		final LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
		mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		listView.setLayoutManager(mLayoutManager);
		listView.setItemAnimator(new DefaultItemAnimator());
		listView.setAdapter(new AppListAdapter(new StorageAppLockPrefs(this), new AppInfo(this).getApps()));
	}
}
