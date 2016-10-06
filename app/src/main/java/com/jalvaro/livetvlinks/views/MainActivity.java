package com.jalvaro.livetvlinks.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import com.jalvaro.livetvlinks.models.Match;
import com.jalvaro.livetvlinks.R;
import com.jalvaro.livetvlinks.UrlDataFetcher;
import com.jalvaro.livetvlinks.Utils;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView list;
    private MyMatchAdapter adapter;
    private Calendar lastUpdate;

    /**
     * TODO:
     * que es pugui actualitzar quan l'usuari vulgui
     * cercador?
     * Obtenir la web en el Locale del mòbil
     * Controlar si els links amagats es mostren
     * Strings en idiomes
     * Que es pugui copiar el link
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

        if(Utils.refresh(lastUpdate)) {
            lastUpdate = Calendar.getInstance();
            UrlDataFetcher.fetchFromUrl("http://livetv.sx/es/", new BaseUrlCallback());
        }
    }

    private void showMatches(List<Match> matches) {
        if (adapter == null) {
            adapter = new MyMatchAdapter(this, R.layout.layout_item, matches);
        } else {
            //TODO: update
        }

        list.setAdapter(adapter);
    }

    private class BaseUrlCallback implements UrlCallback {

        @Override
        public void processHtml(String html) {
            showMatches(UrlDataFetcher.parseBaseHtml(html));
        }
    }
}
