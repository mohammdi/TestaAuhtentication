package com.keyob.payment.gateway.helper.reCycelerViewHandler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.keyob.payment.gateway.R;
import com.keyob.payment.gateway.helper.PersianDateCoordinatore;
import com.keyob.payment.gateway.model.TagDto;

import java.util.List;


public class TagListRecyclerAdapter extends RecyclerView.Adapter<TagListRecyclerAdapter.TagViewHolder> {


    private Context context;
    private List<TagDto> data;
    private OnItemClickListener onItemClickListener;
    private TagDto tagModel;

    public interface OnItemClickListener {
        void onclick(int position);
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public TagListRecyclerAdapter(Context context, List<TagDto> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public TagViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.tag_item_view, parent, false);
        return new TagViewHolder(view);

    }

    @Override
    public void onBindViewHolder(TagViewHolder holder, int position) {
        tagModel = data.get(position);
        String persianDate = PersianDateCoordinatore.ConvertGregorianToPersian(tagModel.getCreateDate());
        String time = PersianDateCoordinatore.getTime(tagModel.getCreateDate());
        holder.time.setText(time);
        holder.type.setText(tagModel.getSubject());
        holder.date.setText(persianDate);
        if (tagModel.isPriceByPayer()){
            holder.amount.setText("مبلغ توسط کاربرانتخاب میشود");
            holder.amount.setTextSize(10);
            holder.amountLabel.setText("");
        }else {
            holder.amount.setText(String.valueOf(tagModel.getPrice()));
        }

        if (tagModel.getActive()) {

            holder.status.setText("فعال");
            holder.status.setTextColor(context.getResources().getColor(R.color.green));
        } else {
            holder.status.setText("غیر فعال");
            holder.status.setTextColor(context.getResources().getColor(R.color.red));
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class TagViewHolder extends RecyclerView.ViewHolder {


        private TextView type;
        private TextView amount;
        private TextView date;
        private TextView time;
        private TextView status;
        private TextView amountLabel;


        public TagViewHolder(final View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.tag_title);
            amount = itemView.findViewById(R.id.tag_amount);
            amountLabel = itemView.findViewById(R.id.tag_amount_label);
            date = itemView.findViewById(R.id.tag_date);
            time = itemView.findViewById(R.id.tag_time);
            status = itemView.findViewById(R.id.tag_status);


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


