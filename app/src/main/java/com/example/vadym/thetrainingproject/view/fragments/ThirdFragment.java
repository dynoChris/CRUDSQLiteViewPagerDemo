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
import com.example.vadym.thetrainingproject.database.model.ThirdItem;
import com.example.vadym.thetrainingproject.utils.MyDividerItemDecoration;
import com.example.vadym.thetrainingproject.utils.ToFragmentsListener;
import com.example.vadym.thetrainingproject.view.AdapterRecyclerThirdItem;

import java.util.ArrayList;
import java.util.List;

public class ThirdFragment extends Fragment implements ToFragmentsListener {
    TextView textViewEmptyItems;
    RecyclerView rv;
    AdapterRecyclerThirdItem adapter;

    DatabaseHelper db;
    public List<ThirdItem> thirdItems = new ArrayList<>();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        adapter = new AdapterRecyclerThirdItem(context, thirdItems);
        db = new DatabaseHelper(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_third, container, false);


        thirdItems.clear();
        thirdItems.addAll((List<ThirdItem>) db.getAllItems(2));

        textViewEmptyItems = (TextView) v.findViewById(R.id.text_view_empty_items);

        rv = (RecyclerView) v.findViewById(R.id.recycler_view);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.addItemDecoration(new MyDividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL, 16));
        rv.setAdapter(adapter);

        toggleVisibilityView(2);

        return v;
    }

    @Override
    public void addItem(int whichTable, String text) {
        long id = db.insertItem(whichTable, text);

        ThirdItem thirdItem = (ThirdItem) db.getItem(id, whichTable);
        thirdItems.add(0, thirdItem);

        adapter.notifyDataSetChanged();

        toggleVisibilityView(2);
    }

    @Override
    public void updateItem(int whichTable, int position, String text) {
        long id = thirdItems.get(position).getId();
        db.updateItem(whichTable, id, text);

        thirdItems.get(position).setText(text);
        adapter.notifyItemChanged(position);

        toggleVisibilityView(whichTable);
    }

    @Override
    public void deleteItem(int whichTable, int position) {
        long id = thirdItems.get(position).getId();
        db.deleteItem(whichTable, id);

        thirdItems.remove(position);
        adapter.notifyItemRemoved(position);

        toggleVisibilityView(whichTable);
    }

    private void toggleVisibilityView(int whichTable) {
        if (thirdItems.size() > 0) {
            textViewEmptyItems.setVisibility(View.GONE);
        } else {
            textViewEmptyItems.setVisibility(View.VISIBLE);
        }
    }
}