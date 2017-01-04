package com.jalvaro.livetvlinks.models.matchlinks;


public enum LinkType {
    BROWSER("Browser", "/webplayer.php", "/webplayer2.php"),
    SOPCAST("Sopcast", "sopcast"),
    ACESTREAM("Acestream", "acestream"),
    OTHER("Other");

    private String id;
    private String[] urlPatterns;

    LinkType(String id, String... urlPatterns) {
        this.id = id;
        this.urlPatterns = urlPatterns;
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
        if (urlPatterns == null)
            return false;

        for (String urlPattern : urlPatterns) {
            if (link.contains(urlPattern))
                return true;
        }

        return false;
    }
}
