package com.example.schoolstuff.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.example.schoolstuff.R;

import java.util.LinkedList;
import java.util.List;

public class SpinnerAdapter extends ArrayAdapter {
    List<String> items = new LinkedList<String>();
    Context context;

    public SpinnerAdapter(@NonNull Context context, int resource, @NonNull String[] objects) {
        super(context, resource, objects);
        this.context = context;
        for (String obj :
                objects) {
            this.items.add(obj);
        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getSpinnerView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getSpinnerView(position, convertView, parent);
    }

    public View getSpinnerView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(getContext()).inflate(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, parent, false);
        return convertView;
    }
}
