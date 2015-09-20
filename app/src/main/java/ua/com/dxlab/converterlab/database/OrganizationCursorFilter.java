package ua.com.dxlab.converterlab.database;

import android.database.sqlite.SQLiteCursor;
import android.util.Log;
import android.widget.Filter;

/**
 * Created by Dima on 16.09.2015.
 */
public class OrganizationCursorFilter extends Filter{

    private SQLiteCursor mCursor;

    public OrganizationCursorFilter(SQLiteCursor _cursor) {
        this.mCursor = _cursor;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        return null;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        String filter = constraint == null ? "%%" : "%" + constraint.toString() + "%";
        mCursor.setSelectionArguments(new String[]{filter.toUpperCase()});
        Log.v(OrganizationCursorFilter.class.getSimpleName(), filter);
        mCursor.requery();
    }
}
