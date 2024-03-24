package com.example.schoolstuff;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schoolstuff.interfaces.ApiResponse;
import com.example.schoolstuff.models.API;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Optional;

public class DataView extends AppCompatActivity {

    Button backBtn;
    ImageButton editBtn;
    ImageButton deleteBtn;
    Button deleteCancelBtn;
    Button deleteConfirmBtn;

    ProgressBar loading;
    LinearLayout dataDisplay;
    LinearLayout controlsContainer;
    LinearLayout deleteConfirmContainer;

    TextView idDisplay;
    TextView nikDisplay;
    TextView alamatDisplay;
    TextView kotaDisplay;
    TextView umurDisplay;
    TextView kelaminDisplay;

    int dataId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_view);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            if (bundle.getString("data_id") != null) {
                dataId = Integer.parseInt(bundle.getString("data_id"));
            }else startActivity(new Intent(DataView.this, AllData.class));
        }else startActivity(new Intent(DataView.this, AllData.class));

        backBtn = findViewById(R.id.backBtn);
        editBtn = findViewById(R.id.editBtn);
        deleteBtn = findViewById(R.id.deleteBtn);
        deleteCancelBtn = findViewById(R.id.deleteCancelBtn);
        deleteConfirmBtn = findViewById(R.id.deleteConfirmBtn);

        loading = findViewById(R.id.loading);
        dataDisplay = findViewById(R.id.dataDisplay);
        controlsContainer = findViewById(R.id.controlsContainer);
        deleteConfirmContainer = findViewById(R.id.deleteConfirmContainer);

        idDisplay = findViewById(R.id.idDisplay);
        nikDisplay = findViewById(R.id.nikDisplay);
        alamatDisplay = findViewById(R.id.alamatDisplay);
        kotaDisplay = findViewById(R.id.kotaDisplay);
        umurDisplay = findViewById(R.id.umurDisplay);
        kelaminDisplay = findViewById(R.id.kelaminDisplay);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DataView.this, AllData.class));
            }
        });
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DataView.this, DataEdit.class);
                i.putExtra("data_id", String.valueOf(dataId));
                startActivity(i);
            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteConfirmContainer.setVisibility(View.VISIBLE);
            }
        });
        deleteCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteConfirmContainer.setVisibility(View.GONE);
            }
        });
        deleteConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteConfirmContainer.setVisibility(View.GONE);
                dataDisplay.setVisibility(View.GONE);
                loading.setVisibility(View.VISIBLE);

                API.get(DataView.this, "/delete/" + dataId, new ApiResponse() {
                    @Override
                    public void onResponse(Optional<JSONObject> response) throws JSONException {
                        if (response.isPresent()) {
                            if (response.get().has("success")) {
                                Toast.makeText(DataView.this, "Data berhasil terhapus", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(DataView.this, AllData.class));
                            }else {
                                Toast.makeText(DataView.this, "Sebuah kesalahan terjadi saat menghapus...", Toast.LENGTH_SHORT).show();
                                deleteConfirmContainer.setVisibility(View.VISIBLE);
                                dataDisplay.setVisibility(View.VISIBLE);
                                loading.setVisibility(View.GONE);
                            }
                        }else {
                            Toast.makeText(DataView.this, "Sebuah kesalahan terjadi saat menghapus...", Toast.LENGTH_SHORT).show();
                            deleteConfirmContainer.setVisibility(View.VISIBLE);
                            dataDisplay.setVisibility(View.VISIBLE);
                            loading.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        //Loads Data
        API.get(this, "/id/"+dataId, new ApiResponse() {
            @Override
            public void onResponse(Optional<JSONObject> response) throws JSONException {
                if (response.get().has("data")) {
                    JSONObject data = response.get().getJSONArray("data").getJSONObject(0);

                    idDisplay.setText("Id: "+ data.getInt("id"));
                    nikDisplay.setText("NIK: "+data.getString("nik"));
                    alamatDisplay.setText("Alamat: "+data.getString("alamat"));
                    umurDisplay.setText("Umur: "+data.getInt("umur"));
                    kotaDisplay.setText("Kota: "+data.getString("kota"));

                    kelaminDisplay.setText("Jenis Kelamin: "+data.getString("kelamin"));

                    loading.setVisibility(View.GONE);
                    dataDisplay.setVisibility(View.VISIBLE);
                    controlsContainer.setVisibility(View.VISIBLE);
                }else {
                    Toast.makeText(DataView.this, "Sebuah kesalahan telah terjadi!", Toast.LENGTH_SHORT).show();
                    if (response.get().has("error")) System.out.println(response.get().getJSONObject("error"));
                }
            }
        });
    }
}