package ru.mirea.webtice.backend.dto;

import java.util.List;

public class TagResponse {
    private String tagName;
    private String description;
    private Boolean isUsed;
    private List<AttributeResponse> attributes;

    public TagResponse(String tagName, String description, Boolean isUsed) {
        this.tagName = tagName;
        this.description = description;
        this.isUsed = isUsed;
    }

    public void setTagName(String tagName){
        this.tagName = tagName;
    }

    public String getTagName(){
        return this.tagName;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getDescription(){
        return this.description;
    }

    public void setIsUsed(Boolean isUsed){
        this.isUsed = isUsed;
    }

    public Boolean getIsUsed(){
        return isUsed;
    }
}
