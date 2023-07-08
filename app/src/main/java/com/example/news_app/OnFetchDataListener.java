package com.example.news_app;

import com.example.news_app.Models.NewsHeadlines;

import java.util.List;

public interface OnFetchDataListener<NewsApiResponse> {
    void onFetchData(List<NewsHeadlines> list,String message);
    void onError(String message);

}
