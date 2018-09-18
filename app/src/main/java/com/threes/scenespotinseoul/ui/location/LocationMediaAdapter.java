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
import com.threes.scenespotinseoul.data.model.Media;
import com.threes.scenespotinseoul.ui.media.MediaDetailActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import static com.threes.scenespotinseoul.utilities.ConstantsKt.EXTRA_MEDIA_ID;

public class LocationMediaAdapter extends RecyclerView.Adapter<LocationMediaAdapter.mediaViewHolder> {

  private HashSet<Media> media_relation_H;
  private Iterator<Media> media_relation_I;
  private List<Media> media_relation_L;

  LocationMediaAdapter(HashSet<Media> media_relation) {
    this.media_relation_H = media_relation;
    this.media_relation_I = this.media_relation_H.iterator();
    this.media_relation_L = new ArrayList<>();
    while(media_relation_I.hasNext()){
      this.media_relation_L.add(media_relation_I.next());
    }
  }

  @NonNull
  @Override
  public mediaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view =
        LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_location_media_detail_recyclerview, parent, false);
    return new mediaViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull mediaViewHolder holder, int position) {
    Context context = holder.itemView.getContext();

    RequestOptions requestOptions = new RequestOptions().centerCrop();

    Glide.with(context)
        .load(media_relation_L.get(position).getImage())
        .apply(requestOptions)
        .into(holder.location_media_item_image);
    holder.itemView.setOnClickListener(
        v -> {
          // 명장면 상세 액티비티로 장면 아이디 값 넘김
          Intent intent = new Intent(context, MediaDetailActivity.class);
          intent.putExtra(EXTRA_MEDIA_ID, media_relation_L.get(position).getUuid());
          context.startActivity(intent);
        });
  }

  @Override
  public int getItemCount() {
    if (media_relation_L != null) return media_relation_L.size();
    else return 0;
  }

  class mediaViewHolder extends RecyclerView.ViewHolder {
    ImageView location_media_item_image;

    private mediaViewHolder(View itemView) {
      super(itemView);
      location_media_item_image = itemView.findViewById(R.id.location_recycler_image_media);
    }
  }
}
