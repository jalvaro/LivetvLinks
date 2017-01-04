package com.jalvaro.livetvlinks.models.matchlinks;


import com.jalvaro.livetvlinks.R;

public enum LinkType {
    BROWSER(
            R.mipmap.chrome,
            new ExternalApp[] {ExternalApp.BROWSER},
            R.string.browser,
            "/webplayer.php", "/webplayer2.php"
    ),
    SOPCAST(
            R.mipmap.sopcast,
            new ExternalApp[] {ExternalApp.SOPCAST, ExternalApp.KORE},
            R.string.sopcast,
            "sopcast"
    ),
    ACESTREAM(
            R.mipmap.acestream,
            new ExternalApp[] {ExternalApp.ACESTREAM, ExternalApp.KORE},
            R.string.acestream,
            "acestream"
    ),
    OTHER(
            R.mipmap.chrome,
            new ExternalApp[] {ExternalApp.BROWSER},
            R.string.other
    );

    public enum ExternalApp {
        BROWSER(R.mipmap.webtv2, "", false, ""),
        ACESTREAM(R.mipmap.acestream, "org.acestream.media", true, ""),
        SOPCAST(R.mipmap.sopcast, "org.sopcast.android", false, "http://www.sopcast.org/download/android.html"),
        KORE(R.mipmap.kore2, "org.xbmc.kore", true, "");

        private int resImgId;
        private String packageName;
        private boolean hasPlayStore;
        private String website;

        ExternalApp(int resImgId, String packageName, boolean hasPlayStore, String website) {
            this.resImgId = resImgId;
            this.packageName = packageName;
            this.hasPlayStore = hasPlayStore;
            this.website = website;
        }

        public int getResImgId() {
            return resImgId;
        }

        public String getPackageName() {
            return packageName;
        }

        public boolean hasPlayStore() {
            return hasPlayStore;
        }

        public String getWebsite() {
            return website;
        }
    }

    private int resImageId;
    private ExternalApp[] externalApps;
    private int resTextId;
    private String[] urlPatterns;

    LinkType(int resImageId, ExternalApp[] externalApps, int resTextId, String... urlPatterns) {
        this.resImageId = resImageId;
        this.externalApps = externalApps;
        this.resTextId = resTextId;
        this.urlPatterns = urlPatterns;
    }

    public int getResImageId() {
        return resImageId;
    }

    public ExternalApp[] getExternalApps() {
        return externalApps;
    }

    public int getResTextId() {
        return resTextId;
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
