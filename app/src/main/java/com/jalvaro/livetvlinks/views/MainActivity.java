package com.jalvaro.livetvlinks.views;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.jalvaro.livetvlinks.R;
import com.jalvaro.livetvlinks.UrlDataFetcher;
import com.jalvaro.livetvlinks.Utils;
import com.jalvaro.livetvlinks.models.Match;

import java.util.Calendar;
import java.util.List;

import static com.jalvaro.livetvlinks.Utils.filter;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private ListView list;
    private TextView infoText;
    private SwipeRefreshLayout swipeRefreshLayout;
    private MyMatchAdapter adapter;
    private Calendar lastUpdate;
    private List<Match> matches;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        infoText = (TextView) findViewById(R.id.infoText);
        infoText.setVisibility(View.GONE);
        list = (ListView) findViewById(R.id.upcomingList);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshMatches);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchMatches();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final List<Match> filteredMatches = filter(matches, newText);

        showMatches(filteredMatches);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(Utils.needsTobeRefreshed(lastUpdate)) {
            fetchMatches();
        }
    }

    private void fetchMatches() {
        swipeRefreshLayout.setRefreshing(true);
        lastUpdate = Calendar.getInstance();
        UrlDataFetcher.fetchFromLiveTv(new BaseUrlCallback());
    }

    private void showMatches() {
        showMatches(matches);
    }

    private void showMatches(List<Match> matches) {
        showInfoText(R.string.empty_results);

        if (adapter == null) {
            adapter = new MyMatchAdapter(this, matches);
            list.setAdapter(adapter);
        } else {
            adapter.setMatches(matches);
            adapter.notifyDataSetChanged();
        }
    }

    private void showServerError() {
        int error = R.string.server_error;
        showInfoText(error);
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    private void showInfoText(int resId) {
        swipeRefreshLayout.setRefreshing(false);

        if (matches.isEmpty()) {
            infoText.setVisibility(View.VISIBLE);
            infoText.setText(resId);
        } else {
            infoText.setVisibility(View.GONE);
        }
    }

    private class BaseUrlCallback implements UrlCallback {

        @Override
        public void processHtml(String html) {
            matches = UrlDataFetcher.parseBaseHtml(html);
            showMatches();
        }

        @Override
        public void processError() {
            showServerError();
        }
    }
}
