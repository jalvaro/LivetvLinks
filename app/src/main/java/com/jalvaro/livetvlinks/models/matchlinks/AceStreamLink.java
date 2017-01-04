package com.jalvaro.livetvlinks.models.matchlinks;


import static com.jalvaro.livetvlinks.models.matchlinks.LinkType.ACESTREAM;

public class AceStreamLink extends PlexusLink {
    AceStreamLink(int quality, String language, String link, int bitRate) {
        super(quality, language, link, bitRate, ACESTREAM);
    }
}
