package com.example.fallinlove.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.fallinlove.Activity.AnniversaryActivity;
import com.example.fallinlove.Activity.FunctionAnniversaryActivity;
import com.example.fallinlove.Activity.ViewImageActivity;
import com.example.fallinlove.DBUtil.AnniversaryDB;
import com.example.fallinlove.Model.Anniversary;
import com.example.fallinlove.Model.User;
import com.example.fallinlove.Provider.DateProvider;
import com.example.fallinlove.Provider.ImageConvert;
import com.example.fallinlove.Provider.SharedPreferenceProvider;
import com.example.fallinlove.R;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public class AnniversaryRecyclerViewAdapter extends RecyclerView.Adapter<AnniversaryRecyclerViewAdapter.ViewHolder>{

    List<Anniversary> anniversaries;
    Intent intentNext;
    User user;

    public AnniversaryRecyclerViewAdapter(List<Anniversary> anniversaries){
        this.anniversaries = anniversaries;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageButton btnOption;
        TextView txtViewName, txtViewDate, txtViewDescription;
        ImageView imgAnniversary;

        public ViewHolder(View itemView) {
            super(itemView);

            getModel(itemView);
            getView();
            setOnClick();
        }

        public void getModel(View itemView){
            user = (User) SharedPreferenceProvider.getInstance(itemView.getContext()).get("user");
        }

        public void getView(){
            btnOption = itemView.findViewById(R.id.btnOption);
            txtViewName = itemView.findViewById(R.id.txtViewName);
            txtViewDate = itemView.findViewById(R.id.txtViewDate);
            txtViewDescription = itemView.findViewById(R.id.txtViewDescription);
            imgAnniversary = itemView.findViewById(R.id.imgAnniversary);
        }

        public void setOnClick(){
            btnOption.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

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
        Anniversary anniversary = anniversaries.get(position);
        holder.txtViewName.setText(anniversary.getName());
        holder.txtViewDate.setText(DateProvider.convertDateTimeSqliteToPerson(anniversary.getDate()));
        holder.txtViewDescription.setText(anniversary.getDescription());
        holder.imgAnniversary.setImageBitmap(ImageConvert.ArrayByteToBitmap(anniversary.getImage()));

        holder.imgAnniversary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.imgAnniversary.getContext(), ViewImageActivity.class);
                intent.putExtra("image", anniversary.getImage());
                holder.imgAnniversary.getContext().startActivity(intent);
            }
        });
        holder.btnOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOptionMenu(v, anniversary);
            }
        });
    }

    public void showOptionMenu(View view, Anniversary anniversary){
        PopupMenu popup = new PopupMenu(view.getContext(), view.findViewById(R.id.btnOption));
        popup.inflate(R.menu.menu_option);
        popup.setGravity(Gravity.RIGHT);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.edit:
                        intentNext = new Intent(view.getContext(), FunctionAnniversaryActivity.class);
                        intentNext.putExtra("function", "edit");
                        intentNext.putExtra("anniversary", anniversary);
                        view.getContext().startActivity(intentNext);
                        return true;
                    case R.id.delete:
                        removeItem(anniversary);
                        AnniversaryDB.getInstance(view.getContext()).delete(anniversary);
                        AnniversaryActivity.anniversaries = AnniversaryDB.getInstance(view.getContext()).gets(user);
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

    @Override
    public int getItemCount() {
        return anniversaries != null ? anniversaries.size() : -1;
    }

    private void removeItem(int position) {
        anniversaries.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, anniversaries.size());
    }

    private void removeItem(Anniversary anniversary) {
        int position = anniversaries.indexOf(anniversary);
        anniversaries.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, anniversaries.size());
    }
}
