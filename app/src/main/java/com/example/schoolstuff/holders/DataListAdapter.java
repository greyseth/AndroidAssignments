package com.example.schoolstuff.holders;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolstuff.DataView;
import com.example.schoolstuff.R;
import com.example.schoolstuff.objects.DataDiri;

import java.util.LinkedList;
import java.util.List;

public class DataListAdapter extends RecyclerView.Adapter<DataListHolder> {

    Context ctx;
    List<DataDiri> dataDiris = new LinkedList<>();

    public DataListAdapter(Context ctx, List<DataDiri> dataDiris) {
        this.ctx = ctx;
        this.dataDiris = dataDiris;
    }

    @NonNull
    @Override
    public DataListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DataListHolder(LayoutInflater.from(ctx).inflate(R.layout.list_data, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DataListHolder holder, int position) {
        DataDiri data = dataDiris.get(position);

        TextView idView = holder.itemView.findViewById(R.id.idView);
        TextView nikView = holder.itemView.findViewById(R.id.nikView);

        idView.setText("Id: "+data.getId());
        nikView.setText(data.getNik());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ctx, DataView.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("data_id", String.valueOf(data.getId()));
                ctx.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataDiris.size();
    }
}
