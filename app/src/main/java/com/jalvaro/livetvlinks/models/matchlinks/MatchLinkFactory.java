package com.jalvaro.livetvlinks.models.matchlinks;


public class MatchLinkFactory {

    public static MatchLink getMatchLink(int quality, String language, String link, int bitRate) {
        MatchLink matchLink = null;

        LinkType linkType = LinkType.getLinkType(link);

        switch (linkType) {
            case BROWSER:
                matchLink = new BrowserLink(quality, language, link, bitRate);
                break;
            case SOPCAST:
                matchLink = new SopCastLink(quality, language, link, bitRate);
                break;
            case ACESTREAM:
                matchLink = new AceStreamLink(quality, language, link, bitRate);
                break;
            case OTHER:
                matchLink = new OtherLink(quality, language, link, bitRate);
                break;
        }

        return matchLink;
    }
}
