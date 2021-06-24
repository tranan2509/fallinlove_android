package com.example.fallinlove.Activity.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fallinlove.Adapter.ResponsibilityRecyclerViewAdapter;
import com.example.fallinlove.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ResponsibilityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResponsibilityFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static View mView;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ResponsibilityFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ResponsibilityFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ResponsibilityFragment newInstance(String param1, String param2) {
        ResponsibilityFragment fragment = new ResponsibilityFragment();
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

    public static RecyclerView recyclerViewResponsibility;
    public static List<String> responsibilities;
    public static ResponsibilityRecyclerViewAdapter responsibilityRecyclerViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_responsibility, container, false);

        getViewFragment(mView);
        setView(mView);

        return mView;
    }

    public void getViewFragment(View view){
        recyclerViewResponsibility = view.findViewById(R.id.recyclerViewResponsibility);
    }

    public void setView(View view){
        loadRecycleView();
    }

    public static void loadRecycleView(){
        responsibilities = new ArrayList<String>();
        Random random = new Random();
        int size = random.nextInt(6) + 1;
        for (int i = 0; i < size; i++) {
            responsibilities.add("1");
        }

        if (responsibilities != null) {
            responsibilityRecyclerViewAdapter = new ResponsibilityRecyclerViewAdapter(responsibilities);
            recyclerViewResponsibility.setAdapter(responsibilityRecyclerViewAdapter);
            recyclerViewResponsibility.setLayoutManager(new LinearLayoutManager(mView.getContext()));
            recyclerViewResponsibility.setItemAnimator(new SlideInUpAnimator());
        }

    }
}