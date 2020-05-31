package com.learn.dynamictabview;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.learn.dynamictabview.databinding.TabViewBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class TabViewFragment extends Fragment {


    private TabViewBinding tvbinding;
    private ArrayList<String> tabNamesList=new ArrayList<>();
    private TabViewAdapter adapter;
    public TabViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.tab_view, container, false);
        tvbinding= DataBindingUtil.inflate(inflater,R.layout.tab_view,container,false);
        View v=tvbinding.getRoot();
        tvbinding.viewpager.setOffscreenPageLimit(1);
        getTabsDynamically();
        tvbinding.viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return v;

    }

    /* This method is used to add Tab data from RAW file. It can be a Api call or something else to get dynamically
    * tab names to add to ViewPager */
    private void getTabsDynamically() {
        if(getActivity()!=null){
            JSONArray tabData = loadJsonArray(getActivity());
            if(tabData!=null){
                try {
                    for (int i = 0; i < tabData.length(); i++) {
                        JSONObject item = tabData.getJSONObject(i);
                        tabNamesList.add(item.getString("name"));
                    }
                    setAdapter(tabNamesList);
                } catch (JSONException exception) {
                    exception.printStackTrace();
                }
            }

        }

    }

    /* This is used to Set the Adapter for Tab View*/
    private void setAdapter(ArrayList<String> tabNamesList) {
        if(getActivity()!=null){
            adapter = new TabViewAdapter(getActivity().getSupportFragmentManager(),tabNamesList.size());
            tvbinding.viewpager.setAdapter(adapter);
            tvbinding.tabs.setupWithViewPager(tvbinding.viewpager,false);
        }
    }

    /* This method is used to get RAW data from file */
    private static JSONArray loadJsonArray(Context context) {
        StringBuilder builder = new StringBuilder();
        InputStream in = context.getResources().openRawResource(R.raw.tabdata);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            JSONObject json = new JSONObject(builder.toString());
            return json.getJSONArray("data");
        } catch (IOException | JSONException exception) {
            exception.printStackTrace();
        }

        return null;
    }


    //Adapter class to Set Fragment in TabView
    public class TabViewAdapter extends FragmentStatePagerAdapter {

        private  int noOfTabs;

        public TabViewAdapter(FragmentManager fm,int noOfTabs){
        super(fm);
        this.noOfTabs=noOfTabs;
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return POSITION_NONE;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
        String name=tabNamesList.get(position);
            return ActualTabFragment.newState(name);
        }

        @Override
        public int getCount() {
            return tabNamesList.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            String name=tabNamesList.get(position);
            return name;
        }
    }
}