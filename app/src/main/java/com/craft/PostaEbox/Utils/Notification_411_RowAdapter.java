package com.craft.PostaEbox.Utils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.craft.PostaEbox.R;

import org.w3c.dom.Text;

/**
 * Created by Oliver on 2/18/2016.
 */
public class Notification_411_RowAdapter extends ArrayAdapter{
    Activity context;
    String [] Titles;
    String [] Bodies;
    String [] Froms;
    String [] Durations;
    public Notification_411_RowAdapter(Activity context,String [] Titles,String [] Bodies, String [] Froms, String [] Durations) {
        super(context, R.layout.notification_411_row,Titles);
        this.context=context;
        this.Titles=Titles;
        this.Bodies=Bodies;
        this.Froms=Froms;
        this.Durations=Durations;
    }
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.notification_411_row, null, true);
        TextView title=(TextView) rowView.findViewById(R.id.Notification_Title);
        TextView body=(TextView) rowView.findViewById(R.id.Notification_Body);
        TextView from=(TextView) rowView.findViewById(R.id.Notification_From);
        TextView duration=(TextView) rowView.findViewById(R.id.Notification_Duration);
        title.setText(Titles[position]);
        body.setText(Bodies[position]);
        from.setText(Froms[position]);
        duration.setText(Durations[position]);
        return rowView;
    }
}
