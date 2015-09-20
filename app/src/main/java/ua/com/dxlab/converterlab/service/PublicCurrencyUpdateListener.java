package ua.com.dxlab.converterlab.service;

/**
 * Created by Dima on 15.09.2015.
 */
public interface PublicCurrencyUpdateListener {
    void updating();

    void updated();

    void updateError(Throwable t);

    void startUpdate();
}
