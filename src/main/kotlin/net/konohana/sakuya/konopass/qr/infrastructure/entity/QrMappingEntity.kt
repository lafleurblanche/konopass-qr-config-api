package net.konohana.sakuya.konopass.qr.infrastructure.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

/**
 * QRコードとチケット情報のマッピングテーブル (cerisier_ticket_qr_mapping) のJPA Entity。
 */
@Entity
@Table(name = "cerisier_ticket_qr_mapping")
data class QrMappingEntity(
    @Id
    @Column(name = "id")
    val id: Int = 0, // 主キー

    @Column(name = "qr_id")
    val qrId: String,

    @Column(name = "request_number")
    val requestNumber: String,

    @Column(name = "sector_kbn")
    val sectorKbn: String,

    // DBにはStringで格納されている利用ステータス (VALID, IN_USEなど)
    @Column(name = "status")
    val status: String,

    @Column(name = "created_at")
    val createdAt: LocalDateTime,

    @Column(name = "updated_at")
    val updatedAt: LocalDateTime
)
