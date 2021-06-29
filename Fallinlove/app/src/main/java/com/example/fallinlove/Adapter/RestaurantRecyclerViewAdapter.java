package com.example.fallinlove.Adapter;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.Settings;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fallinlove.Activity.FunctionRestaurantActivity;
import com.example.fallinlove.Activity.RestaurantActivity;
import com.example.fallinlove.DBUtil.RestaurantDB;
import com.example.fallinlove.Model.Restaurant;
import com.example.fallinlove.Model.User;
import com.example.fallinlove.Provider.SharedPreferenceProvider;
import com.example.fallinlove.R;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public class RestaurantRecyclerViewAdapter extends RecyclerView.Adapter<RestaurantRecyclerViewAdapter.ViewHolder> {

    List<Restaurant> restaurants;
    public static Restaurant restaurantRandom;
    Intent intentNext;
    User user;
    LocationManager locationManager;
    Location location;
    String latitude, longitude;
    String[] backgroundColors = {"#a0e5e1", "#dfe4ba", "#d4a7aa", "#afbec3"};

    public RestaurantRecyclerViewAdapter(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageButton btnOption;
        TextView txtViewName, txtViewTime, txtViewAddress;
        Button btnGo;
        ImageView imgBackground;

        public ViewHolder(View itemView) {
            super(itemView);

            getModel(itemView);
            getView();
            setOnClick();
        }

        public void getModel(View itemView) {
            user = (User) SharedPreferenceProvider.getInstance(itemView.getContext()).get("user");
        }

        public void getView() {
            btnOption = itemView.findViewById(R.id.btnOption);
            txtViewName = itemView.findViewById(R.id.txtViewName);
            txtViewAddress = itemView.findViewById(R.id.txtViewAddress);
            txtViewTime = itemView.findViewById(R.id.txtViewTime);
            btnGo = itemView.findViewById(R.id.btnGo);
            imgBackground = itemView.findViewById(R.id.imgBackground);
        }

        public void setOnClick() {
            btnOption.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }

        private static final int REQUEST_LOCATION = 1;

        private void OnGPS(View view) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new  DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    view.getContext().startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            final AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
        private boolean getLocation(View view) {
            if (ActivityCompat.checkSelfPermission(
                    view.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    view.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions( (Activity)view.getContext(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
            } else {
                Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (locationGPS != null) {
                    double lat = locationGPS.getLatitude();
                    double longi = locationGPS.getLongitude();
                    latitude = String.valueOf(lat);
                    longitude = String.valueOf(longi);
                    return true;
                } else {
                    Toast.makeText(view.getContext(), "Không thể lấy vị trí, ứng dụng chuyển sang xem vị trí quán ăn", Toast.LENGTH_SHORT).show();
                }
            }
            return false;
        }
    }

    @Override
    public RestaurantRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View cartView = inflater.inflate(R.layout.restaurant_item, parent, false);
        RestaurantRecyclerViewAdapter.ViewHolder viewHolder = new RestaurantRecyclerViewAdapter.ViewHolder(cartView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RestaurantRecyclerViewAdapter.ViewHolder holder, int position) {
        Restaurant restaurant = restaurants.get(position);
        holder.txtViewName.setText(restaurant.getName());
        holder.txtViewAddress.setText(restaurant.getAddress());
        holder.txtViewTime.setText(restaurant.getTimeStart() + " - " + restaurant.getTimeEnd());
        if (restaurantRandom != null && restaurant.getId() == restaurantRandom.getId()){
            holder.imgBackground.setBackgroundColor(Color.parseColor("#dfe4ba"));
        }else{
            holder.imgBackground.setBackgroundColor(Color.parseColor("#ffffff"));
        }

        holder.btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions( (Activity)holder.itemView.getContext(),
                        new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, ViewHolder.REQUEST_LOCATION);
                locationManager = (LocationManager) holder.itemView.getContext().getSystemService(Context.LOCATION_SERVICE);
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    holder.OnGPS(holder.itemView);
                } else {
                    boolean isHasLoc = holder.getLocation(holder.itemView);
                    if (isHasLoc)
                        goGoogleMap(holder.itemView, restaurant);
                    else
                        viewGoogleMap(holder.itemView, restaurant);
                }
//                holder.getLocation(view);
            }
        });

        holder.btnOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOptionMenu(v, restaurant);
            }
        });
    }

    public void showOptionMenu(View view, Restaurant restaurant) {
        PopupMenu popup = new PopupMenu(view.getContext(), view.findViewById(R.id.btnOption));
        popup.inflate(R.menu.menu_option);
        popup.setGravity(Gravity.RIGHT);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.edit:
                        intentNext = new Intent(view.getContext(), FunctionRestaurantActivity.class);
                        intentNext.putExtra("function", "edit");
                        intentNext.putExtra("restaurant", restaurant);
                        view.getContext().startActivity(intentNext);
                        return true;
                    case R.id.delete:
                        removeItem(restaurant);
                        RestaurantDB.getInstance(view.getContext()).delete(restaurant);
                        RestaurantActivity.restaurants = RestaurantDB.getInstance(view.getContext()).gets(user);
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

    public void goGoogleMap(View view, Restaurant restaurant) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        String uriMap = "https://www.google.com/maps/dir/" + latitude + "," + longitude + "/" + restaurant.getAddress().replace(" ", "+");
        intent.setData(Uri.parse(uriMap));
        view.getContext().startActivity(intent);
    }

    public void viewGoogleMap(View view, Restaurant restaurant){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        String uriMap = "https://www.google.com/maps/place/" + restaurant.getAddress().replace(" ", "+");
        intent.setData(Uri.parse(uriMap));
        view.getContext().startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return restaurants != null ? restaurants.size() : -1;
    }

    private void removeItem(int position) {
        restaurants.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, restaurants.size());
    }

    private void removeItem(Restaurant restaurant) {
        int position = restaurants.indexOf(restaurant);
        restaurants.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, restaurants.size());
    }

}
