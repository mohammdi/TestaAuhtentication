package com.keyob.payment.gateway.helper.reCycelerViewHandler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.keyob.payment.gateway.dal.model.Wallet;
import com.keyob.payment.gateway.R;

import java.util.List;


public class WalletListRecyclerViewAdapter extends RecyclerView.Adapter<WalletListRecyclerViewAdapter.WalletviewHolder> {


    private Context context;
    private List<Wallet> walletList;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onclick(int position);
    }


    public WalletListRecyclerViewAdapter(Context context, List<Wallet> walletList) {
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
        holder.logoPath.setBackground(wallet.getLogoPath());
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
        private ImageView logoPath;
        private ImageView delete_btn;




        public WalletviewHolder(final View itemView) {
            super(itemView);
            name_Wllet = (TextView) itemView.findViewById(R.id.w_m_wallet_name);
            public_Id = (TextView) itemView.findViewById(R.id.w_m_publicId_id);
            investory = (TextView) itemView.findViewById(R.id.w_m_wallet_balance);
            background = (RelativeLayout) itemView.findViewById(R.id.relative_layout);
            logoPath= (ImageView) itemView.findViewById(R.id.w_m_wallet_logo);
            delete_btn = (ImageView) itemView.findViewById(R.id.delete_icon);
            edit_btn = (ImageView) itemView.findViewById(R.id.edit_icon);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (onItemClickListener !=null){
                        int position = getAdapterPosition();

                        if (position != RecyclerView.NO_POSITION){
                            onItemClickListener.onclick(position);
                        }
                    }

                }



            });

        }
    }
}


