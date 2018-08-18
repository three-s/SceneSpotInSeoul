package com.threes.scenespotinseoul.ui.media;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.threes.scenespotinseoul.R;
import com.threes.scenespotinseoul.data.model.Scene;
import java.util.List;

public class MediaAdapter extends RecyclerView.Adapter<MediaAdapter.mediaViewHolder> {

  private List<Scene> media_good_face;

  MediaAdapter(List<Scene> media_good_face) {
    this.media_good_face = media_good_face;
  }

  class mediaViewHolder extends RecyclerView.ViewHolder {
    private final ImageView media_good_face_image;

    private mediaViewHolder(View itemView) {
      super(itemView);
      media_good_face_image = itemView.findViewById(R.id.media_image);
    }
  }

  @NonNull
  @Override
  public mediaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view =
        LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_media_detail_recyclerview, parent, false);
    return new mediaViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull mediaViewHolder holder, int position) {}

  @Override
  public int getItemCount() {
    if (media_good_face != null) return media_good_face.size();
    else return 0;
  }
}
