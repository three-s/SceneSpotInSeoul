package com.threes.scenespotinseoul.ui.media;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.threes.scenespotinseoul.R;
import com.threes.scenespotinseoul.data.model.Scene;
import java.util.List;

public class MediaAdapter extends RecyclerView.Adapter<MediaAdapter.mediaViewHolder> {

  private List<Scene> media_good_face;

  MediaAdapter(List<Scene> media_good_face) {
    this.media_good_face = media_good_face;
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
  public void onBindViewHolder(@NonNull mediaViewHolder holder, int position) {
    Context context = holder.itemView.getContext();

    RequestOptions requestOptions = new RequestOptions().centerCrop();

    Glide.with(context)
        .load(media_good_face.get(position).getImage())
        .apply(requestOptions)
        .into(holder.media_good_face_image);

    holder.itemView.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            Toast.makeText(v.getContext(), "상세 명장면 보기", Toast.LENGTH_SHORT).show();
            // 명장면 상세 액티비티로 장면 아이디 값 넘김
            //                Intent intent = new Intent(v.getContext(), AnotherActivity.class);
            //                intent.putExtra("Scene", media_good_face.get(position).getId());
            //                startActivity(intent);
          }
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
      media_good_face_image = itemView.findViewById(R.id.media_recycler_image);
    }
  }
}
