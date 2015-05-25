package com.example.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by carlushenry on 5/25/15.
 */
public class SearchResultItem {
    @Expose
    @SerializedName("Title")
    private String title;
    @Expose
    @SerializedName("Year")
    private String year;
    @Expose
    @SerializedName("imdbID")
    private String imdbId;
    @Expose
    @SerializedName("Type")
    private String type;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
