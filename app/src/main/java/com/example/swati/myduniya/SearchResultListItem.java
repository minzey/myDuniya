package com.example.swati.myduniya;

/**
 * Created by Swati on 09-10-2015.
 */
public class SearchResultListItem {
    private String mTitle , mUrl, mContent;
    public SearchResultListItem(){

    }
    public void setmTitle(String mTitle){
        this.mTitle=mTitle;
    }
    public void setmUrl(String mUrl){
        this.mUrl=mUrl;
    }
    public void setmContent(String mContent){
        this.mContent=mContent;
    }
    public String getmTitle(){return mTitle;}
    public String getmUrl(){return mUrl;}
    public String getmContent(){return mContent;}

}
