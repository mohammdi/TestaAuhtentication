package com.keyob.payment.gateway.helper.reCycelerViewHandler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.keyob.payment.gateway.R;
import com.keyob.payment.gateway.dal.model.Wallet;

import java.util.List;


public class HomeWalletListReCycelerView extends RecyclerView.Adapter<HomeWalletListReCycelerView.HomeWalletviewHolder> {


    private Context context;
    private List<Wallet> walletList;
    private ONItemclickListener onItemclickListener ;

    public interface ONItemclickListener{
        void onclick(int position);
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
        holder.logo.setImageDrawable(wallet.getLogoPath());
//        byte[] data = wallet.getLogoPath().getBytes();
//        byte[] decode = Base64.decode(wallet.getLogoPath().getBytes(),0);
//        Bitmap bmp = BitmapFactory.decodeByteArray(decode, 0, decode.length);
    }

    @Override
    public int getItemCount() {
        return walletList.size();
    }

    public  class HomeWalletviewHolder extends  RecyclerView.ViewHolder {

        private TextView name_Wllet;
        private ImageView logo;

        public HomeWalletviewHolder(final View itemView) {
            super(itemView);
            name_Wllet = (TextView) itemView.findViewById(R.id.walletName );
            logo = (ImageView) itemView.findViewById(R.id.wallet_pic);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (onItemclickListener !=null){
                        int position = getAdapterPosition();

                        if (position != RecyclerView.NO_POSITION){
                            onItemclickListener.onclick(position);
                        }
                    }

                }



            });

        }
    }
}


