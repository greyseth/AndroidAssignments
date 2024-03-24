package com.example.schoolstuff.holders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolstuff.R;

public class DataListHolder extends RecyclerView.ViewHolder {

    TextView idView, nikView;

    public DataListHolder(@NonNull View itemView) {
        super(itemView);

        idView = itemView.findViewById(R.id.idView);
        nikView = itemView.findViewById(R.id.nikView);
    }
}
