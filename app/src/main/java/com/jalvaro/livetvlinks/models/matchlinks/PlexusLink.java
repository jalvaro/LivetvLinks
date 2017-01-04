package com.jalvaro.livetvlinks.models.matchlinks;


public abstract class PlexusLink extends MatchLink {
    PlexusLink(String rate, String rateColor, String language, String link, String bitRate, LinkType linkType) {
        super(rate, rateColor, language, link, bitRate, linkType);
    }

    @Override
    public String getId() {
        String[] parts = getLink().split("/");
        return parts[parts.length-1];
    }
}
