package com.example.evenyan.businesscard;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 我想知道的碎片页面
 * @author wwj_748
 *
 */
public class CardActivityFragment extends Fragment {

    public static final int TAKE_PHOTO = 1;
    public static final int CROP_PHOTO = 2;
    private ImageView iv;
    private Uri fileUri;
    private ImageView picture;
    private ContactDatebase dbHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card, container,
                false);

        iv = (ImageView)view.findViewById(R.id.iv_camera);
//        picture = (ImageView)view.findViewById(R.id.picture);

        dbHelper = new ContactDatebase(getActivity(), "Contact.db", null, 1);
        Button createDatabase = (Button) view.findViewById(R.id.create_database);
        createDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.getWritableDatabase();
            }
        });

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "YOU are me", Toast.LENGTH_SHORT).show();
                // 创建File对象,用于存储拍照后的图片
                File outputImage = new File(Environment.
                        getExternalStorageDirectory(), "tempImage.jpg");
                try {
                    if (outputImage.exists()) {
                        outputImage.delete();
                    }

                    outputImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                fileUri = Uri.fromFile(outputImage);

                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                startActivityForResult(intent, TAKE_PHOTO); // 启动相机程序

                String url = "http://bcr2.intsig.net/BCRService/BCR_Crop?user=568036792@qq.com&pass=MC7YFBE4XCQJ45HA&lang=524287";
                postUseUrlConnection(url, outputImage);
            }
        });


        return view;
    }

    public static String postUseUrlConnection(String requestUrl, File file) {
        String end = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        String newName = "image.jpg";
        StringBuffer sb = new StringBuffer();
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            // 允许Input、Output，不使用Cache
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setUseCaches(false);
            // 设置以POST方式进行传送
            con.setRequestMethod("POST");
            // 设置RequestProperty
            con.setRequestProperty("Connection", "Keep-Alive");
            con.setRequestProperty("Charset", "UTF-8");
            con.setRequestProperty("Content-Type",
                    "multipart/form-data;boundary=" + boundary);
            // 构造DataOutputStream流
            DataOutputStream ds = new DataOutputStream(con.getOutputStream());
            ds.writeBytes(twoHyphens + boundary + end);
            ds.writeBytes("Content-Disposition: form-data; "
                    + "name=\"file1\";filename=\"" + newName + "\"" + end);
            ds.writeBytes(end);
            // 构造要上传文件的FileInputStream流
            FileInputStream fis = new FileInputStream(file);
            // 设置每次写入1024bytes
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];
            int length = -1;
            // 从文件读取数据至缓冲区
            while ((length = fis.read(buffer)) != -1) {
                // 将资料写入DataOutputStream中
                ds.write(buffer, 0, length);
            }
            ds.writeBytes(end);
            ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
            // 关闭流
            fis.close();
            ds.flush();
            // 获取响应流
            InputStream is = con.getInputStream();
            int ch;
            while ((ch = is.read()) != -1) {
                sb.append((char) ch);
            }
            // 关闭DataOutputStream
            ds.close();
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }


//    @Override
//    public void onActivityResult(int resutCode, int resultCode, Intent data) {
//
//        switch (resultCode) {
//            case TAKE_PHOTO:
//                if (resultCode == getActivity().RESULT_OK) {
//                    Intent intent = new Intent("com.android.camera.action.CROP");
//                    intent.setDataAndType(fileUri, "image/*");
//                    intent.putExtra("scale", true);
//                    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
//                    startActivityForResult(intent, CROP_PHOTO);
//                }
//                break;
//            case CROP_PHOTO:
//                if(resultCode == getActivity().RESULT_OK) {
//                    try {
//                        Bitmap bitmap =  BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(fileUri));
//                        picture.setImageBitmap(bitmap);
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    }
//                }
//                break;
//            default:
//                break;
//
//        }
//    }

    /**
     * A simple {@link Fragment} subclass.
     * Activities that contain this fragment must implement the
     * {@link HomeFragment.OnFragmentInteractionListener} interface
     * to handle interaction events.
     */
    public static class HomeFragment extends Fragment {

        private OnFragmentInteractionListener mListener;

        public HomeFragment() {
            // Required empty public constructor
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.fragment_home, container, false);
        }

        // TODO: Rename method, update argument and hook method into UI event
        public void onButtonPressed(Uri uri) {
            if (mListener != null) {
                mListener.onFragmentInteraction(uri);
            }
        }

        @Override
        public void onAttach(Context context) {
            super.onAttach(context);
            if (context instanceof OnFragmentInteractionListener) {
                mListener = (OnFragmentInteractionListener) context;
            } else {
                throw new RuntimeException(context.toString()
                        + " must implement OnFragmentInteractionListener");
            }
        }

        @Override
        public void onDetach() {
            super.onDetach();
            mListener = null;
        }

        /**
         * This interface must be implemented by activities that contain this
         * fragment to allow an interaction in this fragment to be communicated
         * to the activity and potentially other fragments contained in that
         * activity.
         * <p/>
         * See the Android Training lesson <a href=
         * "http://developer.android.com/training/basics/fragments/communicating.html"
         * >Communicating with Other Fragments</a> for more information.
         */
        public interface OnFragmentInteractionListener {
            // TODO: Update argument type and name
            void onFragmentInteraction(Uri uri);
        }
    }
}

