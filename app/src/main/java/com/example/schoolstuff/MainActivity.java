package com.example.schoolstuff;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.schoolstuff.adapters.SpinnerAdapter;
import com.example.schoolstuff.interfaces.ApiResponse;
import com.example.schoolstuff.models.API;
import com.example.schoolstuff.models.RequestHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Optional;


public class MainActivity extends AppCompatActivity {

    Button allBtn;

    EditText nikInput;
    EditText alamatInput;
    Spinner kotaInput;
    EditText umurInput;
    RadioGroup kelaminInput;
    Button submitBtn;

    ProgressBar loading;
    LinearLayout errorContainer;
    Button errorConfirmBtn;

    String[] cities = {"City 1", "City 2", "City 3", "City 4"};
    String kota = "";
    String kelamin = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        allBtn = findViewById(R.id.allBtn);

        nikInput = findViewById(R.id.nikInput);
        alamatInput = findViewById(R.id.alamatInput);
        kotaInput = findViewById(R.id.kotaInput);
        umurInput = findViewById(R.id.umurInput);
        kelaminInput = findViewById(R.id.kelaminInput);
        submitBtn = findViewById(R.id.submitBtn);

        loading = findViewById(R.id.loading);
        errorContainer = findViewById(R.id.errorContainer);
        errorConfirmBtn = findViewById(R.id.errorConfirmBtn);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, cities);
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        kotaInput.setAdapter(adapter);
        kotaInput.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                kota = cities[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        kelaminInput.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton selectedButton = (RadioButton) group.findViewById(checkedId);
                kelamin = selectedButton.getText().toString();
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData();
            }
        });

        errorConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                errorContainer.setVisibility(View.GONE);
            }
        });

        allBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AllData.class));
            }
        });
    }

    public void addData() {
        String nik = nikInput.getText().toString();
        String alamat = alamatInput.getText().toString();
        String umur = umurInput.getText().toString();

        if (nik.isEmpty() || alamat.isEmpty() || kota.isEmpty() || umur.isEmpty() || kelamin.isEmpty()) {
            Toast.makeText(this, "Semua field harus terisi!", Toast.LENGTH_SHORT).show();
            return;
        }

        int realUmur = Integer.parseInt(umur);

        loading.setVisibility(View.VISIBLE);
        submitBtn.setVisibility(View.GONE);

        JSONObject params = new JSONObject();
        try {
            params.put("nik", nik);
            params.put("alamat", alamat);
            params.put("kota", kota);
            params.put("umur", realUmur);
            params.put("kelamin", kelamin);
        }catch(JSONException e) {e.printStackTrace();}
        API.post(this, "", params, new ApiResponse() {
            @Override
            public void onResponse(Optional<JSONObject> response) throws JSONException {
                loading.setVisibility(View.GONE);
                submitBtn.setVisibility(View.VISIBLE);

                if (!response.get().has("success")) {
                    errorContainer.setVisibility(View.VISIBLE);
                    System.out.println(response);
                }
                else Toast.makeText(MainActivity.this, "Telah ditambahkan", Toast.LENGTH_SHORT).show();
            }
        });
    }
}