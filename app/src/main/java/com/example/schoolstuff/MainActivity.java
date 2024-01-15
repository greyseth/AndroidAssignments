package com.example.schoolstuff;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.schoolstuff.adapters.SpinnerAdapter;
import com.example.schoolstuff.models.User;
import com.example.schoolstuff.models.UserManager;

public class MainActivity extends AppCompatActivity {

    EditText namaInput;
    EditText alamatInput;
    Spinner kotaInput;
    RadioGroup jenisInput;
    Button submitBtn;

    String[] items = {"Pilih Kota:", "City 1", "City 2", "City 3", "City 4", "City 5"};

    String pickedKota;
    boolean justBooted = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        namaInput = findViewById(R.id.namaInput);
        alamatInput = findViewById(R.id.alamatInput);
        kotaInput = findViewById(R.id.kotaInput);
        jenisInput = findViewById(R.id.jenisInput);
        submitBtn = findViewById(R.id.submitBtn);

        //Spinner initialization
        SpinnerAdapter adapter = new SpinnerAdapter(this, R.layout.list_spinner_item, items);
        kotaInput.setAdapter(adapter);
        kotaInput.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!justBooted) {
                    if (position != 0) pickedKota = items[position];
                    else pickedKota = null;
                }
                else justBooted = false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Button input
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = namaInput.getText().toString();
                String alamat = alamatInput.getText().toString();
                RadioButton jenisInputChecked = findViewById(jenisInput.getCheckedRadioButtonId());
                String jenis = jenisInputChecked.getText().toString();

                if (    nama.isEmpty() ||
                        alamat.isEmpty() ||
                        jenis.isEmpty() ||
                        pickedKota == null) {
                    Toast.makeText(MainActivity.this, "Semua data harus terisi!", Toast.LENGTH_SHORT).show();
                    return;
                }

                UserManager.setSavedUser(new User(nama, alamat, pickedKota, jenis));
                Toast.makeText(MainActivity.this, "User telah tersimpan", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(MainActivity.this, UserView.class));
            }
        });
    }
}