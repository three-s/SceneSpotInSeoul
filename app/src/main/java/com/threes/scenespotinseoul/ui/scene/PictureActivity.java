package com.threes.scenespotinseoul.ui.scene;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import com.threes.scenespotinseoul.R;
import com.threes.scenespotinseoul.data.AppDatabase;

import static com.threes.scenespotinseoul.utilities.ConstantsKt.EXTRA_SCENE_ID;

public class PictureActivity extends AppCompatActivity {
    private ImageView picture;
    String SceneId;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);


        picture = findViewById(R.id.into_picture);

        Intent intent = getIntent();
        //Log.e("받은intent", intent.hasExtra(EX)+"");
        if (intent != null && intent.hasExtra(EXTRA_SCENE_ID)) {
            SceneId = intent.getStringExtra(EXTRA_SCENE_ID);

            if (SceneId ==null) {
                Log.e("PictureActivity", "Can't receive scene id");
                finish();
            }
            AppDatabase db = AppDatabase.getInstance(this);
            db.sceneDao()
                    .loadByIdWithLive(SceneId)
                    .observe(this,
                            scene -> {
                                if (scene != null) {
                                    Log.v("URI",scene.getUploadedImage());
                                    Glide.with(this)
                                            .load(Uri.parse(scene.getUploadedImage()))
                                            .apply(new RequestOptions().fitCenter())
                                            .into(picture);

                                }


                            });
        }


    }
}
