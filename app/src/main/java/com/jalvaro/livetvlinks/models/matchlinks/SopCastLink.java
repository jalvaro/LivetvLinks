package com.jalvaro.livetvlinks.models.matchlinks;


import static com.jalvaro.livetvlinks.models.matchlinks.LinkType.SOPCAST;

public class SopCastLink extends MatchLink {
    SopCastLink(int quality, String language, String link, int bitRate) {
        super(quality, language, link, bitRate, SOPCAST);
    }

    @Override
    public String getId() {
        String[] parts = getLink().split("/");
        return parts[parts.length-1];
    }
}
