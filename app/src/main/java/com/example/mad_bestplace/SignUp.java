package com.example.mad_bestplace;

import android.content.Intent;
import android.os.Bundle;

import com.example.mad_bestplace.Database.DBHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class SignUp extends AppCompatActivity
        implements AdapterView.OnItemSelectedListener{

    DBHelper dbHelper;
    EditText edit_username,edit_name,edit_email,edit_phone,edit_address,edit_password;
    Button signup;
    String TEXT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        dbHelper = new DBHelper(this);

        edit_username = findViewById(R.id.EDIT_SIGNUP_USERNAME);
        edit_name = findViewById(R.id.EDIT_SIGNUP_NAME);
        edit_email = findViewById(R.id.EDIT_SIGNUP_EMAIL);
        edit_phone = findViewById(R.id.EDIT_SIGNUP_PHONE);
        edit_address = findViewById(R.id.EDIT_SIGNUP_ADDRESS);
        edit_password = findViewById(R.id.SIGNUP_PASSWORD);


        signup = findViewById(R.id.SIGNUP_BTN);

        Register();

        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.User_Levels, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
         TEXT = adapterView.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }




    public void Register() {
        signup.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean isInserted = dbHelper.Register_user(edit_username.getText().toString(),
                                edit_name.getText().toString(),
                                edit_email.getText().toString(),
                                edit_phone.getText().toString(),
                                edit_address.getText().toString(),
                                TEXT,
                                edit_password.getText().toString());
                        if (isInserted == true) {
                            Toast.makeText(SignUp.this, "Successfully Registered", Toast.LENGTH_LONG).show();
                            Intent intent1 = new Intent(SignUp.this,MainActivity.class);
                            startActivity(intent1);
                        } else {
                            Toast.makeText(SignUp.this, "Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }
        );
    }


}
