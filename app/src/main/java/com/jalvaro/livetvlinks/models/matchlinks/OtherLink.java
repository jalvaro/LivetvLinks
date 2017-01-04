package com.jalvaro.livetvlinks.models.matchlinks;


import static com.jalvaro.livetvlinks.models.matchlinks.LinkType.OTHER;

public class OtherLink extends ExternalLink {
    OtherLink(String rate, String rateColor, String language, String link, String bitRate) {
        super(rate, rateColor, language, link, bitRate, OTHER);
    }
}
