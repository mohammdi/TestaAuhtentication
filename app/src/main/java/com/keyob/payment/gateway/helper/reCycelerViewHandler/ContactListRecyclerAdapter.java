package com.keyob.payment.gateway.helper.reCycelerViewHandler;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.keyob.payment.gateway.R;
import com.keyob.payment.gateway.helper.PersianDateCoordinatore;
import com.keyob.payment.gateway.helper.transform.PrettyShow;
import com.keyob.payment.gateway.model.ContactDto;
import com.keyob.payment.gateway.model.RequestMoneyDto;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class ContactListRecyclerAdapter extends RecyclerView.Adapter<ContactListRecyclerAdapter.ContactListViewHolder> {


    private Context context;
    private List<ContactDto> contactList;
    private OnItemClickListener onItemclickListener ;
    private LayoutInflater inflater;

    public interface OnItemClickListener{
        void onclick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemclickListener) {
        this.onItemclickListener = onItemclickListener;
    }

    public ContactListRecyclerAdapter(Context context, List<ContactDto> contactList) {
        this.context = context;
        this.contactList = contactList;
    }

    @Override
    public ContactListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.contact_list_item_view, parent, false);
        return new ContactListViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ContactListViewHolder holder, int position) {

        ContactDto contact= contactList.get(position);
        holder.mobileNumber.setText(contact.getMobileNumber());
        holder.contactName.setText(contact.getName());

        String[] split = contact.getImage().split(",");
        String base64Image = split[1];
        byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        holder.image.setImageBitmap(decodedByte);
        if (holder.image.getDrawable()==null){
            Picasso.with(context).load(R.drawable.ic_profile).into(holder.image);
        }
        if (!contact.isRegistered()){
            holder.inviteBtn.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public List<ContactDto> getItemList() {
        return contactList;
    }


    public void  removeItem(int position) {
        contactList.remove(position);
    }

    public void notifyListChange(List<ContactDto> contactList){
        this.contactList = contactList;
        notifyDataSetChanged();
    }


   public ContactDto getRequest(int position){
       return contactList.get(position);
    }

    public  class ContactListViewHolder extends  RecyclerView.ViewHolder {


        private TextView contactName;
        private TextView mobileNumber;
        private ImageView image;
        private Button inviteBtn;

        public ContactListViewHolder(final View itemView) {
            super(itemView);

            contactName =itemView.findViewById(R.id.contact_name);
            mobileNumber = itemView.findViewById(R.id.contact_mobile_number);
            image= itemView.findViewById(R.id.contact_logo);
            inviteBtn= itemView.findViewById(R.id.contact_invite_btn);

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


