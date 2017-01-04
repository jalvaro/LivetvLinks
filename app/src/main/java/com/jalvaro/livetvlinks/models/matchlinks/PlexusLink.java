package com.jalvaro.livetvlinks.models.matchlinks;


public abstract class PlexusLink extends MatchLink {
    PlexusLink(int quality, String language, String link, int bitRate, LinkType linkType) {
        super(quality, language, link, bitRate, linkType, OpenLink.PLEXUS);
    }

    @Override
    public String getId() {
        String[] parts = getLink().split("/");
        return parts[parts.length-1];
    }
}
