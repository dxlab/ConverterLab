package ua.com.dxlab.converterlab.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferencesService {

    private SharedPreferences mSharedPreferences;
    private Context mContext;


    public PreferencesService(Context _context) {
        super();
        this.mContext = _context;
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
    }

    private SharedPreferences getSharedPreferences() {
        return mSharedPreferences;
    }

    private void put(String _key, Object _object) {
        SharedPreferences preferences = getSharedPreferences();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(_key, _object.toString());
        editor.commit();
    }


    public String getString(String _key, String _defValue) {
        return mSharedPreferences.getString(_key, _defValue);
    }

    public String getLatestUpdateDate(){
        return mSharedPreferences.getString("LatestUpdateDate","");
    }

    public void setLatestUpdateDate(String _date){
        put("LatestUpdateDate",_date);
    }

}
