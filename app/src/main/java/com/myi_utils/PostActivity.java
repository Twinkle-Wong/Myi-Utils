package com.myi_utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

public class PostActivity extends Activity {

    public static final String TAG= "PostActivity:";
    public String Server_address = "";
    public Button choose;

    public Button save;
    public RadioGroup All_school;
    public RadioButton WZGJZX;
    public RadioButton GZZX;
    public RadioButton QDJZ;
    public RadioButton QDEZ;
    public RadioButton DMXX;
    public RadioButton DCGZ;
    public RadioButton QHJT;
    public EditText get_User_Id;
    public Button send_message;
    public Button run_script;
    public Button run_url_script;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_activity);
        save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Map<String,String> a = HttpUtils.getUserInfo("384527");
                            String classname = a.get("UserClass");
                            String username = a.get("UserName");
                            Log.d(TAG, classname);
                            Log.d(TAG, username);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
//
                    }
                }.start();
            }
        });
        View school_view  = LayoutInflater.from(this).inflate(R.layout.school_inform,null);
        All_school = school_view.findViewById(R.id.all_school);
        WZGJZX = school_view.findViewById(R.id.WZGJZX);
        GZZX = school_view.findViewById(R.id.GZZX);
        QDJZ = school_view.findViewById(R.id.QDJZ);
        QDEZ = school_view.findViewById(R.id.QDEZ);
        DMXX = school_view.findViewById(R.id.DMXX);
        DCGZ = school_view.findViewById(R.id.DCGZ);
        QHJT = school_view.findViewById(R.id.QHJT);
        All_school.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int school_id) {
                switch (school_id) {
                    case R.id.WZGJZX:
                        Server_address = getResources().getString(R.string.wzgjzx);
                        break;
                    case R.id.GZZX:
                        Server_address = getResources().getString(R.string.gzzx);
                        break;
                    case R.id.QDJZ:
                        Server_address = getResources().getString(R.string.qdjz);
                        break;
                    case R.id.QDEZ:
                        Server_address = getResources().getString(R.string.qdez);
                    case R.id.DMXX:
                        Server_address = getResources().getString(R.string.dmxx);
                        break;
                    case R.id.DCGZ:
                        Server_address = getResources().getString(R.string.dcgz);
                        break;
                    case R.id.QHJT:
                        Server_address = getResources().getString(R.string.qhjt);
                        break;
                }
            }
        });
        choose = findViewById(R.id.choose);
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PostActivity.this);
                    builder.setTitle("请选择学校");
                    builder.setView(school_view);
                    builder.setCancelable(false);
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    builder.show();
            }
        });
        get_User_Id = findViewById(R.id.user_ID);
        Load();
        send_message = findViewById(R.id.send_message);
        send_message.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(PostActivity.this);
            final EditText editText = new EditText(PostActivity.this);
            builder.setTitle("发送指定信息");
            builder.setView(editText);
            builder.setPositiveButton("发送", (dialogInterface, i) -> {
                Log.d(TAG, HttpUtils.get_xml(get_User_Id.getText().toString(),"ShowMessage "+editText.getText().toString()));
                HttpUtils.Post(Server_address,get_User_Id.getText().toString(),"ShowMessage "+editText.getText().toString());
            });
            builder.show();
        });
        run_script = findViewById(R.id.RunScript);
        run_script.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(PostActivity.this);
            final EditText editText = new EditText(PostActivity.this);
            editText.setHint("脚本执行错误，少年派将闪退");
            builder.setTitle("执行指定脚本");
            builder.setView(editText);
            builder.setPositiveButton("执行", (dialogInterface, i) -> {
                Log.d(TAG, HttpUtils.get_xml(get_User_Id.getText().toString(),"RunScript "+editText.getText().toString()));
                HttpUtils.Post(Server_address,get_User_Id.getText().toString(),"RunScript "+editText.getText().toString());
            });
            builder.show();
        });
        run_url_script = findViewById(R.id.RunUrlScript);
        run_url_script.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(PostActivity.this);
            final EditText editText = new EditText(PostActivity.this);
            editText.setHint("执行网页脚本，请输入网址");
            builder.setTitle("执行网页脚本");
            builder.setView(editText);
            builder.setPositiveButton("执行", (dialogInterface, i) -> HttpUtils.Post(Server_address,get_User_Id.getText().toString(),"RunUrlScript "+editText.getText().toString()));
            builder.show();
        });
    }
    public void Sava(View view) throws IOException {
//        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences",MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("user_id",get_User_Id.getText().toString()).apply();
//        Toast.makeText(PostActivity.this, "已保存信息", Toast.LENGTH_SHORT).show();
//
//        Map<String,String> map = new HashMap<String,String>();
//        map.put("lpszUserName","384625");
//        map.put("a","384625");
//        String a = HttpUtils.getSoapXml("UsersGetUserGUID",map);
//        Toast.makeText(PostActivity.this, a, Toast.LENGTH_SHORT).show();
//        String a = HttpUtils.post().body().string();
//        Toast.makeText(PostActivity.this, a, Toast.LENGTH_SHORT).show();
    }
    public void Load() {
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences",MODE_PRIVATE);
        Server_address = sharedPreferences.getString("server_address","");
        get_User_Id.setText(sharedPreferences.getString("user_id",""));
    }
    public Boolean Check() {
        if (Server_address.length() == 0 || get_User_Id.getText().toString().equals("")) {
            Toast.makeText(PostActivity.this, "地址或用户名为空", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if (!Server_address.contains("https://") || !Server_address.contains("lexuewang.cn:")) {
                Toast.makeText(PostActivity.this, "地址输入有误", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (!(get_User_Id.getText().toString().length() ==6)) {
                Toast.makeText(PostActivity.this, "用户名输入有误", Toast.LENGTH_SHORT).show();
                return false;
            }
            else  {
                Toast.makeText(PostActivity.this, "已成功执行", Toast.LENGTH_SHORT).show();
                return true;
            }
        }
    }
    public void Wipe_data_lx(View view) {
        if (Check()) {
            HttpUtils.Post(Server_address, get_User_Id.getText().toString(), HttpUtils.get_content("wipe_data_lx"));
        }
    }
    public void Wipe_data_hw(View view) {
        if (Check()) {
            HttpUtils.Post(Server_address,get_User_Id.getText().toString(),HttpUtils.get_content("wipe_data_hw"));
        }
    }
    public void Wipe_data_sx(View view) {
        if (Check()) {
            HttpUtils.Post(Server_address, get_User_Id.getText().toString(), HttpUtils.get_content("wipe_data_sx"));
        }
    }
    public void Lock_screen(View view) {
        if (Check()) {
            HttpUtils.Post(Server_address, get_User_Id.getText().toString(), "LockScreen");
        }
    }
    public void UnLock_screen(View view) {
        if (Check()) {
            HttpUtils.Post(Server_address,get_User_Id.getText().toString(),"UnLockScreen");
        }}
    public void Vibrator(View view) {
        if (Check()) {
            HttpUtils.Post(Server_address,get_User_Id.getText().toString(),"Vibrator");
        }
    }
    public void Beep(View view) {
        if (Check()) {
            HttpUtils.Post(Server_address,get_User_Id.getText().toString(),"Beep");
        }
    }
    public void Launch_terminal(View view) {
        if (Check()) {
            HttpUtils.Post(Server_address,get_User_Id.getText().toString(),HttpUtils.get_content("launch_terminal"));
        }
    }
    public void Launch_safe_mode(View view) {
        if (Check()) {
            HttpUtils.Post(Server_address,get_User_Id.getText().toString(),HttpUtils.get_content("launch_safe_mode"));
        }
    }
    public void Launch_device_admin(View view) {
        if (Check()) {
            HttpUtils.Post(Server_address,get_User_Id.getText().toString(),HttpUtils.get_content("launch_device_admin"));
        }
    }
}