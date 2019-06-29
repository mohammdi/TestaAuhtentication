package com.keyob.payment.gateway.helper.reCycelerViewHandler;

import android.app.DownloadManager;
import android.content.Context;
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


public class RequestMoneyListRecyclerAdapter extends RecyclerView.Adapter<RequestMoneyListRecyclerAdapter.RequestMoneyListViewHolder> {


    private Context context;
    private List<RequestMoneyDto> requestList;
    private OnItemClickListener onItemclickListener ;

    public interface OnItemClickListener{
        void onclick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemclickListener) {
        this.onItemclickListener = onItemclickListener;
    }

    public RequestMoneyListRecyclerAdapter(Context context, List<RequestMoneyDto> requestList) {
        this.context = context;
        this.requestList = requestList;
    }

    @Override
    public RequestMoneyListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.request_list_item_view, parent, false);
        return new RequestMoneyListViewHolder(view);

    }

    @Override
    public void onBindViewHolder(RequestMoneyListViewHolder holder, int position) {

        RequestMoneyDto request = requestList.get(position);
        if (!request.getDeleted()){
            String persianDate = PersianDateCoordinatore.ConvertGregorianToPersian(request.getCreateDate());
            holder.createDate.setText(persianDate);
            holder.peyerName.setText(request.getPayerName());
            holder.PayerId.setText(request.getPayerId());
            holder.amount.setText(String.valueOf(request.getAmount()));
            Boolean status = request.getStatus();
            if (status!=null && status){
                holder.status.setImageResource(R.drawable.ic_double_check_mark);
                holder.textStatus.setText(R.string.Paid_persian);
                int color = context.getResources().getColor(R.color.bg_login);
                holder.textStatus.setTextColor(color);
            }else {
                holder.status.setImageResource(R.drawable.ic_single_check_mark);
                holder.textStatus.setText(R.string.not_paid_persian);
                int color = context.getResources().getColor(R.color.btn_logut_bg);
                holder.textStatus.setTextColor(color);
            }
        }
    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }

    public List<RequestMoneyDto> getItemList() {
        return requestList;
    }

    public void  removeItem(int position) {
        requestList.remove(position);
    }

    public void notifyListChange(List<RequestMoneyDto> requestList ){
        this.requestList = requestList;
        notifyDataSetChanged();
    }


   public RequestMoneyDto getRequest(int position){
       return requestList.get(position);
    }

    public  class RequestMoneyListViewHolder extends  RecyclerView.ViewHolder {

        private TextView amount;
        private TextView peyerName;
        private TextView PayerId;
        private TextView createDate;
        private ImageView status;
        private TextView textStatus;

        public RequestMoneyListViewHolder(final View itemView) {
            super(itemView);
            peyerName = itemView.findViewById(R.id.w_r_name_sendTo);
            amount =itemView.findViewById(R.id.w_r_amount);
            PayerId =itemView.findViewById(R.id.w_r_id_sendTo);
            createDate = itemView.findViewById(R.id.w_r_createDate);
            status = itemView.findViewById(R.id.w_r_image_status);
            textStatus = itemView.findViewById(R.id.w_r_text_status);

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


