package com.threes.scenespotinseoul.ui.gallery;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.threes.scenespotinseoul.R;
import com.threes.scenespotinseoul.data.AppDatabase;
import java.util.Objects;

public class GalleryFragment extends Fragment {

  RecyclerView recyclerView;
  GalleryAdapter adapter;

  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_gallery, container, false);
    recyclerView = view.findViewById(R.id.recycler);
    AppDatabase db = AppDatabase.getInstance(Objects.requireNonNull(getContext()));
    db.sceneDao()
        .loadAllAreCapturedWithLive()
        .observe(
            this,
            scene -> {
              adapter = new GalleryAdapter(scene);
              recyclerView.setAdapter(adapter);
              recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
              recyclerView.setHasFixedSize(true);
            });
    return view;
  }
}
