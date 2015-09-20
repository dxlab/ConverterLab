package ua.com.dxlab.converterlab;

import android.app.Application;

import ua.com.dxlab.converterlab.database.CurrencyDatabase;
import ua.com.dxlab.converterlab.service.PublicCurrencyUpdateService;

/**
 * Created by Dima on 16.09.2015.
 */
public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CurrencyDatabase currencyDatabase = new CurrencyDatabase(this);
        currencyDatabase.init();
        PublicCurrencyUpdateService.start(this);
    }
}
