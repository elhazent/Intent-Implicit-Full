package com.iswandi.implicitintent.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.iswandi.implicitintent.R;
import com.iswandi.implicitintent.helper.MyFunction;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class CallPhoneActivity extends MyFunction implements EasyPermissions.PermissionCallbacks {

    @BindView(R.id.btncall)
    Button btncall;
    @BindView(R.id.btntampilcall)
    Button btntampilcall;
    @BindView(R.id.btnlistcontact)
    Button btnlistcontact;
    @BindView(R.id.edtnumber)
    EditText edtnumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_phone);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btncall, R.id.btntampilcall, R.id.btnlistcontact})
    public void onViewClicked(View view) {
        String nohp = edtnumber.getText().toString();
        switch (view.getId()) {
            case R.id.btncall:
                if (TextUtils.isEmpty(nohp)) {
                    edtnumber.setError("tidak boleh kosong");
                    edtnumber.requestFocus();
                    myAnimation(edtnumber);
                } else {

                    Call();
//                    int checkPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
//                    if (checkPermission != PackageManager.PERMISSION_GRANTED) {
//                        ActivityCompat.requestPermissions(
//                                this,
//                                new String[]{Manifest.permission.CALL_PHONE},
//                                3);
//                    } else {
//                        startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + nohp)));
//                    }
                }
                break;
            case R.id.btntampilcall:
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + nohp)));
                break;
            case R.id.btnlistcontact:
                Intent i = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                i.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                startActivityForResult(i, 1);
                break;
        }


    }

    //untuk menangkap response dari startActivityForResult
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Cursor cursor = null;
            try {
                Uri uri = data.getData();
                cursor = getContentResolver().query(uri, new String[]{
                        ContactsContract.CommonDataKinds.Phone.NUMBER}, null, null, null);
                if (cursor != null && cursor.moveToNext()) {
                    String phone = cursor.getString(0);
                    edtnumber.setText(phone);
                }
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    @AfterPermissionGranted(123)
    private void Call() {

        String nohp = edtnumber.getText().toString();

        String[] perms = {Manifest.permission.CALL_PHONE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // Already have permission, do the thing
            // ...

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                                this,
                                new String[]{Manifest.permission.CALL_PHONE},
                                3);
            }else {
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + nohp)));
            }


        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, "we need permission",
                    123, perms);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)){
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

}
