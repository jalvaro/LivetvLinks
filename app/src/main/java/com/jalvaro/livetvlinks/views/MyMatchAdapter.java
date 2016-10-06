package com.jalvaro.livetvlinks.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.jalvaro.livetvlinks.R;
import com.jalvaro.livetvlinks.models.Match;

import java.util.List;

import static com.jalvaro.livetvlinks.views.MatchActivity.MATCH_EXTRA;

/**
 * Created by jordi on 2/10/16.
 */
class MyMatchAdapter extends ArrayAdapter<Match> {
    private int resource;
    private List<Match> matches;

    MyMatchAdapter(Context context, int resource, @NonNull List<Match> matches) {
        super(context, resource, matches);
        this.resource = resource;
        this.matches = matches;
    }


    @Override
    public View getView(final int position,
                        View convertView,
                        ViewGroup parent) {

        // Inflate only once
        if(convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater()
                    .inflate(resource, null, false);
        }

        Match match = matches.get(position);

        ((TextView) convertView.findViewById(R.id.matchText)).setText(match.getName());
        ((TextView) convertView.findViewById(R.id.timeText)).setText(match.getTime());
        //((ImageView) convertView.findViewById(R.id.iconImage)).setImageResource(R.drawable.abc_ic_menu_cut_mtrl_alpha);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItem(position);
            }
        });

        return convertView;
    }

    private void onClickItem(int position) {
        Intent intent = new Intent(getContext(), MatchActivity.class);
        intent.putExtra(MATCH_EXTRA, matches.get(position));
        getContext().startActivity(intent);
    }
}
