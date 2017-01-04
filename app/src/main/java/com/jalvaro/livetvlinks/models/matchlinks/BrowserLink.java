package com.jalvaro.livetvlinks.models.matchlinks;


import static com.jalvaro.livetvlinks.models.matchlinks.LinkType.BROWSER;

public class BrowserLink extends ExternalLink {
    BrowserLink(String rate, String rateColor, String language, String link, String bitRate) {
        super(rate, rateColor, language, link, bitRate, BROWSER);
    }
}
