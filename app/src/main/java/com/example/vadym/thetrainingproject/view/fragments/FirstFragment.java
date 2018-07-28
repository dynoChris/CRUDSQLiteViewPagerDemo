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
import com.example.vadym.thetrainingproject.database.model.FirstItem;
import com.example.vadym.thetrainingproject.utils.MyDividerItemDecoration;
import com.example.vadym.thetrainingproject.utils.ToFragmentsListener;
import com.example.vadym.thetrainingproject.view.AdapterRecyclerFirstItem;

import java.util.ArrayList;
import java.util.List;

public class FirstFragment extends Fragment implements ToFragmentsListener {
    private TextView textViewEmptyNotes;
    private RecyclerView rv;
    private AdapterRecyclerFirstItem adapter;

    private DatabaseHelper db;

    public List<FirstItem> firstItems = new ArrayList<>();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        adapter = new AdapterRecyclerFirstItem(context, firstItems);
        db = new DatabaseHelper(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_first, container, false);

        firstItems.clear();

        firstItems.addAll((List<FirstItem>) db.getAllItems(0));
        textViewEmptyNotes = (TextView) v.findViewById(R.id.text_view_empty_notes);

        rv = (RecyclerView) v.findViewById(R.id.recycler_view);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.addItemDecoration(new MyDividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL, 16));
        rv.setAdapter(adapter);

        toggleVisibilityView(0);

        return v;
    }

    @Override
    public void addItem(int whichTable, String text) {
        long id = db.insertItem(whichTable, text);

        FirstItem firstItem = (FirstItem) db.getItem(id, whichTable);
        firstItems.add(0, firstItem);

        adapter.notifyDataSetChanged();

        toggleVisibilityView(0);
    }

    @Override
    public void updateItem(int whichTable, int position, String text) {
        long id = firstItems.get(position).getId();
        db.updateItem(whichTable, id, text);

        firstItems.get(position).setText(text);
        adapter.notifyItemChanged(position);

        toggleVisibilityView(whichTable);
    }

    @Override
    public void deleteItem(int whichTable, int position) {
        long id = firstItems.get(position).getId();
        db.deleteItem(whichTable, id);

        firstItems.remove(position);
        adapter.notifyItemRemoved(position);

        toggleVisibilityView(whichTable);
    }

    private void toggleVisibilityView(int whichTable) {
        if (firstItems.size() > 0) {
            textViewEmptyNotes.setVisibility(View.GONE);
        } else {
            textViewEmptyNotes.setVisibility(View.VISIBLE);
        }
    }
}