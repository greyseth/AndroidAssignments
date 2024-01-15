package com.example.schoolstuff;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.schoolstuff.models.User;
import com.example.schoolstuff.models.UserManager;

public class UserView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view);

        if (!UserManager.savedUser.isPresent()) {
            startActivity(new Intent(UserView.this, MainActivity.class));
            return;
        }

        User user = UserManager.savedUser.get();

        TextView namaDisplay = findViewById(R.id.namaDisplay);
        TextView alamatDisplay = findViewById(R.id.alamatDisplay);
        TextView kotaDisplay = findViewById(R.id.kotaDisplay);
        TextView jenisDisplay = findViewById(R.id.jenisDisplay);

        namaDisplay.setText(user.getNama());
        alamatDisplay.setText(user.getAlamat());
        kotaDisplay.setText(user.getKota());
        jenisDisplay.setText(user.getJenis());

        Button kembaliBtn = findViewById(R.id.kembaliBtn);
        kembaliBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserView.this, MainActivity.class));
            }
        });
    }
}