package com.threes.scenespotinseoul.data;

import android.support.annotation.NonNull;
import com.threes.scenespotinseoul.data.model.*;

import java.util.ArrayList;
import java.util.List;

public class DataRepository {

    @NonNull
    public static List<Location> populateLocationData() {
        List<Location> locations = new ArrayList<>();
        locations.add(new Location(10, 30, 30, "서울", "영화 촬영지", "", false));
        locations.add(new Location(11, 30, 30, "서울", "영화 촬영지", "", false));
        locations.add(new Location(12, 30, 30, "서울", "영화 촬영지", "", false));
        return locations;
    }

    @NonNull
    public static List<Media> populateMediaData() {
        List<Media> media = new ArrayList<>();
        media.add(new Media(10, "서울", "", "영화1"));
        media.add(new Media(11, "서울", "", "영화2"));
        media.add(new Media(12, "서울", "", "영화3"));
        return media;
    }

    @NonNull
    public static List<Scene> populateSceneData() {
        List<Scene> scenes = new ArrayList<>();
        scenes.add(new Scene(10, 10, true, "", "하이라이트1", ""));
        scenes.add(new Scene(10, 11, false, "", "하이라이트2", ""));
        scenes.add(new Scene(10, 12, false, "", "하이라이트3", ""));
        scenes.add(new Scene(11, 10, true, "", "하이라이트4", ""));
        scenes.add(new Scene(11, 11, false, "", "하이라이트5", ""));
        scenes.add(new Scene(11, 12, false, "", "하이라이트6", ""));
        scenes.add(new Scene(12, 10, true, "", "하이라이트7", ""));
        scenes.add(new Scene(12, 11, false, "", "하이라이트8", ""));
        scenes.add(new Scene(12, 12, false, "", "하이라이트9", ""));
        return scenes;
    }

    @NonNull
    public static List<Tag> populateTagData() {
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag(10, "태그1"));
        tags.add(new Tag(11, "태그2"));
        tags.add(new Tag(12, "태그3"));
        tags.add(new Tag(13, "태그4"));
        tags.add(new Tag(14, "태그5"));
        tags.add(new Tag(15, "태그6"));
        return tags;
    }

    @NonNull
    public static List<LocationTag> populateLocationTagData() {
        List<LocationTag> tags = new ArrayList<>();
        tags.add(new LocationTag(10, 10));
        tags.add(new LocationTag(11, 10));
        tags.add(new LocationTag(12, 11));
        tags.add(new LocationTag(13, 11));
        tags.add(new LocationTag(14, 12));
        tags.add(new LocationTag(15, 12));
        return tags;
    }

    @NonNull
    public static List<MediaTag> populateMediaTagData() {
        List<MediaTag> tags = new ArrayList<>();
        tags.add(new MediaTag(10, 10));
        tags.add(new MediaTag(11, 10));
        tags.add(new MediaTag(12, 11));
        tags.add(new MediaTag(13, 11));
        tags.add(new MediaTag(14, 12));
        tags.add(new MediaTag(15, 12));
        return tags;
    }
}
