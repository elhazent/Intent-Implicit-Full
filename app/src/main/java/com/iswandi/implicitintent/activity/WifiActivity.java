package com.iswandi.implicitintent.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsManager;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.iswandi.implicitintent.R;
import com.iswandi.implicitintent.helper.MyFunction;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class WifiActivity extends MyFunction implements EasyPermissions.PermissionCallbacks {

    @BindView(R.id.wifi)
    Switch wifi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);
        ButterKnife.bind(this);
        wifi.setChecked(status());
        wifi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                wifichangestatus(isChecked);
            }
        });
    }

    private void wifichangestatus(boolean b) {

        statusWifi(b);
//        WifiManager manager = (WifiManager)getApplicationContext()
//                .getSystemService(Context.WIFI_SERVICE);
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_WIFI_STATE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.CHANGE_WIFI_STATE) != PackageManager.PERMISSION_GRANTED) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
//                    && checkSelfPermission(Manifest.permission.ACCESS_WIFI_STATE)
//                    != PackageManager.PERMISSION_GRANTED
//                    && checkSelfPermission(Manifest.permission.CHANGE_WIFI_STATE)
//                    != PackageManager.PERMISSION_GRANTED) {
//                requestPermissions(
//                        new String[]{Manifest.permission.ACCESS_WIFI_STATE,
//                                Manifest.permission.CHANGE_WIFI_STATE},
//                        110);
//
//
//            }
//            return;
//        }
//        else if (b==true&& !manager.isWifiEnabled()){
//            mytoast("wifi aktif");
//            manager.setWifiEnabled(true);
//        }else if (b==false& manager.isWifiEnabled()){
//            mytoast("wifi tidak aktif");
//            manager.setWifiEnabled(false);
//        }
    }

    private boolean status() {

        WifiManager manager = (WifiManager)getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE);
        return manager.isWifiEnabled();
    }

    @AfterPermissionGranted(123)
    private void statusWifi(boolean b) {
        String[] perms = {Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.CHANGE_WIFI_STATE};
        if (EasyPermissions.hasPermissions(this, perms)) {

            WifiManager manager = (WifiManager)getApplicationContext()
                    .getSystemService(Context.WIFI_SERVICE);
            if (b==true&& !manager.isWifiEnabled()){
                mytoast("wifi aktif");
                manager.setWifiEnabled(true);
            }else if (b==false& manager.isWifiEnabled()){
                mytoast("wifi tidak aktif");
                manager.setWifiEnabled(false);
            }


        } else {
            EasyPermissions.requestPermissions(this, "We need permissions because this and that",
                    123, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }
}
