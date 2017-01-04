package com.jalvaro.livetvlinks.models.matchlinks;


public class MatchLinkFactory {

    public static MatchLink getMatchLink(String rate, String rateColor, String language, String link, String bitRate) {
        MatchLink matchLink = null;

        LinkType linkType = LinkType.getLinkType(link);

        switch (linkType) {
            case BROWSER:
                matchLink = new BrowserLink(rate, rateColor, language, link, bitRate);
                break;
            case SOPCAST:
                matchLink = new SopCastLink(rate, rateColor, language, link, bitRate);
                break;
            case ACESTREAM:
                matchLink = new AceStreamLink(rate, rateColor, language, link, bitRate);
                break;
            case OTHER:
                matchLink = new OtherLink(rate, rateColor, language, link, bitRate);
                break;
        }

        return matchLink;
    }
}
