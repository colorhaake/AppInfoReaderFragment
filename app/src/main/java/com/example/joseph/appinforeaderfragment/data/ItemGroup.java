package com.example.joseph.appinforeaderfragment.data;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by joseph on 15/9/23.
 */
public class ItemGroup extends Item {
    private List<Item> children = new ArrayList<Item>();
    public ItemGroup() {
        super();
        this.isGroup = true;
    }

    public ItemGroup(String label) {
        super(label);
        this.isGroup = true;
    }
    public void addItem(Item item) {
        children.add(item);
    }

    public ListIterator<Item> iterateItems() {
        return  children.listIterator();
    }

}
