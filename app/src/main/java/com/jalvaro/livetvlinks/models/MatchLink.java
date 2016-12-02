package com.jalvaro.livetvlinks.models;

/**
 * Created by jordi on 30/9/16.
 */
public class MatchLink implements CustomModel {
    // TODO: Create a Factory class and 4 MatchLink subclasses.
    public enum LinkType {
        BROWSER("browser", "/webplayer.php", "/webplayer2.php"),
        SOPCAST("sopcast", "sopcast"),
        ACESTREAM("acestream", "acestream"),
        OTHER("other");

        private String id;
        private String[] contains;

        LinkType(String id, String... contains) {
            this.id = id;
            this.contains = contains;
        }

        public String getId() {
            return id;
        }

        static LinkType getLinkType(String link) {
            for (LinkType lt : LinkType.values()) {
                if (lt.isReferencedIn(link))
                    return lt;
            }

            return LinkType.OTHER;
        }
        
        private boolean isReferencedIn(String link) {
            if (contains == null)
                return false;
            
            for (String text : contains) {
                if (link.contains(text)) 
                    return true;
            }
            
            return false;
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
        this.linkType = LinkType.getLinkType(link);
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
