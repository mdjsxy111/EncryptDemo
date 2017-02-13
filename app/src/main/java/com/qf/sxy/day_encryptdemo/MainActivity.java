package com.qf.sxy.day_encryptdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.rock.teach.utils.encrypt.DES;
import com.rock.teach.utils.encrypt.MD5;
import com.rock.teach.utils.encrypt.RSA;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText etContents;

    private String mDesKey;//des的秘钥

    private String mRSAPrivateKey;//私钥
    private String mRSAPublicKey;//公钥

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etContents = ((EditText) findViewById(R.id.et_contents));

        try {
            mDesKey = DES.initKey();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //获取私钥和公钥
        try {
            Map<String, Object> map = RSA.initKey();
            mRSAPrivateKey = RSA.getPrivateKey(map);
            mRSAPublicKey = RSA.getPublicKey(map);

            Log.e("AAA", "==mRSAPrivateKey=>" + mRSAPrivateKey);
            Log.e("AAA", "==mRSAPublicKey=>" + mRSAPublicKey);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    //加密解密
    public void MyClick(View view) {
        switch (view.getId()) {
            case R.id.btn_urlEncode:
                try {
                    String encode = URLEncoder.encode(etContents.getText().toString().trim(), "utf-8");
                    etContents.setText(encode);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_urlDecode:
                try {
                    String decode = URLDecoder.decode(etContents.getText().toString().trim(), "utf-8");
                    etContents.setText(decode);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_Base64Encode:
                String encodeToString = Base64.encodeToString(etContents.getText().toString().trim().getBytes(), Base64.DEFAULT);
                etContents.setText(encodeToString);

                break;

            case R.id.btn_Base64Decode:
                byte[] base64decode = Base64.decode(etContents.getText().toString().trim(), Base64.DEFAULT);
                etContents.setText(new String(base64decode));
                break;
            case R.id.btn_MD5://不可逆
                try {
                    String md5String = MD5.encryptMD5ToString(etContents.getText().toString().trim().getBytes());
                    etContents.setText(md5String);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case R.id.btn_Des_encode://对称加密
                try {
                    byte[] desEncode = DES.encrypt(etContents.getText().toString().trim().getBytes(), mDesKey);
                    //加密后的 byte[]进行Base64的编码
                    String desEncodeStr = Base64.encodeToString(desEncode, Base64.DEFAULT);
                    etContents.setText(desEncodeStr);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_Des_Decode:
                try {
                    byte[] desDecode = Base64.decode(etContents.getText().toString().trim(), Base64.DEFAULT);
                    byte[] desCode = DES.decrypt(desDecode, mDesKey);
                    etContents.setText(new String(desCode));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_RSA_encode://非对称加密
                try {
                    byte[] bytes = RSA.encryptByPrivateKey(etContents.getText().toString().trim().getBytes(), mRSAPrivateKey);
                    String rsaStr = Base64.encodeToString(bytes, Base64.DEFAULT);
                    etContents.setText(rsaStr);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_RSA_Decode:
                byte[] rasCode = Base64.decode(etContents.getText().toString().trim(),Base64.DEFAULT);
                try {
                    byte[] rsaContent =  RSA.decryptByPublicKey(rasCode,mRSAPublicKey);
                    etContents.setText(new String(rsaContent));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
