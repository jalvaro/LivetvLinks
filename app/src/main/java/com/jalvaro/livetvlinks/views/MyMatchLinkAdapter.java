package com.jalvaro.livetvlinks.views;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.jalvaro.livetvlinks.R;
import com.jalvaro.livetvlinks.models.Match;
import com.jalvaro.livetvlinks.models.matchlinks.LinkType;
import com.jalvaro.livetvlinks.models.matchlinks.MatchLink;

import static com.jalvaro.livetvlinks.Utils.*;
import static com.jalvaro.livetvlinks.models.matchlinks.MatchLink.OpenLink.BROWSER;
import static com.jalvaro.livetvlinks.models.matchlinks.MatchLink.OpenLink.PLEXUS;


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
        populateGroupView(convertView, linkType);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        // Inflate only once
        if(convertView == null) {
            convertView = context.getLayoutInflater()
                    .inflate(R.layout.layout_link_item, null, false);
        }

        LinkType linkType = getLinkTypeByGroupPosition(groupPosition);
        final MatchLink matchLink = match.getMatchLinksGroup(linkType).get(childPosition);
        populateItemView(convertView, matchLink);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    private void openExternalApp(MatchLink matchLink) {
        copyOnClipboard(context, matchLink.getId());
        if (matchLink.getOpenLink() == BROWSER) {
            startExternalBrowser(context, matchLink.getId());
        } else {
            startNewExternalActivity(context, matchLink.getOpenLink().getPackageName());
        }
    }

    private void populateGroupView(View view, LinkType linkType) {
        ((TextView) view.findViewById(R.id.matchText)).setText(linkType.getId());
        ((TextView) view.findViewById(R.id.timeText)).setText("");
        view.findViewById(R.id.iconImage).setVisibility(View.GONE);
        ImageView openLinkIcon = (ImageView) view.findViewById(R.id.openLink);
        openLinkIcon.setVisibility(View.GONE);
    }

    private void populateItemView(View view, final MatchLink matchLink) {
        ((TextView) view.findViewById(R.id.bitRateText)).setText(matchLink.getId());
        ((TextView) view.findViewById(R.id.rateText)).setText(matchLink.getRate());

        int color = Color.parseColor(matchLink.getRateColor());
        view.findViewById(R.id.rateLayout).setBackgroundColor(color);

        int resId = context.getResources().getIdentifier(matchLink.getLanguage(), "mipmap", context.getPackageName());
        ((ImageView) view.findViewById(R.id.iconImage)).setImageResource(resId);

        ImageView openLinkIcon = (ImageView) view.findViewById(R.id.openLink);
        openLinkIcon.setVisibility(View.VISIBLE);

        if (matchLink.getOpenLink() == PLEXUS) {
            openLinkIcon.setImageResource(R.mipmap.kore);
        } else {
            openLinkIcon.setImageResource(android.R.drawable.ic_menu_send);
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openExternalApp(matchLink);
            }
        });

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                copyOnClipboard(context, matchLink.getId());

                return true;
            }
        });
    }

    public void setMatch(Match match) {
        this.match = match;
    }
}
