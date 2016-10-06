package com.jalvaro.livetvlinks.models;

/**
 * Created by jordi on 30/9/16.
 */
public class MatchLink implements CustomModel {
    public enum LinkType {
        BROWSER("browser"), SOPCAST("sopcast"), ACESTREAM("acestream"), OTHER("other");

        private String id;

        LinkType(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }
    }

    private LinkType linkType;
    private int quality;
    private String language;
    private String link;
    private int bitRate;

    public MatchLink(int quality, String language, String link, int bitRate) {
        this.quality = quality;
        this.language = language;
        this.link = link;
        this.bitRate = bitRate;

        if (link.contains("sopcast")) linkType = LinkType.SOPCAST;
        else if (link.contains("acestream")) linkType = LinkType.ACESTREAM;
        else if (link.contains("/webplayer.php") || link.contains("/webplayer2.php")) linkType = LinkType.BROWSER;
        else linkType = LinkType.OTHER;
    }

    public LinkType getLinkType() {
        return linkType;
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
}
