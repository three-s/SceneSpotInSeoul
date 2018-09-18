package com.threes.scenespotinseoul.ui.location;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.threes.scenespotinseoul.R;
import com.threes.scenespotinseoul.data.model.Scene;
import com.threes.scenespotinseoul.ui.scene.SceneDetailActivity;

import java.util.List;

import static com.threes.scenespotinseoul.utilities.ConstantsKt.EXTRA_SCENE_ID;

public class LocationSceneAdapter extends RecyclerView.Adapter<LocationSceneAdapter.sceneViewHolder> {

  private List<Scene> scene_relation_L;

  LocationSceneAdapter(List<Scene> scene_relation) {
    this.scene_relation_L = scene_relation;
  }

  @NonNull
  @Override
  public sceneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view =
        LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_location_scene_detail_recyclerview, parent, false);
    return new sceneViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull sceneViewHolder holder, int position) {
    Context context = holder.itemView.getContext();

    RequestOptions requestOptions = new RequestOptions().centerCrop();

    Glide.with(context)
        .load(scene_relation_L.get(position).getImage())
        .apply(requestOptions)
        .into(holder.location_media_item_image);

    holder.itemView.setOnClickListener(
        v -> {
          // 명장면 상세 액티비티로 장면 아이디 값 넘김
          Intent intent = new Intent(context, SceneDetailActivity.class);
          intent.putExtra(EXTRA_SCENE_ID, scene_relation_L.get(position).getUuid());
          context.startActivity(intent);
        });
  }

  @Override
  public int getItemCount() {
    if (scene_relation_L != null) return scene_relation_L.size();
    else return 0;
  }

  class sceneViewHolder extends RecyclerView.ViewHolder {
    ImageView location_media_item_image;

    private sceneViewHolder(View itemView) {
      super(itemView);
      location_media_item_image = itemView.findViewById(R.id.location_recycler_image_scene);
    }
  }
}
