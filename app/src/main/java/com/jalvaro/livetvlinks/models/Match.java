package com.jalvaro.livetvlinks.models;

import android.os.Parcel;
import android.os.Parcelable;
import com.jalvaro.livetvlinks.models.matchlinks.LinkType;
import com.jalvaro.livetvlinks.models.matchlinks.MatchLink;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Match implements CustomModel, Parcelable {
    private String iconUrl;
    private String name;
    private String time;
    private String matchUrl;
    private Map<LinkType, List<MatchLink>> matchLinksMap;

    public Match(String iconUrl, String name, String time, String matchUrl) {
        this.iconUrl = iconUrl;
        this.name = name;
        this.time = time;
        this.matchUrl = matchUrl;
        matchLinksMap = new HashMap<>();
    }

    protected Match(Parcel in) {
        iconUrl = in.readString();
        name = in.readString();
        time = in.readString();
        matchUrl = in.readString();
        matchLinksMap = new HashMap<>();
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

    private void addMatchLink(MatchLink matchLink) {
        List<MatchLink> list = matchLinksMap.get(matchLink.getLinkType());
        if (list == null) {
            list = new ArrayList<>();
            matchLinksMap.put(matchLink.getLinkType(), list);
        }

        list.add(matchLink);
    }

    public void setMatchLinks(List<MatchLink> links) {
        matchLinksMap = new HashMap<>();
        for (MatchLink matchLink : links){
            addMatchLink(matchLink);
        }
    }

    public void setMatchLinksGroup(LinkType linkType, List<MatchLink> links) {
        matchLinksMap.put(linkType, links);
    }

    public List<MatchLink> getMatchLinksGroup(LinkType linkType) {
        return matchLinksMap.get(linkType);
    }

    public LinkType[] getLinkTypes() {
        return matchLinksMap.keySet().toArray(new LinkType[matchLinksMap.size()]);
    }

    public boolean hasLinks() {
        return !matchLinksMap.isEmpty();
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
