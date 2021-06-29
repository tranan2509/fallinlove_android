package com.example.fallinlove.Adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fallinlove.Activity.ui.PersonDetailMaleFragment;
import com.example.fallinlove.DBUtil.PersonDB;
import com.example.fallinlove.DBUtil.PersonDetailDB;
import com.example.fallinlove.Model.ItemDetail;
import com.example.fallinlove.Model.Person;
import com.example.fallinlove.Model.PersonDetail;
import com.example.fallinlove.Provider.DateProvider;
import com.example.fallinlove.Provider.ImageConvert;
import com.example.fallinlove.R;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public class ItemDetailMaleRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_PERSON = 1;
    private static final int TYPE_PERSON_DETAIL = 2;

    private static final int PICK_IMAGE = 222;
    public static List<ItemDetail> itemDetails;

    public ItemDetailMaleRecyclerViewAdapter(List<ItemDetail> itemDetails){
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
            return new ItemDetailMaleRecyclerViewAdapter.ViewHolderPerson(view);
        } else if (viewType == TYPE_PERSON_DETAIL) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.person_detail_item, parent, false);
            return new ItemDetailMaleRecyclerViewAdapter.ViewHolderPersonDetail(view);
        } else {
            throw new RuntimeException("");
        }
    }

    // load data in each row element
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int listPosition) {
        switch (holder.getItemViewType()) {
            case TYPE_PERSON:
                initLayoutPerson((ItemDetailMaleRecyclerViewAdapter.ViewHolderPerson)holder, listPosition);
                break;
            case TYPE_PERSON_DETAIL:
                initLayoutPersonDetail((ItemDetailMaleRecyclerViewAdapter.ViewHolderPersonDetail) holder, listPosition);
                break;
            default:
                break;
        }
    }

    private void initLayoutPerson(ItemDetailMaleRecyclerViewAdapter.ViewHolderPerson holder, int pos) {
        //get element
        Person person = itemDetails.get(pos).getPerson();
        holder.imgViewAvatar.setImageBitmap(ImageConvert.ArrayByteToBitmap(person.getAvatar()));
        int border = person.isGender() ? R.drawable.bg_border_rounded_purple : R.drawable.bg_border_rounded_pink;
        holder.imgViewBorder.setImageResource(border);
        holder.txtName.setText(person.getName());
        holder.txtDob.setText(DateProvider.convertDateSqliteToPerson(person.getDob()));


        holder.btnSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateProvider.selectDate(holder.txtDob);
            }
        });

        holder.txtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                person.setName(holder.txtName.getText().toString());
                PersonDB.getInstance(holder.itemView.getContext()).update(person);
            }
        });

        holder.txtDob.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                person.setDob(DateProvider.convertDatePersonToSqlite(holder.txtDob.getText().toString()));
                PersonDB.getInstance(holder.itemView.getContext()).update(person);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        holder.btnOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOptionMenuPerson(holder, itemDetails.get(pos));
            }
        });

        holder.imgViewAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonDetailMaleFragment.getImageGallery();
            }
        });
    }

    public void showOptionMenuPerson(ViewHolderPerson holder, ItemDetail itemDetail){
        PopupMenu popup = new PopupMenu(holder.itemView.getContext(), holder.btnOption);
        popup.inflate(R.menu.menu_option_add_info);
        popup.setGravity(Gravity.RIGHT);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.add:
                       //Add person detail
                        PersonDetailMaleFragment.addNewPersonDetail();
                        return true;
                    default:
                        return false;
                }
            }
        });

        //Show icon
        try {
            Field[] fields = popup.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popup);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper
                            .getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod(
                            "setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Exception e) {

        }
        popup.show();
    }

    private void initLayoutPersonDetail(ItemDetailMaleRecyclerViewAdapter.ViewHolderPersonDetail holder, int pos) {
        //get element
        PersonDetail personDetail = itemDetails.get(pos).getPersonDetail();
        holder.txtViewName.setText(personDetail.getName());
        holder.txtDescription.setText(personDetail.getDescription());
        int isVisible = personDetail.isState() ? View.VISIBLE : View.GONE;
        int hide = isVisible == View.VISIBLE ? R.drawable.ic_baseline_eye : R.drawable.ic_baseline_eye_hide;
        holder.btnShow.setImageResource(hide);

        holder.txtDescription.setVisibility(isVisible);
        holder.txtName.setText(personDetail.getName());

        holder.btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int isVisible = holder.txtDescription.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE;
                int hide = isVisible == View.VISIBLE ? R.drawable.ic_baseline_eye : R.drawable.ic_baseline_eye_hide;
                boolean isSate = isVisible == View.VISIBLE;
                personDetail.setState(isSate);
                PersonDetailDB.getInstance(holder.itemView.getContext()).update(personDetail);
                holder.btnShow.setImageResource(hide);
                holder.txtDescription.setVisibility(isVisible);
            }
        });

        holder.btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.txtViewName.setText(holder.txtName.getText().toString());
                personDetail.setName(holder.txtName.getText().toString());
                holder.layoutEdit.setVisibility(View.GONE);
                holder.layoutInfo.setVisibility(View.VISIBLE);
                PersonDetailDB.getInstance(holder.itemView.getContext()).update(personDetail);
            }
        });

        holder.txtDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                personDetail.setDescription(holder.txtDescription.getText().toString());
                PersonDetailDB.getInstance(holder.itemView.getContext()).update(personDetail);
            }
        });

        holder.btnOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOptionMenu(holder, itemDetails.get(pos));
            }
        });

    }

    public void showOptionMenu(ViewHolderPersonDetail holder, ItemDetail itemDetail){
        PopupMenu popup = new PopupMenu(holder.itemView.getContext(), holder.btnOption);
        popup.inflate(R.menu.menu_option);
        popup.setGravity(Gravity.RIGHT);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.edit:
                        holder.layoutInfo.setVisibility(View.GONE);
                        holder.layoutEdit.setVisibility(View.VISIBLE);
                        return true;
                    case R.id.delete:
                        removeItem(itemDetail);
                        PersonDetailDB.getInstance(holder.itemView.getContext()).delete(itemDetail.getPersonDetail());
                        return true;
                    default:
                        return false;
                }
            }
        });

        //Show icon
        try {
            Field[] fields = popup.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popup);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper
                            .getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod(
                            "setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Exception e) {

        }
        popup.show();
    }

    // Static inner class to initialize the views of rows
    public static class ViewHolderPerson extends RecyclerView.ViewHolder implements View.OnClickListener {

        public static ImageView imgViewAvatar, imgViewBorder;
        public static EditText txtName, txtDob;
        public static ImageView btnSelectDate;
        public static ImageButton btnOption;

        public ViewHolderPerson(View itemView) {
            super(itemView);
            getView(itemView);
            setOnClick(itemView);
        }

        public void getView(View itemView){
            imgViewAvatar = itemView.findViewById(R.id.imgViewAvatar);
            imgViewBorder = itemView.findViewById(R.id.imgViewBorder);
            txtName = itemView.findViewById(R.id.txtName);
            txtDob = itemView.findViewById(R.id.txtDob);
            btnSelectDate = itemView.findViewById(R.id.btnSelectDate);
            btnOption = itemView.findViewById(R.id.btnOption);
        }

        public void setOnClick(View itemView)
        {
            imgViewAvatar.setOnClickListener(this);
        }

        public void onClick(View itemView){
            switch (itemView.getId()){
                case R.id.imgViewAvatar:
                    break;
            }
        }
    }

    public static class ViewHolderPersonDetail extends RecyclerView.ViewHolder {

        LinearLayout layoutInfo, layoutEdit;
        TextView txtViewName;
        ImageButton btnShow, btnDone, btnOption;
        EditText txtDescription, txtName;
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
            txtName = itemView.findViewById(R.id.txtName);
            btnShow = itemView.findViewById(R.id.btnShow);
            btnDone = itemView.findViewById(R.id.btnDone);
            btnOption = itemView.findViewById(R.id.btnOption);
            cardViewPersonDetail = itemView.findViewById(R.id.cardViewPersonDetail);
        }
    }

    private void removeItem(int position) {
        itemDetails.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, itemDetails.size());
    }

    private void removeItem(ItemDetail itemDetail) {
        int position = itemDetails.indexOf(itemDetail);
        itemDetails.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, itemDetails.size());
    }

}
