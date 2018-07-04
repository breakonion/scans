package com.example.lenovo.scans;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.xys.libzxing.zxing.activity.CaptureActivity;
import com.xys.libzxing.zxing.encoding.EncodingUtils;
public class MainActivity extends Activity {
    private TextView scanRuslet;
    private EditText mInput;
    private ImageView mImg;
    private CheckBox isLogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scanRuslet= (TextView) findViewById(R.id.scanruselt);
        mInput= (EditText) findViewById(R.id.et_text);
        mImg= (ImageView) findViewById(R.id.img);
        isLogo= (CheckBox) findViewById(R.id.is_logo);
    }
    /**
     *生成二维码
     */
    public void make(View view){
        String input=mInput.getText().toString().trim();
        //生成二维码，然后为二维码增加logo
        Bitmap bitmap=EncodingUtils.createQRCode(input,500,500,
                isLogo.isChecked()? BitmapFactory.decodeResource(getResources(),
                        R.mipmap.ic_launcher):null
        );
        mImg.setImageBitmap(bitmap);
    }
    /**
     *  扫描二维码
     */
    public void scan(View view){
        startActivityForResult(new Intent(MainActivity.this, CaptureActivity.class),0);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Log.i("TAG","resultCode: "+resultCode+" result_ok: "+RESULT_OK);
        if (resultCode==RESULT_OK){
            Bundle bundle=data.getExtras();
            String result= bundle.getString("result");
            Intent intent=new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(result));
            startActivity(intent);
        } if(resultCode == RESULT_CANCELED) {
            scanRuslet.setText("扫描出错");
        }
    }
}
