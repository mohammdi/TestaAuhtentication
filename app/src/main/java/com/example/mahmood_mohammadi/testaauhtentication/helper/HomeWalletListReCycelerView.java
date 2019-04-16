package com.example.mahmood_mohammadi.testaauhtentication.helper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mahmood_mohammadi.testaauhtentication.dal.l.model.Wallet;
import com.example.mahmood_mohammadi.testaauhtentication.R;

import java.util.List;


public class HomeWalletListReCycelerView extends RecyclerView.Adapter<HomeWalletListReCycelerView.HomeWalletviewHolder> {


    private Context context;
    private List<Wallet> walletList;
    private ONItemclickListener onItemclickListener ;

    public interface ONItemclickListener{
        void onclik(int position);
    }


    public HomeWalletListReCycelerView(Context context, List<Wallet> walletList) {
        this.context = context;
        this.walletList = walletList;
    }

    @Override
    public HomeWalletviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.home_horizontal_wallet_item_view, parent, false);
        return new HomeWalletviewHolder(view);

    }

    @Override
    public void onBindViewHolder(HomeWalletviewHolder holder, int position) {

        Wallet wallet = walletList.get(position);
        holder.name_Wllet.setText(wallet.getName());
//        holder.public_Id.setText(wallet.getPublicId());
//        holder.background.setBackground(wallet.getLogoPath());


    }

    @Override
    public int getItemCount() {
        return walletList.size();
    }

    public  class HomeWalletviewHolder extends  RecyclerView.ViewHolder {


        private TextView name_Wllet;
        private TextView public_Id;
        private RelativeLayout background;




        public HomeWalletviewHolder(final View itemView) {
            super(itemView);
            name_Wllet = (TextView) itemView.findViewById(R.id.name_wallet_item);
            public_Id = (TextView) itemView.findViewById(R.id.publicId_id);
            background = (RelativeLayout) itemView.findViewById(R.id.home_relative_layout_item);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (onItemclickListener !=null){
                        int position = getAdapterPosition();

                        if (position != RecyclerView.NO_POSITION){
                            onItemclickListener.onclik(position);
                        }
                    }

                }



            });

        }
    }
}


