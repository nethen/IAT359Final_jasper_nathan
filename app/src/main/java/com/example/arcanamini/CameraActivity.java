package com.example.arcanamini;

import static android.content.ContentValues.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.util.concurrent.ListenableFuture;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraProvider;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

public class CameraActivity extends AppCompatActivity implements View.OnClickListener {

    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
//    PreviewView previewView;
    private CameraProvider cameraProvider;

    private ImageCapture imageCapture;
    private ImageView imageViewCaptured;

    private static final int img_id = 1;

    // new
    private Executor executor = Executors.newSingleThreadExecutor();
    private int REQUEST_CODE_PERMISSIONS = 1001;
    private final String[] REQUIRED_PERMISSIONS = new String[]{"android.permission.CAMERA"};

    private Button buttonCaptureShow, saveButton, reflectModeButton;
    private TextView notes, time;
    private EditText noteContent;
    Calendar calendar;
    private Date dateVal;
    private String date, hourMin;
    private ReadDatabaseHelper databaseHelper;
    private Integer counter = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        calendar = Calendar.getInstance();
//        previewView = findViewById(R.id.previewView);
//        previewView.setVisibility(View.GONE);
        imageViewCaptured = findViewById(R.id.imageViewCapturedImg);
        imageViewCaptured.setVisibility(View.GONE);

        //buttons
        buttonCaptureShow = findViewById(R.id.buttonCaptureShow);
        buttonCaptureShow.setOnClickListener(this);
        saveButton = findViewById(R.id.newReflectionSaveButton);
        saveButton.setOnClickListener(this);
        reflectModeButton = findViewById(R.id.startReflectModeButton);
        reflectModeButton.setOnClickListener(this);

        //text
        notes = findViewById(R.id.newReflectionNotesTextView);
        time = findViewById(R.id.newReflectionTimeTextView);
        noteContent = findViewById(R.id.newReflectionContentEditText);

        //database
        databaseHelper = new ReadDatabaseHelper(this, date);
        databaseHelper.getWritableDatabase();


        if (allPermissionsGranted()) {
            startCamera(); //start camera if permission has been granted by user
        } else {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
        }

        dateVal = calendar.getTime();
        date = new SimpleDateFormat("MMM d, yyyy").format(dateVal);
        hourMin = new SimpleDateFormat("hh:mm aaa").format(dateVal);
        time.setText(hourMin);

    }
    private class CameraThread implements Runnable
    {
        @Override
        public void run() {
//            Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

//            File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
//            camera_intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
//            startActivityForResult(camera_intent, img_id);

        }
    }

    private Executor getExecutor() {
        return ContextCompat.getMainExecutor(this);
    }

    private void startCamera() {

        final ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(this);

        cameraProviderFuture.addListener(new Runnable() {
            @Override
            public void run() {
                try {
                    ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
//                    bindPreview(cameraProvider);


                } catch (ExecutionException | InterruptedException e) {
                    // This should never be reached.
                }
            }
        }, ContextCompat.getMainExecutor(this));
    }

//    void bindPreview(@NonNull ProcessCameraProvider cameraProvider) {
//
//        cameraProvider.unbindAll();
//
//        Preview preview = new Preview.Builder()
//                .build();
//
//        CameraSelector cameraSelector = new CameraSelector.Builder()
//                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
//                .build();
//
//
//        imageCapture = new ImageCapture.Builder()
//                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
//                .build();
//        preview.setSurfaceProvider(previewView.getSurfaceProvider());
//        cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture);
//    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonCaptureShow: {
//                previewView.setVisibility(View.VISIBLE);
//                capturePhoto();

                //start camera
//                Thread myThread = new Thread(new CameraThread());
//                myThread.start();
                chooseImage(this);
                break;
            }
            case R.id.newReflectionSaveButton: {
                //save reflection

                String content = noteContent.getText().toString();
                databaseHelper.insertDataReflection(date,hourMin);
//                int position = databaseHelper.getLength() - 1;
//                Intent intent = new Intent (this, ReflectionDetailsActivity.class);
//                intent.putExtra ("ITEM_KEY", position);
//                this.startActivity(intent);
                //Toast.makeText(this,"saved", Toast.LENGTH_LONG).show();
                break;
            }
            case R.id.startReflectModeButton: {
                //reflection mode!
                Intent intent = new Intent(this, ReflectionModeActivity.class);
                this.startActivity(intent);
            }
        }
    }



    private void capturePhoto() {
        long timeStamp = System.currentTimeMillis();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, timeStamp);
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");


        imageCapture.takePicture(
                new ImageCapture.OutputFileOptions.Builder(
                        getContentResolver(),
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        contentValues
                ).build(),
                getExecutor(),
                new ImageCapture.OnImageSavedCallback() {
                    @Override
                    public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                        //Toast.makeText(CameraActivity.this, "Saving...", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(@NonNull ImageCaptureException exception) {
                        //Toast.makeText(CameraActivity.this, "Error: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private boolean allPermissionsGranted() {

        for (String permission : REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        Bitmap photo = (Bitmap) data.getExtras().get("data");
//        //after taking an image:
//        imageViewCaptured.setImageBitmap(photo);
//
//    }

    // function to let's the user to choose image from camera or gallery
    private void chooseImage(Context context){
        final CharSequence[] optionsMenu = {"Take Photo", "Choose from Gallery", "Exit" }; // create a menuOption Array
        // create a dialog for showing the optionsMenu
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        // set the items in builder
        builder.setItems(optionsMenu, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(optionsMenu[i].equals("Take Photo")){
                    // Open the camera and get the photo
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);
                }
                else if(optionsMenu[i].equals("Choose from Gallery")){
                    // choose from  external storage
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto , 1);
                }
                else if (optionsMenu[i].equals("Exit")) {
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageViewCaptured.setVisibility(View.VISIBLE);//show image

        if (resultCode != RESULT_CANCELED) {
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
                            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
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
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
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


