package org.mswartz.passman;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loginButton = findViewById(R.id.login);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logIn();
            }
        });
    }

    public void logIn() {
        EditText userName = (EditText) findViewById(R.id.username);
        String user = userName.getText().toString();

        EditText password = (EditText) findViewById(R.id.password);
        String pass = password.getText().toString();

        if(pass.equals("password")){
            Intent intent = new Intent(this, ListActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Sorry, that's incorrect", Toast.LENGTH_SHORT).show();
        }
    }
}