package com.example.fallinlove.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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

public class BackgroundRecyclerViewAdapter extends RecyclerView.Adapter<BackgroundRecyclerViewAdapter.ViewHolder>{

    public static List<Background> backgrounds;

    public BackgroundRecyclerViewAdapter(List<Background> backgrounds){

        this.backgrounds = backgrounds;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imgBackground, imgBorder;
        public ImageButton btnClose;
        public CardView cardViewClose;

        public ViewHolder(View itemView) {
            super(itemView);

            imgBackground = itemView.findViewById(R.id.imgBackground);
            imgBorder = itemView.findViewById(R.id.imgBorder);
            btnClose = itemView.findViewById(R.id.btnClose);
            cardViewClose = itemView.findViewById(R.id.cardViewClose);
        }
    }

    @Override
    public BackgroundRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View cartView = inflater.inflate(R.layout.background_item, parent, false);
        BackgroundRecyclerViewAdapter.ViewHolder viewHolder = new BackgroundRecyclerViewAdapter.ViewHolder(cartView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BackgroundRecyclerViewAdapter.ViewHolder holder, int position) {
        User user = (User) SharedPreferenceProvider.getInstance(holder.itemView.getContext()).get("user");
        Background background = backgrounds.get(position);
        holder.imgBackground.setImageBitmap(ImageConvert.ArrayByteToBitmap(background.getImage()));
        if (background.isState()){
            holder.imgBorder.setVisibility(View.VISIBLE);
            holder.cardViewClose.setVisibility(View.GONE);
        }
        else {
            holder.imgBorder.setVisibility(View.GONE);
            holder.cardViewClose.setVisibility(View.VISIBLE);
        }

        holder.imgBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!background.isState()) {
                    setBackgroundUnselected(holder.itemView.getContext());
                    background.setState(true);
                    ImageSetting imageSetting = ImageSettingDB.getInstance(holder.itemView.getContext()).get(user);
                    imageSetting.setBackground(background.getImage());
                    ImageSettingDB.getInstance(holder.imgBackground.getContext()).update(imageSetting);
                    BackgroundDB.getInstance(holder.itemView.getContext()).update(background);
                    BackgroundActivity.backgrounds = BackgroundDB.getInstance(holder.itemView.getContext()).gets(user, "background");
                    BackgroundActivity.loadBackground(BackgroundActivity.backgrounds);

                    //Set background
                    Animation fadeInAnimation = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.fade_in_image);
                    BackgroundActivity.imgBgHome.startAnimation(fadeInAnimation);
                    BackgroundActivity.imgBgHome.setImageBitmap(ImageConvert.ArrayByteToBitmap(background.getImage()));
                    BackgroundActivity.isChangeBackground = true;
                }
            }
        });

        holder.btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BackgroundDB.getInstance(v.getContext()).delete(background);
                removeItem(position);
                BackgroundActivity.backgrounds = BackgroundDB.getInstance(holder.itemView.getContext()).gets(user, "background");
//                BackgroundActivity.loadBackground(BackgroundActivity.backgrounds);
            }
        });
    }

    public void setBackgroundUnselected(Context context){
        for (Background background : backgrounds){
            background.setState(false);
            BackgroundDB.getInstance(context).update(background);
        }
    }

    @Override
    public int getItemCount() {
        return backgrounds.size();
    }

    private void removeItem(int position) {
        backgrounds.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, backgrounds.size());
    }
}
