package ua.com.dxlab.converterlab.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ua.com.dxlab.converterlab.model.PublicCurrency;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;

/**
 * Created by Dima on 15.09.2015.
 */
public class PublicCurrencyUpdateService extends IntentService implements Callback<PublicCurrency> {

    private final PublicCurrencyUpdateServiceBinder mBinder;
    List<PublicCurrencyUpdateListener> mListeners = new ArrayList<PublicCurrencyUpdateListener>();
    private UpdateStatus mStatus = null;
    private Throwable mThrowable = null;
    private CurrencyService mCurrencyService;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param _name Used to _name the worker thread, important only for debugging.
     */
    public PublicCurrencyUpdateService(String _name) {
        super(_name);
        mBinder = new PublicCurrencyUpdateServiceBinder(this);
    }

    public PublicCurrencyUpdateService() {
        this(PublicCurrencyUpdateService.class.getSimpleName());
    }

    public static PublicCurrencyUpdateServiceConnection bind(Context _context, PublicCurrencyUpdateListener _listener) {
        PublicCurrencyUpdateServiceConnection connection = new PublicCurrencyUpdateServiceConnection(_listener);
        Intent intent = new Intent(_context, PublicCurrencyUpdateService.class);
        _context.bindService(intent, connection, BIND_AUTO_CREATE);
        return connection;
    }

    public static void start(Context _context) {
        Intent intent = new Intent(_context, PublicCurrencyUpdateService.class);
        _context.startService(intent);
    }

    public void fireEvent(PublicCurrencyUpdateListener _listener) {
        if (mStatus == null) {
            return;
        }
        switch (mStatus) {
            case LOADING:
                _listener.updating();
                break;
            case LOADED:
                _listener.updated();
                break;
            case ERROR:
                _listener.updateError(mThrowable);
                break;
        }
    }

    @Override
    public void onResponse(final Response<PublicCurrency> response) {
        Log.i(PublicCurrencyUpdateService.class.getSimpleName(), "Successfully updated data:" + response.body());
        AsyncTask<Void, Void, Void> asyncTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                updateDatabase(response);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                fireEvents();
            }
        };

        asyncTask.execute();

    }

    private void updateDatabase(Response<PublicCurrency> _response) {
        mCurrencyService = new CurrencyService(getApplicationContext());
        try {
            mCurrencyService.update(_response.body());
            mThrowable = null;
            mStatus = UpdateStatus.LOADED;
        } catch (Throwable e) {
            mThrowable = e;
            mStatus = UpdateStatus.ERROR;
            Log.e(PublicCurrencyUpdateService.class.getSimpleName(), e.toString(), e);
        }
    }

    @Override
    public void onFailure(Throwable e) {
        mStatus = UpdateStatus.ERROR;
        mThrowable = e;
        fireEvents();
        Log.e(PublicCurrencyUpdateService.class.getSimpleName(), e.toString(), e);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (mStatus != UpdateStatus.LOADING) {
            loadData();
        }
    }

    private void loadData() {
        PublicCurrencyAPIClient publicCurrencyClient = new PublicCurrencyAPIClient();
        Call<PublicCurrency> call = publicCurrencyClient.load();
        mStatus = UpdateStatus.LOADING;
        fireEvents();
        call.enqueue(this);
    }

    private void fireEvents() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                doFireEvents();
            }
        });

    }

    private void doFireEvents() {
        for (PublicCurrencyUpdateListener mListener : mListeners) {
            fireEvent(mListener);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public UpdateStatus getStatus() {
        return this.mStatus;
    }

    public static void unbind(Context context, ServiceConnection serviceConnection) {
        if (serviceConnection != null) {
            context.unbindService(serviceConnection);
        }
    }

    enum UpdateStatus {
        LOADING, LOADED, ERROR
    }
}
