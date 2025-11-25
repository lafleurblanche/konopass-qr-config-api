package net.konohana.sakuya.konopass.qr.infrastructure.entity.dto

/**
 * データクラス: 機器設定データ
 */
data class TReaderSettingData(
    val id: Int,
    val readerId: String?, // Nullableにするか、DBのNOT NULL制約を確認
    val mode: String?,      // Nullableにするか、DBのNOT NULL制約を確認
    val fromStaCode: String?, // Nullableにするか、DBのNOT NULL制約を確認
    val toStaCode: String?,    // Nullableにするか、DBのNOT NULL制約を確認
    val sectorKbn: String?
)
