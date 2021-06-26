package com.example.fallinlove.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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

public class HeartRecyclerViewAdapter extends RecyclerView.Adapter<HeartRecyclerViewAdapter.ViewHolder>{

    public static List<Background> hearts;

    public HeartRecyclerViewAdapter(List<Background> hearts){
        this.hearts = hearts;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imgHeart, imgBorder;

        public ViewHolder(View itemView) {
            super(itemView);

            imgHeart = itemView.findViewById(R.id.imgHeart);
            imgBorder = itemView.findViewById(R.id.imgBorder);
        }
    }

    @Override
    public HeartRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View cartView = inflater.inflate(R.layout.heart_item, parent, false);
        HeartRecyclerViewAdapter.ViewHolder viewHolder = new HeartRecyclerViewAdapter.ViewHolder(cartView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(HeartRecyclerViewAdapter.ViewHolder holder, int position) {
        User user = (User) SharedPreferenceProvider.getInstance(holder.itemView.getContext()).get("user");
        Background heart = hearts.get(position);
        holder.imgHeart.setImageBitmap(ImageConvert.ArrayByteToBitmap(heart.getImage()));
        if (heart.isState())
            holder.imgBorder.setVisibility(View.VISIBLE);
        else
            holder.imgBorder.setVisibility(View.GONE);

        holder.imgHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!heart.isState()) {
                    setHeartsUnselected(holder.itemView.getContext());
                    heart.setState(true);
                    ImageSetting imageSetting = ImageSettingDB.getInstance(holder.itemView.getContext()).get(user);
                    imageSetting.setHeart(heart.getImage());
                    ImageSettingDB.getInstance(holder.imgHeart.getContext()).update(imageSetting);
                    BackgroundDB.getInstance(holder.itemView.getContext()).update(heart);
                    BackgroundActivity.loadHearts(BackgroundDB.getInstance(holder.itemView.getContext()).gets(user, "heart"));
                }
            }
        });
    }

    public void setHeartsUnselected(Context context){
        for (Background heart : hearts){
            heart.setState(false);
            BackgroundDB.getInstance(context).update(heart);
        }
    }

    @Override
    public int getItemCount() {
        return hearts.size();
    }

    private void removeItem(int position) {
        hearts.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, hearts.size());
    }
}
