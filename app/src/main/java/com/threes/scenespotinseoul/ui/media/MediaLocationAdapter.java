package com.threes.scenespotinseoul.ui.media;

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
import com.threes.scenespotinseoul.data.model.Location;
import com.threes.scenespotinseoul.ui.scene.SceneDetailActivity;
import com.threes.scenespotinseoul.ui.location.LocationDetailActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.HashSet;

import static com.threes.scenespotinseoul.utilities.ConstantsKt.EXTRA_LOCATION_ID;

public class MediaLocationAdapter extends RecyclerView.Adapter<MediaLocationAdapter.locationViewHolder> {

  private List<Location> location_relation_L;
  private Iterator<Location> location_relation_I;
  private HashSet<Location> location_relation_H;

  MediaLocationAdapter(HashSet<Location> location_relation) {
    this.location_relation_H = location_relation;
    this.location_relation_I = this.location_relation_H.iterator();
    this.location_relation_L = new ArrayList<>();
    while(location_relation_I.hasNext()){
      this.location_relation_L.add(location_relation_I.next());
    }
  }

  @NonNull
  @Override
  public locationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view =
        LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_media_location_detail_recyclerview, parent, false);
    return new locationViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull locationViewHolder holder, int position) {
    Context context = holder.itemView.getContext();

    RequestOptions requestOptions = new RequestOptions().centerCrop();

    Glide.with(context)
        .load(location_relation_L.get(position).getImage())
        .apply(requestOptions)
        .into(holder.media_location_item_image);

    holder.itemView.setOnClickListener(
        v -> {
          // 명장면 상세 액티비티로 장면 아이디 값 넘김
          Intent intent = new Intent(context, LocationDetailActivity.class);
          intent.putExtra(EXTRA_LOCATION_ID, location_relation_L.get(position).getId());
          context.startActivity(intent);
        });
  }

  @Override
  public int getItemCount() {
    if (location_relation_L != null) return location_relation_L.size();
    else return 0;
  }

  class locationViewHolder extends RecyclerView.ViewHolder {
    ImageView media_location_item_image;

    private locationViewHolder(View itemView) {
      super(itemView);
      media_location_item_image = itemView.findViewById(R.id.media_location_recycler_image);
    }
  }
}
