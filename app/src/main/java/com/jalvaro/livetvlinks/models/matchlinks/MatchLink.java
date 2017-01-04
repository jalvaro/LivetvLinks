package com.jalvaro.livetvlinks.models.matchlinks;

import com.jalvaro.livetvlinks.models.CustomModel;


public abstract class MatchLink implements CustomModel {
    private String rate;
    private String rateColor;
    private String language;
    private String link;
    private String bitRate;
    private LinkType linkType;
    private OpenLink openLink;

    MatchLink(String rate, String rateColor, String language, String link, String bitRate, LinkType linkType, OpenLink openLink) {
        this.rate = rate;
        this.rateColor = rateColor;
        this.language = language;
        this.link = link;
        this.bitRate = bitRate;
        this.linkType = linkType;
        this.openLink = openLink;
    }

    public String getRate() {
        return rate;
    }

    public String getRateColor() {
        return rateColor;
    }

    public String getLanguage() {
        return language;
    }

    public String getLink() {
        return link;
    }

    public String getBitRate() {
        return bitRate;
    }

    public LinkType getLinkType() {
        return linkType;
    }

    public OpenLink getOpenLink() {
        return openLink;
    }

    public abstract String getId();

    public enum OpenLink {
        BROWSER(null), PLEXUS("org.xbmc.kore");

        private String packageName;

        OpenLink(String packageName) {
            this.packageName = packageName;
        }

        public String getPackageName() {
            return packageName;
        }
    }
}
