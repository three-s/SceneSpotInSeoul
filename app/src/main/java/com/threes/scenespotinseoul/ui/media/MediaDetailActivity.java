package com.threes.scenespotinseoul.ui.media;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.app.ActionBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.threes.scenespotinseoul.R;
import com.threes.scenespotinseoul.data.AppDatabase;
import com.threes.scenespotinseoul.data.model.Media;
import com.threes.scenespotinseoul.data.model.MediaTag;
import com.threes.scenespotinseoul.data.model.Scene;
import com.threes.scenespotinseoul.data.model.Tag;
import com.volokh.danylo.hashtaghelper.HashTagHelper;

import java.util.ArrayList;
import java.util.List;

import static com.threes.scenespotinseoul.utilities.AppExecutorsHelperKt.runOnDiskIO;
import static com.threes.scenespotinseoul.utilities.AppExecutorsHelperKt.runOnMain;

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


        // media_id = getIntent().getIntExtra("mediaID", 1); //"mediaID" 키값으로 아이디 값을 받아옴, 디폴트 값은 1
        media_id = 0; // 임의로 아이디값 설정

        // 해당 미디어 명장면 리사이클러뷰 처리
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        //뒤로가기 버튼 추가
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setHomeButtonEnabled(true);

        // 해시태그 헬퍼 시도해봄
//        mHashTagHelper = HashTagHelper.Creator.create(getResources().getColor(R.color.colorPrimary), new HashTagHelper.OnHashTagClickListener() {
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
                    Media mMedia = db.mediaDao().loadByName("무한도전");
                    Log.e("scene", Integer.toString(mMedia.getId()));
                    List<Scene> scene = new ArrayList<>();
                    for (int i = 0; i < 30; i++)
                        scene.add(db.sceneDao().loadByMediaId(mMedia.getId()).get(0));

                    db.mediaTagDao().loadByMediaId(mMedia.getId());

                    List<MediaTag> mediaTags = db.mediaTagDao().loadByMediaId(mMedia.getId());
                    int media_tag_id = mediaTags.get(0).getTagId();
                    Log.e("미디어 태그 테스트", String.valueOf(media_tag_id));
                    Tag media_tag = db.tagDao().loadById(media_tag_id);
                    Log.e("미디어 태그", media_tag.getName());

                    runOnMain(
                            () -> {
                                MediaAdapter adapter = new MediaAdapter(scene);
                                recyclerView.setAdapter(adapter);
                                recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
                                recyclerView.setHasFixedSize(true);

                                // 미디어 대표 이미지 세팅
                                RequestOptions requestOptions = new RequestOptions()
                                        .placeholder(android.R.color.darker_gray)
                                        .centerCrop();

                                Glide.with(this)
                                        .load(mMedia.getImage())
                                        .apply(requestOptions)
                                        .into(mMedia_image);

                                // 미디어 타이틀 세팅
                                mMedia_title.setText(mMedia.getName());

                                // 미디어 상세설명 세팅
                                mMedia_detail.setText(mMedia.getDesc());
                                int tLineCount = mMedia_detail.getLineCount();
                                mMedia_detail.setMaxLines(2);
                                mMedia_detail.setEllipsize(TextUtils.TruncateAt.END);

                                mMedia_detail.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        mMedia_detail.setMaxLines(tLineCount);
                                        mMedia_simpleText.setVisibility(View.VISIBLE);
                                    }
                                });

                                //미디어 간략히보기 이벤트
                                mMedia_simpleText.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        mMedia_detail.setMaxLines(2);
                                        mMedia_simpleText.setVisibility(View.GONE);
                                    }
                                });

                                // 미디어 해시티그 세팅
                                mMedia_hash_tag.setText(media_tag.getName());
//                                mHashTagHelper.handle(mMedia_hash_tag);
                            });
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Toast.makeText(this, "뒤로가기 이벤트", Toast.LENGTH_LONG).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
