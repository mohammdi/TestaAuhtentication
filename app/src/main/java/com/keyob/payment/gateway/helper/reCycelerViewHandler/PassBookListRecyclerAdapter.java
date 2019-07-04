package com.keyob.payment.gateway.helper.reCycelerViewHandler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.keyob.payment.gateway.R;
import com.keyob.payment.gateway.helper.PersianDateCoordinatore;
import com.keyob.payment.gateway.helper.enums.EntryType;
import com.keyob.payment.gateway.helper.transform.PrettyShow;
import com.keyob.payment.gateway.model.PassBookResponseDto;

import java.util.List;


public class PassBookListRecyclerAdapter extends RecyclerView.Adapter<PassBookListRecyclerAdapter.passBookViewHolder> {


    private Context context;
    private List<PassBookResponseDto> data;
    private OnItemClickListener onItemClickListener;
    private PassBookResponseDto passbook;

    public interface OnItemClickListener {
        void onclick(int position);
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public PassBookListRecyclerAdapter(Context context, List<PassBookResponseDto> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public passBookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.pass_book_item_view, parent, false);
        return new passBookViewHolder(view);

    }

    @Override
    public void onBindViewHolder(passBookViewHolder holder, int position) {
        passbook = data.get(position);
        holder.amount.setText(PrettyShow.separatedZero(passbook.getAmount()));
        holder.publicId.setText(PrettyShow.separatedPublicId(passbook.getParticipant()));
        if (passbook.getType()== 1) {
                holder.type.setText("ارسال به");
                holder.iconStatus.setImageResource(R.drawable.ic_send_money);
            }
            if (passbook.getType()== 0){

                holder.type.setText("دریافت از");
                holder.iconStatus.setImageResource(R.drawable.ic_receive_money);
            }

        String persianDate = PersianDateCoordinatore.ConvertGregorianToPersian(passbook.getTransactionDate());
        String time = PersianDateCoordinatore.getTime(passbook.getTransactionDate());
        holder.date.setText(persianDate);
        holder.time.setText(time);
        if (passbook.getStatus()){

            holder.status.setText("موفق");
            holder.status.setTextColor(context.getResources().getColor(R.color.green));
        }else {

            holder.status.setText("ناموفق");
            holder.status.setTextColor(context.getResources().getColor(R.color.red));
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class passBookViewHolder extends RecyclerView.ViewHolder {


        private TextView type;
        private TextView amount;
        private TextView date;
        private TextView time;
        private TextView status;
        private ImageView iconStatus;
        private TextView publicId;


        public passBookViewHolder(final View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.pk_type);
            amount = itemView.findViewById(R.id.pk_amount);
            date = itemView.findViewById(R.id.pk_date);
            time = itemView.findViewById(R.id.pk_time);
            status = itemView.findViewById(R.id.pk_status);
            iconStatus = itemView.findViewById(R.id.pk_icon_status);
            publicId =itemView.findViewById(R.id.pk_publicId);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (onItemClickListener != null) {
                        int position = getAdapterPosition();

                        if (position != RecyclerView.NO_POSITION) {
                            onItemClickListener.onclick(position);
                        }
                    }

                }


            });

        }
    }
}


