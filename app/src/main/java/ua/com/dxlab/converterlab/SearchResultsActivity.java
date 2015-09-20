package ua.com.dxlab.converterlab;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Dima on 16.09.2015.
 */
public class SearchResultsActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {

        handleIntent(intent);
    }

    private void handleIntent(Intent _intent) {

        if (Intent.ACTION_SEARCH.equals(_intent.getAction())) {
            String query = _intent.getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow
        }
    }
}
