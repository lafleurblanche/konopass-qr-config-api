package net.konohana.sakuya.konopass.qr.domain.dtos

/**
 * 新規端末マスタ登録用のリクエストボディ。
 */
data class ReaderMasterCreateRequest(
    val readerId: String,
    val locationName: String,
    val registeredBy: String // 登録者情報
)
