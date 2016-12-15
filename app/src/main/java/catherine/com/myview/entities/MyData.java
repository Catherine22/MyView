package catherine.com.myview.entities;

/**
 * Created by Catherine on 2016/12/15.
 * Soft-World Inc.
 * catherine919@soft-world.com.tw
 */

public class MyData {
    private String title;
    private String picUrl;
    private String description;

    @Override
    public String toString() {
        return "MyData{" +
                "description='" + description + '\'' +
                ", title='" + title + '\'' +
                ", picUrl='" + picUrl + '\'' +
                '}';
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
