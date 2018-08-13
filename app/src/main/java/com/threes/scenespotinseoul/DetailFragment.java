package com.threes.scenespotinseoul;

import static android.app.Activity.RESULT_OK;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.threes.scenespotinseoul.data.AppDatabase;
import com.threes.scenespotinseoul.data.dao.MediaDao;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailFragment extends Fragment {
  private final int GALLERY_CODE = 1111;
  ImageView media_im;
  View line1;
  ImageView pic;
  ImageButton came;
  ImageButton uplo;
  TextView te;
  String mImageName;
  int CAMERACODE = 531;
  private Uri photoUri;
  private String currentPhotoPath;

  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceStat) {
    View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
    media_im = rootView.findViewById(R.id.media_image);
    line1 = rootView.findViewById(R.id.line1);
    pic = rootView.findViewById(R.id.picture);
    came = rootView.findViewById(R.id.camera);
    uplo = rootView.findViewById(R.id.upload);

    AppDatabase db = AppDatabase.getInstance(getActivity());
    MediaDao dao = db.mediaDao();

    came.setOnClickListener(
        new View.OnClickListener() {
          public void onClick(View v) {
            selectPhoto();
          }
        });

    uplo.setOnClickListener(
        new View.OnClickListener() {
          public void onClick(View v) {
            selectGalloy();
          }
        });
    return rootView;
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == CAMERACODE && resultCode == RESULT_OK) {
      getPictureForPhoto();
    } else if (requestCode == GALLERY_CODE && resultCode == RESULT_OK) {
      sendPicture(data.getData());
    }
  }

  private void selectPhoto() {
    String state = Environment.getExternalStorageState();
    if (Environment.MEDIA_MOUNTED.equals(state)) {
      Intent intent = new Intent((MediaStore.ACTION_IMAGE_CAPTURE));
      if (intent.resolveActivity(getContext().getPackageManager()) != null) {
        File photoFile = null;
        try {
          photoFile = createImageFile();
        } catch (IOException e) {

        }
        if (photoFile != null) {
          photoUri =
              FileProvider.getUriForFile(getContext(), getContext().getPackageName(), photoFile);
          intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
          startActivityForResult(intent, CAMERACODE);
        }
      }
    }
  }

  private File createImageFile() throws IOException {
    File dir =
        new File(
            Environment.getExternalStorageDirectory()
                + "/Android/data/com.threes.scenespotinseoul/files/Pictures/");
    if (!dir.exists()) {
      dir.mkdirs();
    }
    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    mImageName = timeStamp + ".png";
    File storageDir =
        new File(
            Environment.getExternalStorageDirectory().getAbsoluteFile()
                + "/Android/data/com.threes.scenespotinseoul/files/Pictures/"
                + mImageName);
    currentPhotoPath = storageDir.getAbsolutePath();
    return storageDir;
  }

  private void getPictureForPhoto() {
    Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath);
    ExifInterface exif = null;
    try {
      exif = new ExifInterface(currentPhotoPath);
    } catch (IOException e) {
      e.printStackTrace();
      ;
    }
    int exifOrientation;
    int exifDegree;

    if (exif != null) {
      exifOrientation =
          exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
      exifDegree = exifOrientationToDegrees(exifOrientation);
    } else {
      exifDegree = 0;
    }
    pic.setImageBitmap(rotate(bitmap, exifDegree));
  }

  private void selectGalloy() {
    Intent intent = new Intent(Intent.ACTION_PICK);
    intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    intent.setType("image/*");
    startActivityForResult(intent, GALLERY_CODE);
  }

  private void sendPicture(Uri imgUri) {
    String imagePath = getRealPathFromURI(imgUri);
    ExifInterface exif = null;
    try {
      exif = new ExifInterface(imagePath);
    } catch (IOException e) {
      e.printStackTrace();
    }
    int exifOrientation =
        exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
    int exifDegree = exifOrientationToDegrees(exifOrientation);

    Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
    pic.setImageBitmap(rotate(bitmap, exifDegree));
  }

  private int exifOrientationToDegrees(int exifOrientation) {
    if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
      return 90;
    } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
      return 180;
    } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
      return 270;
    }
    return 0;
  }

  private Bitmap rotate(Bitmap src, float degree) {
    Matrix matrix = new Matrix();
    matrix.postRotate(degree);
    return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
  }

  private String getRealPathFromURI(Uri contentUri) {
    int column_index = 0;
    String[] proj = {MediaStore.Images.Media.DATA};
    Cursor cursor = getContext().getContentResolver().query(contentUri, proj, null, null, null);
    if (cursor.moveToFirst()) {
      column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
    }
    return cursor.getString(column_index);
  }
}
