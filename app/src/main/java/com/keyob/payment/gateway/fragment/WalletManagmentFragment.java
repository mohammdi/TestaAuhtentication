package com.keyob.payment.gateway.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.keyob.payment.gateway.R;
import com.keyob.payment.gateway.activities.CreateWalletActivity;
import com.keyob.payment.gateway.activities.WalletDetail;
import com.keyob.payment.gateway.dal.model.Wallet;
import com.keyob.payment.gateway.helper.DataBase.FackWalletInfoData;
import com.keyob.payment.gateway.helper.reCycelerViewHandler.WalletListRecyclerViewAdapter;
import com.keyob.payment.gateway.staticRepository.PutExtraKey;

import java.util.List;

public class WalletManagmentFragment extends Fragment implements WalletListRecyclerViewAdapter.OnItemClickListener {
    private View view;
    private RecyclerView recyclerView;
    private FloatingActionButton create_wallet_btn;
    private List<Wallet> dataList ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_wallet_managment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.wallet_Recycle_View);

//        WalletDao walletDao = new WalletDao(getContext());
//        List<HashMap<String, String>> walletHashMapList = walletDao.getWalletList();
//        WalletConvertor convertor = new WalletConvertor();
//        dataList = convertor.walletHashMapToList(walletHashMapList);

        FackWalletInfoData fackData = new FackWalletInfoData();
        dataList = fackData.getData(getContext());
        setUpRecyclerView(dataList);
        setupFloatActionButton(view);

        return view;
    }
    public void setUpRecyclerView(List<Wallet> walletList) {

        if (walletList.size() > 0) {
            WalletListRecyclerViewAdapter recyclerAdapter = new WalletListRecyclerViewAdapter(getActivity(), walletList);
            RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

            recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
            recyclerView.setAdapter(recyclerAdapter);
        } else {

            Toast.makeText(this.getContext(), "nothing wallet in your account", Toast.LENGTH_SHORT).show();
        }

    }
    public void setupFloatActionButton(View view) {
        create_wallet_btn = (FloatingActionButton) view.findViewById(R.id.fab);
        create_wallet_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WalletManagmentFragment.this.getActivity(), CreateWalletActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onclick(int position) {

        Intent intent = new Intent(this.getContext(), WalletDetail.class);
        Wallet w = dataList.get(position);

        intent.putExtra(PutExtraKey.EDIT, true);
        intent.putExtra(PutExtraKey.WALLET_NAME, w.getName());
        intent.putExtra(PutExtraKey.PUBLIC_ID, w.getPublicId());
        intent.putExtra(PutExtraKey.WALLET_ID, w.getId());
        intent.putExtra(PutExtraKey.WALLET_TYPE ,w.getWalletType().getId());
        intent.putExtra(PutExtraKey.WALLET_PASS, w.getPassPayment());
        if (w.getWalletType().getId() == 2) {
            intent.putExtra(PutExtraKey.WALLET_ADDRESS, w.getWalletAddress());
        }
        startActivity(intent);
    }


}
