package com.example.fallinlove.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fallinlove.Model.ItemDetail;
import com.example.fallinlove.Model.Person;
import com.example.fallinlove.Model.PersonDetail;
import com.example.fallinlove.Provider.DateProvider;
import com.example.fallinlove.Provider.ImageConvert;
import com.example.fallinlove.R;

import java.util.List;

public class ItemDetailRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int TYPE_PERSON = 1;
    private static final int TYPE_PERSON_DETAIL = 2;

    public static List<ItemDetail> itemDetails;

    public ItemDetailRecyclerViewAdapter(List<ItemDetail> itemDetails){
        this.itemDetails = itemDetails;
    }

    @Override
    public int getItemCount() {
        return itemDetails == null ? 0 : itemDetails.size();
    }

    // determine which layout to use for the row
    @Override
    public int getItemViewType(int position) {
        ItemDetail item = itemDetails.get(position);
        if (item.getType() == ItemDetail.ItemType.PERSON) {
            return TYPE_PERSON;
        } else if (item.getType() == ItemDetail.ItemType.PERSON_DETAIL) {
            return TYPE_PERSON_DETAIL;
        } else {
            return -1;
        }
    }

    // specify the row layout file and click for each row
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_PERSON) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.person_item, parent, false);
            return new ViewHolderPerson(view);
        } else if (viewType == TYPE_PERSON_DETAIL) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.person_detail_item, parent, false);
            return new ViewHolderPersonDetail(view);
        } else {
            throw new RuntimeException("");
        }
    }

    // load data in each row element
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int listPosition) {
        switch (holder.getItemViewType()) {
            case TYPE_PERSON:
                initLayoutPerson((ViewHolderPerson)holder, listPosition);
                break;
            case TYPE_PERSON_DETAIL:
                initLayoutPersonDetail((ViewHolderPersonDetail) holder, listPosition);
                break;
            default:
                break;
        }
    }

    private void initLayoutPerson(ViewHolderPerson holder, int pos) {
        //get element
        Person person = itemDetails.get(pos).getPerson();
        holder.imgViewAvatar.setImageBitmap(ImageConvert.ArrayByteToBitmap(person.getAvatar()));
        int border = person.isGender() ? R.drawable.bg_border_rounded_purple : R.drawable.bg_border_rounded_pink;
        holder.imgViewBorder.setImageResource(border);
        int dateIcon = person.isGender() ? R.drawable.ic_baseline_date_range_24 : R.drawable.ic_baseline_date_range_pink;
        holder.btnSelectDate.setImageResource(dateIcon);
        holder.txtName.setText(person.getName());
        holder.txtDob.setText(DateProvider.convertDateSqliteToPerson(person.getDob()));
    }

    private void initLayoutPersonDetail(ViewHolderPersonDetail holder, int pos) {
        //get element
        PersonDetail personDetail = itemDetails.get(pos).getPersonDetail();
        holder.txtViewName.setText(personDetail.getName());
        holder.txtDescription.setText(personDetail.getDescription());
        int isVisible = personDetail.isState() ? View.VISIBLE : View.GONE;
        holder.cardViewPersonDetail.setVisibility(isVisible);
    }

    // Static inner class to initialize the views of rows
    static class ViewHolderPerson extends RecyclerView.ViewHolder {

        ImageView imgViewAvatar, imgViewBorder;
        EditText txtName, txtDob;
        ImageView btnSelectDate;

        public ViewHolderPerson(View itemView) {
            super(itemView);
            getView(itemView);
        }

        public void getView(View itemView){
            imgViewAvatar = itemView.findViewById(R.id.imgViewAvatar);
            imgViewBorder = itemView.findViewById(R.id.imgViewBorder);
            txtName = itemView.findViewById(R.id.txtName);
            txtDob = itemView.findViewById(R.id.txtDob);
            btnSelectDate = itemView.findViewById(R.id.btnSelectDate);
        }
    }

    static class ViewHolderPersonDetail extends RecyclerView.ViewHolder {

        LinearLayout layoutInfo, layoutEdit;
        TextView txtViewName;
        ImageButton btnShow, btnDone;
        EditText txtDescription;
        CardView cardViewPersonDetail;

        public ViewHolderPersonDetail(View itemView) {
            super(itemView);
            getView(itemView);
        }

        public void getView(View itemView){
            layoutInfo = itemView.findViewById(R.id.layoutInfo);
            layoutEdit = itemView.findViewById(R.id.layoutEdit);
            txtViewName = itemView.findViewById(R.id.txtViewName);
            txtDescription = itemView.findViewById(R.id.txtDescription);
            btnShow = itemView.findViewById(R.id.btnShow);
            btnDone = itemView.findViewById(R.id.btnDone);
            cardViewPersonDetail = itemView.findViewById(R.id.cardViewPersonDetail);
        }
    }

}
