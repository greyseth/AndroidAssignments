package com.example.schoolstuff;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.schoolstuff.holders.DataListAdapter;
import com.example.schoolstuff.interfaces.ApiResponse;
import com.example.schoolstuff.models.API;
import com.example.schoolstuff.objects.DataDiri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class AllData extends AppCompatActivity {

    Button backBtn;
    ProgressBar loading;
    RecyclerView dataView;

    List<DataDiri> dataDiris = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_data);

        backBtn = findViewById(R.id.backBtn);
        loading = findViewById(R.id.loading);
        dataView = findViewById(R.id.dataView);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AllData.this, MainActivity.class));
            }
        });

        API.get(this, "", new ApiResponse() {
            @Override
            public void onResponse(Optional<JSONObject> response) throws JSONException {
                if (response.get().has("data")) {
                    JSONArray dataArray = response.get().getJSONArray("data");
                    for(int i = 0; i < dataArray.length(); i++) {
                        JSONObject data = dataArray.getJSONObject(i);

                        DataDiri newDD = new DataDiri(data.getInt("id"), data.getString("nik"), data.getString("alamat"), data.getString("kota"), data.getString("kelamin"));
                        dataDiris.add(newDD);
                    }

                    dataView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    dataView.setAdapter(new DataListAdapter(AllData.this, dataDiris));

                    loading.setVisibility(View.GONE);
                    dataView.setVisibility(View.VISIBLE);
                }else
                    Toast.makeText(AllData.this, "Sebuah kesalahan telah terjadi!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}