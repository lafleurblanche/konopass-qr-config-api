package net.konohana.sakuya.konopass.qr.infrastructure.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

/**
 * 端末の詳細マスタテーブル (t_reader_master) のJPA Entity。
 */
@Entity
@Table(name = "t_reader_master")
data class TReaderMasterEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Int? = null,

    @Column(name = "reader_id", unique = true)
    val readerId: String,

    @Column(name = "location_name")
    val locationName: String,

    @Column(name = "is_active")
    val isActive: Boolean,

    @Column(name = "registered_by")
    val registeredBy: String?,

    @Column(name = "created_at")
    val createdAt: LocalDateTime,

    @Column(name = "updated_at")
    val updatedAt: LocalDateTime
)
