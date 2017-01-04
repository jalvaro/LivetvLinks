package com.jalvaro.livetvlinks.models.matchlinks;


import com.jalvaro.livetvlinks.R;

public enum LinkType {
    BROWSER(R.mipmap.chrome, R.string.browser, "/webplayer.php", "/webplayer2.php"),
    SOPCAST(R.mipmap.sopcast, R.string.sopcast, "sopcast"),
    ACESTREAM(R.mipmap.acestream, R.string.acestream, "acestream"),
    OTHER(R.mipmap.chrome, R.string.other);

    private int resImageId;
    private int resTextId;
    private String[] urlPatterns;

    LinkType(int resImageId, int resTextId, String... urlPatterns) {
        this.resImageId = resImageId;
        this.resTextId = resTextId;
        this.urlPatterns = urlPatterns;
    }

    public int getResTextId() {
        return resTextId;
    }

    public int getResImageId() {
        return resImageId;
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
