package com.example.fallinlove.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.fallinlove.R;

public class StringSpinnerAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    String[] contents;

    public StringSpinnerAdapter (Context applicationContext, String[] contents) {

        this.context = applicationContext;
        this.contents = contents;
        inflater = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return contents.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.spn_item_string, null);
        TextView content = (TextView) view.findViewById(R.id.txtContent);
        content.setText(contents[i]);
        return view;
    }
}

