package org.mswartz.passman;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class CreateLoginActivity extends AppCompatActivity {

    public static final String EXTRA_LOGIN_SERVICE = "org.mswartz.application.passman.EXTRA_LOGIN_SERVICE";
    public static final String EXTRA_LOGIN_USER = "org.mswartz.application.passman.EXTRA_LOGIN_USER";
    public static final String EXTRA_LOGIN_PASS = "org.mswartz.application.passman.EXTRA_LOGIN_PASS";
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_login);

        Button createButton = findViewById(R.id.createLoginButton);

        databaseHelper = new DatabaseHelper(this);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText login_service = (EditText) findViewById(R.id.login_service);
                String login_s = login_service.getText().toString();

                EditText login_pass = (EditText) findViewById(R.id.login_password);
                String login_p = login_pass.getText().toString();

                EditText login_user = (EditText) findViewById(R.id.login_username);
                String login_u = login_user.getText().toString();

                if(login_s.length() !=0 || login_u.length()!=0) {
                    Login newLogin = new Login();
                    newLogin.setServiceName(login_s);
                    newLogin.setUserName(login_u);

                    if(login_p.length()==0) {
                        newLogin.setPassWord(""+Login.generatePassword(8));
                    } else {
                        newLogin.setPassWord(login_p);
                    }
                    createLogin(newLogin);
                }
            }
        });
    }

    public void createLogin(Login newLogin) {
        Intent intent = new Intent(this, ListActivity.class);
        boolean insertData = databaseHelper.addData(newLogin);

        if(insertData) {
            Toast.makeText(this, "Login stored.", 1).show();
        } else {
            Toast.makeText(this, "Oops, something went wrong.", 1).show();
        }

        startActivity(intent);
    }
}
