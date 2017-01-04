package com.jalvaro.livetvlinks.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class MainActivity extends AppCompatActivity {
    private ListView list;
    private TextView infoText;
    private MyMatchAdapter adapter;
    private Calendar lastUpdate;
    private List<Match> matches;

    /**
     * TODO:
     * que es pugui actualitzar quan l'usuari vulgui
     * cercador?
     * Controlar si els links amagats es mostren
     * Visualment més agradable
     *
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        infoText = (TextView) findViewById(R.id.infoText);
        infoText.setVisibility(View.GONE);
        list = (ListView) findViewById(R.id.upcomingList);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(Utils.needsTobeRefreshed(lastUpdate)) {
            lastUpdate = Calendar.getInstance();
            UrlDataFetcher.fetchFromLiveTv(new BaseUrlCallback());
        }
    }

    private void showMatches() {
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
