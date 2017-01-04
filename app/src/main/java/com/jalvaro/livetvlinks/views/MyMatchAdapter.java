package com.jalvaro.livetvlinks.views;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.jalvaro.livetvlinks.R;
import com.jalvaro.livetvlinks.models.Match;

import java.util.List;

import static com.jalvaro.livetvlinks.Utils.startMatchActivity;

class MyMatchAdapter extends BaseAdapter{
    private Activity context;
    private List<Match> matches;

    MyMatchAdapter(Activity context, @NonNull List<Match> matches) {
        this.context = context;
        this.matches = matches;
    }

    @Override
    public int getCount() {
        return matches.size();
    }

    @Override
    public Object getItem(int position) {
        return matches.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position,
                        View convertView,
                        ViewGroup parent) {

        // Inflate only once
        if(convertView == null) {
            convertView = context.getLayoutInflater()
                    .inflate(R.layout.layout_item, null, false);
        }

        final Match match = matches.get(position);

        ((TextView) convertView.findViewById(R.id.matchText)).setText(match.getName());
        ((TextView) convertView.findViewById(R.id.timeText)).setText(match.getTime());
        //((ImageView) convertView.findViewById(R.id.iconImage)).setImageResource(R.drawable.abc_ic_menu_cut_mtrl_alpha);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItem(match);
            }
        });

        return convertView;
    }

    private void onClickItem(Match match) {
        startMatchActivity(context, match);
    }

    void setMatches(List<Match> matches) {
        this.matches = matches;
    }
}
