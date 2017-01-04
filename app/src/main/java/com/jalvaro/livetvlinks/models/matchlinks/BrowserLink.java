package com.jalvaro.livetvlinks.models.matchlinks;


import static com.jalvaro.livetvlinks.models.matchlinks.LinkType.BROWSER;

public class BrowserLink extends MatchLink {
    BrowserLink(int quality, String language, String link, int bitRate) {
        super(quality, language, link, bitRate, BROWSER);
    }

    @Override
    public String getId() {
        return getLink();
    }
}
