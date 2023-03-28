package com.example.arcanamini;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.camera.core.CameraProvider;
import androidx.camera.core.ImageCapture;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class ReflectionDetailsFragment extends Fragment {
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    //    PreviewView previewView;
    private CameraProvider cameraProvider;

    private ImageCapture imageCapture;
    private ConstraintLayout imageWrapper;
    private ImageView imageViewCaptured;

    private static final int img_id = 1;

    // new
    private Executor executor = Executors.newSingleThreadExecutor();
    private int REQUEST_CODE_PERMISSIONS = 1001;
    private final String[] REQUIRED_PERMISSIONS = new String[]{"android.permission.CAMERA"};

    private Integer counter = 0;
    TextView dayTextView, timeTextView;
    public static ArrayList<String> list;
    String date;
    LinearLayout buttonSet;
    ImageButton editButton, deleteButton, imageButton, confirmButton;
    Space a, b, c;
    EditText contentEditText;
    Boolean edit;
    int position;
    ReadDatabaseHelper databaseHelper;
    public ReflectionDetailsFragment() {
        // Required empty public constructor
    }

    public static ReflectionDetailsFragment newInstance() {
        ReflectionDetailsFragment fragment = new ReflectionDetailsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_reflection_details, container, false);
        imageWrapper = v.findViewById(R.id.imageWrapper);
        imageWrapper.setVisibility(View.GONE);//show image
        imageViewCaptured = v.findViewById(R.id.imageViewCapture);
        edit = false;
        //text views
        dayTextView = v.findViewById(R.id.reflectionDetailsDayTextView);
        timeTextView = v.findViewById(R.id.reflectionDetailsTimeTextView);
        contentEditText= v.findViewById(R.id.reflectionDetailsContentEditText);
        //edit text
        contentEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        contentEditText.setEnabled(false);
        buttonSet = v.findViewById(R.id.buttonSet);
        imageButton = v.findViewById(R.id.reflectDetails_photo);
        editButton = v.findViewById(R.id.reflectDetails_edit);
        deleteButton = v.findViewById(R.id.reflectDetails_delete);
        confirmButton = v.findViewById(R.id.reflectDetails_confirm);
        confirmButton.setVisibility(View.GONE);

        //button


//        position = 0;
        // retrieve the bundle from the intent that started this activity
        Bundle extra_data = getArguments();
        if (extra_data!= null) {
            date = extra_data.getString("ITEM_DATE");
            Log.i("pos", String.valueOf(date));
            databaseHelper = new ReadDatabaseHelper(getActivity(), date);
            databaseHelper.getReadableDatabase();
            list = databaseHelper.getItemsWithContent();
            Log.i("tester", String.valueOf(list));

            position = extra_data.getInt("ITEM_KEY");
            //Toast.makeText(this, , Toast.LENGTH_SHORT).show();



            String[] reflection = list.get(position).split("~");
            String day = reflection[0];
            String time = reflection[1];
            dayTextView.setText(day);
            timeTextView.setText(time);
            //account for empty content
//            Log.i("test", String.valueOf(reflection[3]));
            if (reflection.length > 2) {
                String content = reflection[2];
                Log.i("test", String.valueOf(reflection));
                contentEditText.setText(content.equals("emptyContent") ? "" : content);
            }

            if (extra_data.containsKey("EDIT_AUTO") && extra_data.getBoolean("EDIT_AUTO")){
                contentEditText.setEnabled(true);
                confirmButton.setVisibility(View.VISIBLE);
                buttonSet.setVisibility(View.GONE);
                contentEditText.requestFocus();
            }
            if (extra_data.containsKey("FORCE_CAM")){
                Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePicture, 0);
            }
        }

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contentEditText.setEnabled(true);
                confirmButton.setVisibility(View.VISIBLE);
                buttonSet.setVisibility(View.GONE);

                contentEditText.requestFocus();

            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contentEditText.setEnabled(false);
                confirmButton.setVisibility(View.GONE);
                buttonSet.setVisibility(View.VISIBLE);
                String i = databaseHelper.getId(position);
                Log.i("pos", String.valueOf(position));
                Log.i("pos", String.valueOf(i));
                Log.i("pos", String.valueOf(contentEditText.getText()));
                databaseHelper.updateContent(i, String.valueOf(contentEditText.getText()).equals("") ? "emptyContent" : String.valueOf(contentEditText.getText()));
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String i = databaseHelper.getId(position);
                databaseHelper.deleteContent(i);
                Navigation.findNavController(v).navigateUp();
            }
        });



        return v;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_CANCELED) {
            imageWrapper.setVisibility(View.VISIBLE);//show image
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        imageViewCaptured.setImageBitmap(selectedImage);
                        //save image
                        saveBitmap(selectedImage);
                    }
                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (selectedImage != null) {
                            Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                            if (cursor != null) {
                                cursor.moveToFirst();
                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                String picturePath = cursor.getString(columnIndex);
                                imageViewCaptured.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                                cursor.close();
                            }
                        }
                    }
                    break;
            }
        }
    }

    private void saveBitmap(Bitmap bitmap){
        String filename = "arcanaminiphoto"+counter+".png";
        File sd = Environment.getExternalStorageDirectory();
        ContextWrapper cw = new ContextWrapper(getActivity().getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File dest = new File(directory, filename);

        try {
            FileOutputStream out = new FileOutputStream(dest);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            counter = counter + 1;
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}