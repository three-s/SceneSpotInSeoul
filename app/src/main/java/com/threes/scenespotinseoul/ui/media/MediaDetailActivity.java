package com.threes.scenespotinseoul.ui.media;

import static com.threes.scenespotinseoul.utilities.AppExecutorsHelperKt.runOnDiskIO;
import static com.threes.scenespotinseoul.utilities.AppExecutorsHelperKt.runOnMain;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.threes.scenespotinseoul.R;
import com.threes.scenespotinseoul.data.AppDatabase;
import com.threes.scenespotinseoul.data.model.Media;
import com.threes.scenespotinseoul.data.model.Scene;
import java.util.List;

public class MediaDetailActivity extends AppCompatActivity {

  private int media_id;

  private Bitmap bt;

  private ImageView mMedia_image;
  private TextView mMedia_hash_tag;
  private TextView mMedia_title;
  private TextView mMedia_detail;
  //  private HashTagHelper mHashTagHelper;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_media_detail);

    mMedia_image = (ImageView) findViewById(R.id.media_image);
    mMedia_hash_tag = (TextView) findViewById(R.id.media_hash_tag);
    mMedia_title = (TextView) findViewById(R.id.media_title);
    mMedia_detail = (TextView) findViewById(R.id.media_detail);

    // media_id = getIntent().getIntExtra("mediaID", 1); //"mediaID" 키값으로 아이디 값을 받아옴, 디폴트 값은 1
    media_id = 0; // 임의로 아이디값 설정

    // 해당 미디어 명장면 리사이클러뷰 처리
    RecyclerView recyclerView = findViewById(R.id.recyclerView);

    // 미디어 데이터 가져옴 Executor 사용
    AppDatabase db = AppDatabase.getInstance(this);
    runOnDiskIO(
        () -> {
          // List<Media> media = db.mediaDao().loadAll();
          Media mMedia = db.mediaDao().loadByName("무한도전");
          List<Scene> scene = db.sceneDao().loadByMediaId(media_id);
          MediaAdapter adapter = new MediaAdapter(scene);
          recyclerView.setAdapter(adapter);
          recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
          recyclerView.setHasFixedSize(true);

          //            //그리드뷰 세팅
          //            MediaGridAdapter adapter = new MediaGridAdapter(getApplicationContext(),
          // R.layout.media_detail_gridview_item, scene);
          //            GridView gridView = (GridView) findViewById(R.id.gridVIew);
          //            gridView.setAdapter(adapter);

          runOnMain(
              () -> {
                // 미디어 대표 이미지 세팅
                Glide.with(this).load(mMedia.getImage()).into(mMedia_image);

                // 미디어 타이틀 세팅
                mMedia_title.setText(mMedia.getName());

                // 미디어 상세설명 세팅
                mMedia_detail.setText(mMedia.getDesc());
              });
        });
    //        executors.diskIO().execute(() -> {
    //            MediaDao mediaDao = db.mediaDao();
    //            mMedia = mediaDao.loadById(1);
    //            executors.mainThread().execute(() -> {
    //                String s = mMedia.getName();
    //                Log.e("테스트", s);
    //            });
    //        });

    //        MediaDao mDao = db.mediaDao();
    //        media = mDao.loadAll();
    //        mMedia = mDao.loadById(media_id); // 아이디값으로 그아이디에 해당하는 미디어 데이터를 받아옴
    //        media = mDao.loadAll();

    //        //미디어 대표이미지 세팅
    //        Thread thread = new Thread() {
    //            @Override
    //            public void run() {
    //                try {
    //                    URL url = new URL(mMedia.getImage());
    //
    //                    HttpURLConnection connection = (HttpURLConnection)url.openConnection();
    //                    connection.setDoInput(true);
    //                    connection.connect();
    //
    //                    InputStream inputStream = connection.getInputStream();
    //                    bt = BitmapFactory.decodeStream(inputStream);
    //
    //                } catch (Exception e) {
    //                    e.printStackTrace();
    //                }
    //            }
    //        };
    //        thread.start();
    //
    //        try {
    //            thread.join();
    //            mMedia_image.setImageBitmap(bt);
    //        } catch(InterruptedException e) {
    //            e.printStackTrace();
    //        }
    //
    //        //미디어 타이틀 세팅
    //        mMedia_title.setText(mMedia.getName());
    //
    //        //미디어 상세설명 세팅
    //        mMedia_detail.setText(mMedia.getDesc());
    //

    //
    //        // 해시태그 처리
    //
    //        mHashTagHelper =
    // HashTagHelper.Creator.create(getResources().getColor(R.color.colorPrimary),
    //                new HashTagHelper.OnHashTagClickListener() {
    //                    @Override
    //                    public void onHashTagClicked(String hashTag) {
    //                        // 이벤트 처리
    //                        if(hashTag.equals("무한도전")) {
    //
    //                        }
    //                    }
    //        });
    //        mHashTagHelper.handle(mMedia_hash_tag);
  }
}
