package org.mswartz.passman;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class LoginAdapter extends ArrayAdapter<Login> {
    public final Context context;
    public final ArrayList<Login> logins;

    public LoginAdapter(Context context, ArrayList<Login> logins) {
        super(context, 0, logins);
        this.context = context;
        this.logins = logins;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Login login = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_login, parent, false);
        }
        // Lookup view for data population
        TextView item_Username = (TextView) convertView.findViewById(R.id.item_Username);
        TextView item_Service = (TextView) convertView.findViewById(R.id.item_Service);
        TextView item_Password = (TextView) convertView.findViewById(R.id.item_Password);

        // Populate the data into the template view using the data object
        item_Username.setText(login.getUserName());
        item_Service.setText(login.getServiceName());
        item_Password.setText(login.getPassWord());
        // Return the completed view to render on screen
        return convertView;
    }

    public void refreshEvents(ArrayList<Login> logins) {
        this.logins.clear();
        this.logins.addAll(logins);
        notifyDataSetChanged();
    }
}