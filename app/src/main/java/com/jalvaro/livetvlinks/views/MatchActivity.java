package com.jalvaro.livetvlinks.views;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;
import com.jalvaro.livetvlinks.R;
import com.jalvaro.livetvlinks.UrlDataFetcher;
import com.jalvaro.livetvlinks.Utils;
import com.jalvaro.livetvlinks.models.Match;
import com.jalvaro.livetvlinks.models.matchlinks.MatchLink;

import java.util.Calendar;
import java.util.List;

public class MatchActivity extends AppCompatActivity {
    private MyMatchLinkAdapter adapter;
    private ExpandableListView expList;
    private TextView infoText;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Calendar lastUpdate;
    private Match match;

    public static final String MATCH_EXTRA = "match";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        match = (Match) getIntent().getExtras().get(MATCH_EXTRA);
        ((TextView)findViewById(R.id.matchText)).setText(match.getName());
        ((TextView)findViewById(R.id.timeText)).setText(match.getTime());

        infoText = (TextView) findViewById(R.id.infoMatchText);
        infoText.setVisibility(View.GONE);
        expList = (ExpandableListView) findViewById(R.id.expandableListView);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLinks);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchLinks();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(Utils.needsTobeRefreshed(lastUpdate) && match != null) {
            fetchLinks();
        }
    }

    private void fetchLinks() {
        swipeRefreshLayout.setRefreshing(true);
        lastUpdate = Calendar.getInstance();
        UrlDataFetcher.fetchFromLiveTv(match, new MatchUrlCallback());
    }

    private void showMatchLinks() {
        showInfoText(R.string.empty_results);

        if (adapter == null) {
            adapter = new MyMatchLinkAdapter(this, match);
            expList.setAdapter(adapter);
        }  else {
            adapter.setMatch(match);
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

        if (!match.hasLinks()) {
            infoText.setVisibility(View.VISIBLE);
            infoText.setText(resId);
        } else {
            infoText.setVisibility(View.GONE);
        }
    }

    private class MatchUrlCallback implements UrlCallback {

        @Override
        public void processHtml(String html) {
            List<MatchLink> matchLinks = UrlDataFetcher.parseMatchHtml(html);
            match.setMatchLinks(matchLinks);
            showMatchLinks();
        }

        @Override
        public void processError() {
            showServerError();
        }
    }
}
