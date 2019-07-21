package com.keyob.payment.gateway.activities;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.keyob.payment.gateway.R;
import com.keyob.payment.gateway.helper.ImageUtils;
import com.keyob.payment.gateway.helper.PicassoImageDownloader;
import com.keyob.payment.gateway.helper.SingletonWalletInfo;
import com.keyob.payment.gateway.helper.URLAttacher;
import com.keyob.payment.gateway.helper.reCycelerViewHandler.PassBookListRecyclerAdapter;
import com.keyob.payment.gateway.helper.transform.PrettyShow;
import com.keyob.payment.gateway.model.HomeDto;
import com.keyob.payment.gateway.model.PassBookResponseDto;
import com.keyob.payment.gateway.network.ApiClient;
import com.keyob.payment.gateway.network.MyURLRepository;
import com.keyob.payment.gateway.network.RetrofitApiService;
import com.keyob.payment.gateway.network.UploadImageResponse;
import com.keyob.payment.gateway.viewModel.WalletViewModelNetWork;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.keyob.payment.gateway.staticRepository.PutExtraKey.MESSAGE;
import static com.keyob.payment.gateway.staticRepository.PutExtraKey.TRANSIT;
import static com.keyob.payment.gateway.staticRepository.PutExtraKey.WALLET;
import static com.keyob.payment.gateway.staticRepository.PutExtraKey.WALLET_LIST;

public class WalletDetailActivity extends AppCompatActivity {

    private TextView edit;
    private TextView delete;
    private TextView name;
    private TextView publicId;
    private TextView balance;
    private TextView link;
    private TextView type;
    private ImageView logo;
    private Toolbar toolbar;
    private WalletViewModelNetWork viewModel;
    private RecyclerView recyclerView;
    private CoordinatorLayout rootView;
    private RetrofitApiService apiService;
    private Switch defaultWallet;
    private static int SELECT_PICTURE = 1;
    private static final int PERMISSION_READ_IMAGE =80;
    private Intent pik_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_detail);

        name = findViewById(R.id.w_d_name);
        publicId = findViewById(R.id.w_d_publicId);
        balance = findViewById(R.id.w_d_balance);
        link = findViewById(R.id.w_d_link);
        type = findViewById(R.id.w_d_type);
        logo = findViewById(R.id.w_d_logo);
        edit = findViewById(R.id.w_d_edit);
        delete = findViewById(R.id.w_d_delete);
        recyclerView = findViewById(R.id.w_d_passbook);
        rootView = findViewById(R.id.w_d_rootView);
        defaultWallet = findViewById(R.id.w_d_select_def);

//        Toolbar toolbar = findViewById(R.id.w_d_toolbar);
//        toolbar.setTitle("صفحه اصلی ");
//        setSupportActionBar(toolbar);


        Intent intent = getIntent();
        final HomeDto wallet = (HomeDto) intent.getSerializableExtra(WALLET);

        publicId.setText(PrettyShow.separatedPublicId(wallet.getPublicId()));
        name.setText(wallet.getName());
        link.setText(URLAttacher.doAttach(wallet.getBaseLink(), wallet.getWalletToken(), null));
        balance.setText(PrettyShow.separatedZero(wallet.getBalance()));
        Picasso.with(getApplicationContext()).load(new File(PicassoImageDownloader.getFileName(wallet.getPublicId()))).into(logo);
        viewModel = ViewModelProviders.of(this).get(WalletViewModelNetWork.class);
        int wType = wallet.getType();
        if (wType == 1) {

            this.type.setText("تجاری");
        } else {

            this.type.setText("شخصی");
        }


        defaultWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingletonWalletInfo rootWallet = SingletonWalletInfo.getInstance();
                rootWallet.replace(wallet);
            }
        });


        viewModel.getRecentPassBook(wallet.getId()).observe(this, new Observer<List<PassBookResponseDto>>() {
            @Override
            public void onChanged(@Nullable List<PassBookResponseDto> passBookResponseList) {
                if (passBookResponseList != null) {
                    setUpRecyclerView(passBookResponseList);
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String message = "";
                if (wallet.getBalance() > 0) {
                    message = "بگسک شما دارای اعتبار میباشد!!!";
                } else {
                    message = "ایا مطمئن هستید ؟";
                }

                Snackbar
                        .make(rootView, message,
                                Snackbar.LENGTH_SHORT)
                        .setAction("بله", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                RetrofitApiService apiService = ApiClient.getInstance().create(RetrofitApiService.class);
                                Call<Void> call = apiService.deleteWallet(wallet.getId());
                                call.enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {

                                        String message = "";
                                        if (response.isSuccessful()) {

                                            message = "بگسک شما با موفقیت حذف شد ";
                                        } else {
                                            message = " خطا در سرور";
                                        }
                                        Intent i = new Intent(WalletDetailActivity.this, HomeActivity.class);
                                        i.putExtra(TRANSIT, WALLET_LIST);
                                        i.putExtra(MESSAGE, message);
                                        startActivity(i);
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {

                                    }
                                });
                            }
                        }).show();
            }
        });

        if (wallet.getId().toString().equals(SingletonWalletInfo.getInstance().getId().toString())) {
            defaultWallet.setChecked(true);
        }


        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (ActivityCompat.checkSelfPermission(WalletDetailActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                 && ActivityCompat.checkSelfPermission(WalletDetailActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED) {
                    pik_image = new Intent();
                    pik_image.setType("image/*");
                    pik_image.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(pik_image,
                            "Select Picture"), SELECT_PICTURE);

                }else {

                    ActivityCompat.requestPermissions(WalletDetailActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},PERMISSION_READ_IMAGE );
//                    ActivityCompat.requestPermissions(WalletDetailActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSION_READ_IMAGE );

                }




            }
        });

    }

    public void setUpRecyclerView(List<PassBookResponseDto> dataList) {
        PassBookListRecyclerAdapter recyclerAdapter = new PassBookListRecyclerAdapter(this, dataList);
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(recyclerAdapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_PICTURE && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));

