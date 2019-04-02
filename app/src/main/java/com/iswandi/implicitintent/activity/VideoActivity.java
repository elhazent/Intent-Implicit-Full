package com.iswandi.implicitintent.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.widget.Button;

import com.iswandi.implicitintent.R;
import com.iswandi.implicitintent.helper.MyFunction;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class VideoActivity extends MyFunction implements EasyPermissions.PermissionCallbacks {

    @BindView(R.id.btnvideo)
    Button btnvideo;
    Uri lokasifile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        ButterKnife.bind(this);
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
//                    && checkSelfPermission(Manifest.permission.CAMERA)
//                    != PackageManager.PERMISSION_GRANTED) {
//                requestPermissions(
//                        new String[]{Manifest.permission.CAMERA},
//                        10);
//            }
//            return;
//        }if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
//                    && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                    != PackageManager.PERMISSION_GRANTED) {
//                requestPermissions(
//                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                        107);
//            }
//            return;
//        }
    }

    @OnClick(R.id.btnvideo)
    public void onViewClicked() {
        opeenVideo();
//        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
//        StrictMode.setVmPolicy(builder.build());
//
//        String foldercamera="videoku1";
//        File file = new File(Environment.getExternalStorageDirectory(),foldercamera);
//        if (!file.exists()){
//            file.mkdir();
//        }
//        File isifile= new File(Environment.getExternalStorageDirectory()
//                .getAbsolutePath()+"/"+foldercamera+"/VID"+currentDate()+".mp4");
//        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
//        lokasifile = Uri.fromFile(isifile);
//        //lokasifile = FileProvider.getUriForFile(c, c.getApplicationContext().getPackageName(), isifile);
//
//     //   intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT,lokasifile);
//        startActivityForResult(intent,1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                mytoast("berhasil menyimpan video \n lokasi" + lokasifile.toString());
            } else if (resultCode == RESULT_CANCELED) {
                mytoast("cancel");
            } else {
                mytoast("gagal mengambil gambar");
            }
        }
    }

    @AfterPermissionGranted(123)
    private void opeenVideo() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());

            String foldercamera="videoku1";
            File file = new File(Environment.getExternalStorageDirectory(),foldercamera);
            if (!file.exists()){
                file.mkdir();
            }
            File isifile= new File(Environment.getExternalStorageDirectory()
                    .getAbsolutePath()+"/"+foldercamera+"/VID"+currentDate()+".mp4");
            Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            lokasifile = Uri.fromFile(isifile);
            //lokasifile = FileProvider.getUriForFile(c, c.getApplicationContext().getPackageName(), isifile);

            //   intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.putExtra(MediaStore.EXTRA_OUTPUT,lokasifile);
            startActivityForResult(intent,1);
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
