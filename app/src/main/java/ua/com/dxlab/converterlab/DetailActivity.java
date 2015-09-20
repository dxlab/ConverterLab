package ua.com.dxlab.converterlab;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;

import ua.com.dxlab.converterlab.adapter.CurrencyAdapter;
import ua.com.dxlab.converterlab.model.Organization;
import ua.com.dxlab.converterlab.service.CurrencyService;
import ua.com.dxlab.converterlab.util.PhoneUtils;


public class DetailActivity extends AppCompatActivity {

    public static final String ID = "id";
    private CurrencyService mCurrencyService;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private CurrencyAdapter mAdapter;
    private ViewGroup mCurrancyListLayout;
    private TextView mTitle;
    private TextView mRegion;
    private TextView mCity;
    private TextView mPhone;
    private TextView mAddress;
    private TextView mLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mCurrencyService = new CurrencyService(this);
        mCurrancyListLayout = (ViewGroup) findViewById(R.id.currencyList);
        mAdapter = new CurrencyAdapter(this);
        mTitle = (TextView) findViewById(R.id.titleText);
        mRegion = (TextView)findViewById(R.id.regionText);
        mCity = (TextView) findViewById(R.id.cityText);
        mPhone = (TextView) findViewById(R.id.phoneText);
        mAddress = (TextView) findViewById(R.id.addressText);
        mLink = (TextView) findViewById(R.id.linkText);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String id = getIntent().getStringExtra(ID);
        Organization organization = mCurrencyService.getOrganization(id);
        getSupportActionBar().setTitle(organization.getTitle());
        getSupportActionBar().setSubtitle(organization.getCity().getName());

        mTitle.setText(organization.getTitle());
        mLink.setText(organization.getLink());
        mAddress.setText(organization.getAddress());
        mRegion.setText(organization.getRegion().getName());
        mCity.setText(organization.getCity().getName());
        mPhone.setText(PhoneUtils.formatPhoneNumber(organization.getPhone()));

        mAdapter.setCurrencies(organization.getCurrencies());
        render();
    }

    private void render() {
        mCurrancyListLayout.removeAllViews();
        for (int i = 0; i < mAdapter.getItemCount(); i++) {
            CurrencyAdapter.RecyclerViewHolder holder = mAdapter.onCreateViewHolder(mCurrancyListLayout, i);
            mAdapter.onBindViewHolder(holder, i);
            mCurrancyListLayout.addView(holder.itemView);
        }
    }

}

