package com.example.mahmood_mohammadi.testaauhtentication.helper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mahmood_mohammadi.testaauhtentication.dal.l.model.WalletType;
import com.example.mahmood_mohammadi.testaauhtentication.R;

import java.util.List;

/**
 * Created by Mahmood_mohammadi on 3/31/2018.
 */

public class SpinnerAdapter extends ArrayAdapter<String> {

    private Context context;
    private List<WalletType> walletTypeList;


    public SpinnerAdapter(@NonNull Context context, List<WalletType> walletTypes, String[] array) {
        super(context, R.layout.spinner_items_layout, array);
        this.context = context;
        this.walletTypeList = walletTypes;

    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.spinner_items_layout, parent, false);
        TextView typeName = (TextView) view.findViewById(R.id.layout);

        if (walletTypeList.size() >= 1) {
            WalletType wt = walletTypeList.get(position);
            typeName.setText(wt.getName());
        }


        return view;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.spinner_items_layout, parent, false);
        TextView typeName = (TextView) view.findViewById(R.id.layout);

        if (walletTypeList.size() >= 1) {
            WalletType wt = walletTypeList.get(position);
            typeName.setText(wt.getName());
        }

        return view;
    }
}
