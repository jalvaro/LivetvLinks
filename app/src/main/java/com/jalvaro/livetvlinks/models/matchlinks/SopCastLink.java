package com.jalvaro.livetvlinks.models.matchlinks;


import static com.jalvaro.livetvlinks.models.matchlinks.LinkType.SOPCAST;

public class SopCastLink extends PlexusLink {
    SopCastLink(int quality, String language, String link, int bitRate) {
        super(quality, language, link, bitRate, SOPCAST);
    }
}
