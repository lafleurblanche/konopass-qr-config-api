package net.konohana.sakuya.konopass.qr.infrastructure.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import net.konohana.sakuya.konopass.qr.enums.ReaderMode

@Entity
@Table(name = "t_reader_settings")
data class TReaderSettingsEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Int? = null,

    @Column(name = "reader_id")
    val readerId: String,

    @Column(name = "major_id") // ★ 追加: 大系統ID (01, 02...)
    val majorId: String,

    @Column(name = "mode")
    val mode: String = ReaderMode.PREPARING.code,

    @Column(name = "from_sta_code")
    val fromStaCode: String,

    @Column(name = "to_sta_code")
    val toStaCode: String,

    @Column(name = "sector_kbn") // ここを「小系統（NE, PRIなど）」として扱う
    val sectorKbn: String,
)
