package com.jalvaro.livetvlinks.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jordi on 30/9/16.
 */
public class Match implements CustomModel, Parcelable {
    private String iconUrl;
    private String name;
    private String time;
    private String matchUrl;
    private Map<MatchLink.LinkType, List<MatchLink>> matchLinks;

    public Match(String iconUrl, String name, String time, String matchUrl) {
        this.iconUrl = iconUrl;
        this.name = name;
        this.time = time;
        this.matchUrl = matchUrl;
        matchLinks = new HashMap<>();
    }

    protected Match(Parcel in) {
        iconUrl = in.readString();
        name = in.readString();
        time = in.readString();
        matchUrl = in.readString();
        matchLinks = new HashMap<>();
    }

    public static final Creator<Match> CREATOR = new Creator<Match>() {
        @Override
        public Match createFromParcel(Parcel in) {
            return new Match(in);
        }

        @Override
        public Match[] newArray(int size) {
            return new Match[size];
        }
    };

    public String getIconUrl() {
        return iconUrl;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public String getMatchUrl() {
        return matchUrl;
    }

    public void setMatchLinksGroup(MatchLink.LinkType linkType, List<MatchLink> links) {
        matchLinks.put(linkType, links);
    }

    public List<MatchLink> getMatchLinksGroup(MatchLink.LinkType linkType) {
        return matchLinks.get(linkType);
    }

    public int getMatchLinkGroupsCount() {
        return matchLinks.size();
    }

    public Map<MatchLink.LinkType, List<MatchLink>> getMatchLinks() {
        return matchLinks;
    }


    public MatchLink.LinkType[] getTypeLinks() {
        return matchLinks.keySet().toArray(new MatchLink.LinkType[matchLinks.size()]);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(iconUrl);
        dest.writeString(name);
        dest.writeString(time);
        dest.writeString(matchUrl);
    }
}
