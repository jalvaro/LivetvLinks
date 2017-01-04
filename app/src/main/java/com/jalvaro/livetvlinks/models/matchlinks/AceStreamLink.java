package com.jalvaro.livetvlinks.models.matchlinks;


import static com.jalvaro.livetvlinks.models.matchlinks.LinkType.ACESTREAM;

public class AceStreamLink extends MatchLink {
    AceStreamLink(int quality, String language, String link, int bitRate) {
        super(quality, language, link, bitRate, ACESTREAM);
    }

    @Override
    public String getId() {
        String[] parts = getLink().split("/");
        return parts[parts.length-1];
    }
}
