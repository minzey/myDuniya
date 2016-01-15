package com.example.swati.myduniya;

/**
 * Created by Swati on 04-12-2015.
 */
public class SearchItem {
    private String description,image,story,title;
    public SearchItem(String description, String image, String title,
                       String story){
        super();
        this.image = image;
        this.title = title;
        this.description = description;
        this.story=story;

    }
    public SearchItem(){}
    public SearchItem(SearchItem fd) {
        this.image = fd.image;
        this.title = fd.title;
        this.description = fd.description;
        this.story = fd.story;
    }

    public String getImge() {
        return image;
    }

    public void setImge(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStory(String story){this.story = story;}

    public String getStory(){return story;}

}
