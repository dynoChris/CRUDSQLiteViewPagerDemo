package com.example.vadym.thetrainingproject.utils;

import android.content.Context;

public interface ToFragmentsListener {

    public void addItem(int whichTable, String text);

    public void updateItem(int whichTable, int position, String text);

    public void deleteItem(int whichTable, int position);
}
