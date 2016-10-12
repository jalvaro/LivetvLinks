package com.jalvaro.livetvlinks.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;
import com.jalvaro.livetvlinks.R;
import com.jalvaro.livetvlinks.UrlDataFetcher;
import com.jalvaro.livetvlinks.Utils;
import com.jalvaro.livetvlinks.models.Match;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.jalvaro.livetvlinks.Utils.LIVETV_URL;

public class MainActivity extends AppCompatActivity {
    private ListView list;
    private MyMatchAdapter adapter;
    private Calendar lastUpdate;
    private boolean test;

    /**
     * TODO:
     * que es pugui actualitzar quan l'usuari vulgui
     * cercador?
     * Obtenir la web en el Locale del mòbil
     * Controlar si els links amagats es mostren
     * Strings en idiomes
     * Que es pugui copiar els links de sopcast, acestream...
     * Visualment més agradable
     *
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = (ListView) findViewById(R.id.upcomingList);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(Utils.needsTobeRefreshed(lastUpdate)) {
            lastUpdate = Calendar.getInstance();
            UrlDataFetcher.fetchFromUrl(LIVETV_URL, new BaseUrlCallback());
        }
    }

    private void showMatches(List<Match> matches) {
        if (adapter == null) {
            adapter = new MyMatchAdapter(this, matches);
            list.setAdapter(adapter);
        } else {
            adapter.setMatches(matches);
            adapter.notifyDataSetChanged();
        }
    }

    private void showError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    private class BaseUrlCallback implements UrlCallback {

        @Override
        public void processHtml(String html) {
            showMatches(UrlDataFetcher.parseBaseHtml(html));
        }

        @Override
        public void processError(String error) {
            showError(error);
        }
    }
}
