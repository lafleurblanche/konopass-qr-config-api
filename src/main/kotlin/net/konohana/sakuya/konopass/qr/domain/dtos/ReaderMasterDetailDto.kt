package net.konohana.sakuya.konopass.qr.domain.dtos

import java.time.LocalDateTime

/**
 * 端末マスタ情報と端末設定情報を統合した詳細表示DTO。
 */
data class ReaderMasterDetailDto(
    // TReaderMasterEntity からの情報
    val readerId: String,
    val locationName: String,
    val isActive: Boolean,

    // TReaderSettingsEntity からの情報
    val mode: String,
    val fromStaCode: String?,

    // 共通情報
    val updatedAt: LocalDateTime
)
