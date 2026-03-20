package net.konohana.sakuya.konopass.qr.infrastructure.entity.dto

/**
 * データクラス: 機器設定データ (名称と構造を復旧)
 */
data class TReaderSettingData(
    val id: Int?,
    val readerId: String?,
    val majorId: String?,   // ★ 元の名称を踏襲しつつ、必要な項目のみ追加
    val mode: String?,
    val fromStaCode: String?,
    val toStaCode: String?,
    val sectorKbn: String?  // これが小系統(略称)
)
