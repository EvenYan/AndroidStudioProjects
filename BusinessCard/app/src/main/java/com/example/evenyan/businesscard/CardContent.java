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
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

import okhttp3.MediaType;

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


//        try {
//            Toast.makeText(this, imgToBase64(uri, BitmapFactory.decodeStream(getContentResolver().openInputStream(fileUri)), "jpg").substring(0, 20), Toast.LENGTH_LONG).show();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }


        //开启一个线程,做联网操作
//        new Thread(){
//            @Override
//            public void run(){
//                OkHttpClient client = new OkHttpClient();
//                String post(String url, String json) throws IOException{
//                    RequestBody body = new RequestBody.create(JSON, json);
//
//                }
//            }
//        }.start();

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

//        if (resultCode == RESULT_OK) {
//            Toast.makeText(this, "heoll", Toast.LENGTH_SHORT).show();
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
//                    Toast.makeText(this, "Ok", Toast.LENGTH_SHORT).show();

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
            String bs64 =bitmaptoString(bitmap);
            Toast.makeText(this, bs64.substring(0, 20), Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

                }
//        file = new File(String.valueOf(fileUri));
//
//        FileInputStream inputFile = new FileInputStream(file);
//        byte[] buffer = new byte[(int)file.length()];
//        inputFile.read(buffer);
//        inputFile.close();
//        String bs64 = Base64.encodeToString(buffer, Base64.DEFAULT);
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

//    public static String imgToBase64(String imgPath, Bitmap bitmap, String imgFormat) {
////        String imgPath = String.valueOf(imgUri);
//        if (imgPath !=null && imgPath.length() > 0) {
//            bitmap = readBitmap(imgPath);
//        }
//        if(bitmap == null){
//            //bitmap not found!!
//        }
//        ByteArrayOutputStream out = null;
//        try {
//            out = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
//
//            out.flush();
//            out.close();
//
//            byte[] imgBytes = out.toByteArray();
//            return Base64.encodeToString(imgBytes, Base64.DEFAULT);
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            return null;
//        } finally {
//            try {
//                out.flush();
//                out.close();
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//    }
//
//    private static Bitmap readBitmap(String imgPath) {
//        try {
//            return BitmapFactory.decodeFile(imgPath);
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            return null;
//        }
//
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

}
