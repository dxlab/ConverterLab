package ua.com.dxlab.converterlab;

import android.app.SearchManager;
import android.content.Context;
import android.content.ServiceConnection;
import android.database.sqlite.SQLiteCursor;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import ua.com.dxlab.converterlab.adapter.OrganizationCursorAdapter;
import ua.com.dxlab.converterlab.database.OrganizationCursorFilter;
import ua.com.dxlab.converterlab.service.CurrencyService;
import ua.com.dxlab.converterlab.service.PublicCurrencyUpdateListener;
import ua.com.dxlab.converterlab.service.PublicCurrencyUpdateService;


public class MainActivity extends AppCompatActivity implements PublicCurrencyUpdateListener, SwipeRefreshLayout.OnRefreshListener, SearchView.OnQueryTextListener {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ServiceConnection mServiceConnection;
    private OrganizationCursorAdapter mAdapter;
    private SQLiteCursor mCursor;
    private OrganizationCursorFilter mCursorFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        initAdapter();

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        PublicCurrencyUpdateService.start(this);
    }

    private void initAdapter() {
        CurrencyService currencyService = new CurrencyService(this);
        mCursor = currencyService.createOrganizationCursor();
        mCursorFilter = new OrganizationCursorFilter(mCursor);
        mAdapter = new OrganizationCursorAdapter(this, mCursor);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        SearchManager sm = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(sm.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mServiceConnection = PublicCurrencyUpdateService.bind(this, this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        PublicCurrencyUpdateService.unbind(this, mServiceConnection);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void updating() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void updated() {
        mSwipeRefreshLayout.setRefreshing(false);
        mCursor.requery();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter.clear();
    }

    @Override
    public void updateError(Throwable t) {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void startUpdate() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void onRefresh() {
        PublicCurrencyUpdateService.start(this);
    }


    private void filter(CharSequence _s) {
        if (_s != null && _s.length() > 2) {
            mCursorFilter.filter(_s);
        } else {
            mCursorFilter.filter(null);
        }
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        filter(newText);
        return false;
    }
}
