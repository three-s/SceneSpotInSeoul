package com.threes.scenespotinseoul.ui.media;

import static com.threes.scenespotinseoul.utilities.AppExecutorsHelperKt.runOnDiskIO;
import static com.threes.scenespotinseoul.utilities.AppExecutorsHelperKt.runOnMain;
import static com.threes.scenespotinseoul.utilities.ConstantsKt.EXTRA_MEDIA_ID;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.threes.scenespotinseoul.R;
import com.threes.scenespotinseoul.data.AppDatabase;
import com.threes.scenespotinseoul.data.model.Media;
import com.threes.scenespotinseoul.data.model.MediaTag;
import com.threes.scenespotinseoul.data.model.Location;
import com.threes.scenespotinseoul.data.model.Scene;
import com.threes.scenespotinseoul.data.model.Tag;
import com.volokh.danylo.hashtaghelper.HashTagHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;

public class MediaDetailActivity extends AppCompatActivity {

  private int media_id;

  private Bitmap bt;

  private ImageView mMedia_image;
  private TextView mMedia_hash_tag;
  private TextView mMedia_title;
  private TextView mMedia_detail;
  private HashTagHelper mHashTagHelper;
  private ActionBar mActionBar;
  private TextView mMedia_simpleText;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_media_detail);

    mMedia_image = findViewById(R.id.media_image);
    mMedia_hash_tag = findViewById(R.id.media_hash_tag);
    mMedia_title = findViewById(R.id.media_title);
    mMedia_detail = findViewById(R.id.media_detail);
    mMedia_simpleText = findViewById(R.id.simple);
    mMedia_simpleText.setText("간략히보기");
    mMedia_simpleText.setVisibility(View.GONE);

    Intent intent = getIntent();
    if (intent != null && intent.hasExtra(EXTRA_MEDIA_ID)) {
      media_id = intent.getIntExtra(EXTRA_MEDIA_ID, 0);
    }

    // 해당 미디어 명장면 리사이클러뷰 처리
    RecyclerView recyclerView_scene = findViewById(R.id.media_recyclerView_scene);
    RecyclerView recyclerView_location = findViewById(R.id.media_recyclerView_location);

    // 뒤로가기 버튼 추가
    mActionBar = getSupportActionBar();
    mActionBar.setDisplayHomeAsUpEnabled(true);
    mActionBar.setHomeButtonEnabled(true);

    // 해시태그 헬퍼 시도해봄
    //        mHashTagHelper =
    // HashTagHelper.Creator.create(getResources().getColor(R.color.colorPrimary), new
    // HashTagHelper.OnHashTagClickListener() {
    //            @Override
    //            public void onHashTagClicked(String hashTag) {
    //                Toast.makeText(getApplicationContext(), hashTag, Toast.LENGTH_SHORT).show();
    //            }
    //        });

    // 미디어 데이터 가져옴 Executor 사용
    AppDatabase db = AppDatabase.getInstance(this);
    runOnDiskIO(
        () -> {
          // List<Media> media = db.mediaDao().loadAll();
          Media mMedia = db.mediaDao().loadById(media_id);
          List<Scene> scenes = db.sceneDao().loadByMediaId(media_id);
          List<MediaTag> mediaTags = db.mediaTagDao().loadByMediaId(mMedia.getId());
          HashSet<Location> locations = new HashSet<>();
          for (Scene _scene : scenes){
              locations.add(db.locationDao().loadById(_scene.getLocationId()));
          }
          List<Tag> tags = new ArrayList<>();
          for (int i = 0; i < mediaTags.size(); i++) {
            tags.add(db.tagDao().loadById(mediaTags.get(i).getTagId()));
            Log.e("태그", tags.get(i).getName());
          }

          runOnMain(
              () -> {
                MediaSceneAdapter adapter_scene = new MediaSceneAdapter(scenes);
                recyclerView_scene.setAdapter(adapter_scene);
                recyclerView_scene.setLayoutManager(new GridLayoutManager(this, 3));
                recyclerView_scene.setHasFixedSize(true);
                MediaLocationAdapter adapter = new MediaLocationAdapter(locations);
                recyclerView_location.setAdapter(adapter);
                recyclerView_location.setLayoutManager(new GridLayoutManager(this, 3));
                recyclerView_location.setHasFixedSize(true);

                // 미디어 대표 이미지 세팅
                RequestOptions requestOptions =
                    new RequestOptions().placeholder(android.R.color.darker_gray).centerCrop();

                Glide.with(this).load(mMedia.getImage()).apply(requestOptions).into(mMedia_image);

                // 미디어 타이틀 세팅
                mMedia_title.setText(mMedia.getName());

                // 미디어 상세설명 세팅
                mMedia_detail.setText(mMedia.getDesc());
                int tLineCount = mMedia_detail.getLineCount();
                mMedia_detail.setMaxLines(2);
                mMedia_detail.setEllipsize(TextUtils.TruncateAt.END);

                mMedia_detail.setOnClickListener(
                    v -> {
                      mMedia_detail.setMaxLines(tLineCount);
                      mMedia_simpleText.setVisibility(View.VISIBLE);
                    });

                // 미디어 간략히보기 이벤트
                mMedia_simpleText.setOnClickListener(
                    v -> {
                      mMedia_detail.setMaxLines(2);
                      mMedia_simpleText.setVisibility(View.GONE);
                    });

                // 미디어 해시티그 세팅
                StringBuilder mTag = new StringBuilder();
                for (Tag tag : tags) {
                  mTag.append("#").append(tag.getName()).append(" ");
                }
                mMedia_hash_tag.setText(mTag.toString());
              });
        });
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        finish();
        return true;
    }
    return super.onOptionsItemSelected(item);
  }
}
