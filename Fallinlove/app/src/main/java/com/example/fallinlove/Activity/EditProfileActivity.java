package com.example.fallinlove.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fallinlove.R;

public class EditProfileActivity extends AppCompatActivity  implements View.OnClickListener{

    Intent intent, intentNext;
    EditText txtName, txtDob;
    ImageView imgAvatar;
    ImageButton btnAdd, btnBack;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        intent = getIntent();

        getView();
        setView();
        setOnClick();
    }

    public void getView(){
        txtName = findViewById(R.id.txtName);
        txtDob = findViewById(R.id.txtDob);
        btnAdd = findViewById(R.id.btnAdd);
        btnBack = findViewById(R.id.btnBack);
        btnSave = findViewById(R.id.btnSave);
    }

    public void setView() {
        switch (intent.getStringExtra("gender")){
            case "male":

                break;
            default:
                break;
        }
    }

    public void setOnClick(){
        btnBack.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        btnSave.setOnClickListener(this);
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.btnBack:
                finish();
                break;
            case R.id.btnSave:
                save();
                intentNext = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intentNext);
                break;
        }
    }

    public void save(){

    }
}