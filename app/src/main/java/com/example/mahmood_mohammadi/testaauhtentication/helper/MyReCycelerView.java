package com.example.mahmood_mohammadi.testaauhtentication.helper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mahmood_mohammadi.testaauhtentication.ObjectModel.Wallet;
import com.example.mahmood_mohammadi.testaauhtentication.R;

import java.util.List;


public class MyReCycelerView extends RecyclerView.Adapter<MyReCycelerView.WalletviewHolder> {


    private Context context;
    private List<Wallet> walletList;
    private ONItemclickListener onItemclickListener ;

    public interface ONItemclickListener{
        void onclik(int position);
    }


    public MyReCycelerView(Context context, List<Wallet> walletList) {
        this.context = context;
        this.walletList = walletList;
    }

    @Override
    public WalletviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.wallet_item_view, parent, false);
        return new WalletviewHolder(view);

    }

    @Override
    public void onBindViewHolder(WalletviewHolder holder, int position) {

        Wallet wallet = walletList.get(position);
        holder.name_Wllet.setText(wallet.getName());
        holder.public_Id.setText(wallet.getPublicId());
        holder.background.setBackground(wallet.getLogoPath());
        holder.delete_btn.setId(position);
        holder.edit_btn.setId(position + 12);

    }

    @Override
    public int getItemCount() {
        return walletList.size();
    }

    public  class WalletviewHolder extends  RecyclerView.ViewHolder {


        private TextView name_Wllet;
        private TextView public_Id;
        private TextView investory;
        private RelativeLayout background;
        private ImageView edit_btn;
        private ImageView delete_btn;




        public WalletviewHolder(final View itemView) {
            super(itemView);
            name_Wllet = (TextView) itemView.findViewById(R.id.name_wallet_item);
            public_Id = (TextView) itemView.findViewById(R.id.publicId_id);
            investory = (TextView) itemView.findViewById(R.id.investor_Id);
            background = (RelativeLayout) itemView.findViewById(R.id.relative_layout);
            delete_btn = (ImageView) itemView.findViewById(R.id.delete_icon);
            edit_btn = (ImageView) itemView.findViewById(R.id.edit_icon);

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


