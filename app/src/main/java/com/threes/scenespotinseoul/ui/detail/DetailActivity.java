package com.threes.scenespotinseoul.ui.detail;



import static android.widget.ImageView.ScaleType.CENTER_CROP;
import static com.threes.scenespotinseoul.utilities.AppExecutorsHelperKt.runOnDiskIO;
import static com.threes.scenespotinseoul.utilities.AppExecutorsHelperKt.runOnMain;

import com.threes.scenespotinseoul.R;
import com.threes.scenespotinseoul.data.*;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.threes.scenespotinseoul.data.AppDatabase;
import com.threes.scenespotinseoul.data.dao.MediaDao;
import com.threes.scenespotinseoul.data.dao.SceneDao;
import com.threes.scenespotinseoul.data.model.Media;
import com.threes.scenespotinseoul.data.model.Scene;
import com.threes.scenespotinseoul.data.model.SceneTag;
import com.threes.scenespotinseoul.data.model.Tag;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    View line1;
    ImageView mediaM;
    ImageButton pic;
    TextView SceneName;
    TextView TagName;
    TextView guid;
    View line2;
    private Uri photoUri;
    private final int GALLERY_CODE = 1111;
    private final int CAMERACODE = 531;
    private String mImageName;
    private String currentPhotoPath;


    String[] permissions = new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION};

    private FloatingActionButton fab;
    public void onCreate(Bundle savedInstanceStat){
        super.onCreate(savedInstanceStat);
        setContentView(R.layout.activity_detail);
        hasPermissions();
        mediaM = (ImageView)findViewById(R.id.media_image);
        pic = (ImageButton)findViewById(R.id.picture);
        line1 = (View)findViewById(R.id.line1);
        line2 = (View)findViewById(R.id.line2);
        SceneName = (TextView)findViewById(R.id.Scenename);
        TagName = (TextView)findViewById(R.id.tagView);
        guid= (TextView)findViewById(R.id.guide);
        fab = (FloatingActionButton)findViewById(R.id.fab);

        //데이터베이스 작업
        AppDatabase db = AppDatabase.getInstance(this);

        runOnDiskIO(
                ()->{
                    Scene se = db.sceneDao().loadById(1);
                    List <SceneTag> st = db.sceneTagDao().loadBySceneId(se.getId());
                    List <Tag> tags = new ArrayList<>();
                    for(int i=0;i<st.size();i++){
                        tags.add(db.tagDao().loadById(st.get(i).getTagId()));
                    }
//                    int scene_tag_id = st.get(0).getTagId();
//                    Tag scene_tag = db.tagDao().loadById(scene_tag_id);

                    runOnMain(() -> {
                        String textTag=null;
                        for(int i=0;i<tags.size();i++){
                            textTag = ""+tags.get(i).getName();
                        }

                        TagName.setText(textTag);
                        SceneName.setText(se.getDesc());
                    });

        });

        db.sceneDao().loadByIdWithLive(1).observe(
                this,Scene -> {
                    if(Scene != null) {
                        Log.v("URL--------------------", Scene.getImage());
                        Glide.with(this).load(Uri.parse(Scene.getImage())).centerCrop()
                                .into(mediaM);

                    }
                    if(Scene.getCapturedImage()!=null){
                        //Log.v("이미지 URL","NULL");
                        Glide.with(this).load(Uri.parse(Scene.getCapturedImage())).centerCrop()
                                .into(pic);
                    }


        });





        fab.setOnClickListener(new ImageView.OnClickListener() {

            public void onClick(View v){
                //startActivity(new Intent(DetailActivity.class, ));
                Log.v("버튼이 클릭되었는가?","버튼클릭");
            }


        });


        pic.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v){
                CharSequence info[] = new CharSequence[] {"사진을 찍어 업로드", "갤러리에서 업로드","취소" };


                AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);

                builder.setTitle("사진 업로드");

                builder.setItems(info, new DialogInterface.OnClickListener() {

                    @Override

                    public void onClick(DialogInterface dialog, int which) {
                        switch(which)

                        {

                            case 0:
                                int permissionCheck1 = ContextCompat.checkSelfPermission(DetailActivity.this,permissions[0]);
                                int permissionCheck2 = ContextCompat.checkSelfPermission(DetailActivity.this,permissions[1]);
                                int permissionCheck3 = ContextCompat.checkSelfPermission(DetailActivity.this,permissions[2]);
                                if(permissionCheck1 == PackageManager.PERMISSION_DENIED && permissionCheck2 == PackageManager.PERMISSION_DENIED && permissionCheck3 == PackageManager.PERMISSION_DENIED){
                                    Snackbar.make(v, "권한이 없어서 기능을 이용할 수 없습니다.", Snackbar.LENGTH_SHORT).show();
                                }
                                else{
                                    selectPhoto();
                                }

                                break;


                            case 1:

                                permissionCheck3 = ContextCompat.checkSelfPermission(DetailActivity.this,permissions[2]);
                                if(permissionCheck3 == PackageManager.PERMISSION_DENIED){

                                    Snackbar.make(v, "권한이 없어서 기능을 이용할 수 없습니다.", Snackbar.LENGTH_SHORT).show();
                                }
                                else{
                                    selectGalloy();
                                }
                                break;

                            case 2:
                                dialog.dismiss();
                                break;
                        }

                        dialog.dismiss();

                    }

                });

                builder.show();


            }


        });




    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == CAMERACODE && resultCode == RESULT_OK){
            getPictureForPhoto();
            //setImage(data.getData());
        }
        else if(requestCode == GALLERY_CODE && resultCode ==RESULT_OK){
            sendPicture(data.getData());
            //setImage(data.getData());
        }
    }

