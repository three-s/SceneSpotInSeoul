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

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.mediaViewHolder> {

  private List<Scene> media_good_face;

  LocationAdapter(List<Scene> media_good_face) {
    this.media_good_face = media_good_face;
  }

  @NonNull
  @Override
  public mediaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view =
        LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_location_detail_recyclerview, parent, false);
    return new mediaViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull mediaViewHolder holder, int position) {
    Context context = holder.itemView.getContext();

    RequestOptions requestOptions = new RequestOptions().centerCrop();

    Glide.with(context)
        .load(media_good_face.get(position).getImage())
        .apply(requestOptions)
        .into(holder.media_good_face_image);

    holder.itemView.setOnClickListener(
        v -> {
          // 명장면 상세 액티비티로 장면 아이디 값 넘김
          Intent intent = new Intent(context, SceneDetailActivity.class);
          intent.putExtra(EXTRA_SCENE_ID, media_good_face.get(position).getId());
          context.startActivity(intent);
        });
  }

  @Override
  public int getItemCount() {
    if (media_good_face != null) return media_good_face.size();
    else return 0;
  }

  class mediaViewHolder extends RecyclerView.ViewHolder {
    ImageView media_good_face_image;

    private mediaViewHolder(View itemView) {
      super(itemView);
      media_good_face_image = itemView.findViewById(R.id.location_recycler_image);
    }
  }
}
