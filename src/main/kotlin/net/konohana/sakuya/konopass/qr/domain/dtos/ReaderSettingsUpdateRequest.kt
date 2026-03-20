package net.konohana.sakuya.konopass.qr.domain.dtos

data class ReaderSettingsUpdateRequest(
    val fromStaCode: String,
    val toStaCode: String,
    val sectorKbn: String
)
