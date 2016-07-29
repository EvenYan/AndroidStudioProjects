package com.example.evenyan.businesscard;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import okhttp3.MediaType;

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
    public static final String TAG = "MainActivity";
    public static final MediaType JSON=MediaType.parse("application/json; charset=utf-8");

    private String[] data = { "Apple", "Banana", "Orange", "Watermelon", "Pear", "Grape", "Pineapple", "Strawberry", "Cherry", "Mango" };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    //        Toast.makeText(this, url, Toast.LENGTH_LONG).show();
//    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
//    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
//    startActivityForResult(intent, TAKE_PHOTO); // 启动相机程序

//        try {
//            String bs = imgToBase64(uri, BitmapFactory.decodeStream(getContentResolver().openInputStream(fileUri)), "jpg");
//            Toast.makeText(this, bs.substring(0, 20), Toast.LENGTH_LONG).show();
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card, container,
                false);

        iv = (ImageView)view.findViewById(R.id.iv_camera);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, data);
        ListView listView = (ListView) view.findViewById(R.id.list_view_user_list);
        listView.setAdapter(adapter);
//        picture = (ImageView)view.findViewById(R.id.picture);

//        dbHelper = new ContactDatebase(getActivity(), "Contact.db", null, 1);
//        Button createDatabase = (Button) view.findViewById(R.id.create_database);
//        createDatabase.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dbHelper.getWritableDatabase();
//            }
//        });

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "YOU are me", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), CardContent.class);
                startActivity(intent);
            }
        });



        return view;
    }



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

