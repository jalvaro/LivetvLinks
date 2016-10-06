package com.jalvaro.livetvlinks.views;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.jalvaro.livetvlinks.models.MatchLink;
import com.jalvaro.livetvlinks.R;

import java.util.List;

/**
 * Created by jordi on 2/10/16.
 */
class MyMatchLinkAdapter extends ArrayAdapter<MatchLink> {
    private int resource;
    private List<MatchLink> matchLinks;

    MyMatchLinkAdapter(Context context, int resource, @NonNull List<MatchLink> matchLinks) {
        super(context, resource, matchLinks);
        this.resource = resource;
        this.matchLinks = matchLinks;
    }


    @Override
    public View getView(int position,
                        View convertView,
                        ViewGroup parent) {

        // Inflate only once
        if(convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater()
                    .inflate(resource, null, false);

            MatchLink matchLink = matchLinks.get(position);

            ((TextView) convertView.findViewById(R.id.matchText)).setText(matchLink.getLink());
            ((TextView) convertView.findViewById(R.id.timeText)).setText(matchLink.getLanguage());
        }

        return convertView;
    }
}
