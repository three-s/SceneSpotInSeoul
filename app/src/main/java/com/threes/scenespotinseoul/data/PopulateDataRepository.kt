package com.threes.scenespotinseoul.data

import com.threes.scenespotinseoul.data.PopulateDataHelper.findLocationIdByName
import com.threes.scenespotinseoul.data.PopulateDataHelper.findMediaIdByName
import com.threes.scenespotinseoul.data.PopulateDataHelper.insertLocation
import com.threes.scenespotinseoul.data.PopulateDataHelper.insertMedia
import com.threes.scenespotinseoul.data.PopulateDataHelper.insertScene
import com.threes.scenespotinseoul.data.model.Location
import com.threes.scenespotinseoul.data.model.Media
import com.threes.scenespotinseoul.data.model.Scene

class PopulateDataRepository(private var db: AppDatabase) {

    private fun populateLocationData() {
        insertLocation(
            db,
            Location(
                id = 0,
                lat = 37.5806712,
                lon = 127.0052719,
                name = "낙산공원과 서울성곽",
                desc = "-",
                address = "서울특별시 종로구 낙산길 54",
                image = "http://www.visitseoul.net/file_save/art_img/hallyu/article07/hallyu_article07_001.jpg",
                isCaptured = false
            ),
            tags = listOf("장소 태그 테스트1")
        )
        insertLocation(
            db,
            Location(
                id = 0,
                lat = 37.577983,
                lon = 127.0050028,
                name = "이화동 벽화마을",
                desc = "-",
                address = "서울특별시 종로구 낙산4길 49",
                image = "http://www.visitseoul.net/file_save/art_img/hallyu/article07/hallyu_article07_003.jpg",
                isCaptured = false
            ),
            tags = listOf("장소 태그 테스트2")
        )
        insertLocation(
            db,
            Location(
                id = 0,
                lat = 37.5667292,
                lon = 127.0073173,
                name = "DDP",
                desc = "-",
                address = "서울특별시 중구 을지로 281",
                image = "http://www.visitseoul.net/file_save/art_img/hallyu/article07/hallyu_article07_007.jpg",
                isCaptured = false
            ),
            tags = listOf("장소 태그 테스트3")
        )
    }

    private fun populateMediaData() {
        insertMedia(
            db,
            Media(
                id = 0,
                name = "무한도전",
                desc = "대한민국 평균 이하임을 자처하는 남자들이 매주 새로운 상황 속에서 펼치는 좌충우돌 도전기",
                image = "https://post-phinf.pstatic.net/MjAxODAzMTBfMjcg/MDAxNTIwNjU4OTAzMDMx.xVzwX4wtw4wVA18Qx-8XQ3I3fsPhnvDAvnSCZ88yRIcg.eKmI6ExZZhFZZ0JdLD-Ys8pKfxsIH3Cp9AaiEe6ncYAg.PNG/1_%281%29.png?type=w1200"
            ),
            tags = listOf("예능")
        )
        insertMedia(
            db,
            Media(
                id = 0,
                name = "런닝맨",
                desc = "누구도 상상하지 못했던 예측불허 빅웃음!! 대한민국을 대표하는 랜드마크, 얼마나 알고 계십니까? 런닝맨이 몸으로 직접 알려드리는 대한민국 대표 랜드마크! 대한민국 최고의 연예인들이 그곳에 모였다! 곳곳에 있는 미션을 해결하는 예능 프로그램",
                image = "http://image.hankookilbo.com/i.aspx?Guid=de8c76acb93f442bb2bceda792f100b0&Month=201701&size=640"
            ),
            tags = listOf("예능")
        )
        insertMedia(
            db,
            Media(
                id = 0,
                name = "식샤를 합시다 3 : 비긴즈",
                desc = "식샤님 더 비긴즈! 서른넷. 슬럼프에 빠진 구대영이 식샤님의 시작을 함께했던 이지우와 재회하면서 스무 살 그 시절의 음식과 추억을 공유하며 상처를 극복하는 이야기",
                image = "https://i.mydramalist.com/AYmYZc.jpg"
            ),
            tags = listOf("드라마")
        )
    }

    private fun populateSceneData() {
        insertScene(
            db,
            Scene(
                id = 0,
                locationId = findLocationIdByName(db, "낙산공원과 서울성곽"),
                mediaId = findMediaIdByName(db, "무한도전"),
                desc = "명장면1",
                image = "",
                isCaptured = false,
                capturedImage = null
            ),
            tags = listOf("명장면 태그 테스트1")
        )
        insertScene(
            db,
            Scene(
                id = 0,
                locationId = findLocationIdByName(db, "이화동 벽화마을"),
                mediaId = findMediaIdByName(db, "무한도전"),
                desc = "명장면2",
                image = "",
                isCaptured = false,
                capturedImage = null
            ),
            tags = listOf("명장면 태그 테스트2")
        )
        insertScene(
            db,
            Scene(
                id = 0,
                locationId = findLocationIdByName(db, "DDP"),
                mediaId = findMediaIdByName(db, "런닝맨"),
                desc = "명장면3",
                image = "",
                isCaptured = false,
                capturedImage = null
            ),
            tags = listOf("명장면 태그 테스트3")
        )
        insertScene(
            db,
            Scene(
                id = 0,
                locationId = findLocationIdByName(db, "이화동 벽화마을"),
                mediaId = findMediaIdByName(db, "식샤를 합시다 3 : 비긴즈"),
                desc = "명장면3",
                image = "",
                isCaptured = false,
                capturedImage = null
            ),
            tags = listOf("명장면 태그 테스트2")
        )
    }

    fun populateData() {
        populateLocationData()
        populateMediaData()
        populateSceneData()
    }
}