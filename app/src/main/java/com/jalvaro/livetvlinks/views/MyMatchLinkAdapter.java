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
import static com.jalvaro.livetvlinks.models.matchlinks.LinkType.ExternalApp.BROWSER;


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
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        // Inflate only once
        if(convertView == null) {
            convertView = context.getLayoutInflater()
                    .inflate(R.layout.layout_link_group, null, false);
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
        return true;
    }

    private void populateGroupView(View view, LinkType linkType) {
        ((TextView) view.findViewById(R.id.groupNameText)).setText(linkType.getResTextId());
        ((ImageView) view.findViewById(R.id.groupLinkImage)).setImageResource(linkType.getResImageId());
    }

    private void populateItemView(View view, final MatchLink matchLink) {
        ((TextView) view.findViewById(R.id.bitRateText)).setText(matchLink.getId());
        ((TextView) view.findViewById(R.id.rateText)).setText(matchLink.getRate());

        int color = Color.parseColor(matchLink.getRateColor());
        view.findViewById(R.id.rateLayout).setBackgroundColor(color);

        int resId = context.getResources().getIdentifier(matchLink.getLanguage(), "mipmap", context.getPackageName());
        ((ImageView) view.findViewById(R.id.iconImage)).setImageResource(resId);

        populateExternalAppIcon(view, matchLink, 0);

        if (matchLink.getLinkType().getExternalApps().length > 1) {
            populateExternalAppIcon(view, matchLink, 1);
        } else {
            view.findViewById(R.id.openLink1).setVisibility(View.GONE);
        }

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                copyLinkOnClipboard(matchLink.getLink());

                return true;
            }
        });
    }

    private void populateExternalAppIcon(View view, final MatchLink matchLink, final int posExtApp) {
        final LinkType linkType = matchLink.getLinkType();

        int resId = context.getResources().getIdentifier("openLink" + posExtApp, "id", context.getPackageName());
        ImageView openLinkIcon = (ImageView) view.findViewById(resId);
        openLinkIcon.setVisibility(View.VISIBLE);
        openLinkIcon.setImageResource(linkType.getExternalApps()[posExtApp].getResImgId());
        openLinkIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openExternalApp(matchLink.getLink(), linkType.getExternalApps()[posExtApp]);
            }
        });
    }

    private void openExternalApp(String link, LinkType.ExternalApp externalApp) {
        copyLinkOnClipboard(link);
        if (externalApp == BROWSER) {
            startExternalBrowser(context, link);
        } else {
            startNewExternalActivity(context, externalApp);
        }
    }
    
    private void copyLinkOnClipboard(String toCopy) {
        copyOnClipboard(context, toCopy);
    }

    void setMatch(Match match) {
        this.match = match;
    }
}
