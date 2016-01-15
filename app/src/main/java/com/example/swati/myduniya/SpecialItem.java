package com.example.swati.myduniya;

/**
 * Created by Swati on 29-11-2015.
 */
public class SpecialItem { private int id;
    private String category, description,title,image, pubdate,fullstory,html;
    int priority;

    public SpecialItem() {
    }

    public SpecialItem(int id, String category, String image, String title,
                    String pubdate, String description,String fullstory,String html,int priority) {
        super();
        this.id = id;
        this.category = category;
        this.image = image;
        this.title = title;
        this.pubdate = pubdate;
        this.description = description;
        this.fullstory=fullstory;
        this.html=html;
        this.priority=priority;

    }
    public SpecialItem(SpecialItem fd){
        this.id = fd.id;
        this.category = fd.category;
        this.image = fd.image;
        this.title = fd.title;
        this.pubdate = fd.pubdate;
        this.description = fd.description;
        this.fullstory=fd.fullstory;
        this.html=fd.html;
        this.priority=fd.priority;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public String getPubdate() {
        return pubdate;
    }

    public void setPubdate(String pubdate) {this.pubdate = pubdate;    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFullstory(String fullstory){this.fullstory = fullstory;}

    public String getFullstory(){return fullstory;}

    public void setHtml(String html){this.html=html;}

    public String getHtml(){return html;}

    public void setPriority(int priority){this.priority=priority;}

    public int getPriority(){return priority;}
}
