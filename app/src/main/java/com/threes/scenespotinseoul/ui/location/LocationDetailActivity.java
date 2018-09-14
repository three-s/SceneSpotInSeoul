package com.threes.scenespotinseoul.ui.location;

import static com.threes.scenespotinseoul.utilities.AppExecutorsHelperKt.runOnDiskIO;
import static com.threes.scenespotinseoul.utilities.AppExecutorsHelperKt.runOnMain;
import static com.threes.scenespotinseoul.utilities.ConstantsKt.EXTRA_LOCATION_ID;
import static com.threes.scenespotinseoul.utilities.ItemOffsetDecorationKt.DIR_RIGHT;
import static com.threes.scenespotinseoul.utilities.ItemOffsetDecorationKt.OFFSET_NORMAL;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
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
import com.threes.scenespotinseoul.data.model.Location;
import com.threes.scenespotinseoul.data.model.LocationTag;
import com.threes.scenespotinseoul.data.model.Media;
import com.threes.scenespotinseoul.data.model.Scene;
import com.threes.scenespotinseoul.data.model.Tag;
import com.threes.scenespotinseoul.utilities.ItemOffsetDecoration;
import com.volokh.danylo.hashtaghelper.HashTagHelper;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class LocationDetailActivity extends AppCompatActivity {

  private String location_id;

  private Bitmap bt;

  private ImageView mLocation_image;
  private TextView mLocation_hash_tag;
  private TextView mLocation_name;
  private TextView mLocation_address;
  private TextView mLocation_detail;
  private HashTagHelper mHashTagHelper;
  private ActionBar mActionBar;
  private TextView mLocation_simpleText;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_location_detail);

    mLocation_image = findViewById(R.id.location_image);
    mLocation_hash_tag = findViewById(R.id.location_hash_tag);
    mLocation_name = findViewById(R.id.location_name);
    mLocation_address = findViewById(R.id.location_address);
    mLocation_detail = findViewById(R.id.location_detail);
    mLocation_simpleText = findViewById(R.id.location_simple);
    mLocation_simpleText.setText("간략히보기");
    mLocation_simpleText.setVisibility(View.GONE);

    //TEST를 위한 주석처리
    Intent intent = getIntent();
    if (intent != null && intent.hasExtra(EXTRA_LOCATION_ID)) {
      location_id = intent.getStringExtra(EXTRA_LOCATION_ID);
    }

    // 해당 미디어 명장면 리사이클러뷰 처리
    RecyclerView recyclerView_scene = findViewById(R.id.location_recyclerView_scene);
    recyclerView_scene.addItemDecoration(new ItemOffsetDecoration(DIR_RIGHT, OFFSET_NORMAL));
    RecyclerView recyclerView_media = findViewById(R.id.location_recyclerView_media);
    recyclerView_media.addItemDecoration(new ItemOffsetDecoration(DIR_RIGHT, OFFSET_NORMAL));

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
          Location mLocation = db.locationDao().loadById(location_id);
          List<Scene> scenes = db.sceneDao().loadByLocationId(location_id);
          List<LocationTag> locationTags = db.locationTagDao().loadByLocationId(mLocation.getUuid());
          HashSet<Media> medias = new HashSet<>();
          for (Scene _scene : scenes){
              medias.add(db.mediaDao().loadById(_scene.getMediaId()));
          }
          List<Tag> tags = new ArrayList<>();
          for (int i = 0; i < locationTags.size(); i++) {
            tags.add(db.tagDao().loadById(locationTags.get(i).getTagId()));
            Log.e("태그", tags.get(i).getName());
          }

          runOnMain(
              () -> {
                LocationSceneAdapter adapter_scene = new LocationSceneAdapter(scenes);
                recyclerView_scene.setAdapter(adapter_scene);
                recyclerView_scene.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
                recyclerView_scene.setHasFixedSize(true);
                LocationMediaAdapter adapter_media = new LocationMediaAdapter(medias);
                recyclerView_media.setAdapter(adapter_media);
                recyclerView_media.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
                recyclerView_media.setHasFixedSize(true);

                //LocationMediaAdapter

                // 미디어 대표 이미지 세팅
                RequestOptions requestOptions =
                    new RequestOptions().placeholder(android.R.color.darker_gray).centerCrop();

                Glide.with(this).load(mLocation.getImage()).apply(requestOptions).into(mLocation_image);

                // 미디어 타이틀 세팅
                mLocation_name.setText(mLocation.getName());

                mLocation_address.setText(mLocation.getAddress());

                // 미디어 상세설명 세팅
                mLocation_detail.setText(mLocation.getDesc());

                if (mLocation_detail.getLineCount() > 2) {
                  int tLineCount = mLocation_detail.getLineCount();
                  mLocation_detail.setMaxLines(2);
                  mLocation_detail.setEllipsize(TextUtils.TruncateAt.END);

                  mLocation_detail.setOnClickListener(
                      v -> {
                        mLocation_detail.setMaxLines(tLineCount);
                        mLocation_simpleText.setVisibility(View.VISIBLE);
                      });

                  // 미디어 간략히보기 이벤트
                  mLocation_simpleText.setOnClickListener(
                      v -> {
                        mLocation_detail.setMaxLines(2);
                        mLocation_simpleText.setVisibility(View.GONE);
                      });
                }

                // 미디어 해시티그 세팅
                StringBuilder mTag = new StringBuilder();
                for (Tag tag : tags) {
                  mTag.append("#").append(tag.getName()).append(" ");
                }
                mLocation_hash_tag.setText(mTag.toString());
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
