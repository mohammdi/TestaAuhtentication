package com.keyob.payment.gateway.helper.reCycelerViewHandler;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.keyob.payment.gateway.helper.PicassoImageDownloader;
import com.keyob.payment.gateway.model.HomeDto;
import com.keyob.payment.gateway.R;
import com.keyob.payment.gateway.network.MyURLRepository;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;


public class WalletListRecyclerViewAdapter extends RecyclerView.Adapter<WalletListRecyclerViewAdapter.WalletviewHolder> {


    private Context context;
    private List<HomeDto> walletList;
    private OnItemClickListener onItemClickListener;
    private HomeDto wallet;

    public interface OnItemClickListener {
        void onclick(int position);
    }


    public WalletListRecyclerViewAdapter(Context context, List<HomeDto> walletList) {
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

        wallet = walletList.get(position);
        holder.name_Wllet.setText(wallet.getName());
        holder.public_Id.setText(wallet.getPublicId());
        holder.balance.setText(String.valueOf(wallet.getBalance()));
        File imageFile = new File(PicassoImageDownloader.getFileName(wallet.getPublicId()));
        Picasso.with(context).load(imageFile).into(holder.logoPath);
    }

    @Override
    public int getItemCount() {
        return walletList.size();
    }

    public  class WalletviewHolder extends  RecyclerView.ViewHolder {


        private TextView name_Wllet;
        private TextView public_Id;
        private TextView balance;
        private RelativeLayout background;
        private ImageView edit_btn;
        private ImageView logoPath;
        private ImageView delete_btn;




        public WalletviewHolder(final View itemView) {
            super(itemView);
            name_Wllet = (TextView) itemView.findViewById(R.id.w_m_wallet_name);
            public_Id = (TextView) itemView.findViewById(R.id.w_m_publicId_id);
            balance = (TextView) itemView.findViewById(R.id.w_m_wallet_balance);
            background = (RelativeLayout) itemView.findViewById(R.id.relative_layout);
            logoPath= (ImageView) itemView.findViewById(R.id.w_m_wallet_logo);


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

//        public void setImage(Long id) {
//            Picasso.with(itemView.getContext()).load(PicassoImageDownloader.getImageUrl(wallet.getId())).into(logoPath);
//        }
    }
}


