package com.jalvaro.livetvlinks.views;

/**
 * Created by jordi on 2/10/16.
 */

public interface UrlCallback {
    void processHtml(String html);
    void processError(String error);
}