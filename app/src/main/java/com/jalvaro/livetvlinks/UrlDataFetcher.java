package com.jalvaro.livetvlinks;

import com.jalvaro.livetvlinks.models.Match;
import com.jalvaro.livetvlinks.models.matchlinks.MatchLink;
import com.jalvaro.livetvlinks.models.matchlinks.MatchLinkFactory;
import com.jalvaro.livetvlinks.views.UrlCallback;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.List;


public class UrlDataFetcher {

    public static void fetchFromUrl(final String url, final UrlCallback urlCallback) {
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    String data = Jsoup.connect(url).timeout(5000).get().html();
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
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onNext(String html) {
                        urlCallback.processHtml(html);
                    }

                    @Override
                    public void onCompleted() { }

                    @Override
                    public void onError(Throwable e) {
                        urlCallback.processError();
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
            String url;
            if (as.size() > 1) {
                url = as.get(1).attr("href");
            } else {
                url = as.first().attr("href");
            }

            links.add(MatchLinkFactory.getMatchLink(0, "", url, 0));
            //Integer bitRate = Integer.valueOf(table.select("").text());
        }

        return links;
    }
}
