package com.example.vadym.thetrainingproject.view;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vadym.thetrainingproject.R;
import com.example.vadym.thetrainingproject.database.DatabaseHelper;
import com.example.vadym.thetrainingproject.database.model.FirstItem;
import com.example.vadym.thetrainingproject.database.model.SecondItem;
import com.example.vadym.thetrainingproject.database.model.ThirdItem;
import com.example.vadym.thetrainingproject.utils.OnRecyclerLongClickListener;
import com.example.vadym.thetrainingproject.utils.ToFragmentsListener;
import com.example.vadym.thetrainingproject.view.fragments.FirstFragment;
import com.example.vadym.thetrainingproject.view.fragments.SecondFragment;
import com.example.vadym.thetrainingproject.view.fragments.ThirdFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnRecyclerLongClickListener {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MyPagerAdapter pagerAdapter;

    private DatabaseHelper db;

    private ToFragmentsListener listener;

    private FirstFragment firstFragment = new FirstFragment();
    private SecondFragment secondFragment = new SecondFragment();
    private ThirdFragment thirdFragment = new ThirdFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = new DatabaseHelper(this);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(pagerAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showItemDialog(false, null, -1);
            }
        });
    }

    @Override
    public void onLongClick(int position) {
        showActionDialog(position);
    }

    private void showItemDialog(final boolean needUpdate, String text, final int position) {
        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
        View v = inflater.inflate(R.layout.dialog, null);

        final EditText editTextNote = (EditText) v.findViewById(R.id.edit_text_item_dialog);
        if (needUpdate)
            editTextNote.setText(text);
        editTextNote.setSelection(editTextNote.getText().length()); //set cursor to end
        TextView textViewTitleDialog = (TextView) v.findViewById(R.id.text_view_title_dialog);
        textViewTitleDialog.setText(needUpdate ? R.string.update_item : R.string.new_item);

        AlertDialog.Builder builderDialog = new AlertDialog.Builder(MainActivity.this);
        builderDialog.setView(v)
                .setPositiveButton(needUpdate ? R.string.update : R.string.save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alertDialog = builderDialog.create();
        alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE); //open keyboard when dialog created
        alertDialog.show();

        alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(editTextNote.getText().toString())) {
                    Toast.makeText(MainActivity.this, R.string.enter_text, Toast.LENGTH_SHORT).show();
                } else {
                    if (!needUpdate) {
                        String text = editTextNote.getText().toString();
                        addItem(text);
                    } else {
                        String text = editTextNote.getText().toString();
                        updateItem(text, position);
                    }
                    alertDialog.dismiss();
                }
            }
        });
    }

    private void showActionDialog(final int position) {
        String choice[] = new String[]{getResources().getString(R.string.edit), getResources().getString(R.string.delete)};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.choose_option);
        builder.setItems(choice, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) { //Edit
                    int whichFragment = viewPager.getCurrentItem();
                    String text;
                    switch (whichFragment) {
                        case 0:
                            firstFragment = (FirstFragment) selectFragment(whichFragment);
                            text = firstFragment.firstItems.get(position).getText();
                            showItemDialog(true, text, position);
                            break;
                        case 1:
                            secondFragment = (SecondFragment) selectFragment(whichFragment);
                            text = secondFragment.secondItems.get(position).getText();
                            showItemDialog(true, text, position);
                            break;
                        case 2:
                            thirdFragment = (ThirdFragment) selectFragment(whichFragment);
                            text = thirdFragment.thirdItems.get(position).getText();
                            showItemDialog(true, text, position);
                            break;
                    }
                } else { //delete
                    deleteItem(position);
                }
            }


        });
        builder.show();
    }

    private Fragment selectFragment(int whichFragment) {
        switch (whichFragment) {
            case 0:
                return (FirstFragment) pagerAdapter.getCurrentFragment();
            case 1:
                return (SecondFragment) pagerAdapter.getCurrentFragment();
            case 2:
                return (ThirdFragment) pagerAdapter.getCurrentFragment();
        }
        return null;
    }

    private void addItem(String text) {
        int whichFragment = viewPager.getCurrentItem();

        switch (whichFragment) {
            case 0:
                listener = (FirstFragment) selectFragment(whichFragment);
                listener.addItem(whichFragment, text);
                break;
            case 1:
                listener = (SecondFragment) selectFragment(whichFragment);
                listener.addItem(whichFragment, text);
                break;
            case 2:
                listener = (ThirdFragment) selectFragment(whichFragment);
                listener.addItem(whichFragment, text);
                break;
        }
    }

    private void updateItem(String text, int position) {
        int whichFragment = viewPager.getCurrentItem();
        switch (whichFragment) {
            case 0:
                listener = (FirstFragment) selectFragment(whichFragment);
                listener.updateItem(whichFragment, position, text);
                break;
            case 1:
                listener = (SecondFragment) selectFragment(whichFragment);
                listener.updateItem(whichFragment, position, text);
                break;
            case 2:
                listener = (ThirdFragment) selectFragment(whichFragment);
                listener.updateItem(whichFragment, position, text);
                break;
        }
    }

    private void deleteItem(int position) {
        int whichTable = viewPager.getCurrentItem();
        switch (whichTable) {
            case 0:
                listener = (FirstFragment) selectFragment(whichTable);
                listener.deleteItem(whichTable, position);
                break;
            case 1:
                listener = (SecondFragment) selectFragment(whichTable);
                listener.deleteItem(whichTable, position);
                break;
            case 2:
                listener = (ThirdFragment) selectFragment(whichTable);
                listener.deleteItem(whichTable, position);
                break;
        }
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragmentList = new ArrayList<>();
        private Fragment currentFragment;

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            fragmentList.add(new FirstFragment());
            fragmentList.add(new SecondFragment());
            fragmentList.add(new ThirdFragment());
        }

        public Fragment getCurrentFragment() {
            return this.currentFragment;
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            if (getCurrentFragment() != object) {
                currentFragment = ((Fragment) object);
            }
            super.setPrimaryItem(container, position, object);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getResources().getString(R.string.first_tab);
                case 1:
                    return getResources().getString(R.string.second_tab);
                case 2:
                    return getResources().getString(R.string.third_tab);
            }
            return null;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }
}