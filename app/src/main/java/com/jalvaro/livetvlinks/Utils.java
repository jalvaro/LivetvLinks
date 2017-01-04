package com.jalvaro.livetvlinks;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;
import com.jalvaro.livetvlinks.models.Match;
import com.jalvaro.livetvlinks.models.matchlinks.LinkType;
import com.jalvaro.livetvlinks.views.MatchActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.jalvaro.livetvlinks.views.MatchActivity.MATCH_EXTRA;


public class Utils {
    private static final int MINUTES = 2;

    public static boolean needsTobeRefreshed(Calendar lastUpdate) {
        Calendar someMinutesAgo = Calendar.getInstance();
        someMinutesAgo.add(Calendar.MINUTE, -MINUTES);

        return lastUpdate == null || lastUpdate.before(someMinutesAgo);
    }

    public static void copyOnClipboard(Context context, CharSequence text) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("test", text);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(context, R.string.link_copied, Toast.LENGTH_LONG).show();
    }

    public static List<Match> filter(List<Match> matches, String query) {
        query = query.toLowerCase();
        List<Match> filteredModelList = new ArrayList<>();

        for (Match match : matches) {
            String text = match.getName().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(match);
            }
        }

        return filteredModelList;
    }

    public static void startMatchActivity(Context context, Match match) {
        Intent intent = new Intent(context, MatchActivity.class);
        intent.putExtra(MATCH_EXTRA, match);
        context.startActivity(intent);
    }

    public static void startNewExternalActivity(Context context, LinkType.ExternalApp externalApp) {
        String packageName = externalApp.getPackageName();
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);

        if (intent == null) {
            String uri;
            if (externalApp.hasPlayStore())
                uri = "market://details?id=" + packageName;
            else
                uri = externalApp.getWebsite();

            startExternalBrowser(context, uri);
        } else {
            startActivity(context, intent);
        }
    }

    public static void startExternalBrowser(Context context, String uri) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(context, intent);
    }

    private static void startActivity(Context context, Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
