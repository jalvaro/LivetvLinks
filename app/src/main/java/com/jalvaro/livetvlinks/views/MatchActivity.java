package com.jalvaro.livetvlinks.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.jalvaro.livetvlinks.R;
import com.jalvaro.livetvlinks.UrlDataFetcher;
import com.jalvaro.livetvlinks.Utils;
import com.jalvaro.livetvlinks.models.Match;
import com.jalvaro.livetvlinks.models.MatchLink;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MatchActivity extends AppCompatActivity {
    //private Map<MatchLink.LinkType, ListView> lists;
    //private Map<MatchLink.LinkType, LinearLayout> blocks;
    //private Map<MatchLink.LinkType, MyMatchLinkAdapter> adapters;
    private MyMatchLinkAdapter2 adapter2;
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

        /*adapters = new HashMap<>();
        lists = new HashMap<>();
        blocks = new HashMap<>();

        for (MatchLink.LinkType linkType : MatchLink.LinkType.values()) {
            adapters.put(linkType, null);

            int listId = getResources().getIdentifier(linkType.getId() + "List", "id", getPackageName());
            lists.put(linkType, (ListView) findViewById(listId));

            int blockId = getResources().getIdentifier(linkType.getId() + "Block", "id", getPackageName());
            LinearLayout ll = (LinearLayout) findViewById(blockId);
            ll.setVisibility(View.GONE);
            blocks.put(linkType, ll);
        }*/
        expList = (ExpandableListView) findViewById(R.id.expandableListView);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(Utils.refresh(lastUpdate) && match != null) {
            lastUpdate = Calendar.getInstance();
            UrlDataFetcher.fetchFromUrl("http://livetv.sx/" + match.getMatchUrl(), new MatchUrlCallback());
        }
    }

    private void showMatchLinks() {
        /*for (Map.Entry<MatchLink.LinkType, MyMatchLinkAdapter> entry : adapters.entrySet()) {
            MyMatchLinkAdapter adapter = entry.getValue();
            MatchLink.LinkType linkType = entry.getKey();

            List<MatchLink> filtered = match.getMatchLinksGroup(linkType);
            if (adapter == null) {
                adapter = new MyMatchLinkAdapter(this, R.layout.layout_item, filtered);
            } else {
                //TODO: update
            }

            adapters.put(linkType, adapter);
            lists.get(linkType).setAdapter(adapter);
            if (filtered.size() > 0) blocks.get(linkType).setVisibility(View.VISIBLE);
        }*/

        if (adapter2 == null) {
            adapter2 = new MyMatchLinkAdapter2(this, R.layout.layout_item, match);
        } else {
            //TODO: update
        }

        expList.setAdapter(adapter2);
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
    }
}
