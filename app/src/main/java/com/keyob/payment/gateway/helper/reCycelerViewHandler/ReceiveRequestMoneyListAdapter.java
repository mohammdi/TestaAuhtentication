package com.keyob.payment.gateway.helper.reCycelerViewHandler;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.keyob.payment.gateway.R;
import com.keyob.payment.gateway.helper.PersianDateCoordinatore;
import com.keyob.payment.gateway.model.RequestMoneyDto;

import java.util.List;


public class ReceiveRequestMoneyListAdapter extends RecyclerView.Adapter<ReceiveRequestMoneyListAdapter.ResponseMoneyListViewHolder > {


    private Context context;
    private List<RequestMoneyDto> responseList;
    private OnItemClickListener onItemclickListener ;

    public interface OnItemClickListener {
        void onclick(int position);
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemclickListener = onItemClickListener;
    }


    public ReceiveRequestMoneyListAdapter(Context context, List<RequestMoneyDto> responseList) {
        this.context = context;
        this.responseList = responseList;
    }

    @Override
    public ResponseMoneyListViewHolder  onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.response_list_item_view, parent, false);
        return new ResponseMoneyListViewHolder (view);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(ResponseMoneyListViewHolder  holder, int position) {

        RequestMoneyDto request = responseList.get(position);
        if (!request.getDeletedByPayer()){
            String persianDate = PersianDateCoordinatore.ConvertGregorianToPersian(request.getCreateDate());
            holder.createDate.setText(persianDate);
            holder.senderId.setText(request.getSenderId());
            holder.amount.setText(String.valueOf(request.getAmount()));
            holder.senderName.setText(request.getSenderName());
            Boolean status = request.getStatus();
            if (status!=null && status){
                holder.status.setImageResource(R.drawable.ic_double_check_mark);
                holder.textStatus.setText(R.string.Paid_persian);
                int color = context.getResources().getColor(R.color.bg_login);
                holder.textStatus.setTextColor(color);
            }else {
                holder.status.setImageResource(R.drawable.ic_single_check_mark);
                holder.textStatus.setText(R.string.not_Paid_persian);
                int color = context.getResources().getColor(R.color.btn_logut_bg);
                holder.textStatus.setTextColor(color);
            }
        }
    }

    @Override
    public int getItemCount() {
        return responseList.size();
    }



    public List<RequestMoneyDto> getItemList() {
        return responseList;
    }

    public void  removeItem(int position) {
         responseList.remove(position);
    }

    public void notifyListChange(List<RequestMoneyDto> requestList ){
        this.responseList = requestList;
        notifyDataSetChanged();
    }

   public RequestMoneyDto getRequest(int position){
        return responseList.get(position);
    }

    public  class ResponseMoneyListViewHolder extends  RecyclerView.ViewHolder {

        private TextView amount;
        private TextView senderName;
        private TextView senderId;
        private TextView createDate;
        private ImageView status;
        private TextView textStatus;

        public ResponseMoneyListViewHolder (final View itemView) {
            super(itemView);
            senderName = itemView.findViewById(R.id.response_name_receive_from);
            amount =itemView.findViewById(R.id.response_amount);
            senderId =itemView.findViewById(R.id.response_sender_id);
            createDate = itemView.findViewById(R.id.response_createDate);
            status = itemView.findViewById(R.id.response_image_status);
            textStatus = itemView.findViewById(R.id.response_text_status);

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


