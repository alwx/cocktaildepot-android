package com.cocktaildepot.utilities.preferences;

public class EntryItem implements Item{
    public final String title;
    public final String subtitle;
    public final int drawable;

    public EntryItem(String title, String subtitle, int drawable) {
        this.title = title;
        this.subtitle = subtitle;
        this.drawable = drawable;
    }

    @Override
    public boolean isSection() {
        return false;
    }
}