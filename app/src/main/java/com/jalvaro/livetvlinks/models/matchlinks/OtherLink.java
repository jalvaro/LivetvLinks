package com.jalvaro.livetvlinks.models.matchlinks;


import static com.jalvaro.livetvlinks.models.matchlinks.LinkType.OTHER;

public class OtherLink extends ExternalLink {
    OtherLink(int quality, String language, String link, int bitRate) {
        super(quality, language, link, bitRate, OTHER);
    }
}
