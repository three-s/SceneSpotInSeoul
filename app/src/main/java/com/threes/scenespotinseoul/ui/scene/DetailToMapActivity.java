package com.threes.scenespotinseoul.ui.scene;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.overlay.NMapPOIdata;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;
import com.threes.scenespotinseoul.R;
import com.threes.scenespotinseoul.data.AppDatabase;
import com.threes.scenespotinseoul.data.model.Location;
import com.threes.scenespotinseoul.data.model.Tag;
import com.threes.scenespotinseoul.ui.map.NMapPOIflagType;
import com.threes.scenespotinseoul.ui.map.NMapViewerResourceProvider;
import com.threes.scenespotinseoul.utilities.AppExecutors;

import java.util.List;

/** Created by Chun on 2018-08-16. */
public class DetailToMapActivity extends NMapActivity {
  private NMapView mapView; // 지도 화면 View
  private final String CLIENT_ID = "bu0cuRVRS2yONcn8FGxq";
  NMapPOIdata poiData;
  NMapOverlayManager nMapOverlayManager;
  NMapViewerResourceProvider mMapViewerResourceProvider;
  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      Log.e("result.info", "intent1");
      setContentView(R.layout.fragment_detail_tomap);

      mapView = new NMapView(this);
      mapView.setClientId(CLIENT_ID); // 클라이언트 아이디 값 설정
      mapView.setClickable(true);
      mapView.setEnabled(true);
      mapView.setFocusable(true);
      mapView.setFocusableInTouchMode(true);
      mapView.requestFocus();

      mMapViewerResourceProvider =
              new NMapViewerResourceProvider(
                      this,
                      new NMapViewerResourceProvider.OnMarkerEventListener() {

                          @Override
                          public void onClicked(int id) {

                          }
                      });

      nMapOverlayManager =
              new NMapOverlayManager(
                      this,
                      mapView,
                      mMapViewerResourceProvider); // null 자리에 mMapViewerResourceProiveder 로 오버레이 아이템들을 내맘대로


      AppDatabase db = AppDatabase.getInstance(this);
      AppExecutors executors = new AppExecutors();
      executors
              .diskIO()
              .execute(
                      () -> {
                          List<Location> locations = db.locationDao().loadAll();
                          executors
                                  .mainThread()
                                  .execute(
                                          () -> {
                                              // 메인 스레드에서 데이터 처리
                                              poiData = new NMapPOIdata(2, mMapViewerResourceProvider);
                                              Log.e("result.info", String.valueOf(locations.size()));
                                              poiData.beginPOIdata(locations.size());

                                              for (int i = 0; i < locations.size(); i++) {
                                                  poiData.addPOIitem(
                                                          locations.get(i).getLon(),
                                                          locations.get(i).getLat(),
                                                          locations.get(i).getName(),
                                                          NMapPOIflagType.PIN,
                                                          i);
                                              }
                                              poiData.endPOIdata();
                                              nMapOverlayManager.createPOIdataOverlay(poiData, null);
                                          });
                      });
  }
}
