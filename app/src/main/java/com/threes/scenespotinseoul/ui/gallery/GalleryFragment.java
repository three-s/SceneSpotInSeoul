package com.threes.scenespotinseoul.ui.gallery;


import static com.threes.scenespotinseoul.utilities.AppExecutorsHelperKt.runOnDiskIO;
import static com.threes.scenespotinseoul.utilities.AppExecutorsHelperKt.runOnMain;

import android.content.ClipData;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.threes.scenespotinseoul.R;
import com.threes.scenespotinseoul.data.AppDatabase;
import com.threes.scenespotinseoul.data.model.Scene;

import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment {
    RecyclerView recyclerView;
    GalleryAdapter adapter;
    GridLayoutManager layoutManager;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);



//        TextView title = (TextView)view.findViewById(R.id.gtitle);
//        View line = (View)view.findViewById(R.id.gline);
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler);

        // 미디어 데이터 가져옴 Executor 사용
        AppDatabase db = AppDatabase.getInstance(getContext());
        db.sceneDao()
                .loadAllAreCapturedWithLive()
                .observe(
                        this,
                        scene -> {
                            for(int i=0;i<scene.size();i++){

                                if (scene.get(i) != null) {

                                    adapter = new GalleryAdapter(scene);
                                    recyclerView.setAdapter(adapter);
                                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
                                    recyclerView.setHasFixedSize(true);


                                }
                            }

                        });






//        runOnDiskIO(
//                () -> {
//                    // List<Media> media = db.mediaDao().loadAll();
//
//                    List<Scene> scenes = db.sceneDao().loadAll();
//                    List<Scene> capturedScene = new ArrayList<>();
//                    int s=0;
//                    for (int i = 0; i < scenes.size(); i++) {
//
//                        if(scenes.get(i).isCaptured() == true) {
//                            s++;
//                            capturedScene.add(scenes.get(i));
//                        }
//                    }
//                    Log.v("캡쳐된 이미지수",""+s);
//
//                    runOnMain(
//                            () -> {
//                                if(capturedScene!=null){
//                                    adapter = new GalleryAdapter(capturedScene);
//                                    recyclerView.setAdapter(adapter);
//                                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
//                                    recyclerView.setHasFixedSize(true);
//
//                                }
//                    });
//        });
        return view;
    }





}
