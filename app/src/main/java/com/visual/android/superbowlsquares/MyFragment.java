package com.visual.android.superbowlsquares;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by RamiK on 9/14/2016.
 */
public class MyFragment  extends Fragment {

    public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";

    public static MyFragment newInstance(FragmentConfig fragmentConfig) {
        MyFragment f = new MyFragment();
        Bundle bdl = new Bundle(1);
        bdl.putSerializable(EXTRA_MESSAGE, fragmentConfig);
        f.setArguments(bdl);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentConfig fragmentConfig = (FragmentConfig)getArguments().getSerializable(EXTRA_MESSAGE);
        View v = inflater.inflate(fragmentConfig.getLayout(), container, false);
        TextView messageTextView = (TextView)v.findViewById(R.id.fragment_text);
        messageTextView.setText(fragmentConfig.getMessage());

        ImageView imageView = (ImageView)v.findViewById(R.id.fragment_image);
        switch (fragmentConfig.getId()){
            case "nameInput":
                imageView.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.howtoplay_nameinput4));
                break;
            case "boardSetUp":
                imageView.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.howtoplay_boardsetup4));
                break;
            case "gameSelection":
                imageView.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.howtoplay_gameselection4));
                break;
            case "mainBoard":
                imageView.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.howtoplay_mainboard4));
                break;
        }

        return v;
    }
}
