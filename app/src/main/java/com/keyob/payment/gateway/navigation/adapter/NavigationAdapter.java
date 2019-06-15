package com.keyob.payment.gateway.navigation.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.keyob.payment.gateway.R;
import com.keyob.payment.gateway.model.Wallet;
import com.keyob.payment.gateway.navigation.model.NavigationData;

import java.util.ArrayList;
import java.util.List;

public class NavigationAdapter extends RecyclerView.Adapter<NavigationAdapter.ViewHolder> {

    private List<NavigationData> navigationDatas;
    private INavigation listener;
    private Context context;

    public NavigationAdapter( Context context ,INavigation listener) {
        navigationDatas = new ArrayList<>();
        this.listener = listener;
        this.context= context;
    }



    @Override
    public NavigationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_horizontal_wallet_item_view, parent, false);

        return new NavigationAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NavigationAdapter.ViewHolder holder, int position) {
        NavigationData navigationData = navigationDatas.get(position);
        if (holder.textView != null) {
            holder.textView.setText(navigationData.getName());
        }
        if (holder.imageView!= null) {
            holder.imageView.setImageResource(navigationData.getDrawableId());
            holder.imageView.setTag(position);
        }

        holder.itemView.setTag(position);
//        holder.itemView.setBackgroundResource(navigationData.isSelected() ? R.color.ripple_color : android.R.color.transparent);
    }

    @Override
    public int getItemCount() {
        return navigationDatas.size();
    }

    public void refreshAdapter(ArrayList<NavigationData> data) {
        navigationDatas.clear();
        navigationDatas.addAll(data);
        notifyDataSetChanged();
    }

    public void setSelected(int position)
    {
        for (int i = 0; i < navigationDatas.size(); i++) {
            navigationDatas.get(i).setSelected(i == position);
        }

        notifyDataSetChanged();
    }



    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
         TextView textView ;
         ImageView imageView;
        private ViewHolder(View itemView) {
            super(itemView);
            textView =itemView.findViewById(R.id.walletName);
            imageView= itemView.findViewById(R.id.wallet_pic);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view){
            listener.onViewClick(Integer.parseInt(view.getTag().toString()));
        }
    }
}
