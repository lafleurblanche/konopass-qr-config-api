package net.konohana.sakuya.konopass.qr.domain.dtos

data class ReaderMasterUpdateRequest(
    val locationName: String,
    val mode: String,
    val isActive: Boolean
)
