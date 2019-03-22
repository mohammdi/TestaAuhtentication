package com.example.mahmood_mohammadi.testaauhtentication.fragment;


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

import com.example.mahmood_mohammadi.testaauhtentication.ObjectModel.Users;
import com.example.mahmood_mohammadi.testaauhtentication.ObjectModel.Wallet;
import com.example.mahmood_mohammadi.testaauhtentication.R;
import com.example.mahmood_mohammadi.testaauhtentication.activities.CreateWalletActivity;
import com.example.mahmood_mohammadi.testaauhtentication.activities.WalletDetail;
import com.example.mahmood_mohammadi.testaauhtentication.helper.ApiService;
import com.example.mahmood_mohammadi.testaauhtentication.helper.CurrentUser;
import com.example.mahmood_mohammadi.testaauhtentication.helper.FackWalletInfoData;
import com.example.mahmood_mohammadi.testaauhtentication.helper.GsonUtils;
import com.example.mahmood_mohammadi.testaauhtentication.helper.MyReCycelerView;
import com.example.mahmood_mohammadi.testaauhtentication.staticRepository.PutExtraKey;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WalletManagmentFragment extends Fragment implements MyReCycelerView.ONItemclickListener {
    private View view;
    private RecyclerView recyclerView;
    private FloatingActionButton create_wallet_btn;
    private JSONObject jsonObject;
    private ApiService apiService;
    private List<Wallet> walletList;
    private Wallet wallet;
    private List<Wallet> data;
    private Users myCurrentUser;
    private Long userId;
    List<Wallet> dataList ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_wallet_managment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.wallet_Recycle_View);

        FackWalletInfoData  fackWalletInfoData =  new FackWalletInfoData();
        dataList = fackWalletInfoData.getData(this.getContext());

        /*apiService = new ApiService(this.getActivity());
        getWallets();*/

        setUprecyclerView(dataList);

        setupFloatActionbutton(view);

        return view;
    }



    public void setUprecyclerView(List<Wallet> walletList) {

        if (walletList.size() > 0) {

            MyReCycelerView recyclerAdapter = new MyReCycelerView(getActivity(), dataList);
            RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

            recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
            recyclerView.setAdapter(recyclerAdapter);
        } else {

            Toast.makeText(this.getContext(), "nothing wallet in your account", Toast.LENGTH_SHORT).show();
        }

    }


   /* public void setUprecyclerView() {

        if (walletList.size() > 0) {

            MyReCycelerView recyclerAdapter = new MyReCycelerView(getActivity(), data);
            RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

            recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
            recyclerView.setAdapter(recyclerAdapter);
        } else {

            Toast.makeText(this.getContext(), "nothing wallet in your account", Toast.LENGTH_SHORT).show();
        }

    }*/

    public void getWallets() {
        CurrentUser currentUser = new CurrentUser(this.getContext());
        try {
            myCurrentUser = currentUser.getCurrentUser();
            String id = myCurrentUser.getId();
            userId = Long.getLong(id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (myCurrentUser != null && userId > 0) {

            sendRequset(userId);

        }
    }


    public void setupFloatActionbutton(View view) {

        create_wallet_btn = (FloatingActionButton) view.findViewById(R.id.fab);
        create_wallet_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WalletManagmentFragment.this.getActivity(), CreateWalletActivity.class);
                startActivity(intent);
            }
        });
    }


    public void sendRequset(Long userId) {

        Map<String, Long> params = new HashMap<>();
        params.put("userId", userId);
        jsonObject = new JSONObject(params);

        apiService.getWalletByuserId(new ApiService.OnResponsReceive() {
            @Override
            public void recieve(String message) {
                Wallet wallet = new Wallet();
                if (message != null) {
                    walletList = GsonUtils.toList(message, Wallet.class);
                    Toast.makeText(view.getContext(), "wallet of this user ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError() {
                Toast.makeText(view.getContext(), "ERORR", Toast.LENGTH_SHORT).show();
            }
        }, jsonObject);
    }

    @Override
    public void onclik(int position) {

        Intent intent = new Intent(this.getContext(), WalletDetail.class);
        Wallet w = walletList.get(position);

        intent.putExtra(PutExtraKey.EDIT, true);
        intent.putExtra(PutExtraKey.WALLET_NAME, w.getName());
        intent.putExtra(PutExtraKey.PUBLIC_ID, w.getPublicId());
        intent.putExtra(PutExtraKey.WALLET_ID, w.getId());
        intent.putExtra(PutExtraKey.WALLET_TYPE ,w.getTypeId());
        intent.putExtra(PutExtraKey.WALLET_PASS, w.getPassPayment());
        if (wallet.getTypeId() == 2) {
            intent.putExtra(PutExtraKey.WALLET_ADDRESS, w.getWalletAddress());
        }
        startActivity(intent);
    }


}
