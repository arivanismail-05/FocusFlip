package com.example.focusflip.view;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.focusflip.R;

public class CustomAdapter extends BaseAdapter {

    String[] subjects;
    String[] durations;
    String[] dates;
    Activity Context;

    public CustomAdapter(String[] subjects, String[] durations, String[] dates, Activity Context) {
        this.subjects = subjects;
        this.durations = durations;
        this.dates = dates;
        this.Context = Context;
    }

    @Override
    public int getCount() { return subjects.length; }

    @Override
    public Object getItem(int i) { return subjects[i]; }

    @Override
    public long getItemId(int i) { return i; }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater Inflater = Context.getLayoutInflater();
            view = Inflater.inflate(R.layout.single_item, viewGroup, false);
        }
        TextView item_subject = view.findViewById(R.id.item_subject);
        TextView item_duration = view.findViewById(R.id.item_duration);
        TextView item_date = view.findViewById(R.id.item_date);

        item_subject.setText(subjects[i]);
        item_duration.setText(durations[i]);
        item_date.setText(dates[i]);
        return view;
    }
}

