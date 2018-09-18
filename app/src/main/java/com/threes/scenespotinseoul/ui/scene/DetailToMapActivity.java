package com.threes.scenespotinseoul.ui.scene;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapContext;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapLocationManager;
import com.nhn.android.maps.NMapProjection;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.overlay.NMapPOIdata;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;
import com.nhn.android.mapviewer.overlay.NMapResourceProvider;
import com.threes.scenespotinseoul.R;
import com.threes.scenespotinseoul.data.AppDatabase;
import com.threes.scenespotinseoul.data.model.Location;
import com.threes.scenespotinseoul.ui.map.NMapPOIflagType;
import com.threes.scenespotinseoul.ui.map.NMapViewerResourceProvider;
import com.threes.scenespotinseoul.utilities.AppExecutors;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.threes.scenespotinseoul.utilities.ConstantsKt.EXTRA_LOCATION_ID;

/** Created by Chun on 2018-08-16. */
public class DetailToMapActivity extends NMapActivity {
  private NMapView mapView; // 지도 화면 View
  private final String CLIENT_ID = "bu0cuRVRS2yONcn8FGxq";
  private NMapPOIdata poiData;
  private NMapOverlayManager nMapOverlayManager;
  private NMapViewerResourceProvider mMapViewerResourceProvider;
  private NMapContext mMapContext;
  private NMapController mapController;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Log.e("result.info", "intent1");

    String location_id = getIntent().getStringExtra(EXTRA_LOCATION_ID);
    mapView = new NMapView(this);
    setContentView(mapView);
    mapView.setClientId(CLIENT_ID); // 클라이언트 아이디 값 설정
    mapView.setClickable(true);
    mapView.setEnabled(true);
    //mapView.setFocusable(true);
    //mapView.setFocusableInTouchMode(true);
    //mapView.requestFocus();
    mapController = mapView.getMapController();
    mMapViewerResourceProvider = new NMapViewerResourceProvider(this, this::showLayout);
    nMapOverlayManager =
            new NMapOverlayManager(
                    Objects.requireNonNull(this),
                    mapView,
                    mMapViewerResourceProvider); // null 자리에 mMapViewerResourceProiveder 로 오버레이 아이템들을 내맘대로
    // 꾸밀 수 있다.

    //nMapLocationManager = new NMapLocationManager(this);

    mapView.setOnMapViewTouchEventListener(new NMapView.OnMapViewTouchEventListener() {
        @Override
        public void onLongPress(NMapView nMapView, MotionEvent motionEvent) {

        }

        @Override
        public void onLongPressCanceled(NMapView nMapView) {

        }

        @Override
        public void onTouchDown(NMapView nMapView, MotionEvent motionEvent) {

        }

        @Override
        public void onTouchUp(NMapView nMapView, MotionEvent motionEvent) {

        }

        @Override
        public void onScroll(NMapView nMapView, MotionEvent motionEvent, MotionEvent motionEvent1) {

        }

        @Override
        public void onSingleTapUp(NMapView nMapView, MotionEvent motionEvent) {

        }
    });

    AppDatabase db = AppDatabase.getInstance(this);
    AppExecutors executors = new AppExecutors();
    executors
        .diskIO()
        .execute(
            () -> {
              List<Location> locations = Collections.singletonList(db.locationDao().loadById(location_id));
              executors
                  .mainThread()
                  .execute(
                      () -> {
                        // 메인 스레드에서 데이터 처리
                        poiData = new NMapPOIdata(2, mMapViewerResourceProvider);
                        Log.e("result.info", String.valueOf(locations.size()));
                        poiData.beginPOIdata(locations.size());
                        double lon = 0, lat = 0;
                        for (int i = 0; i < locations.size(); i++) {
                          poiData.addPOIitem(
                              locations.get(i).getLon(),
                              locations.get(i).getLat(),
                              locations.get(i).getName(),
                              NMapPOIflagType.PIN,
                              i);
                          lon = locations.get(i).getLon();
                          lat = locations.get(i).getLat();
                        }

                        poiData.endPOIdata();
                        nMapOverlayManager.createPOIdataOverlay(poiData, null);

                        mapController.setMapCenter(lon,lat);
                      });
            });

  }

    private void showLayout(int i) {
    }

}
