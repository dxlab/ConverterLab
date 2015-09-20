package ua.com.dxlab.converterlab.database;

import java.io.File;
import java.text.MessageFormat;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;

/**
 * Database helper class used to manage the creation and upgrading of your
 * database. This class also usually provides the DAOs used by the other
 * classes.
 */
public class OrmLiteDatabase extends OrmLiteSqliteOpenHelper {

	// any time you make changes to your database objects, you may have to
	// increase the database version
	private static final int DATABASE_VERSION = 1;
	protected Context context;

	public OrmLiteDatabase(Context context, String name, boolean obb) {
		super(context, createDatabaseName(context, name, obb), null, DATABASE_VERSION);
		this.context = context;
	}

	public final String getQuery(int resId) {
		return context.getString(resId);
	}	
	
	public final String getQuery(int resId,Object...params) {
		String string = context.getString(resId);
		string = MessageFormat.format(string, params);
		return string;
	}

	public static String createDatabaseName(Context context, String name, boolean obb) {
		return obb ?  context.getObbDir() + File.separator + name : name ;
	}

	/**
	 * This is called when the database is first created. Usually you should
	 * call createTable statements here to create the tables that will store
	 * your data.
	 */
	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {

	}

	/**
	 * This is called when your application is upgraded and it has a higher
	 * version number. This allows you to adjust the various data to match the
	 * new version number.
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {

	}

	/**
	 * Close the database connections and clear any cached DAOs.
	 */
	@Override
	public void close() {
		super.close();
	}
}
