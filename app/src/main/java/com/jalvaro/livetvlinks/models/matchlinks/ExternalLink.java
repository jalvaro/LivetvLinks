package com.jalvaro.livetvlinks.models.matchlinks;


public abstract class ExternalLink extends MatchLink {
    ExternalLink(int quality, String language, String link, int bitRate, LinkType linkType) {
        super(quality, language, link, bitRate, linkType, OpenLink.BROWSER);
    }

    @Override
    public String getId() {
        return getLink();
    }
}
