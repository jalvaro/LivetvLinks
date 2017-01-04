package com.jalvaro.livetvlinks;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;
import com.jalvaro.livetvlinks.models.Match;
import com.jalvaro.livetvlinks.views.MatchActivity;

import java.util.Calendar;

import static com.jalvaro.livetvlinks.views.MatchActivity.MATCH_EXTRA;


public class Utils {
    public static final String LIVETV_BASE_URL = "http://livetv.sx/";
    public static final String LIVETV_URL = LIVETV_BASE_URL + "es/";
    private static final int SECONDS = 30;
    private static final int MINUTES = 5;

    public static boolean needsTobeRefreshed(Calendar lastUpdate) {
        Calendar someMinutesAgo = Calendar.getInstance();
        //someMinutesAgo.add(Calendar.MINUTE, -MINUTES);
        someMinutesAgo.add(Calendar.SECOND, -SECONDS);

        return lastUpdate == null || lastUpdate.before(someMinutesAgo);
    }

    public static void copyOnClipboard(Context context, CharSequence text) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("test", text);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(context, R.string.link_copied, Toast.LENGTH_LONG).show();
    }

    public static void startMatchActivity(Context context, Match match) {
        Intent intent = new Intent(context, MatchActivity.class);
        intent.putExtra(MATCH_EXTRA, match);
        context.startActivity(intent);
    }

    public static void startNewExternalActivity(Context context, String packageName) {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        if (intent == null) {
            // Bring user to the market or let them choose an app?
            startExternalBrowser(context, "market://details?id=" + packageName);
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
