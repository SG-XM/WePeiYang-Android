package com.twt.service.ui.lostfound.post.event;

/**
 * Created by sunjuntao on 16/3/12.
 */
public class PostFoundContactInfoEvent {
    private String name;
    private String number;

    public PostFoundContactInfoEvent(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }
}
