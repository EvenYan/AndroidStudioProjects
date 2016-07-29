package com.example.evenyan.businesscard;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CardContent extends Activity {

    public static final int TAKE_PHOTO = 1;
    public static final int SEND_PICTURE = 2;
    private ImageView iv;
    private Uri fileUri;
    private ImageView picture;
    private ContactDatebase dbHelper;
    public static final String TAG = "MainActivity";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    String uri;
    public String bs64;
    public String json;

    public static final int REQUEST_CAPTURE_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_content);
        picture = (ImageView)findViewById(R.id.picture);


        // 创建File对象,用于存储拍照后的图片
        File outputImage = new File(Environment.
                getExternalStorageDirectory(), createFileName());
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }

            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        fileUri = Uri.fromFile(outputImage);
        uri = toString().valueOf(fileUri);

        uri=uri.replace("file:/","");
//        Toast.makeText(this, url, Toast.LENGTH_LONG).show();
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, TAKE_PHOTO); // 启动相机程序


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

//        if (resultCode == RESULT_OK) {
//
//        }

//        if(requestCode == getActivity().RESULT_OK){
//            if(requestCode == TAKE_PHOTO) {
//                Bundle extras = data.getExtras();
//                Bitmap photoBit = (Bitmap) extras.get("data");
//            }
//        }
//        else {
//            Toast.makeText(getContext(), "获取资源失败", Toast.LENGTH_SHORT).show();
//            return;
//        }

        switch (requestCode) {
            case REQUEST_CAPTURE_IMAGE:
                if (resultCode == RESULT_OK) {

                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(fileUri, "image/*");
                    intent.putExtra("scale", true);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                    startActivityForResult(intent, SEND_PICTURE);


                }
                break;
            case SEND_PICTURE:
                if (resultCode == RESULT_OK) {
//                    try {
//                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(fileUri));
//                        if (bitmap != null){
//                            Toast.makeText(this, "Ok", Toast.LENGTH_SHORT).show();
//                        }
//                        picture.setImageBitmap(bitmap);
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    }
            try {
            Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(fileUri));
            bs64 =bitmaptoString(bitmap);
//            Toast.makeText(this, bs64.substring(0, 20), Toast.LENGTH_LONG).show();

            //        开启一个线程,做联网操作
            new Thread(){
                @Override
                public void run(){
                    try {
                        postPicture(bs64);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }.start();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

                }


                break;
            default:
                break;
//
        }
    }

    public static String createFileName() {
        String filename = "";
        Date date = new Date((System.currentTimeMillis()));
        java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
        filename = dateFormat.format(date) + ".jpg";
        return filename;
    }

    //    public static void savePhotoToSDCard(String path, String photoName, Bitmap photoBitMap){
//        if (android.os.Environment.getExternalStorageDirectory().equals(
//                Environment.MEDIA_MOUNTED)){
//            File dir = new File(path);
//            if (!dir.exists()) {
//                dir.mkdir();
//            }
//            File photoFile = new File(path, photoName);
//            FileOutputStream fileOutputStream = null;
//            try {
//                fileOutputStream = new FileOutputStream(photoFile);
//                if (photoBitMap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)) {
//                    fileOutputStream.flush();
//                    fileOutputStream.close();
//                };
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                try {
//                    fileOutputStream.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }

    public String bitmaptoString(Bitmap bitmap) {

        // 将Bitmap转换成字符串
        String string = null;
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bStream);
        byte[] bytes = bStream.toByteArray();
        string = Base64.encodeToString(bytes, Base64.DEFAULT);
        return string;
    }



    public Response postPicture(String string) throws JSONException {
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("uid", "118.12.0.12");
//        map.put("lang", "chns");
//        map.put("color", "original");
//        map.put("image", "hello");
//        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//        list.add(map);
//        Gson gson = new Gson();
//        json = gson.toJson(list);
//        json.toString();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("uid", "118.12.0.12");
        jsonObject.put("lang", "chns");
        jsonObject.put("color", "original");
        jsonObject.put("image", string);
        json = jsonObject.toString();
        Log.d(TAG, json);

        //申明给服务端传递一个json串
        //创建一个OkHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //创建一个RequestBody(参数1：数据类型 参数2传递的json串)
        RequestBody requestBody = RequestBody.create(JSON, json);
        //创建一个请求对象
        Request request = new Request.Builder()
                .url("http://api.hanvon.com/rt/ws/v1/ocr/bcard/recg?key=84407f1b-56c9-4077-89d8-2396f24b26b1&code=cf22e3bb-d41c-47e0-aa44-a92984f5829d")
                .post(requestBody)
                .build();
        //发送请求获取响应
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
            //判断请求是否成功
            if (response.isSuccessful()) {
                //打印服务端返回结果
                Log.i(TAG, response.body().string());

            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        return response;
    }

}
