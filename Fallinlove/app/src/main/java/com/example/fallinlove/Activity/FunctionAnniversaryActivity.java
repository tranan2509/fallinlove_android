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

public class FunctionAnniversaryActivity extends AppCompatActivity implements View.OnClickListener {

    Intent intent;
    EditText txtTitle, txtDate, txtDescription;
    ImageView imgAnniversary;
    ImageButton btnAdd, btnBack;
    Button btnSave, btnEdit, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function_anniversary);

        intent = getIntent();

        getView();
        setView();
        setOnClick();

    }

    public void getView(){
        txtTitle = findViewById(R.id.txtName);
        txtDate = findViewById(R.id.txtDate);
        txtDescription = findViewById(R.id.txtDescription);
        imgAnniversary = findViewById(R.id.imgAnniversary);
        btnAdd = findViewById(R.id.btnAdd);
        btnBack = findViewById(R.id.btnBack);
        btnSave = findViewById(R.id.btnSave);
        btnEdit = findViewById(R.id.btnEdit);
        btnDelete = findViewById(R.id.btnDelete);
    }

    public void setView() {
        switch (intent.getStringExtra("function")){
            case "add":
                btnSave.setVisibility(View.VISIBLE);
                btnEdit.setVisibility(View.GONE);
                btnDelete.setVisibility(View.GONE);
                break;
            default:
                btnSave.setVisibility(View.GONE);
                btnEdit.setVisibility(View.VISIBLE);
                btnDelete.setVisibility(View.VISIBLE);
                break;
        }
    }

    public void setOnClick(){
        btnBack.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnEdit.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.btnBack:
                finish();
                break;
        }
    }
}