package com.jalvaro.livetvlinks.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;
import com.jalvaro.livetvlinks.R;
import com.jalvaro.livetvlinks.UrlDataFetcher;
import com.jalvaro.livetvlinks.Utils;
import com.jalvaro.livetvlinks.models.Match;
import com.jalvaro.livetvlinks.models.MatchLink;

import java.util.Calendar;
import java.util.List;

import static com.jalvaro.livetvlinks.Utils.LIVETV_BASE_URL;

public class MatchActivity extends AppCompatActivity {
    private MyMatchLinkAdapter adapter;
    private ExpandableListView expList;
    private Calendar lastUpdate;
    private Match match;

    static final String MATCH_EXTRA = "match";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        match = (Match) getIntent().getExtras().get(MATCH_EXTRA);
        ((TextView)findViewById(R.id.matchText)).setText(match.getName());
        ((TextView)findViewById(R.id.timeText)).setText(match.getTime());

        expList = (ExpandableListView) findViewById(R.id.expandableListView);

        lastUpdate = Calendar.getInstance();
        UrlDataFetcher.fetchFromUrl(LIVETV_BASE_URL + match.getMatchUrl(), new MatchUrlCallback());
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(Utils.needsTobeRefreshed(lastUpdate) && match != null) {
            lastUpdate = Calendar.getInstance();
            finish();
        }
    }

    private void showMatchLinks() {
        if (adapter == null) {
            adapter = new MyMatchLinkAdapter(this, match);
            expList.setAdapter(adapter);
        } else {
            //TODO: update
        }
    }

    private void showError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }


    private class MatchUrlCallback implements UrlCallback {

        @Override
        public void processHtml(String html) {
            List<MatchLink> matchLinks = UrlDataFetcher.parseMatchHtml(html);

            for (MatchLink.LinkType linkType : MatchLink.LinkType.values()) {
                List<MatchLink> filtered = Utils.filter(matchLinks, linkType);
                if (filtered.size() > 0) match.setMatchLinksGroup(linkType, filtered);
            }

            showMatchLinks();
        }

        @Override
        public void processError(String error) {
            showError(error);
        }
    }
}
