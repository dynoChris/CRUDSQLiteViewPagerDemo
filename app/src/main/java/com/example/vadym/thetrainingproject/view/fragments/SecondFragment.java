package com.example.vadym.thetrainingproject.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vadym.thetrainingproject.R;
import com.example.vadym.thetrainingproject.database.DatabaseHelper;
import com.example.vadym.thetrainingproject.database.model.SecondItem;
import com.example.vadym.thetrainingproject.utils.MyDividerItemDecoration;
import com.example.vadym.thetrainingproject.utils.ToFragmentsListener;
import com.example.vadym.thetrainingproject.view.AdapterRecyclerSecondItem;

import java.util.ArrayList;
import java.util.List;

public class SecondFragment extends Fragment implements ToFragmentsListener {
    TextView textViewEmptyNotes;
    RecyclerView rv;
    AdapterRecyclerSecondItem adapter;

    DatabaseHelper db;
    public List<SecondItem> secondItems = new ArrayList<>();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        adapter = new AdapterRecyclerSecondItem(context, secondItems);
        db = new DatabaseHelper(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_second, container, false);

        secondItems.clear();
        secondItems.addAll((List<SecondItem>) db.getAllItems(1));

        textViewEmptyNotes = (TextView) v.findViewById(R.id.text_view_empty_notes);

        rv = (RecyclerView) v.findViewById(R.id.recycler_view);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.addItemDecoration(new MyDividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL, 16));
        rv.setAdapter(adapter);

        toggleVisibilityView(1);

        return v;
    }


    @Override
    public void addItem(int whichTable, String text) {
        long id = db.insertItem(whichTable, text);

        SecondItem secondItem = (SecondItem) db.getItem(id, whichTable);
        secondItems.add(0, secondItem);

        adapter.notifyDataSetChanged();

        toggleVisibilityView(1);
    }

    @Override
    public void updateItem(int whichTable, int position, String text) {
        long id = secondItems.get(position).getId();
        db.updateItem(whichTable, id, text);

        secondItems.get(position).setText(text);
        adapter.notifyItemChanged(position);

        toggleVisibilityView(whichTable);
    }

    @Override
    public void deleteItem(int whichTable, int position) {
        long id = secondItems.get(position).getId();
        db.deleteItem(whichTable, id);

        secondItems.remove(position);
        adapter.notifyItemRemoved(position);

        toggleVisibilityView(whichTable);
    }

    private void toggleVisibilityView(int whichTable) {
        if (db.getItemsCount(whichTable) > 0) {
            textViewEmptyNotes.setVisibility(View.GONE);
        } else {
            textViewEmptyNotes.setVisibility(View.VISIBLE);
        }
    }

}