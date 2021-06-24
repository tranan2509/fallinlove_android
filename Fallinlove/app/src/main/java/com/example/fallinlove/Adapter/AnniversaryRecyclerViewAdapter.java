package com.example.fallinlove.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.fallinlove.Activity.FunctionAnniversaryActivity;
import com.example.fallinlove.R;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public class AnniversaryRecyclerViewAdapter extends RecyclerView.Adapter<AnniversaryRecyclerViewAdapter.ViewHolder>{

    List<String> responsibilities;
    Intent intentNext;

    public AnniversaryRecyclerViewAdapter(List<String> responsibilities){

        this.responsibilities = responsibilities;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageButton btnOption;

        public ViewHolder(View itemView) {
            super(itemView);

            getView();
            setOnClick();
        }

        public void getView(){
            btnOption = itemView.findViewById(R.id.btnOption);
        }

        public void setOnClick(){
            btnOption.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnOption:
                    showOptionMenu(v);
                    break;
            }
        }

        public void showOptionMenu(View view){
            PopupMenu popup = new PopupMenu(itemView.getContext(), btnOption);
            popup.inflate(R.menu.menu_option);
            popup.setGravity(Gravity.RIGHT);
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.edit:
                            intentNext = new Intent(view.getContext(), FunctionAnniversaryActivity.class);
                            intentNext.putExtra("function", "edit");
                            view.getContext().startActivity(intentNext);
                            return true;
                        case R.id.delete:
                            Toast.makeText(btnOption.getContext(), "Delete", Toast.LENGTH_LONG).show();
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
    }


    @Override
    public AnniversaryRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View cartView = inflater.inflate(R.layout.anniversary_item, parent, false);
        AnniversaryRecyclerViewAdapter.ViewHolder viewHolder = new AnniversaryRecyclerViewAdapter.ViewHolder(cartView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AnniversaryRecyclerViewAdapter.ViewHolder holder, int position) {


    }


    @Override
    public int getItemCount() {
        return responsibilities != null ? responsibilities.size() : -1;
    }

    private void removeItem(int position) {
        responsibilities.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, responsibilities.size());
    }
}
