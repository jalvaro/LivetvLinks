package com.jalvaro.livetvlinks.views;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import com.jalvaro.livetvlinks.R;
import com.jalvaro.livetvlinks.models.Match;
import com.jalvaro.livetvlinks.models.matchlinks.LinkType;
import com.jalvaro.livetvlinks.models.matchlinks.MatchLink;

class MyMatchLinkAdapter extends BaseExpandableListAdapter {
    private Activity context;
    private Match match;

    MyMatchLinkAdapter(Activity context, Match match) {
        this.context = context;
        this.match = match;
    }

    @Override
    public int getGroupCount() {
        return match.getLinkTypes().length;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return match.getMatchLinksGroup(getLinkTypeByGroupPosition(groupPosition)).size();
    }

    private LinkType getLinkTypeByGroupPosition(int groupPosition) {
        return match.getLinkTypes()[groupPosition];
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        // Inflate only once
        if(convertView == null) {
            convertView = context.getLayoutInflater()
                    .inflate(R.layout.layout_item, null, false);
        }

        LinkType linkType = getLinkTypeByGroupPosition(groupPosition);
        ((TextView) convertView.findViewById(R.id.matchText)).setText(linkType.getId());
        ((TextView) convertView.findViewById(R.id.timeText)).setText("");

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        // Inflate only once
        if(convertView == null) {
            convertView = context.getLayoutInflater()
                    .inflate(R.layout.layout_item, null, false);
        }

        LinkType linkType = getLinkTypeByGroupPosition(groupPosition);
        MatchLink matchLink = match.getMatchLinksGroup(linkType).get(childPosition);

        ((TextView) convertView.findViewById(R.id.matchText)).setText(matchLink.getId());
        ((TextView) convertView.findViewById(R.id.timeText)).setText(matchLink.getLanguage());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
