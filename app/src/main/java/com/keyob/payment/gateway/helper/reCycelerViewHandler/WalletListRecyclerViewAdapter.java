package com.keyob.payment.gateway.helper.reCycelerViewHandler;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.keyob.payment.gateway.helper.PicassoImageDownloader;
import com.keyob.payment.gateway.helper.transform.PrettyShow;
import com.keyob.payment.gateway.model.HomeDto;
import com.keyob.payment.gateway.R;
import com.keyob.payment.gateway.model.RequestMoneyDto;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;


public class WalletListRecyclerViewAdapter extends RecyclerView.Adapter<WalletListRecyclerViewAdapter.WalletViewHolder> {


    private Context context;
    private List<HomeDto> walletList;
    private OnItemClickListener onItemClickListener;
    private HomeDto wallet;

    public interface OnItemClickListener {
        void onclick(int position);
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public WalletListRecyclerViewAdapter(Context context, List<HomeDto> walletList) {
        this.context = context;
        this.walletList = walletList;
    }

    @Override
    public WalletViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.wallet_item_view, parent, false);

        return new WalletViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final WalletViewHolder holder, final int position) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                wallet = walletList.get(position);
                Picasso.with(context).load(new File(PicassoImageDownloader.getFileName(wallet.getName()))).into(holder.logoPath);
                holder.name_Wallet.setText(wallet.getName());
                holder.public_Id.setText(PrettyShow.separatedPublicId(wallet.getPublicId()));
                holder.balance.setText(PrettyShow.separatedZero(wallet.getBalance()));

            }
        },1000);

    }

    @Override
    public int getItemCount() {
        return walletList.size();
    }

    @Override
    public long getItemId(int position) {
        return walletList.get(position).getId();
    }

    public HomeDto getWallet(int position){
        return walletList.get(position);
    }

    public void notifyListChange(List<HomeDto> list){
        this.walletList = list;
        notifyDataSetChanged();
    }

    public  class WalletViewHolder extends  RecyclerView.ViewHolder {


        private TextView name_Wallet;
        private TextView public_Id;
        private TextView balance;
        private ImageView logoPath;


        public WalletViewHolder(final View itemView) {
            super(itemView);
            name_Wallet = (TextView) itemView.findViewById(R.id.w_m_name);
            public_Id = (TextView) itemView.findViewById(R.id.w_m_publicId);
            balance = (TextView) itemView.findViewById(R.id.w_m_wallet_balance);
            logoPath= (ImageView) itemView.findViewById(R.id.w_m_logo);


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


