package com.jalvaro.livetvlinks.views;



public interface UrlCallback {
    void processHtml(String html);
    void processError();
}