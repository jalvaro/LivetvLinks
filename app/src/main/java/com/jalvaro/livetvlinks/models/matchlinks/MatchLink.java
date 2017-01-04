package com.jalvaro.livetvlinks.models.matchlinks;

import com.jalvaro.livetvlinks.models.CustomModel;


public abstract class MatchLink implements CustomModel {
    private int quality;
    private String language;
    private String link;
    private int bitRate;
    private LinkType linkType;
    private OpenLink openLink;

    MatchLink(int quality, String language, String link, int bitRate, LinkType linkType, OpenLink openLink) {
        this.quality = quality;
        this.language = language;
        this.link = link;
        this.bitRate = bitRate;
        this.linkType = linkType;
        this.openLink = openLink;
    }

    public int getQuality() {
        return quality;
    }

    public String getLanguage() {
        return language;
    }

    public String getLink() {
        return link;
    }

    public int getBitRate() {
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
