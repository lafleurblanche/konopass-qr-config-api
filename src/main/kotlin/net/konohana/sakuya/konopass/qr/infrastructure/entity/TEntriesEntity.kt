package net.konohana.sakuya.konopass.qr.infrastructure.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

/**
 * TEntriesテーブルのJPA Entity。
 */
@Entity
@Table(name = "t_entries")
data class TEntriesEntity(
    @Id
    @Column(name = "id")
    val id: Int,

    @Column(name = "qr_code_id")
    val qrCodeId: String,

    @Column(name = "entry_date")
    val entryDate: LocalDateTime,

    @Column(name = "from_sta_name")
    val fromStaName: String,

    @Column(name = "from_sta_code")
    val fromStaCode: String,

    @Column(name = "area_type")
    val areaType: String,

    @Column(name = "exit_date")
    val exitDate: LocalDateTime?, // Nullable

    @Column(name = "status")
    val status: String, // IN_USE, COMPLETED などの状態

)
