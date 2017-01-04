package com.jalvaro.livetvlinks.models.matchlinks;

import com.jalvaro.livetvlinks.models.CustomModel;


public abstract class MatchLink implements CustomModel {
    private String rate;
    private String rateColor;
    private String language;
    private String link;
    private String bitRate;
    private LinkType linkType;

    MatchLink(String rate, String rateColor, String language, String link, String bitRate, LinkType linkType) {
        this.rate = rate;
        this.rateColor = rateColor;
        this.language = language;
        this.link = link;
        this.bitRate = bitRate;
        this.linkType = linkType;
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

    public abstract String getId();
}
