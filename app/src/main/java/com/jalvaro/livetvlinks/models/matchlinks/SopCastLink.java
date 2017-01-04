package com.jalvaro.livetvlinks.models.matchlinks;


import static com.jalvaro.livetvlinks.models.matchlinks.LinkType.SOPCAST;

public class SopCastLink extends PlexusLink {
    SopCastLink(String rate, String rateColor, String language, String link, String bitRate) {
        super(rate, rateColor, language, link, bitRate, SOPCAST);
    }
}
