package com.example.fallinlove.Activity.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.fallinlove.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AnniversaryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AnniversaryFragment extends Fragment {

    private static View mView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AnniversaryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AnniversaryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AnniversaryFragment newInstance(String param1, String param2) {
        AnniversaryFragment fragment = new AnniversaryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    ListView lvAnniversary;
    ArrayAdapter<String> adapter;
    String TAG="FIREBASE";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_anniversary, container, false);

        getViewFragment(mView);
        setView(mView);
        return mView;
    }

    void getViewFragment(View view){
        lvAnniversary = view.findViewById(R.id.lvAnniversary);
    }

    void setView(View view){
        adapter=new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_list_item_1);
        lvAnniversary.setAdapter(adapter);

        //l???y ?????i t?????ng FirebaseDatabase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //K???t n???i t???i node c?? t??n l?? contacts (node n??y do ta ?????nh ngh??a trong CSDL Firebase)
        DatabaseReference myRef = database.getReference("contacts");
        //truy su???t v?? l???ng nghe s??? thay ?????i d??? li???u
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                adapter.clear();
        //v??ng l???p ????? l???y d??? li???u khi c?? s??? thay ?????i tr??n Firebase
                for (DataSnapshot data: dataSnapshot.getChildren())
                {
        //l???y key c???a d??? li???u
                    String key=data.getKey();
        //l???y gi?? tr??? c???a key (n???i dung)
                    String value=data.getValue().toString();
                    adapter.add(key+"\n"+value);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        });
    }
}