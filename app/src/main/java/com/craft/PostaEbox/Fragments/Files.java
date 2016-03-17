package com.craft.PostaEbox.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.craft.PostaEbox.R;

import org.w3c.dom.Text;


public class Files extends Fragment {
    TextView AddFile;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_files, container, false);
        AddFile=(TextView) rootView.findViewById(R.id.AddFile);
        return rootView;
    }
}
