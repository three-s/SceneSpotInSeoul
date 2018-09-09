package com.threes.scenespotinseoul.ui.scene;

import static com.threes.scenespotinseoul.utilities.AppExecutorsHelperKt.runOnDiskIO;
import static com.threes.scenespotinseoul.utilities.AppExecutorsHelperKt.runOnMain;
import static com.threes.scenespotinseoul.utilities.ConstantsKt.EXTRA_LOCATION_ID;
import static com.threes.scenespotinseoul.utilities.ConstantsKt.EXTRA_SCENE_ID;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.threes.scenespotinseoul.R;
import com.threes.scenespotinseoul.data.AppDatabase;
import com.threes.scenespotinseoul.data.model.Scene;
import com.threes.scenespotinseoul.data.model.SceneTag;
import com.threes.scenespotinseoul.data.model.Tag;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SceneDetailActivity extends AppCompatActivity {

  private static final int GALLERY_CODE = 1111;
  private static final int CAMERACODE = 531;
  private static final String[] PERMISSIONS =
      new String[]{
          Manifest.permission.CAMERA,
          Manifest.permission.WRITE_EXTERNAL_STORAGE,
          Manifest.permission.READ_EXTERNAL_STORAGE,
          Manifest.permission.ACCESS_FINE_LOCATION
      };
  //map에 전달한 location id 값
  int idfromScenetoMap;
  private ImageView mediaM;
  private ImageView pic;
  private TextView SceneName;
  private TextView TagName;
  private TextView guid;
  private Uri photoUri;
  private FloatingActionButton fab;
  private ActionBar mActionBar;
  private int mSceneId;

  public void onCreate(Bundle savedInstanceStat) {
    super.onCreate(savedInstanceStat);
    setContentView(R.layout.activity_scene_detail);
    mediaM = findViewById(R.id.media_image);
    pic = findViewById(R.id.picture);
    SceneName = findViewById(R.id.Scenename);
    TagName = findViewById(R.id.tagView);
    guid = findViewById(R.id.guide);
    fab = findViewById(R.id.fab);

    mActionBar = getSupportActionBar();
    mActionBar.setDisplayHomeAsUpEnabled(true);
    mActionBar.setHomeButtonEnabled(true);

    Intent intent = getIntent();
    if (intent != null && intent.hasExtra(EXTRA_SCENE_ID)) {
      mSceneId = intent.getIntExtra(EXTRA_SCENE_ID, 0);

      if (mSceneId == 0) {
        Log.e("DetailActivity", "Can't receive scene id");
        finish();
      }
      AppDatabase db = AppDatabase.getInstance(this);
      db.sceneDao()
          .loadByIdWithLive(mSceneId)
          .observe(
              this,
              scene -> {
                if (scene != null) {
                  getTags(db, scene);

                  SceneName.setText(scene.getDesc());
                  idfromScenetoMap = scene.getLocationId();
                  Glide.with(this)
                      .load(Uri.parse(scene.getImage()))
                      .apply(new RequestOptions().centerCrop())
                      .into(mediaM);

                  guid.setVisibility(View.VISIBLE);

                  if (scene.isCaptured()) {
                    guid.setVisibility(View.GONE);
                    Glide.with(this)
                        .load(Uri.parse(scene.getCapturedImage()))
                        .apply(new RequestOptions())
                        .into(pic);
                  }
                }
              });
    }

    fab.setOnClickListener(
        v -> {
          Intent mapIntent = new Intent(this, DetailToMapActivity.class);
          mapIntent.putExtra(EXTRA_LOCATION_ID, idfromScenetoMap);
          startActivity(mapIntent);
        });

    pic.setOnClickListener(
        v -> {
          AppDatabase db = AppDatabase.getInstance(this);
          CharSequence info[] = new CharSequence[]{"사진을 찍어 업로드", "갤러리에서 업로드", "갤러리에서 보기",
              "업로드된 사진 내리기", "취소"};
          AlertDialog.Builder builder = new AlertDialog.Builder(SceneDetailActivity.this);
          builder.setTitle("사진 업로드");
          builder.setItems(
              info,
              (dialog, which) -> {
                switch (which) {
                  case 0:
                    int permissionCheck1 =
                        ContextCompat.checkSelfPermission(SceneDetailActivity.this, PERMISSIONS[0]);
                    int permissionCheck2 =
                        ContextCompat.checkSelfPermission(SceneDetailActivity.this, PERMISSIONS[1]);
                    int permissionCheck3 =
                        ContextCompat.checkSelfPermission(SceneDetailActivity.this, PERMISSIONS[2]);
                    if (permissionCheck1 == PackageManager.PERMISSION_DENIED
                        && permissionCheck2 == PackageManager.PERMISSION_DENIED
                        && permissionCheck3 == PackageManager.PERMISSION_DENIED) {
                      Snackbar.make(v, "권한이 없어서 기능을 이용할 수 없습니다.", Snackbar.LENGTH_SHORT).show();
                    } else {
                      selectPhoto();
                    }
                    break;
                  case 1:
                    permissionCheck3 =
                        ContextCompat.checkSelfPermission(SceneDetailActivity.this, PERMISSIONS[2]);
                    if (permissionCheck3 == PackageManager.PERMISSION_DENIED) {
                      Snackbar.make(v, "권한이 없어서 기능을 이용할 수 없습니다.", Snackbar.LENGTH_SHORT).show();
                    } else {
                      selectGallery();
                    }
                    break;

                  case 2:
                    db.sceneDao()
                        .loadByIdWithLive(mSceneId)
                        .observe(
                            this,
                            scene -> {
                              //Log.v("사진내리고 URL", scene.getCapturedImage());
                              if (scene.getCapturedImage() != null) {
                                String url = scene.getCapturedImage();
                                Intent gintent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                startActivity(gintent);
                              } else {
                                Snackbar.make(v, "이전에 등록된 사진이 없습니다.", Snackbar.LENGTH_SHORT).show();
                              }
                            });

                    break;

                  case 3:
                    //pic.setImageBitmap(null);
                    runOnDiskIO(
                        () -> {
                          Scene scene = db.sceneDao().loadById(mSceneId);
                          scene.setCaptured(false);
                          scene.setCapturedImage(null);
                          db.sceneDao().update(scene);
                        });
                    pic.setImageResource(0);
                    guid.setVisibility(View.VISIBLE);
                    break;

                  case 4:
                    dialog.dismiss();
                    break;


                }
                dialog.dismiss();
              });
          builder.show();
        });
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == CAMERACODE && resultCode == RESULT_OK) {
      getPictureForPhoto();
    } else if (requestCode == GALLERY_CODE && resultCode == RESULT_OK) {
      sendPicture(data.getData());
    }
  }

  private void getTags(AppDatabase db, Scene scene) {
    runOnDiskIO(
        () -> {
          List<SceneTag> st = db.sceneTagDao().loadBySceneId(scene.getId());
          List<Tag> tags = new ArrayList<>();
          for (int i = 0; i < st.size(); i++) {
            tags.add(db.tagDao().loadById(st.get(i).getTagId()));
          }
          runOnMain(
              () -> {
                String textTag = null;
                for (int i = 0; i < tags.size(); i++) {
                  textTag = "" + tags.get(i).getName();
                }
                TagName.setText(textTag);
              });
        });
  }

  private void selectPhoto() {
    String state = Environment.getExternalStorageState();
    if (Environment.MEDIA_MOUNTED.equals(state)) {
      Intent intent = new Intent((MediaStore.ACTION_IMAGE_CAPTURE));
      if (intent.resolveActivity(getPackageManager()) != null) {
        File photoFile;
        photoFile = createImageFile();
        if (photoFile != null) {
          photoUri = FileProvider.getUriForFile(this, getPackageName(), photoFile);
          intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
          startActivityForResult(intent, CAMERACODE);
        }
      }
    }
  }

  private File createImageFile() {
    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    String imageName = timeStamp;
    File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
    File image = new File(storageDir + "/" + imageName + ".jpg");
    String currentPhotoPath = image.getAbsolutePath();
    return image;
  }

  private void getPictureForPhoto() {
    AppDatabase db = AppDatabase.getInstance(this);
    runOnDiskIO(
        () -> {
          Scene scene = db.sceneDao().loadById(mSceneId);
          scene.setCaptured(true);
          scene.setCapturedImage(photoUri.toString());
          db.sceneDao().update(scene);
        });
  }

  private void selectGallery() {
    Intent intent = new Intent(Intent.ACTION_PICK);
    intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    intent.setType("image/*");
    startActivityForResult(intent, GALLERY_CODE);
  }

  private void sendPicture(Uri imgUri) {
    AppDatabase db = AppDatabase.getInstance(this);
    runOnDiskIO(
        () -> {
          Scene scene = db.sceneDao().loadById(mSceneId);
          scene.setCaptured(true);
          scene.setCapturedImage(imgUri.toString());
          db.sceneDao().update(scene);
        });
  }

//  private int exifOrientationToDegrees(int exifOrientation) {
//    switch (exifOrientation) {
//      case ExifInterface.ORIENTATION_ROTATE_90:
//        return 90;
//      case ExifInterface.ORIENTATION_ROTATE_180:
//        return 180;
//      case ExifInterface.ORIENTATION_ROTATE_270:
//        return 270;
//    }
//    return 0;
//  }
//
//  private Bitmap rotate(Bitmap src, float degree) {
//    Matrix matrix = new Matrix();
//    matrix.postRotate(degree);
//    return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
//  }

  private String getRealPathFromURI(Uri contentUri) {
    int column_index = 0;
    String[] proj = {MediaStore.Images.Media.DATA};
    Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);

    if (cursor.moveToFirst()) {

      column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
    }
    return cursor.getString(column_index);
  }

  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        finish();
        return true;
    }
    return super.onOptionsItemSelected(item);
  }
}


