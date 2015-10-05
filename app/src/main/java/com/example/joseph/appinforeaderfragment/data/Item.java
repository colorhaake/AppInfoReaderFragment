package com.example.joseph.appinforeaderfragment.data;

import android.graphics.drawable.Drawable;

/**
 * Created by joseph on 15/9/23.
 */
public class Item {
    protected boolean isGroup = false;
    public boolean isExpanded = false;
    public Drawable icon;
    public String label;

    public Item() {
    }

    public Item(String label) {
        this.label = label;
    }

    public boolean isGroup() {
        return this.isGroup;
    }
}
