package com.example.focusflip.component;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.focusflip.R;

public class DashboardSessionAdapter extends BaseAdapter {

    private final String[] subjects;
    private final String[] durations;
    private final Activity context;

    public DashboardSessionAdapter(String[] subjects, String[] durations, Activity context) {
        this.subjects = subjects;
        this.durations = durations;
        this.context = context;
    }

    @Override
    public int getCount() {
        return subjects.length;
    }

    @Override
    public Object getItem(int position) {
        return subjects[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.dashboard_session_item, parent, false);
        }

        TextView subject = convertView.findViewById(R.id.dashboard_subject);
        TextView duration = convertView.findViewById(R.id.dashboard_duration);

        subject.setText(subjects[position]);
        duration.setText(durations[position]);

        return convertView;
    }
}

