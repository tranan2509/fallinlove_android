package com.example.fallinlove.Adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.fallinlove.R;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public class AnniversaryRecyclerViewAdapter extends RecyclerView.Adapter<AnniversaryRecyclerViewAdapter.ViewHolder>{

    List<String> responsibilities;

    public AnniversaryRecyclerViewAdapter(List<String> responsibilities){

        this.responsibilities = responsibilities;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageButton btnOption;

        public ViewHolder(View itemView) {
            super(itemView);

            btnOption = itemView.findViewById(R.id.btnOption);

            btnOption.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popup = new PopupMenu(itemView.getContext(), btnOption);
                    popup.inflate(R.menu.menu_option);
                    popup.setGravity(Gravity.RIGHT);
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.edit:
                                    Toast.makeText(btnOption.getContext(), "Edit", Toast.LENGTH_LONG).show();
                                    return true;
                                case R.id.delete:
                                    Toast.makeText(btnOption.getContext(), "Delete", Toast.LENGTH_LONG).show();
                                    return true;
                                default:
                                    return false;
                            }
                        }
                    });

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
            });
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
