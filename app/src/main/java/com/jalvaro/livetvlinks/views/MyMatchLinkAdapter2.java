package com.jalvaro.livetvlinks.views;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import com.jalvaro.livetvlinks.R;
import com.jalvaro.livetvlinks.models.Match;
import com.jalvaro.livetvlinks.models.MatchLink;

/**
 * Created by jordi on 2/10/16.
 */
class MyMatchLinkAdapter2 extends BaseExpandableListAdapter {
    private Activity context;
    private int resource;
    private Match match;

    MyMatchLinkAdapter2(Activity context, int resource, Match match) {
        this.context = context;
        this.resource = resource;
        this.match = match;
    }

    @Override
    public int getGroupCount() {
        return match.getTypeLinks().length;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return match.getMatchLinksGroup(getGroupPosition(groupPosition)).size();
    }

    private MatchLink.LinkType getGroupPosition(int groupPosition) {
        return match.getTypeLinks()[groupPosition];
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
                    .inflate(resource, null, false);
        }

        MatchLink.LinkType linkType = getGroupPosition(groupPosition);
        ((TextView) convertView.findViewById(R.id.matchText)).setText(linkType.getId());
        ((TextView) convertView.findViewById(R.id.timeText)).setText("");

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        // Inflate only once
        if(convertView == null) {
            convertView = context.getLayoutInflater()
                    .inflate(resource, null, false);
        }

        MatchLink.LinkType linkType = getGroupPosition(groupPosition);
        MatchLink matchLink = match.getMatchLinksGroup(linkType).get(childPosition);

        ((TextView) convertView.findViewById(R.id.matchText)).setText(matchLink.getLink());
        ((TextView) convertView.findViewById(R.id.timeText)).setText(matchLink.getLanguage());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
