package eu.ttbox.geoping.service.slave;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.telephony.CellLocation;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;

/**
 * {link http://blog.gregfiumara.com/archives/82}
 * 
 */
public class BackgroudLocService extends Service {

	private static final String TAG = "BackgroudLocService";

	private final IBinder binder = new LocalBinder();

	// Service
	private TelephonyManager telephonyManager;

	@Override
	public void onCreate() {
		Log.v(TAG, "BackgroudLocService Created");
	}

	@Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v(TAG, "BackgroudLocService -- onStartCommand()");
        // Service
        this.telephonyManager =  (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE); 
        this.telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_CELL_LOCATION | PhoneStateListener.LISTEN_SIGNAL_STRENGTHS  | PhoneStateListener.LISTEN_DATA_CONNECTION_STATE);
        // We want this service to continue running until it is explicitly
        // stopped, so return sticky.
        return START_STICKY;
    }

	/*
	 * In Android 2.0 and later, onStart() is depreciated. Use onStartCommand()
	 * instead, or compile against API Level 5 and use both.
	 * http://android-developers
	 * .blogspot.com/2010/02/service-api-changes-starting-with.html
	 * 
	 * @Override public void onStart(Intent intent, int startId) { Log.v(TAG,
	 * "BackgroudLocService -- onStart()"); }
	 */

	@Override
	public void onDestroy() {
		Log.v(TAG, "BackgroudLocService Destroyed");
		//Service
		 this.telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_NONE);
	}

	// ===========================================================
	// Listener
	// ===========================================================

	private PhoneStateListener phoneStateListener = new PhoneStateListener() {
		/**
		 * Callback invoked when device cell location changes.
		 * bug @see {link http://code.google.com/p/android/issues/detail?id=10931}
		 */
		@Override
		public void onCellLocationChanged(CellLocation location) {
			super.onCellLocationChanged(location);
			Log.d(TAG, "onCellLocationChanged : " + location);
			if (location instanceof GsmCellLocation) {
				GsmCellLocation gsmLocation = (GsmCellLocation)location;
			}
		}
		
		@Override 
	    public void onSignalStrengthsChanged(SignalStrength phone_sig){
			super.onSignalStrengthsChanged(phone_sig);

		}
		
	};

	// ===========================================================
	// Binder
	// ===========================================================

	public class LocalBinder extends Binder {
		public BackgroudLocService getService() {
			return BackgroudLocService.this;
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}
}