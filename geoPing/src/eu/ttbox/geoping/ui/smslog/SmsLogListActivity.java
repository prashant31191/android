package eu.ttbox.geoping.ui.smslog;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.apps.analytics.GoogleAnalyticsTracker;

import eu.ttbox.geoping.GeoPingApplication;
import eu.ttbox.geoping.R;
import eu.ttbox.geoping.domain.SmsLogProvider;

public class SmsLogListActivity extends FragmentActivity {

    private static final String TAG = "SmsLogListActivity";

    // init
    private SmsLogListFragment listFragment;

    // ===========================================================
    // Constructor
    // ===========================================================

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.smslog_list_activity);
        // Intents
        handleIntent(getIntent());
        // Tracker
        GoogleAnalyticsTracker tracker = ((GeoPingApplication)getApplication()).tracker();
        tracker.trackPageView("/"+TAG);
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        if (fragment instanceof SmsLogListFragment) {
            listFragment = (SmsLogListFragment) fragment;
        }
    }

    // ===========================================================
    // Menu
    // ===========================================================

    public void onViewEntityClick(String entityId) {
        // TODO View
        // Intent intent = Intents.editSmsLog(SmsLogListActivity.this,
        // entityId);
        // startActivityForResult(intent, EDIT_ENTITY);
    }

    private void deleteAllSmsLog() {
        int deleteCount = getContentResolver().delete(SmsLogProvider.Constants.CONTENT_URI, null, null);
    }

    private void onCancelClick() {
        finish();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_smslog_list, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.menu_delete_all:
            deleteAllSmsLog();
            return true;
        case R.id.menu_cancel:
            onCancelClick();
            return true;
        }
        return false;
    }

    // ===========================================================
    // Intent
    // ===========================================================

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    protected void handleIntent(Intent intent) {
        if (intent == null) {
            return;
        }
        if (Log.isLoggable(TAG, Log.DEBUG)) {
            Log.d(TAG, "handleIntent for action : " + intent.getAction());
        }
    }


    // ===========================================================
    // Other
    // ===========================================================

}
