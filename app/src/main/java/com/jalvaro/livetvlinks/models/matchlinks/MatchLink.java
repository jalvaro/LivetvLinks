package com.jalvaro.livetvlinks.models.matchlinks;

import com.jalvaro.livetvlinks.models.CustomModel;


public abstract class MatchLink implements CustomModel {
    private int quality;
    private String language;
    private String link;
    private int bitRate;
    private LinkType linkType;

    MatchLink(int quality, String language, String link, int bitRate, LinkType linkType) {
        this.quality = quality;
        this.language = language;
        this.link = link;
        this.bitRate = bitRate;
        this.linkType = linkType;
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

    public abstract String getId();

    public LinkType getLinkType() {
        return linkType;
    }
}
