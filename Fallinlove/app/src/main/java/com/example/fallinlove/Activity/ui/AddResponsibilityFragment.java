package com.example.fallinlove.Activity.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.example.fallinlove.Adapter.StringSpinnerAdapter;
import com.example.fallinlove.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddResponsibilityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddResponsibilityFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static View mView;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddResponsibilityFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddResponsibilityFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddResponsibilityFragment newInstance(String param1, String param2) {
        AddResponsibilityFragment fragment = new AddResponsibilityFragment();
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

    Button btnSave;
    EditText txtName, txtDate;
    Spinner spnTypeResponsibilities, spnLevels;
    String[] typeResponsibility, level;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_add_responsibility, container, false);

        getViewFragment(mView);
        setView(mView);

        return mView;
    }

    public void getViewFragment(View view){
        btnSave = view.findViewById(R.id.btnSave);
        txtName = view.findViewById(R.id.txtName);
        txtDate = view.findViewById(R.id.txtDate);
        spnTypeResponsibilities = view.findViewById(R.id.spnTypeResponsibility);
        spnLevels = view.findViewById(R.id.spnLevel);
    }

    public void setView(View view){
        typeResponsibility = new String[]{"Hàng ngày", "Trách nhiệm"};

        level = new String[]{"Bình thường", "Quan trọng", "Rất quan trọng"};

        setSpinner(view, typeResponsibility, level);
    }

    public void setSpinner(View view, String[] typeResponsibilities, String[] levels){
        StringSpinnerAdapter stringSpinnerAdapterType = new StringSpinnerAdapter(getContext(), typeResponsibilities);
        spnTypeResponsibilities.setAdapter(stringSpinnerAdapterType);
        spnTypeResponsibilities.setSelection(0);

        StringSpinnerAdapter stringSpinnerAdapterLevel = new StringSpinnerAdapter(getContext(), levels);
        spnLevels.setAdapter(stringSpinnerAdapterLevel);
        spnLevels.setSelection(0);
    }

    public void setOnClick(){

    }

}