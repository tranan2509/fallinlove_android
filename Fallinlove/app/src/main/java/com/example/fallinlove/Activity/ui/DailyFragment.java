package com.example.fallinlove.Activity.ui;

import android.content.Context;
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
    public static Context context;

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

        context = mView.getContext();
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

    public static void move(Responsibility responsibility, Responsibility newResponsibility){
        int fromPosition = getPosition(responsibilities, responsibility);
        ResponsibilityDB.getInstance(context).update(newResponsibility);
        List<Responsibility> responsibilitiesNew = ResponsibilityDB.getInstance(mView.getContext()).getsSorted(user, Responsibility.TYPE_DAILY);
        int toPosition = getPosition(responsibilitiesNew, newResponsibility);
        // update data array
        responsibilities.remove(fromPosition);
        responsibilities.add(toPosition, newResponsibility);
        // notify adapter
        responsibilityRecyclerViewAdapter.notifyItemMoved(fromPosition, toPosition);
    }

    public static void update(Responsibility responsibility){
        int position = getPosition(responsibilities, responsibility);
        responsibilities.set(position, responsibility);
        responsibilityRecyclerViewAdapter.notifyItemChanged(position);
    }

    public static void add(Responsibility responsibility){
        ResponsibilityDB.getInstance(context).add(responsibility);
        int id = ResponsibilityDB.getInstance(context).getMaxId();
        responsibility.setId(id);
        List<Responsibility> newResponsibilities = ResponsibilityDB.getInstance(context).getsSorted(user, Responsibility.TYPE_DAILY);
        int insertIndex = getPosition(newResponsibilities, responsibility);
        if (insertIndex == -1)
            insertIndex = 0;
        responsibilities.add(insertIndex, responsibility);
        responsibilityRecyclerViewAdapter.notifyItemInserted(insertIndex);
    }

    public static int getPosition(List<Responsibility> responsibilities, Responsibility responsibility){
        int position = -1;
        for (int i = 0; i < responsibilities.size(); i++){
            if (responsibilities.get(i).getId() == responsibility.getId()){
                return  i;
            }
        }
        return position;
    }
}