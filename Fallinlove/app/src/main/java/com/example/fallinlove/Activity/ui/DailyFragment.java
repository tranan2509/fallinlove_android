package com.example.fallinlove.Activity.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fallinlove.Adapter.ResponsibilityRecyclerViewAdapter;
import com.example.fallinlove.DBUtil.ResponsibilityDB;
import com.example.fallinlove.Model.Responsibility;
import com.example.fallinlove.Model.User;
import com.example.fallinlove.Provider.SharedPreferenceProvider;
import com.example.fallinlove.R;

import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DailyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DailyFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static View mView;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DailyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DailyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DailyFragment newInstance(String param1, String param2) {
        DailyFragment fragment = new DailyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public static User user;
    public static RecyclerView recyclerViewResponsibility;
    public static List<Responsibility> responsibilities;
    public static ResponsibilityRecyclerViewAdapter responsibilityRecyclerViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_daily, container, false);

        getModel(mView);
        getViewFragment(mView);
        setView(mView);

        return mView;
    }

    public void getModel(View mView){
        user = (User) SharedPreferenceProvider.getInstance(mView.getContext()).get("user");
        responsibilities = ResponsibilityDB.getInstance(mView.getContext()).getsSorted(user, 1);
    }

    public void getViewFragment(View view){
        recyclerViewResponsibility = view.findViewById(R.id.recyclerViewResponsibility);
    }

    public void setView(View view){
        loadRecycleView(responsibilities);
    }

    public static void loadRecycleView(List<Responsibility> responsibilities){
        if (responsibilities != null) {
            responsibilityRecyclerViewAdapter = new ResponsibilityRecyclerViewAdapter(responsibilities);
            recyclerViewResponsibility.setAdapter(responsibilityRecyclerViewAdapter);
            recyclerViewResponsibility.setLayoutManager(new LinearLayoutManager(mView.getContext()));
            recyclerViewResponsibility.setItemAnimator(new SlideInUpAnimator());
        }

    }
}