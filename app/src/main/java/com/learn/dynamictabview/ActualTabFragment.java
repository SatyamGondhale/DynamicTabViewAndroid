package com.learn.dynamictabview;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.learn.dynamictabview.databinding.ActualTabBinding;


public class ActualTabFragment extends Fragment {

    private ActualTabBinding atBinding;
    private String tabNameDisplay;
    public ActualTabFragment() {
        // Required empty public constructor
    }

    // This is used to create instance of Fragment class
    public static Fragment newState(String name) {
        ActualTabFragment tabFragment=new ActualTabFragment();
        Bundle b=new Bundle();
        b.putString("tabname",name);
        tabFragment.setArguments(b);
        return tabFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            tabNameDisplay=getArguments().getString("tabname","");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.actual_tab, container, false);
        atBinding= DataBindingUtil.inflate(inflater,R.layout.actual_tab,container,false);
        View v=atBinding.getRoot();
        atBinding.tabName.setText(tabNameDisplay);
        return v;
    }
}