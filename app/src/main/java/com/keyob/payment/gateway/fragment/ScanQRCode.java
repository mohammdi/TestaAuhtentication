package com.keyob.payment.gateway.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.keyob.payment.gateway.R;
import com.keyob.payment.gateway.activities.ReqShowWalletInfoActivity;
import com.keyob.payment.gateway.activities.RequestMoneyActivity;
import com.keyob.payment.gateway.activities.WalletInfoActivity;
import com.keyob.payment.gateway.activities.hiddenActivity.CaptureActivityAnyOrientation;
import com.keyob.payment.gateway.activities.hiddenActivity.PayProxyActivity;
import com.keyob.payment.gateway.model.HomeDto;
import com.keyob.payment.gateway.model.QrCodeScanResponseDto;
import com.keyob.payment.gateway.network.ApiClient;
import com.keyob.payment.gateway.network.RetrofitApiService;
import com.keyob.payment.gateway.viewModel.WalletViewModelNetWork;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.keyob.payment.gateway.staticRepository.PutExtraKey.AMOUNT;
import static com.keyob.payment.gateway.staticRepository.PutExtraKey.DESCRIPTION;
import static com.keyob.payment.gateway.staticRepository.PutExtraKey.PAYER_WALLET_ID;
import static com.keyob.payment.gateway.staticRepository.PutExtraKey.PUBLIC_ID;
import static com.keyob.payment.gateway.staticRepository.PutExtraKey.WALLET;
import static com.keyob.payment.gateway.staticRepository.PutExtraKey.WALLET_NAME;

public class ScanQRCode extends Fragment {

    private View view;
    private Button scanBtn;
    private EditText phoneNumber;
    private ImageButton phoneNumberBtn;
    private String scanContent;
    private String scanFormat;
    private RetrofitApiService apiService;
    private QrCodeScanResponseDto qr;
    private Intent payProxy;
    private WalletViewModelNetWork viewModel;

//    private CameraSource cameraSource;
//    private BarcodeDetector barcodeDetector;
//    private final int request_camera_permission_id = 1001;
//    private SurfaceView camera_preview;

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        switch (requestCode) {
//
//            case request_camera_permission_id: {
//                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                    if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//
//                        return;
//                    }
//                    try {
//                        cameraSource.start(camera_preview.getHolder());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//            }
//            break;
//        }
//    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_scan_qrcode, container, false);
        scanBtn = view.findViewById(R.id.qr_scan_btn);
        phoneNumber = view.findViewById(R.id.qr_scan_phoneNumber);
        phoneNumberBtn = view.findViewById(R.id.qr_scan_phoneNumber_btn);

        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                IntentIntegrator scanIntegrator = IntentIntegrator.forSupportFragment(ScanQRCode.this);
                scanIntegrator.setPrompt("Scan");
                scanIntegrator.setBeepEnabled(true);

                //enable the following line if you want QR code
                //scanIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);

                scanIntegrator.setCaptureActivity(CaptureActivityAnyOrientation.class);
                scanIntegrator.setOrientationLocked(true);
                scanIntegrator.setBarcodeImageEnabled(true);
                scanIntegrator.initiateScan();
            }
        });

        phoneNumberBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (phoneNumber.getText()==null){
                    Toast.makeText(getContext(), "لطفا شماره تلفن را وارد نمایید", Toast.LENGTH_LONG).show();
                } else {
                    // TODO
                }
            }
        });

        phoneNumberBtnListener();

