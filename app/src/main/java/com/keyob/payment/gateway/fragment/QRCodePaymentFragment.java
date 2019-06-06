package com.keyob.payment.gateway.fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.keyob.payment.gateway.R;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class QRCodePaymentFragment extends Fragment {

    SurfaceView camera_priview;
    TextView txtresult;
    BarcodeDetector barcodeDetector;
    final int request_camera_permission_id = 1001;
    View view;
    CameraSource cameraSource;


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {

            case request_camera_permission_id: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                        return;
                    }
                    try {
                        cameraSource.start(camera_priview.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
            break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        view = inflater.inflate(R.layout.fragment_qrcode_payment, container, false);
        camera_priview = (SurfaceView) view.findViewById(R.id.camera_scanner_view);
        txtresult = (TextView) view.findViewById(R.id.txt_result);

        barcodeDetector = new BarcodeDetector.Builder(getActivity())
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();

        cameraSource = new CameraSource
                .Builder(getActivity(), barcodeDetector)
                .setRequestedPreviewSize(640, 400)
                .build();


        camera_priview.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    //  request Permission
                    ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CAMERA},request_camera_permission_id);

                    return;
                }
                try {
                    cameraSource.start(camera_priview.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                  cameraSource.stop();
            }
        });


        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> qrcode= detections.getDetectedItems();

                if (qrcode.size()!=0){
                     txtresult.post(new Runnable() {
                         @Override
                         public void run() {
                             Vibrator vibrator  = (Vibrator)getContext().getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                             vibrator.vibrate(1000);
                             txtresult.setText(qrcode.valueAt(0).displayValue);
                         }
                     });
                }
            }
        });

        return view;

    }

}
