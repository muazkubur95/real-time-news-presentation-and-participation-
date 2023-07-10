package jdrsservice;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DataDTO {
    private String title;
    private String description;
    private String date;
    private String link;

    public DataDTO() {
    }

    // Constructor with @JsonCreator and @JsonProperty annotations
    @JsonCreator
    public DataDTO(@JsonProperty("title") String title,
                   @JsonProperty("description") String description,
                   @JsonProperty("date") String date,
                   @JsonProperty("link") String link) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.link = link;
    }

    // Getters and setters for the fields

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
