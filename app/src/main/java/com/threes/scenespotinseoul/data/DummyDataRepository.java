package com.threes.scenespotinseoul.data;

import android.support.annotation.NonNull;
import com.threes.scenespotinseoul.data.model.*;
import java.util.ArrayList;
import java.util.List;

public class DummyDataRepository {

  @NonNull
  public static List<Location> populateLocationData() {
    List<Location> locations = new ArrayList<>();
    locations.add(
        new Location(
            1,
            37.5806712,
            127.0052719,
            "서울특별시 종로구 낙산길 54",
            "낙산공원과 서울성곽",
            "http://www.visitseoul.net/file_save/art_img/hallyu/article07/hallyu_article07_001.jpg",
            false));
    locations.add(
        new Location(
            2,
            37.577983,
            127.0050028,
            "서울특별시 종로구 낙산4길 49",
            "이화동 벽화마을",
            "http://www.visitseoul.net/file_save/art_img/hallyu/article07/hallyu_article07_003.jpg",
            false));
    locations.add(
        new Location(
            3,
            37.5667292,
            127.0073173,
            "서울특별시 중구 을지로 281",
            "DDP",
            "http://www.visitseoul.net/file_save/art_img/hallyu/article07/hallyu_article07_007.jpg",
            false));
    return locations;
  }

  @NonNull
  public static List<Media> populateMediaData() {
    List<Media> media = new ArrayList<>();
    media.add(
        new Media(
            1,
            "무한도전",
            "https://post-phinf.pstatic.net/MjAxODAzMTBfMjcg/MDAxNTIwNjU4OTAzMDMx.xVzwX4wtw4wVA18Qx-8XQ3I3fsPhnvDAvnSCZ88yRIcg.eKmI6ExZZhFZZ0JdLD-Ys8pKfxsIH3Cp9AaiEe6ncYAg.PNG/1_%281%29.png?type=w1200",
            "대한민국 평균 이하임을 자처하는 남자들이 매주 새로운 상황 속에서 펼치는 좌충우돌 도전기"));
    media.add(
        new Media(
            2,
            "런닝맨",
            "http://image.hankookilbo.com/i.aspx?Guid=de8c76acb93f442bb2bceda792f100b0&Month=201701&size=640",
            "누구도 상상하지 못했던 예측불허 빅웃음!! 대한민국을 대표하는 랜드마크, 얼마나 알고 계십니까? 런닝맨이 몸으로 직접 알려드리는 대한민국 대표 랜드마크! 대한민국 최고의 연예인들이 그곳에 모였다! 곳곳에 있는 미션을 해결하는 예능 프로그램"));
    media.add(
        new Media(
            3,
            "식샤를 합시다 3 : 비긴즈",
            "https://i.mydramalist.com/AYmYZc.jpg",
            "식샤님 더 비긴즈! 서른넷. 슬럼프에 빠진 구대영이 식샤님의 시작을 함께했던 이지우와 재회하면서 스무 살 그 시절의 음식과 추억을 공유하며 상처를 극복하는 이야기"));
    return media;
  }

  @NonNull
  public static List<Scene> populateSceneData() {
    List<Scene> scenes = new ArrayList<>();
    scenes.add(new Scene(1, 1, 1, "", "명장면1", false, null));
    scenes.add(new Scene(2, 1, 1, "", "명장면2", false, null));
    scenes.add(new Scene(3, 2, 1, "", "명장면3", false, null));
    scenes.add(new Scene(4, 2, 2, "", "명장면4", false, null));
    scenes.add(new Scene(5, 3, 3, "", "명장면5", false, null));
    scenes.add(new Scene(6, 3, 2, "", "명장면6", false, null));
    return scenes;
  }

  @NonNull
  public static List<Tag> populateTagData() {
    List<Tag> tags = new ArrayList<>();
    tags.add(new Tag(1, "예능"));
    tags.add(new Tag(2, "드라마"));
    tags.add(new Tag(3, "장소 태그 테스트1"));
    tags.add(new Tag(4, "장소 태그 테스트2"));
    tags.add(new Tag(5, "명장면 태그 테스트1"));
    return tags;
  }

  @NonNull
  public static List<LocationTag> populateLocationTagData() {
    List<LocationTag> tags = new ArrayList<>();
    tags.add(new LocationTag(3, 1));
    tags.add(new LocationTag(4, 1));
    tags.add(new LocationTag(3, 2));
    return tags;
  }

  @NonNull
  public static List<MediaTag> populateMediaTagData() {
    List<MediaTag> tags = new ArrayList<>();
    tags.add(new MediaTag(1, 1));
    tags.add(new MediaTag(1, 2));
    tags.add(new MediaTag(1, 3));
    return tags;
  }

  @NonNull
  public static List<SceneTag> populateSceneTagData() {
    List<SceneTag> tags = new ArrayList<>();
    tags.add(new SceneTag(5, 1));
    tags.add(new SceneTag(5, 2));
    tags.add(new SceneTag(5, 3));
    return tags;
  }
}
