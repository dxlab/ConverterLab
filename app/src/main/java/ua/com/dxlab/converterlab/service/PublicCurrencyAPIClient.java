package ua.com.dxlab.converterlab.service;

import ua.com.dxlab.converterlab.model.PublicCurrency;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by Dima on 15.09.2015.
 */
public class PublicCurrencyAPIClient {

    public static final String HTTP_RESOURCES_FINANCE_UA = "http://resources.finance.ua/ru/public/";
    private final PublicCurrencyAPI mService;

    public PublicCurrencyAPIClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HTTP_RESOURCES_FINANCE_UA)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        mService = retrofit.create(PublicCurrencyAPI.class);
    }

    Call<PublicCurrency> load() {
        return mService.load();
    }

    public static void main(String[] args) {
        Call<PublicCurrency> call = new PublicCurrencyAPIClient().load();
        call.enqueue(new Callback<PublicCurrency>() {

            @Override
            public void onResponse(Response<PublicCurrency> response) {
                System.out.println(response.body());
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }
}
