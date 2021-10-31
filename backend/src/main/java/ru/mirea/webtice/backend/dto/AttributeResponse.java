package ru.mirea.webtice.backend.dto;

public class AttributeResponse {
    private String attributeName;
    private String description;

    public AttributeResponse(String attributeName, String description) {
        this.attributeName = attributeName;
        this.description = description;
    }

    public void setAttributeName(String attributeName){
        this.attributeName = attributeName;
    }

    public String getAttributeName(){
        return this.attributeName;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getDescription(){
        return this.description;
    }
}
