package com.jalvaro.livetvlinks.models.matchlinks;


public abstract class ExternalLink extends MatchLink {
    ExternalLink(String rate, String rateColor, String language, String link, String bitRate, LinkType linkType) {
        super(rate, rateColor, language, link, bitRate, linkType);
    }

    @Override
    public String getId() {
        return getLink();
    }
}
