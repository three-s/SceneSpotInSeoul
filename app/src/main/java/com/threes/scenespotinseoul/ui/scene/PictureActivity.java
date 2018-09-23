package com.threes.scenespotinseoul.ui.scene;

import static com.threes.scenespotinseoul.utilities.ConstantsKt.EXTRA_SCENE_ID;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.chrisbanes.photoview.PhotoView;
import com.threes.scenespotinseoul.R;
import com.threes.scenespotinseoul.data.AppDatabase;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PictureActivity extends AppCompatActivity {

  private PhotoView mPicture;
  private TextView mTvName;
  private TextView mTvDate;
  private ImageButton mBtnNavigateUp;
  private View mShadowView;
  private ConstraintLayout mContainer;

  private boolean isImmersive;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_picture);
    mPicture = findViewById(R.id.into_picture);
    mTvName = findViewById(R.id.tv_name);
    mTvDate = findViewById(R.id.tv_date);
    mContainer = findViewById(R.id.container);
    mBtnNavigateUp = findViewById(R.id.btn_navigate_up);
    mShadowView = findViewById(R.id.shadow);

    mBtnNavigateUp.setOnClickListener(view -> finish());

    mPicture.setOnClickListener(view -> {
      if (isImmersive) {
        showSystemUI();
        showUI();
      } else {
        hideSystemUI();
        hideUI();
      }
      isImmersive = !isImmersive;
    });
    showSystemUI();

    Intent intent = getIntent();
    //Log.e("받은intent", intent.hasExtra(EX)+"");
    if (intent != null && intent.hasExtra(EXTRA_SCENE_ID)) {
      String sceneId = intent.getStringExtra(EXTRA_SCENE_ID);

      if (sceneId == null) {
        Log.e("PictureActivity", "Can't receive scene id");
        finish();
      }
      AppDatabase db = AppDatabase.getInstance(this);
      db.sceneDao()
          .loadByIdWithLive(sceneId)
          .observe(this,
              scene -> {
                if (scene != null) {
                  Log.v("URI", scene.getUploadedImage());
                  Glide.with(this)
                      .load(Uri.parse(scene.getUploadedImage()))
                      .apply(new RequestOptions().fitCenter())
                      .into(mPicture);
                  mTvName.setText(scene.getDesc());
                  mTvDate.setText(formatDate(scene.getUploadedDate()));
                }
              });
    }
  }

  @SuppressLint("SimpleDateFormat")
  private String formatDate(long timestamp) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy. MM. dd.");
    return dateFormat.format(new Date(timestamp));
  }

  private void showUI() {
    TransitionManager.beginDelayedTransition(mContainer);
    ConstraintSet constraintSet = new ConstraintSet();
    constraintSet.clone(mContainer);
    constraintSet.setVisibility(mShadowView.getId(), View.VISIBLE);
    constraintSet.setVisibility(mBtnNavigateUp.getId(), View.VISIBLE);
    constraintSet.setVisibility(mTvName.getId(), View.VISIBLE);
    constraintSet.setVisibility(mTvDate.getId(), View.VISIBLE);
    constraintSet.applyTo(mContainer);
  }

  private void hideUI() {
    TransitionManager.beginDelayedTransition(mContainer);
    ConstraintSet constraintSet = new ConstraintSet();
    constraintSet.clone(mContainer);
    constraintSet.setVisibility(mShadowView.getId(), View.GONE);
    constraintSet.setVisibility(mBtnNavigateUp.getId(), View.GONE);
    constraintSet.setVisibility(mTvName.getId(), View.GONE);
    constraintSet.setVisibility(mTvDate.getId(), View.GONE);
    constraintSet.applyTo(mContainer);
  }

  private void showSystemUI() {
    View decorView = getWindow().getDecorView();
    decorView.setSystemUiVisibility(
        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
  }

  private void hideSystemUI() {
    View decorView = getWindow().getDecorView();
    decorView.setSystemUiVisibility(
        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_FULLSCREEN
            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
  }
}
