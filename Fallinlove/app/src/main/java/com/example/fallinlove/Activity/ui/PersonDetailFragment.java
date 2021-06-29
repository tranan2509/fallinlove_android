package com.example.fallinlove.Activity.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fallinlove.Adapter.ItemDetailRecyclerViewAdapter;
import com.example.fallinlove.DBUtil.PersonDB;
import com.example.fallinlove.DBUtil.PersonDetailDB;
import com.example.fallinlove.Model.ItemDetail;
import com.example.fallinlove.Model.Person;
import com.example.fallinlove.Model.PersonDetail;
import com.example.fallinlove.Provider.ImageConvert;
import com.example.fallinlove.Provider.ImageResizer;
import com.example.fallinlove.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PersonDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PersonDetailFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static View mView;
    private static Person mPerson;
    private static final int PICK_IMAGE = 222;
    public static Context context;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PersonDetailFragment() {
        // Required empty public constructor
    }

    public PersonDetailFragment(Person person) {
        // Required empty public constructor
        mPerson = person;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PersonDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PersonDetailFragment newInstance(String param1, String param2) {
        PersonDetailFragment fragment = new PersonDetailFragment();
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
    public static ItemDetailRecyclerViewAdapter itemDetailRecyclerViewAdapter;
    public static List<ItemDetail> itemDetails;

    RecyclerView recyclerViewPersonDetail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_person_detail, container, false);

        context = mView.getContext();
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
        recyclerViewPersonDetail.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerViewPersonDetail.setItemAnimator(new DefaultItemAnimator());
        itemDetailRecyclerViewAdapter = new ItemDetailRecyclerViewAdapter(itemDetails);
        recyclerViewPersonDetail.setAdapter(itemDetailRecyclerViewAdapter);
    }

    public static void getImageGallery(){
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");
        Intent pickIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");
        Intent chooserIntent = Intent.createChooser(getIntent, "Chọn ảnh");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});
        ((Activity)context).startActivityForResult(chooserIntent, PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            try {
                Uri imageUri = data.getData();
                Bitmap photo = MediaStore.Images.Media.getBitmap(context.getContentResolver(), imageUri);
                photo = ImageResizer.reduceBitmapSize(photo, ImageResizer.MAX_SIZE);
                mPerson.setAvatar(ImageConvert.BitmapToArrayByte(photo));
                if (mPerson.getId() % 2 == 0) {
                    System.out.println(mPerson.getId() % 2);
                    PersonDB.getInstance(context).update(mPerson);
                    ItemDetailRecyclerViewAdapter.ViewHolderPerson.imgViewAvatar.setImageBitmap(photo);
                }
            } catch (IOException e) {

            }
            return;
        }
    }

    public static void addPersonDetail(PersonDetail personDetail){
        ItemDetail itemDetail = new ItemDetail(personDetail, ItemDetail.ItemType.PERSON_DETAIL);
        itemDetails.add(itemDetail);
        itemDetailRecyclerViewAdapter.notifyItemInserted(itemDetails.size() - 1);
    }

    public static void addNewPersonDetail(){
        PersonDetail personDetail = new PersonDetail(mPerson.getId(), "Tiêu đề", "Nội dung tiêu đề", true);
        PersonDetailDB.getInstance(context).add(personDetail);
        ItemDetail itemDetail = new ItemDetail(personDetail, ItemDetail.ItemType.PERSON_DETAIL);
        itemDetails.add(itemDetails.size(), itemDetail);
        itemDetailRecyclerViewAdapter.notifyItemInserted(itemDetails.size() - 1);
    }

}