//        camera_preview = (SurfaceView) view.findViewById(R.id.camera_scanner_view);
//        barcodeDetector = new BarcodeDetector.Builder(getActivity())
//                .setBarcodeFormats(Barcode.QR_CODE)
//                .build();
//
//        cameraSource = new CameraSource
//                .Builder(getActivity(), barcodeDetector)
//                .setRequestedPreviewSize(640, 400)
//                .build();
//
//
//        camera_preview.getHolder().addCallback(new SurfaceHolder.Callback() {
//            @Override
//            public void surfaceCreated(SurfaceHolder holder) {
//
//                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//                    //  request Permission
//                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, request_camera_permission_id);
//                    return;
//                }
//                try {
//                    cameraSource.start(camera_preview.getHolder());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
//
//            }
//
//            @Override
//            public void surfaceDestroyed(SurfaceHolder holder) {
//                cameraSource.stop();
//            }
//        });
//
//
//        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
//            @Override
//            public void release() {
//            }
//
//            @Override
//            public void receiveDetections(Detector.Detections<Barcode> detections) {
//                final SparseArray<Barcode> qrcode = detections.getDetectedItems();
//
//                if (qrcode.size() != 0) {
//                    txtResult.post(new Runnable() {
//                        @Override
//                        public void run() {
//                                Vibrator vibrator = (Vibrator) getContext().getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
//                                vibrator.vibrate(500);
//                                txtResult.setText(qrcode.valueAt(0).displayValue);
//                                qToken= qrcode.valueAt(0).displayValue;
//                                getActivity().runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        Intent intent = new Intent(view.getContext(), PayProxyActivity.class);
//                                        intent.putExtra("QToken",qToken);
//                                        startActivity(intent);
//                                    }
//                                });
//
//
//                        }
//                    });
//                }
//
//            }
//        });
        return view;
    }

    private void phoneNumberBtnListener() {
        phoneNumberBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = phoneNumber.getText().toString();
                if (phoneNumber!=null){
                    viewModel = ViewModelProviders.of(ScanQRCode.this).get(WalletViewModelNetWork.class);
                    viewModel.findWalletByPhoneNumber(number).observe(ScanQRCode.this, new Observer<HomeDto>() {
                        @Override
                        public void onChanged(@Nullable HomeDto homeDto) {
                            if (homeDto!=null){
                                Intent intent = new Intent(getActivity(), WalletInfoActivity.class);
                                intent.putExtra(WALLET,homeDto);
                                startActivity(intent);
                            }else {
                                Toast.makeText(getContext(), "شماره مورد نظر صحیح نمیباشد", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else {
                    Toast.makeText(getContext(), "ابتدا شماره تلفون را وارد کنید!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (scanningResult != null) {
            if (scanningResult.getContents() != null) {
                scanContent = scanningResult.getContents().toString();
                scanFormat = scanningResult.getFormatName().toString();

                apiService = ApiClient.getInstance().create(RetrofitApiService.class);
                Call<QrCodeScanResponseDto> call = apiService.getQToken(scanContent);
                call.enqueue(new Callback<QrCodeScanResponseDto>() {
                    @Override
                    public void onResponse(Call<QrCodeScanResponseDto> call, Response<QrCodeScanResponseDto> response) {
                        if (response.body()!=null){

                            qr = response.body();
                            payProxy = new Intent(view.getContext(), PayProxyActivity.class);
                            payProxy.putExtra("QrType", qr);
                            startActivity(payProxy);
                        }

                    }
                    @Override
                    public void onFailure(Call<QrCodeScanResponseDto> call, Throwable t) {
                        t.getCause();
                        t.getMessage();
                        call.cancel();
                    }
                });
            }

            Toast.makeText(getActivity(), scanContent + "   type:" + scanFormat, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Nothing scanned", Toast.LENGTH_SHORT).show();
        }
    }



//   public QrCodeScanResponseDto  getQRData(String qToken){
//       apiService = ApiClient.getInstance().create(RetrofitApiService.class);
//       Call<QrCodeScanResponseDto> call = apiService.getQToken(qToken);
//       call.enqueue(new Callback<QrCodeScanResponseDto>() {
//           @Override
//           public void onResponse(Call<QrCodeScanResponseDto> call, Response<QrCodeScanResponseDto> response) {
//               if (response.body()!=null){
//
//                   qr = response.body();
//               }
//
//           }
//           @Override
//           public void onFailure(Call<QrCodeScanResponseDto> call, Throwable t) {
//               t.getCause();
//               t.getMessage();
//               call.cancel();
//           }
//       });
//       return qr;
//    }
}
