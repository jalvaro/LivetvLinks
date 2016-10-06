package com.jalvaro.livetvlinks;

import com.jalvaro.livetvlinks.models.Match;
import com.jalvaro.livetvlinks.models.MatchLink;
import com.jalvaro.livetvlinks.views.UrlCallback;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jordi on 2/10/16.
 */
public class UrlDataFetcher {

    public static void fetchFromUrl(final String url, final UrlCallback urlCallback) {
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    String data = Jsoup.connect(url).get().html();
                    subscriber.onNext(data); // Emit the contents of the URL
                    subscriber.onCompleted(); // Nothing more to emit
                }catch(Exception e){
                    subscriber.onError(e); // In case there are network errors
                }
            }
        });

        observable
                .subscribeOn(Schedulers.newThread()) // Create a new Thread
                .observeOn(AndroidSchedulers.mainThread()) // Use the UI thread
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String html) {
                        urlCallback.processHtml(html);
                    }
                });
    }

    public static List<Match> parseBaseHtml(String html) {
        Document doc = Jsoup.parse(html);
        Element mainTable = doc.select("span#upcoming").select("tbody").first();

        Elements tables = mainTable.select("table");

        List<Match> matches = new ArrayList<>();

        for (Element table : tables) {
            Element a = table.select("a").first();
            Element desc = table.select("span.evdesc").first();
            Element img = table.select("img").first();
            matches.add(new Match(img.attr("src"), a.text(), desc.text(), a.attr("href")));
        }

        return matches;
    }

    public static List<MatchLink> parseMatchHtml(String html) {
        List<MatchLink> links = new ArrayList<>();

        Document doc = Jsoup.parse(html);

        Elements tables = doc.select("table.lnktbj");

        for (Element table : tables) {
            Elements as = table.select("a");
            if (as.size() > 1) {
                String url = as.get(1).attr("href");
                links.add(new MatchLink(0, "", url, 0));
            } else {
                String url = as.first().attr("href");
                links.add(new MatchLink(0, "", url, 0));
            }
            //Integer bitRate = Integer.valueOf(table.select("").text());
        }

        return links;
    }
}