//    private void setImage(Uri photoUri){
//
//        AppDatabase db = AppDatabase.getInstance(this);
//        db.sceneDao().loadByRowIdWithLive(0).observe(
//                this,Scene -> {
//                    if(Scene.getCapturedImage()!=null){
//                        Glide.with(this).load(Uri.parse(Scene.getCapturedImage()))
//                                .into(mediaM);
//                    }
//                });
//    }

    private  void selectPhoto(){
        String state = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state)){
            Intent intent = new Intent((MediaStore.ACTION_IMAGE_CAPTURE));
            if(intent.resolveActivity(getPackageManager())!=null){
                File photoFile = null;
                try{
                    photoFile = createImageFile();
                }catch(IOException e){

                }
                if(photoFile!=null){
                    photoUri = FileProvider.getUriForFile(this,getPackageName(),photoFile);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,photoUri);
                    startActivityForResult(intent,CAMERACODE);
                }
            }
        }
    }

    private  File createImageFile() throws IOException{

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        mImageName = timeStamp;

        File storageDir =getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File image = new File(storageDir+"/"+mImageName+".jpg");

        currentPhotoPath = image.getAbsolutePath();

        return image;
    }


    private void getPictureForPhoto(){
//        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath);
//        ExifInterface exif = null;
//        try{
//            exif = new ExifInterface(currentPhotoPath);
//        }
//        catch(IOException e){
//            e.printStackTrace();;
//        }
//        int exifOrientation;
//        int exifDegree;
//
//        if(exif != null){
//
//            exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,ExifInterface.ORIENTATION_NORMAL);
//            exifDegree = exifOrientationToDegrees(exifOrientation);
//        }
//        else{
//            exifDegree=0;
//        }
//        ((ImageView) findViewById(R.id.picture)).setImageBitmap(rotate(bitmap,exifDegree));
//        ((ImageView) findViewById(R.id.picture)).setScaleType(ImageView.ScaleType.CENTER_CROP);
//

        AppDatabase db = AppDatabase.getInstance(this);
        runOnDiskIO(() -> {
            Scene se= db.sceneDao().loadById(1);

            se.setCaptured(true);
            se.setCapturedImage(photoUri.toString());
            db.sceneDao().update(se);
        });
    }



    private void selectGalloy(){

        Intent intent = new Intent(Intent.ACTION_PICK);

        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        intent.setType("image/*");

        startActivityForResult(intent, GALLERY_CODE);

    }


    private void sendPicture(Uri imgUri){
//        String imagePath = getRealPathFromURI(imgUri);
//        ExifInterface exif = null;
//        try{
//            exif = new ExifInterface(imagePath);
//        }
//        catch(IOException e){
//            e.printStackTrace();
//        }
//        int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,ExifInterface.ORIENTATION_NORMAL);
//        int exifDegree = exifOrientationToDegrees(exifOrientation);
//
//        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
//        ((ImageView) findViewById(R.id.picture)).setImageBitmap(rotate(bitmap,exifDegree));
        //((ImageView) findViewById(R.id.picture)).setScaleType(ImageView.ScaleType.CENTER_CROP);


        AppDatabase db = AppDatabase.getInstance(this);
        runOnDiskIO(() -> {
            Scene se= db.sceneDao().loadById(1);

            se.setCaptured(true);

            se.setCapturedImage(imgUri.toString());
            db.sceneDao().update(se);



        });

    }

    private int exifOrientationToDegrees(int exifOrientation){
        if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_90){
            return 90;
        }
        else if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_180){
            return 180;
        }
        else if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_270){
            return 270;
        }
        return 0;
    }

    private Bitmap rotate(Bitmap src, float degree){
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(src,0,0,src.getWidth(),src.getHeight(),matrix,true);
    }

    private String getRealPathFromURI(Uri contentUri){
        int column_index = 0;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor =  getContentResolver().query(contentUri,proj,null,null,null);

        if(cursor.moveToFirst()){

            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        }
        return cursor.getString(column_index);
    }


    private void hasPermissions(){

        int i=0;
        if(Build.VERSION.SDK_INT>=23){
            int permissionCheck = ContextCompat.checkSelfPermission(this,permissions[i]);

            if(permissionCheck == PackageManager.PERMISSION_DENIED){

                if(!shouldShowRequestPermissionRationale(permissions[i])){

                    AlertDialog.Builder dialog = new AlertDialog.Builder(this);

                    dialog.setTitle("권한이 필요합니다.")
                            .setMessage("어플의 기능을 이용하기 위해서 권한이 필요합니다.")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int which) {
                                    requestPermissions(permissions,1); }
                            })
                            .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            })
                            .create()
                            .show();

                }
                else{
                    requestPermissions(permissions,1);
                }
            }
        }
        else{
            //마시멜로우 이하버전
        }
    }






}


