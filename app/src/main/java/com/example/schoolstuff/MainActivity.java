package com.example.schoolstuff;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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

    EditText nikInput;
    EditText alamatInput;
    EditText kotaInput;
    Spinner kelaminInput;
    Button submitBtn;

    ProgressBar loading;
    LinearLayout errorContainer;
    Button errorConfirmBtn;

    String[] kelamins = {"Laki-Laki", "Perempuan", "Lainnya"};
    String kelamin = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nikInput = findViewById(R.id.nikInput);
        alamatInput = findViewById(R.id.alamatInput);
        kotaInput = findViewById(R.id.kotaInput);
        kelaminInput = findViewById(R.id.kelaminInput);
        submitBtn = findViewById(R.id.submitBtn);

        loading = findViewById(R.id.loading);
        errorContainer = findViewById(R.id.errorContainer);
        errorConfirmBtn = findViewById(R.id.errorConfirmBtn);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, kelamins);
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        kelaminInput.setAdapter(adapter);
        kelaminInput.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                kelamin = kelamins[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
    }

    public void addData() {
        String nik = nikInput.getText().toString();
        String alamat = alamatInput.getText().toString();
        String kota = kotaInput.getText().toString();

        if (nik.isEmpty() || alamat.isEmpty() || kota.isEmpty() || kelamin == null) {
            Toast.makeText(this, "Semua field harus terisi!", Toast.LENGTH_SHORT).show();
            return;
        }

        loading.setVisibility(View.VISIBLE);
        submitBtn.setVisibility(View.GONE);

        JSONObject params = new JSONObject();
        try {
            params.put("nik", nik);
            params.put("alamat", alamat);
            params.put("kota", kota);
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