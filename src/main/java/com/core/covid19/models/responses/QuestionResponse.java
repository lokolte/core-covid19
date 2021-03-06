package com.core.covid19.models.responses;

import com.core.covid19.models.entities.Item;

public class QuestionResponse {

    private String title;
    private String subtitle;
    private String type;

    public QuestionResponse() {
    }

    public QuestionResponse(Item i) {
        this.title = i.getTitle();
        this.subtitle = i.getSubtitle();
        if (i.getType() != null) {
            if (i.getType().equalsIgnoreCase("CHECK"))
                this.type = "Sí / No";
            else if (i.getType().equalsIgnoreCase("INPUT_TEXT"))
                this.type = "Texto";
            else if (i.getType().equalsIgnoreCase("CHOICE"))
                this.type = "Selección multiple";
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
