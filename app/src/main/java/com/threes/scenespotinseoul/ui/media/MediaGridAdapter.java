package com.threes.scenespotinseoul.ui.media;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.threes.scenespotinseoul.R;
import com.threes.scenespotinseoul.data.model.Scene;
import java.util.List;

public class MediaGridAdapter extends BaseAdapter {
  private Context context;
  private int layout;
  private List<Scene> scenes;
  LayoutInflater inflater;

  public MediaGridAdapter(Context context, int layout, List<Scene> scenes) {
    this.context = context;
    this.layout = layout;
    this.scenes = scenes;
    inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  }

  @Override
  public int getCount() {
    return scenes.size();
  }

  @Override
  public Object getItem(int position) {
    return scenes.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    if (convertView == null) convertView = inflater.inflate(layout, null);
    ImageView imageView = (ImageView) convertView.findViewById(R.id.media_image_grid);
    Glide.with(convertView).load(scenes.get(position).getImage()).into(imageView);
    return convertView;
  }
}
