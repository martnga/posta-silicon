package com.craft.PostaEbox.Utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.craft.PostaEbox.R;

/**
 * Created by Oliver on 3/2/2016.
 */
public class DrawerAdapter extends ArrayAdapter {
    Activity context;
    String [] DrawerMenu;
    int [] DrawerIcons;
    public DrawerAdapter(Activity context, String [] DrawerMenu,int [] DrawerIcons) {
        super(context,  R.layout.drawer_row,DrawerMenu);
        this.context=context;
        this.DrawerIcons=DrawerIcons;
        this.DrawerMenu=DrawerMenu;
    }
    public View getView(int position, View view, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.drawer_row, null, true);
        TextView DrawerItem=(TextView) rowView.findViewById(R.id.DrawerMenu);
        ImageView DrawerIcon=(ImageView) rowView.findViewById(R.id.DrawerIcon);
        DrawerItem.setText(DrawerMenu[position]);
        DrawerIcon.setImageDrawable(context.getResources().getDrawable(DrawerIcons[position]));
        return rowView;
        }
}
