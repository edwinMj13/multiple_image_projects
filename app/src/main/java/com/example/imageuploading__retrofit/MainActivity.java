package com.example.imageuploading__retrofit;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.gun0912.tedpermission.TedPermission;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity implements RecyclerviewClickInterface {
    EditText editText;
    ImageView imagesV;
    Button uploadButton, selectButton;
    private int IMG_REQUEST = 2;
    final int CODE_MULTIPLE_IMG_GALLERY=2;

  //  List<File> files = new ArrayList<>();

    Bitmap bitmaps;
  //  ArrayList<Bitmap> bitmapArray;
 //   Bitmap mutableBitmap;
 //   ArrayList<Bitmap> mutBitArray;
    ArrayList<Bitmap> array;
    ArrayList<Uri> uriList;

    RecyclerAdapter recyclerAdapter;
    RecyclerView recyclerView;

    ProgressDialog progressDialog ;
    String imgName;
    private String encodedImage;
   Bitmap recy;
    InputStream inputStream;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imagesV = findViewById(R.id.imageView3);
        uploadButton = findViewById(R.id.upload_IMgae_BUTT);
        selectButton = findViewById(R.id.sel_Image_BUTT);
        editText = findViewById(R.id.shows);
        recyclerView=findViewById(R.id.recyclervie);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading... ");
        array = new ArrayList<>();


        //  bitmapArray=new ArrayList<>();


        recyclerAdapter=new RecyclerAdapter(array,getApplicationContext(),this);
        recyclerAdapter.notifyDataSetChanged();

        GridLayoutManager layoutManager=new GridLayoutManager(this,3, LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(layoutManager);

        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermissionsok();
            }
        });
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
         //       Log.d(MainActivity.class.getSimpleName(),"MUTBITARRAY   :::::"+mutBitArray);
           //     Log.d(MainActivity.class.getSimpleName(),"BITMAP_ARRAY   :::::"+bitmapArray);
        //        if (mutBitArray.isEmpty() && bitmapArray.isEmpty()) {
          //          Toast.makeText(MainActivity.this, "mutBitArray bitmapArray Null", Toast.LENGTH_SHORT).show();
            //    }
                 if(array.isEmpty()){
                    Toast.makeText(MainActivity.this, "array Null", Toast.LENGTH_SHORT).show();
                }
                 if (uriList.isEmpty()){
                    Toast.makeText(MainActivity.this, "urilist Null", Toast.LENGTH_SHORT).show();
                }else{
                  //  uploadImage();
                     Toast.makeText(MainActivity.this, "Well", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void requestPermissionsok(){
        com.gun0912.tedpermission.PermissionListener permissionlistener = new com.gun0912.tedpermission.PermissionListener() {
            @Override
            public void onPermissionGranted() {
              /*  new ImagePicker.Builder(MainActivity.this)
                        .mode(ImagePicker.Mode.CAMERA_AND_GALLERY)
                        .compressLevel(ImagePicker.ComperesLevel.SOFT)
                        .directory(ImagePicker.Directory.DEFAULT)
                        .extension(ImagePicker.Extension.JPG)
                        .allowMultipleImages(true)
                        .enableDebuggingMode(true)
                        .build();
               */
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"),CODE_MULTIPLE_IMG_GALLERY);
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(getApplicationContext(), "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }
        };
        TedPermission.with(getApplicationContext())
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.INTERNET)
                .check();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("Data any THING", String.valueOf(data));
        if (requestCode == CODE_MULTIPLE_IMG_GALLERY && resultCode == RESULT_OK) {
            uriList=new ArrayList<>();
            ClipData clipData = data.getClipData();
            if (clipData != null && clipData.getItemCount() > 0) {
                for (int i = 0; i < clipData.getItemCount(); i++) {
                    ClipData.Item item = clipData.getItemAt(i);
                   uriList.add(item.getUri());
                   recyclerAdapter.notifyDataSetChanged();
              //      files.add(new File(String.valueOf(uriList)).getAbsoluteFile());
                    Log.e("newURI", String.valueOf(item.getUri()));
                }

            }

            if (data.getData() != null) {
                uriList.add(data.getData());
                recyclerAdapter.notifyDataSetChanged();

                //      files.add(new File(String.valueOf(uriList)).getAbsoluteFile());
            }
            //      assert data != null;
         //   List<String> mPaths = data.getStringArrayListExtra(ImagePicker.EXTRA_IMAGE_PATH);
           // Log.d("mPaths MainActivity", String.valueOf(mPaths));
/*
            for (int i = 0; i < mPaths.size(); i++) {
                files.add(new File(mPaths.get(i)));
            }
 */
                    for (int i = 0; i <= uriList.size(); i++) {


                        try {
                                inputStream = getContentResolver().openInputStream(uriList.get(i));
                                bitmaps = BitmapFactory.decodeStream(inputStream);
                         //   imageResize(bitmaps);
                            resizeIMagestoAdapter();
                        } catch (IndexOutOfBoundsException | FileNotFoundException e) {
                            e.printStackTrace();
                            e.getMessage();
                            e.getCause();
                        }
                        Log.e("urilistSize", String.valueOf(uriList.size()));

                    }

                            Toast.makeText(this, "bitmaps"+String.valueOf(bitmaps), Toast.LENGTH_SHORT).show();

                            Log.e("urilist", String.valueOf(uriList));
                            Log.e("stream", String.valueOf(inputStream));
                            editText.setText(String.valueOf(uriList));

                        Log.e("IMages From Stream", String.valueOf(bitmaps));
                        Log.d("Images from Steam", String.valueOf(bitmaps));

        }
    }
    /*
    public  void imageResize(Bitmap sourceImgage) {
        int heights = sourceImgage.getHeight();
        int widths = sourceImgage.getWidth();
        if (heights > widths && heights > 1300 && heights <= 3500) {

            double aspectRatio = (double) widths / (double) heights;
            int targetWidth = (int) (widths / (aspectRatio + 2.3));
            int targetHeight = (int) (heights / (aspectRatio + 2.3));
            Bitmap bitmaps = Bitmap.createScaledBitmap(sourceImgage, targetWidth, targetHeight, true);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmaps.compress(Bitmap.CompressFormat.JPEG, 20, stream);
            Bitmap lastImage = BitmapFactory.decodeStream(new ByteArrayInputStream(stream.toByteArray()));

            watermarkImages(lastImage);
        }

        else if (heights > widths && heights >3500 && heights <= 4500)
        {
                double aspectRatio = (double) widths / (double) heights;
                int targetWidth = (int) (widths / (aspectRatio + 3.8));
                int targetHeight = (int) (heights / (aspectRatio + 3.8));
                Bitmap bitmaps = Bitmap.createScaledBitmap(sourceImgage, targetWidth, targetHeight, true);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmaps.compress(Bitmap.CompressFormat.JPEG, 20, stream);
                Bitmap lastImage = BitmapFactory.decodeStream(new ByteArrayInputStream(stream.toByteArray()));

                watermarkImages(lastImage);
            }


        else
        if (heights > widths && heights > 4500 && heights < 5500) {

                double aspectRatio = (double) widths / (double) heights;
                int targetWidth = (int) (widths / (aspectRatio + 4));
                int targetHeight = (int) (heights / (aspectRatio + 4));
                Bitmap bitmaps = Bitmap.createScaledBitmap(sourceImgage, targetWidth, targetHeight, true);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmaps.compress(Bitmap.CompressFormat.JPEG, 20, stream);
                Bitmap lastImage = BitmapFactory.decodeStream(new ByteArrayInputStream(stream.toByteArray()));

                watermarkImages(lastImage);
            }
         else if (heights >widths && heights >= 5500) {

                double aspectRatio = (double) widths / (double) heights;
                int targetWidth = (int) (widths / (aspectRatio + 4.3));
                int targetHeight = (int) (heights / (aspectRatio + 4.3));
                Bitmap bitmaps = Bitmap.createScaledBitmap(sourceImgage, targetWidth, targetHeight, true);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmaps.compress(Bitmap.CompressFormat.JPEG, 20, stream);

                Bitmap lastImage = BitmapFactory.decodeStream(new ByteArrayInputStream(stream.toByteArray()));
                watermarkImages(lastImage);
            }
         else if (heights == widths && heights > 1300) {

                Bitmap bitmaps = Bitmap.createScaledBitmap(sourceImgage, 900, 900, true);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmaps.compress(Bitmap.CompressFormat.JPEG, 20, stream);
                Bitmap lastImage = BitmapFactory.decodeStream(new ByteArrayInputStream(stream.toByteArray()));
                watermarkImages(lastImage);

        } else if (widths > heights && widths > 1300 && widths <= 3500) {


                        double aspectRatio = (double) heights / (double) widths;
                        int targetWidth = (int) (widths / (aspectRatio + 2.8));
                        int targetHeight = (int) (heights / (aspectRatio + 2.8));

                        Bitmap bitmaps = Bitmap.createScaledBitmap(sourceImgage, targetWidth, targetHeight, true);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmaps.compress(Bitmap.CompressFormat.JPEG, 20, stream);
                        Bitmap lastImage = BitmapFactory.decodeStream(new ByteArrayInputStream(stream.toByteArray()));

                        watermarkImages(lastImage);

        } else if (widths > heights && widths > 3500 && widths <= 4500) {

           double aspectRatio = (double)  heights/ (double) widths;
                int targetWidth = (int) (widths/ (aspectRatio + 3.8));
                int targetHeight = (int) (heights / (aspectRatio + 3.8));

                Bitmap bitmaps = Bitmap.createScaledBitmap(sourceImgage, targetWidth, targetHeight, true);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();

                bitmaps.compress(Bitmap.CompressFormat.JPEG, 20, stream);
                Bitmap lastImage = BitmapFactory.decodeStream(new ByteArrayInputStream(stream.toByteArray()));
                watermarkImages(lastImage);

        } else if (widths > heights && widths >= 4500 && widths < 5500) {

                double aspectRatio = (double) heights / (double) widths;
                int targetWidth = (int) (widths / (aspectRatio + 4));
                int targetHeight = (int) (heights / (aspectRatio + 4));
                Bitmap bitmaps = Bitmap.createScaledBitmap(sourceImgage, targetWidth, targetHeight, true);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmaps.compress(Bitmap.CompressFormat.JPEG, 20, stream);
                Bitmap lastImage = BitmapFactory.decodeStream(new ByteArrayInputStream(stream.toByteArray()));
                watermarkImages(lastImage);

        } else if (widths > heights && widths >= 5500) {

           double aspectRatio = (double) heights / (double) widths;
                int targetWidth = (int) (widths / (aspectRatio + 4.3));
                int targetHeight = (int) (heights / (aspectRatio + 4.3));
                Bitmap bitmaps = Bitmap.createScaledBitmap(sourceImgage, targetWidth, targetHeight, true);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmaps.compress(Bitmap.CompressFormat.JPEG, 20, stream);
                Bitmap lastImage = BitmapFactory.decodeStream(new ByteArrayInputStream(stream.toByteArray()));
                watermarkImages(lastImage);

        } else if ((widths & heights) <= 1300) {
             watermarkImages(sourceImgage);
        }
    }
    */
    public void resizeIMagestoAdapter(){

        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmaps.compress(Bitmap.CompressFormat.JPEG, 70, stream);
            Bitmap equals = BitmapFactory.decodeStream(new ByteArrayInputStream(stream.toByteArray()));
            int heig = equals.getHeight();
            int widt = equals.getWidth();
            if (heig > widt) {
                recy = Bitmap.createScaledBitmap(equals, 160, 240, false);
            }
            if (heig < widt) {
                recy = Bitmap.createScaledBitmap(equals, 240, 160, false);
            }
            if (heig == widt) {
                recy = Bitmap.createScaledBitmap(equals, 240, 240, false);
            }
           /*
                    String savedImageURL = MediaStore.Images.Media.insertImage(
                            getContentResolver(),
                            recy,
                            String.valueOf(Calendar.getInstance().getTimeInMillis()),
                            "Image of bird");
            */
            //   sdCards(recy);
            array.add(recy);
            recyclerAdapter.notifyDataSetChanged();

            Log.e("arraysize", String.valueOf(array.size()));

       //     updateBitmap(array);
        }catch (Exception e){
            e.printStackTrace();
            Log.e("ExceptionResize  ::",e.getMessage());
        }
    }


/*
    void updateBitmap(List<Bitmap> array){
        recyclerAdapter.notifyDataSetChanged();
        for(Bitmap bitmap : array){
            if(!bitmapArray.contains(bitmap)){
                bitmapArray.add(bitmap);
                Log.e("bitmapArraySize", String.valueOf(bitmapArray.size()));

                recyclerAdapter.notifyItemInserted(bitmapArray.size()-1);

                recyclerAdapter.notifyDataSetChanged();
            }
        }
    }

 */
/*
    public void watermarkImages(Bitmap waterbitmaps){
        mutBitArray=new ArrayList<>();
        Bitmap waterMark = ((BitmapDrawable) ResourcesCompat.getDrawable(getApplicationContext().getResources(), R.drawable.p, null)).getBitmap();
        Bitmap waterM = Bitmap.createScaledBitmap(waterMark, 200, 69, false);
        mutableBitmap = waterbitmaps.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(mutableBitmap);
        Matrix matrix = new Matrix();
        matrix.postTranslate(waterbitmaps.getWidth() - 230, waterbitmaps.getHeight() - 99);
        canvas.drawBitmap(waterM, matrix, null);
        canvas.save();
        mutBitArray.add(mutableBitmap);
        Log.e("mutBitArraySize", String.valueOf(mutBitArray.size()));

        Log.d("the watermarked Bitmaps", String.valueOf(mutableBitmap));
        Log.d("Mutable Bitmap", String.valueOf(mutableBitmap));
    }

    private void uploadImage() {
        progressDialog.show();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        for (int i=0; i<mutBitArray.size(); i++) {

            mutBitArray.get(i).compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] imageInByte=byteArrayOutputStream.toByteArray();

            encodedImage=Base64.encodeToString(imageInByte, Base64.DEFAULT);
            imgName=String.valueOf(Calendar.getInstance().getTimeInMillis());

            Toast.makeText(this, encodedImage, Toast.LENGTH_SHORT).show();

            ApiInterface apiInterface = RetroClient.getRetrofit().create(ApiInterface.class);
            Call<ResponsePOJO> call = apiInterface.uploadIm(imgName, encodedImage);
            call.enqueue(new Callback<ResponsePOJO>() {

                @Override
                public void onResponse(Call<ResponsePOJO> call, retrofit2.Response<ResponsePOJO> response) {
                    progressDialog.dismiss();

                    if (response.isSuccessful()) {

                        Toast.makeText(MainActivity.this, "Successfull" + response.message(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Not Successfull Response", Toast.LENGTH_SHORT).show();
                        Log.d("code Response", String.valueOf(response.code()));
                        String po = String.valueOf(response.code());
                        //  apiInterface.uploadIm(apiName,apiIMage);
                        editText.setText(po);
                    }
                }

                @Override
                public void onFailure(Call<ResponsePOJO> call, Throwable t) {
                    progressDialog.dismiss();
                    Log.d("onFailure logs", t.getMessage());
                    Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    editText.setText(t.getMessage());
                }
            });
        }
    }
*/

    @Override
    public void onItemClicks(int position) {
        Toast.makeText(this, String.valueOf(array.get(position)), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onLongItemClicks(int position) {
        uriList.remove(position);
        array.remove(position);
        recyclerAdapter.notifyDataSetChanged();

        //  mutBitArray.remove(mutBitArray.size()-1);
  //      bitmapArray.remove(bitmapArray.size()-1);
        recyclerAdapter.notifyItemRemoved(position);
        recyclerAdapter.notifyItemRemoved(position);
  //      recyclerAdapter.notifyItemRemoved(mutBitArray.size()-1);
       // recyclerAdapter.notifyItemRemoved(bitmapArray.size()-1);
        recyclerAdapter.notifyDataSetChanged();

        recyclerAdapter.notifyItemChanged(position);
        recyclerAdapter.notifyItemChanged(position);
  //      recyclerAdapter.notifyItemChanged(mutBitArray.size()-1);
   //     recyclerAdapter.notifyItemChanged(bitmapArray.size()-1);



        recyclerAdapter.notifyDataSetChanged();
    }
}
