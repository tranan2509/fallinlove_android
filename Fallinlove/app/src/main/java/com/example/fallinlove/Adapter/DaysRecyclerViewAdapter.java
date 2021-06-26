package com.example.fallinlove.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fallinlove.Activity.BackgroundActivity;
import com.example.fallinlove.DBUtil.BackgroundDB;
import com.example.fallinlove.DBUtil.ImageSettingDB;
import com.example.fallinlove.Model.Background;
import com.example.fallinlove.Model.ImageSetting;
import com.example.fallinlove.Model.User;
import com.example.fallinlove.Provider.ImageConvert;
import com.example.fallinlove.Provider.SharedPreferenceProvider;
import com.example.fallinlove.R;

import java.util.List;

public class DaysRecyclerViewAdapter extends RecyclerView.Adapter<DaysRecyclerViewAdapter.ViewHolder>{

    public static List<Background> days;

    public DaysRecyclerViewAdapter(List<Background> days){

        this.days = days;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imgBgDays, imgBorder;
        public ImageButton btnClose;
        public CardView cardViewClose;

        public ViewHolder(View itemView) {
            super(itemView);

            imgBgDays = itemView.findViewById(R.id.imgBgDays);
            imgBorder = itemView.findViewById(R.id.imgBorder);
            btnClose = itemView.findViewById(R.id.btnClose);
            cardViewClose = itemView.findViewById(R.id.cardViewClose);
        }
    }

    @Override
    public DaysRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View cartView = inflater.inflate(R.layout.days_item, parent, false);
        DaysRecyclerViewAdapter.ViewHolder viewHolder = new DaysRecyclerViewAdapter.ViewHolder(cartView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DaysRecyclerViewAdapter.ViewHolder holder, int position) {
        User user = (User) SharedPreferenceProvider.getInstance(holder.itemView.getContext()).get("user");
        Background day = days.get(position);
        holder.imgBgDays.setImageBitmap(ImageConvert.ArrayByteToBitmap(day.getImage()));
        if (day.isState()){
            holder.imgBorder.setVisibility(View.VISIBLE);
            holder.cardViewClose.setVisibility(View.GONE);
        }
        else {
            holder.imgBorder.setVisibility(View.GONE);
            holder.cardViewClose.setVisibility(View.VISIBLE);
        }
        holder.imgBgDays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!day.isState()) {
                    setBackgroundUnselected(holder.itemView.getContext());
                    day.setState(true);
                    ImageSetting imageSetting = ImageSettingDB.getInstance(holder.itemView.getContext()).get(user);
                    imageSetting.setDays(day.getImage());
                    ImageSettingDB.getInstance(holder.itemView.getContext()).update(imageSetting);
                    BackgroundDB.getInstance(holder.itemView.getContext()).update(day);
                    BackgroundActivity.days = BackgroundDB.getInstance(holder.itemView.getContext()).gets(user, "days");
                    BackgroundActivity.loadDays(BackgroundActivity.days);
                }
            }
        });

        holder.btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BackgroundDB.getInstance(v.getContext()).delete(day);
                BackgroundActivity.days = BackgroundDB.getInstance(holder.itemView.getContext()).gets(user, "days");
                BackgroundActivity.loadDays(BackgroundActivity.days);
            }
        });
    }

    public void setBackgroundUnselected(Context context){
        for (Background day : days){
            day.setState(false);
            BackgroundDB.getInstance(context).update(day);
        }
    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    private void removeItem(int position) {
        days.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, days.size());
    }
}
