package ua.com.dxlab.converterlab.service;

import ua.com.dxlab.converterlab.model.PublicCurrency;
import retrofit.Call;
import retrofit.http.GET;

/**
 * Created by Dima on 15.09.2015.
 */
public interface PublicCurrencyAPI {

    @GET("currency-cash.json")
    public Call<PublicCurrency> load();
}
