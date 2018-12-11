package org.mswartz.passman;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    private ListView itemList;
    private ArrayList<Login> items = new ArrayList<Login>();
    DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        itemList = findViewById(R.id.items_list);
        databaseHelper = new DatabaseHelper(this);

        populateListView();

        FloatingActionButton create_loginButton = findViewById(R.id.floatingActionButton);

        create_loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCreateLogin();
            }
        });
    }

    private void populateListView() {
        Log.d("ListActivity", "populateListView: Displaying data in the listview");

        Cursor data = databaseHelper.getData();
        final ArrayList<Login> items = new ArrayList<>();
        while(data.moveToNext()){
            Login tempLogin = new Login();
            tempLogin.setServiceName(data.getString(data.getColumnIndex("service")));
            tempLogin.setUserName(data.getString(data.getColumnIndex("user")));
            tempLogin.setPassWord(data.getString(data.getColumnIndex("pass")));
            items.add(tempLogin);
        }

        final LoginAdapter adapter = new LoginAdapter(this, items);
        ListView itemList = (ListView) findViewById(R.id.items_list);
        itemList.setAdapter(adapter);

        itemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

                String passwordToPaste = adapter.getItem(position).getPassWord();

                ClipData clip = ClipData.newPlainText("password", passwordToPaste);
                clipboard.setPrimaryClip(clip);
            }
        });

        itemList.setLongClickable(true);
        itemList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View v,
                                           int position, long id) {
                items.remove(position);
//                adapter.refreshEvents(items);
                Toast.makeText(ListActivity.this, "Deleted!", Toast.LENGTH_SHORT).show();

                return true;
            }
        });

    }

    public void startCreateLogin(){
        Intent intent = new Intent(this, CreateLoginActivity.class);
        startActivity(intent);
    }
}
