package com.example.schoolstuff;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.schoolstuff.interfaces.ApiResponse;
import com.example.schoolstuff.models.API;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Optional;
import java.lang.String;

public class DataEdit extends AppCompatActivity {

    Button backBtn;
    Button viewBtn;
    Button updateBtn;

    ProgressBar loading;
    LinearLayout dataDisplay;
    LinearLayout updateContainer;

    TextView idDisplay;
    EditText nikInput;
    EditText alamatInput;
    EditText umurInput;
    Spinner kotaInput;
    RadioGroup kelaminInput;

    String[] cities = {"City 1", "City 2", "City 3", "City 4"};
    String kota = "";
    String kelamin = "";

    int dataId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_edit);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            if (bundle.getString("data_id") != null) {
                dataId = Integer.parseInt(bundle.getString("data_id"));
            }else startActivity(new Intent(DataEdit.this, AllData.class));
        }else startActivity(new Intent(DataEdit.this, AllData.class));

        backBtn = findViewById(R.id.backBtn);
        viewBtn = findViewById(R.id.viewBtn);
        updateBtn = findViewById(R.id.updateBtn);

        loading = findViewById(R.id.loading);
        dataDisplay = findViewById(R.id.dataDisplay);
        updateContainer = findViewById(R.id.updateContainer);

        idDisplay = findViewById(R.id.idDisplay);
        nikInput = findViewById(R.id.nikInput);
        alamatInput = findViewById(R.id.alamatInput);
        umurInput = findViewById(R.id.umurInput);
        kotaInput = findViewById(R.id.kotaInput);
        kelaminInput = findViewById(R.id.kelaminInput);

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
                RadioButton selected = (RadioButton) group.findViewById(checkedId);
                kelamin = selected.getText().toString();
            }
        });

        API.get(DataEdit.this, "/id/" + dataId, new ApiResponse() {
            @Override
            public void onResponse(Optional<JSONObject> response) throws JSONException {
                if (response.isPresent()) {
                    idDisplay.setText("Mengedit Akun "+dataId);
                    JSONArray targetArray = response.get().getJSONArray("data");
                    JSONObject target = targetArray.getJSONObject(0);
                    nikInput.setText(target.getString("nik"));
                    alamatInput.setText(target.getString("alamat"));
                    umurInput.setText(target.getString("umur"));

                    kotaInput.setSelection(adapter.getPosition(target.getString("kota")));
                    String gender = target.getString("kelamin");
                    switch (gender) {
                        case "Laki-Laki":
                            kelaminInput.check(R.id.kelaminMale);
                            break;
                        case "Perempuan":
                            kelaminInput.check(R.id.kelaminFemale);
                            break;
                        case "Lainnya":
                            kelaminInput.check(R.id.kelaminOther);
                            break;
                    }

                    loading.setVisibility(View.GONE);
                    dataDisplay.setVisibility(View.VISIBLE);
                    updateContainer.setVisibility(View.VISIBLE);
                }else {
                    Toast.makeText(DataEdit.this, "Sebuah kesalahan telah terjadi....", Toast.LENGTH_SHORT).show();
                }
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DataEdit.this, AllData.class));
            }
        });

        viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DataEdit.this, DataView.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("data_id", String.valueOf(dataId));
                startActivity(i);
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nik = nikInput.getText().toString();
                String alamat = alamatInput.getText().toString();
                String umur = umurInput.getText().toString();

                if (nik.isEmpty() || alamat.isEmpty() || umur.isEmpty() || kota.isEmpty() || kelamin.isEmpty()) {
                    Toast.makeText(DataEdit.this, "Semua data harus terisi!", Toast.LENGTH_SHORT).show();
                    return;
                }

                loading.setVisibility(View.VISIBLE);
                dataDisplay.setVisibility(View.GONE);
                updateContainer.setVisibility(View.GONE);

                int realUmur = Integer.parseInt(umur);

                JSONObject params = new JSONObject();
                try {
                    params.put("nik", nik);
                    params.put("alamat", alamat);
                    params.put("kota", kota);
                    params.put("kelamin", kelamin);
                    params.put("umur", realUmur);
                }catch(JSONException e) {e.printStackTrace();}
                API.post(DataEdit.this, "/update/" + dataId, params, new ApiResponse() {
                    @Override
                    public void onResponse(Optional<JSONObject> response) throws JSONException {
                        loading.setVisibility(View.GONE);
                        dataDisplay.setVisibility(View.VISIBLE);
                        updateContainer.setVisibility(View.VISIBLE);

                        if (response.get().has("success")) {
                            Toast.makeText(DataEdit.this, "Data berhasil terupdate", Toast.LENGTH_SHORT).show();
                        }else
                        {
                            Toast.makeText(DataEdit.this, "Sebuah kesalahan terjadi saat update...", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}