//                File file = new File(uri.getPath());

                String realPathFromURI = getPath(getApplicationContext(),uri);
                File file = new File(realPathFromURI);
                boolean mkdirs = file.mkdirs();
                file.createNewFile();

                String convert = ImageUtils.convert(bitmap);

                uploadImage(convert,file,SingletonWalletInfo.getInstance().getId());
                logo.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImage(String base64,File part ,Long walletId) {
//        File file = new File(PicassoImageDownloader.getFileName(SingletonWalletInfo.getInstance().getPublicId()));
//        RequestBody fileReqBody = RequestBody.create(MediaType.parse("image/*"), file);
//        MultipartBody.Part part = MultipartBody.Part.createFormData("upload", file.getName(), fileReqBody);
////                RequestBody description = RequestBody.create(MediaType.parse("text/plain"), "image-type");

        File myfile = new File(part, String.valueOf(walletId));
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), myfile);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", myfile.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), myfile.getName());
        apiService = ApiClient.getInstance().create(RetrofitApiService.class);
        apiService.uploadImage(fileToUpload,walletId).enqueue(new Callback<UploadImageResponse>() {
            @Override
            public void onResponse(Call<UploadImageResponse> call, Response<UploadImageResponse> response) {
                if (response.isSuccessful()) {
                    String uri = URLAttacher.doAttach(MyURLRepository.GET_LOGO_BY_WALLET_ID, String.valueOf(SingletonWalletInfo.getInstance().getId()), null);
                    Picasso.with(getApplicationContext()).load(uri).into(logo);
                } else {
                    Snackbar.make(rootView, "خطا در شبکه !!", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UploadImageResponse> call, Throwable t) {
                Snackbar.make(rootView, "خطا در شبکه !!", Snackbar.LENGTH_LONG).show();
            }
        });
    }


    public static String getPath(final Context context, final Uri uri) {


        // DocumentProvider
        if (DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
//            if (isExternalStorageDocument(uri)) {
//                final String docId = DocumentsContract.getDocumentId(uri);
//                final String[] split = docId.split(":");
//                final String type = split[0];
//
//                if ("primary".equalsIgnoreCase(type)) {
//                    return Environment.getExternalStorageDirectory() + "/" + split[1];
//                }
//
//                // TODO handle non-primary volumes
//            }
//            // DownloadsProvider
//            else if (isDownloadsDocument(uri)) {
//
//                final String id = DocumentsContract.getDocumentId(uri);
//                final Uri contentUri = ContentUris.withAppendedId(
//                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
//
//                return getDataColumn(context, contentUri, null, null);
//            }
            // MediaProvider
//            else
//                if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)){
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);

        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode ==PERMISSION_READ_IMAGE){

            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent,
                    "Select Picture"), SELECT_PICTURE);

            }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
