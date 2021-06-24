package com.example.fallinlove.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.recyclerview.widget.RecyclerView;

import com.example.fallinlove.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

public class ResponsibilityRecyclerViewAdapter extends RecyclerView.Adapter<ResponsibilityRecyclerViewAdapter.ViewHolder>{

    List<String> responsibilities;

    BottomSheetDialog bottomSheetDialog;
    View bottomSheetView;
    String[] typeResponsibility, level;
    Spinner spnTypeResponsibilities, spnLevels;

    public ResponsibilityRecyclerViewAdapter(List<String> responsibilities){

        this.responsibilities = responsibilities;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View cartView = inflater.inflate(R.layout.responsibility_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(cartView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(v, "edit");
            }
        });
    }

    public void showDialog(View view, String type){
        typeResponsibility = new String[]{"Hàng ngày", "Trách nhiệm"};
        level = new String[]{"Bình thường", "Quan trọng", "Rất quan trọng"};

        bottomSheetDialog = new BottomSheetDialog(view.getContext(), R.style.BottomSheetDialogTheme);
        bottomSheetView = LayoutInflater.from(view.getContext())
                .inflate(R.layout.bottom_sheet_responsibility, (LinearLayout)view.findViewById(R.id.btnSheetContainer));
        spnTypeResponsibilities = bottomSheetView.findViewById(R.id.spnTypeResponsibility);
        spnLevels = bottomSheetView.findViewById(R.id.spnLevel);
        setSpinner(bottomSheetView.getRootView(), typeResponsibility, level);

        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
        switch (type){
            case "add":
                bottomSheetDialog.findViewById(R.id.btnSave).setVisibility(View.VISIBLE);
                bottomSheetDialog.findViewById(R.id.btnEdit).setVisibility(View.GONE);
                bottomSheetDialog.findViewById(R.id.btnDelete).setVisibility(View.GONE);
                break;
            default:
                bottomSheetDialog.findViewById(R.id.btnSave).setVisibility(View.GONE);
                bottomSheetDialog.findViewById(R.id.btnEdit).setVisibility(View.VISIBLE);
                bottomSheetDialog.findViewById(R.id.btnDelete).setVisibility(View.VISIBLE);
                break;
        }
    }


    public void setSpinner(View view, String[] typeResponsibilities, String[] levels){
        StringSpinnerAdapter stringSpinnerAdapterType = new StringSpinnerAdapter(view.getContext(), typeResponsibilities);
        spnTypeResponsibilities.setAdapter(stringSpinnerAdapterType);
        spnTypeResponsibilities.setSelection(0);

        StringSpinnerAdapter stringSpinnerAdapterLevel = new StringSpinnerAdapter(view.getContext(), levels);
        spnLevels.setAdapter(stringSpinnerAdapterLevel);
        spnLevels.setSelection(0);
    }

    @Override
    public int getItemCount() {
        return responsibilities.size();
    }

    private void removeItem(int position) {
        responsibilities.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, responsibilities.size());
    }
}
