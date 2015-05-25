package com.example.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by carlushenry on 5/25/15.
 */
public class MovieSearchResult {
    @Expose
    @SerializedName("Search")
    List<SearchResultItem> searchResultItems;

    public List<SearchResultItem> getSearchResultItems() {
        return searchResultItems;
    }

    public void setSearchResultItems(List<SearchResultItem> searchResultItems) {
        this.searchResultItems = searchResultItems;
    }
}
