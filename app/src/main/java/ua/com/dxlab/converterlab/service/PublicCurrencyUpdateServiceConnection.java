package ua.com.dxlab.converterlab.service;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

/**
 * Created by Dima on 15.09.2015.
 */
public class PublicCurrencyUpdateServiceConnection implements ServiceConnection {

    private PublicCurrencyUpdateListener mListener;
    private PublicCurrencyUpdateServiceBinder mBinder;

    public PublicCurrencyUpdateServiceConnection(PublicCurrencyUpdateListener listener) {
        this.mListener = listener;
    }

    @Override
    public void onServiceDisconnected(ComponentName arg0) {
        mBinder.removeListener(mListener);
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        // We've bound to LocalService, cast the IBinder and get LocalService instance
        mBinder = (PublicCurrencyUpdateServiceBinder) service;
        mBinder.addListener(mListener);
    }
}
