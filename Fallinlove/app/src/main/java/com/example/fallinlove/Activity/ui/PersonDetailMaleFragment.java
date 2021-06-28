package com.example.fallinlove.Activity.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fallinlove.Adapter.ItemDetailMaleRecyclerViewAdapter;
import com.example.fallinlove.DBUtil.PersonDetailDB;
import com.example.fallinlove.Model.ItemDetail;
import com.example.fallinlove.Model.Person;
import com.example.fallinlove.Model.PersonDetail;
import com.example.fallinlove.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PersonDetailMaleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PersonDetailMaleFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static View mView;
    private static Person mPerson;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PersonDetailMaleFragment() {
        // Required empty public constructor
    }

    public PersonDetailMaleFragment(Person person) {
        // Required empty public constructor
        mPerson = person;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PersonDetailMaleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PersonDetailMaleFragment newInstance(String param1, String param2) {
        PersonDetailMaleFragment fragment = new PersonDetailMaleFragment();
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

    //Model
    List<PersonDetail> personDetails;
    ItemDetailMaleRecyclerViewAdapter itemDetailRecyclerViewAdapter;
    List<ItemDetail> itemDetails;

    RecyclerView recyclerViewPersonDetail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_person_detail_male, container, false);

        getModel(mView);
        getViewFragment(mView);
        setView(mView);

        return mView;
    }

    public void getModel(View view){
        personDetails = PersonDetailDB.getInstance(view.getContext()).gets(mPerson);
        itemDetails = new ArrayList<ItemDetail>();
    }

    public void getViewFragment(View view){
        recyclerViewPersonDetail = view.findViewById(R.id.recyclerViewPersonDetail);
    }

    public void setView(View view){
        itemDetails.add(new ItemDetail(mPerson, ItemDetail.ItemType.PERSON));
        for (PersonDetail personDetail:personDetails){
            itemDetails.add(new ItemDetail(personDetail, ItemDetail.ItemType.PERSON_DETAIL));
        }
        itemDetailRecyclerViewAdapter = new ItemDetailMaleRecyclerViewAdapter(itemDetails);
        recyclerViewPersonDetail.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerViewPersonDetail.setItemAnimator(new DefaultItemAnimator());
        recyclerViewPersonDetail.setAdapter(itemDetailRecyclerViewAdapter);
    }
}