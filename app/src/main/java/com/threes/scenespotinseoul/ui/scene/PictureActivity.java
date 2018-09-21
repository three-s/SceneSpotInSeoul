package com.threes.scenespotinseoul.ui.scene;

import static com.threes.scenespotinseoul.utilities.ConstantsKt.EXTRA_SCENE_ID;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.threes.scenespotinseoul.R;
import com.threes.scenespotinseoul.data.AppDatabase;

public class PictureActivity extends AppCompatActivity {

  String SceneId;
  private ImageView picture;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_picture);

    picture = findViewById(R.id.into_picture);

    ActionBar supportActionBar = getSupportActionBar();
    if (supportActionBar != null) {
      supportActionBar.setDisplayHomeAsUpEnabled(true);
      supportActionBar.setHomeButtonEnabled(true);
      supportActionBar.setTitle("");
    }

    Intent intent = getIntent();
    //Log.e("받은intent", intent.hasExtra(EX)+"");
    if (intent != null && intent.hasExtra(EXTRA_SCENE_ID)) {
      SceneId = intent.getStringExtra(EXTRA_SCENE_ID);

      if (SceneId == null) {
        Log.e("PictureActivity", "Can't receive scene id");
        finish();
      }
      AppDatabase db = AppDatabase.getInstance(this);
      db.sceneDao()
          .loadByIdWithLive(SceneId)
          .observe(this,
              scene -> {
                if (scene != null) {
                  Log.v("URI", scene.getUploadedImage());
                  Glide.with(this)
                      .load(Uri.parse(scene.getUploadedImage()))
                      .apply(new RequestOptions().fitCenter())
                      .into(picture);

                }
              });
    }
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      finish();
    }
    return super.onOptionsItemSelected(item);
  }
}
