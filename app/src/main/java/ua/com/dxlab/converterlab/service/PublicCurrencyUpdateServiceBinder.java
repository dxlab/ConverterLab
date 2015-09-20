package ua.com.dxlab.converterlab.service;

import android.os.Binder;

/**
 * Created by Dima on 15.09.2015.
 */
public class PublicCurrencyUpdateServiceBinder extends Binder {

    private PublicCurrencyUpdateService mService;

    public PublicCurrencyUpdateServiceBinder(PublicCurrencyUpdateService service) {
        this.mService = service;
    }

    public PublicCurrencyUpdateService getService() {
        return mService;
    }

    public void addListener(PublicCurrencyUpdateListener listener) {
        mService.fireEvent(listener);
        mService.mListeners.add(listener);
    }

    public void removeListener(PublicCurrencyUpdateListener listener) {
        mService.mListeners.remove(listener);
    }
}
