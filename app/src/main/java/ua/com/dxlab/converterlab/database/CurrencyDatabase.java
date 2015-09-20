package ua.com.dxlab.converterlab.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import ua.com.dxlab.converterlab.model.City;
import ua.com.dxlab.converterlab.model.Currency;
import ua.com.dxlab.converterlab.model.Organization;
import ua.com.dxlab.converterlab.model.OrganizationCurrancy;
import ua.com.dxlab.converterlab.model.OrganizationType;
import ua.com.dxlab.converterlab.model.Region;

public class CurrencyDatabase extends OrmLiteDatabase {


	public CurrencyDatabase(Context _context) {
		super(_context, "currency", true);
	}

	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		try {
			Log.i(CurrencyDatabase.class.getName(), "onCreate");
			TableUtils.createTable(connectionSource, Currency.class);
			TableUtils.createTable(connectionSource, Region.class);
			TableUtils.createTable(connectionSource, City.class);
			TableUtils.createTable(connectionSource, OrganizationType.class);
			TableUtils.createTable(connectionSource, Organization.class);
			TableUtils.createTable(connectionSource, OrganizationCurrancy.class);
		} catch (Throwable e) {
			Log.e(CurrencyDatabase.class.getName(), "Can't create database", e);
			throw new RuntimeException(e);
		}

	}

	public void init() {
		SQLiteDatabase database = getWritableDatabase();
		database.close();
	}
}
