package com.threes.scenespotinseoul.ui.gallery;

import static com.threes.scenespotinseoul.utilities.ConstantsKt.EXTRA_SCENE_ID;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder> {

  private List<Scene> captured;

  GalleryAdapter(List<Scene> captured) {
    this.captured = captured;
  }

  @NonNull
  public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view =
        LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_gallery_recyclerview, parent, false);
    return new GalleryViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull GalleryViewHolder holder, int position) {
    Context context = holder.itemView.getContext();
    Log.v("캡쳐된 이미지", captured.get(position).getUploadedImage());
    RequestOptions requestOptions = new RequestOptions().centerCrop();
    if (captured != null) {
      Glide.with(context)
          .load(Uri.parse(captured.get(position).getUploadedImage()))
          .apply(requestOptions)
          .into(holder.image);
    }

    holder.itemView.setOnClickListener(
        v -> {
          // 명장면 상세 액티비티로 장면 아이디 값 넘김
          Intent intent = new Intent(context, SceneDetailActivity.class);
          intent.putExtra(EXTRA_SCENE_ID, captured.get(position).getUuid());
          context.startActivity(intent);
        });
  }

  @Override
  public int getItemCount() {
    if (captured != null) {
      return captured.size();
    } else {
      return 0;
    }
  }

  public class GalleryViewHolder extends RecyclerView.ViewHolder {
    ImageView image;

    public GalleryViewHolder(View itemView) {
      super(itemView);
      image = itemView.findViewById(R.id.gallery_recycler_image);
    }
  }
}
