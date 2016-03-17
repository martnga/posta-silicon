package com.craft.PostaEbox.Utils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.craft.PostaEbox.R;

/**
 * Created by Oliver on 2/18/2016.
 */
public class Ebox_Notification_Row extends ArrayAdapter {
    Activity context;
    String Title [];
    String Body [];
    String Duration[];
    String Action [];
    int Images [];
    public Ebox_Notification_Row(Activity context,  String Title [],String Body [],String Duration[],String Action [],int Images []) {
        super(context, R.layout.notification_ebox,Title);
        this.context=context;
        this.Title=Title;
        this.Body=Body;
        this.Duration=Duration;
        this.Action=Action;
        this.Images=Images;
    }
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.notification_ebox, null, true);
        TextView title=(TextView) rowView.findViewById(R.id.Notification_eBox_Title);
        TextView body=(TextView) rowView.findViewById(R.id.Notification_eBox_Body);
        TextView duration=(TextView) rowView.findViewById(R.id.Notification_eBox_Duration);
        TextView action=(TextView) rowView.findViewById(R.id.Notification_eBox_Action);
        ImageView senderLogo=(ImageView) rowView.findViewById(R.id.Notifier_Logo);
        title.setText(Title[position]);
        body.setText(Body[position]);
        duration.setText(Duration[position]);
        action.setText(Action[position]);
        senderLogo.setImageDrawable(context.getResources().getDrawable(Images[position]));
        return rowView;
    }
}
