package com.craft.PostaEbox.Utils;

/**
 * Created by mansa on 3/17/16.
 */
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.craft.PostaEbox.R;
import com.craft.PostaEbox.models.PartnersModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PartnersDataAdapter extends RecyclerView.Adapter<PartnersDataAdapter.ViewHolder> {
    private ArrayList<PartnersModel> partners;
    private Context context;

    public PartnersDataAdapter(Context context,ArrayList<PartnersModel> partners) {
        this.partners = partners;
        this.context = context;
    }

    @Override
    public PartnersDataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.partner_row_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PartnersDataAdapter.ViewHolder viewHolder, int i) {

        viewHolder.tv_partner_name.setText(partners.get(i).getPartnerName());
        Picasso.with(context).load(partners.get(i).getPartner_image_url()).resize(240, 120).into(viewHolder.img_android);
    }

    @Override
    public int getItemCount() {
        return partners.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_partner_name;
        private ImageView img_android;
        public ViewHolder(View view) {
            super(view);

            tv_partner_name = (TextView)view.findViewById(R.id.tv_partner_name);
            img_android = (ImageView) view.findViewById(R.id.img_android);
        }
    }

}

