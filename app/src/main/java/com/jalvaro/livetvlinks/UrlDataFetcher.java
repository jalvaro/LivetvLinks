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
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class UrlDataFetcher {
    private static final String LIVETV_BASE_URL = "http://livetv.sx/";
    private static final String LIVETV_URL = LIVETV_BASE_URL + "en/";

    public static void fetchFromLiveTv(Match match, UrlCallback urlCallback) {
        fetchFromUrl(LIVETV_BASE_URL + match.getMatchUrl(), urlCallback);
    }

    public static void fetchFromLiveTv(UrlCallback urlCallback) {
        fetchFromUrl(LIVETV_URL, urlCallback);
    }

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
            matches.add(new Match(getIconUrl(table), getName(table), getDesc(table), getMatchUrl(table)));
        }

        return matches;
    }

    private static String getIconUrl(Element table) {
        return table.select("img").first().attr("src");
    }

    private static String getName(Element table) {
        return table.select("a").first().text();
    }

    private static String getDesc(Element table) {
        return table.select("span.evdesc").first().text();
    }

    private static String getMatchUrl(Element table) {
        return table.select("a").first().attr("href");
    }

    public static List<MatchLink> parseMatchHtml(String html) {
        List<MatchLink> links = new ArrayList<>();

        Document doc = Jsoup.parse(html);

        Elements tables = doc.select("table.lnktbj");

        for (Element table : tables) {
            links.add(MatchLinkFactory.getMatchLink(getRate(table), getRateColor(table), getLanguage(table), getLink(table), getBitRate(table)));
        }

        return links;
    }

    private static String getRate(Element table) {
        String rate = table.select("td.rate").select("div").first().text();
        return rate.substring(1);
    }

    private static String getRateColor(Element table) {
        String style = table.select("td.rate").select("div").first().attr("style");
        Pattern pattern = Pattern.compile("background-color: (#[a-fA-F0-9]*);");
        Matcher matcher = pattern.matcher(style);

        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }

    private static String getLanguage(Element table) {
        String url = table.select("img").first().attr("src");
        Pattern pattern = Pattern.compile("/([0-9]*)\\.png");
        Matcher matcher = pattern.matcher(url);

        if (matcher.find()) {
            return "lang_" + matcher.group(1);
        }
        return "";
    }

    private static String getLink(Element table) {
        Elements as = table.select("a");

        if (as.size() > 1) {
            return as.get(1).attr("href");
        }

        return as.first().attr("href");
    }

    private static String getBitRate(Element table) {
        return table.select("td.bitrate").first().text();
    }
}
