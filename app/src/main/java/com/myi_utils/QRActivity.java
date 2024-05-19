package com.myi_utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Locale;

public class QRActivity extends Activity {

    String sig;
    String sts;
    String cmd;
    EditText get_reset_code;
    EditText get_qr_password;
    EditText get_STS;
    EditText get_CMD;
    Button show_QR;
    Bitmap createBitmap ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qr_activity);

        get_reset_code = findViewById(R.id.reset_code);
        get_qr_password = findViewById(R.id.qr_password);
        get_STS = findViewById(R.id.STS);
        get_CMD = findViewById(R.id.CMD);
        show_QR = findViewById(R.id.Show_QR);
        show_QR.setOnClickListener(view -> {
            if (get_CMD.getText().toString().length() != 0) {
                set_QR(get_CMD.getText().toString());
                Toast.makeText(QRActivity.this, "正在生成二维码......", Toast.LENGTH_LONG).show();
                new Handler().postDelayed(() -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(QRActivity.this);
                    builder.setTitle("         RESET CODE：" + get_reset_code.getText().toString());
                    final ImageView QR_image = new ImageView(QRActivity.this);
                    QR_image.setImageBitmap(createBitmap);
                    builder.setView(QR_image);
                    builder.setCancelable(true);
                    builder.show();
                }, 1500);
            } else Toast.makeText(QRActivity.this, "CMD为空，无法生成！", Toast.LENGTH_SHORT).show();
        });
    }

    public void set_STS(String QRType) {
        //获取时间，并根据选择二维码类型生成对应的SIS
        this.sts = get_reset_code.getText().toString() + "-" + QRType + "-" + new SimpleDateFormat("yyyy-MM-dd HH", Locale.ENGLISH).format(Calendar.getInstance().getTime()) + "-" + get_qr_password.getText().toString();
        get_STS.setText(this.sts);
    }

    //获取签名后返回值
    private String sign_low(String str) throws Exception {
        PrivateKey generatePrivate = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(Base64.decode("MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAPLvL0Mh0Ax0qx8gieuGHJc\nRHiH8WexQM6Ity4krVhQN8Nk0t9jninxMV4h0frqBQ/AX7u+NflGAMzBh8jFMywUWEwGyc34pOF\nrq/lDnoFn1+4ikfu8RJ1mf+V+kyQf+6gNl96c2cVuDpgONGTa7tiDKZK3JN9e7pKgZKTaU\nwS9xAgMBAAECgYA8Q/3Th5XXITI6XJYaaYC6MTLqd3HPX+4PM5EkjC2kd/jhyWQ6WOZQA5\nhLb6rolZzG+Qj7QHyvtz/7prCwbj6Gnr/YaxeHPFjobRKCDuiNyk7JW9v6Qitoi9Uh89S/8Pj\naxWA7/ZXqmcQTA+az26tG3jfp3vNKtqQ62LFmwo7e6QJBAPw+KSwOVEitcnT13/IQ7sqluBe\nprn55zE++AwTnD/axE8m/KMhNoKXi0ZiIYbafbBDTg1991zDunbEKbmBXuRMCQQD2jYdzBz\n+r8snh6Ra2OETMWH8qBV7VqsT42JsO8dAiyg5Xzcbdd0aaI1wnvrhTG8SKudIYZTr+DVhV2dK\nhGOnrAkEA4E7KwCGx87tB6B5p2lISd2Ss82XyBQh8loDrIGX1fTN03FhAhPb7H+k258SH2CW\nmrMPatIUsYpge5LvY8ZWwvwJAVJfev3LfP8n/xolJB6Bqyooap3pMPZdoGdGH8iq5tRYKscmi\nz93a1YwIVIK1j55ZYxe6pDAne39srZgdErUPnQJAVdQNsZ3f1ccaqzi5XEagPds4O5r1urb\nDUyS26euA8ygEQ69+Ijgkxb7wHFd5/mnr0IF9RUNlpdVcV2nw843vZQ==\n", 0)));
        Signature signature = Signature.getInstance("SHA1WithRSA");
        signature.initSign(generatePrivate);
        signature.update(str.getBytes());
        return Base64.encodeToString(signature.sign(), 0);
    }
    private String sign_high(String str) throws Exception {
        PrivateKey generatePrivate = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(Base64.decode("MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALz6tUYOBPxogujuAgQi1CoT\nuYncbjoPs8211IWOueMg+P6utDILN3m+Ti9LJ8VE7j8h4tVkPlu/vlqMKkysOLkuHuQu11de\nmF+Lgr/o7PdXz7d3KpbuRCvRdwNOsZnK1LnUA1mVGtI35RlgkLegXR2BB6w6W6mLCz1c01NG\nV1ERAgERAoGACFZa0iZD/BOrbCieNONrPhd1XKubc4CxWCJf9tVbBkF0ZZbEL2JgkKpHNsrY\nV8MKgskGPh7GhAw5VtF6VjTGRFQhz4kpimCeBzd5ffJfiTw2ihpfcNm/jQU0gXApj7IbO+dH\nhOQDZqbmtfs5nkYGwRSK8TDpwtRWG6NXLOHK7iUCQQD4Pac6M3xYnliqmWooJVRjUTXKlQRV\noEgRoJmypRnbFN05X9T47xNdr21qLN5lLb8iprZW2GeyQWX+FRsR4gqtAkEAwuLcz6DvzVFj\nTNIu9ad8SlTyAXeUpli8nzBJAwkcKqvkIE5rFolSvcg5t/SAXUDwjL5FjtCsYSoPkwY+4Sgw\ndQJBAL3UyyyBuXDxjxkL58RYyA+2kovbXarj+uBNorXJjD4fA4Y6OXMRLO1J6kIiT7bIoTid\nmn6lfHlBEcJMX/6dy+0CQQCgfpe6DBDHM/dscNtg5Eg9NuVqnranG+ahNtK3NK3IycrtbcGa\nFrycSoniyVqnJmu/M0hXfqwTyEkPqspfEgnJAkEAnWKNmV38X3NR2g/QADg6WzFAaNLm9sSi\nSRLSyo7UdgGNiq+IjNLaP+uKsw1FoEGeT2qFc8aswopREuPRE2RdNg==\n", 0)));
        Signature signature = Signature.getInstance("SHA1WithRSA");
        signature.initSign(generatePrivate);
        signature.update(str.getBytes());
        return Base64.encodeToString(signature.sign(), 0);
    }
    //显示返回值，填入Edittext中
    public void sign_QR_low(View view) {
        try {
            this.sig = sign_low(get_STS.getText().toString());
        } catch (Exception e) {
            this.sig = e.toString();
        }
        this.cmd = "cmd://" + this.sig;
        get_CMD.setText(this.cmd);
    }
    public void sign_QR_high(View view) {
        try {
            this.sig = sign_high(get_STS.getText().toString());
        } catch (Exception e) {
            this.sig = e.toString();
        }
        this.cmd = "cmd://" + this.sig;
        get_CMD.setText(this.cmd);
    }
    //写入二维码
    private void set_QR(String cmd) {
        QRCodeWriter qRCodeWriter = new QRCodeWriter();
        Hashtable hashtable = new Hashtable<EncodeHintType, ErrorCorrectionLevel>();
        hashtable.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        try {
            BitMatrix encode = qRCodeWriter.encode(cmd, BarcodeFormat.QR_CODE, 1024, 1024, hashtable);
            int width = encode.getWidth();
            int height = encode.getHeight();
            createBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    createBitmap.setPixel(x, y, encode.get(x, y) ? -16777216 : -1);
                }
            }
        } catch (Exception e) {
            Toast.makeText(QRActivity.this, "写入失败", Toast.LENGTH_SHORT).show();
        }
    }
    //设置二维码类型
    public void Bootloader (View view){
        set_STS("Bootloader");
    }
    public void UnlockAccount (View view){
        set_STS("UnlockAccount");
    }
    public void UnlockServerAddress (View view){
        set_STS("UnlockServerAddress");
    }
    public void WipeData (View view){
        set_STS("WipeSpecial");
    }
    //清空所有内容
    public void CleanAll (View view){
        get_CMD.setText("");
        get_STS.setText("");
        get_reset_code.setText("");
        Toast.makeText(QRActivity.this, "已清空", Toast.LENGTH_SHORT).show();
    }
}