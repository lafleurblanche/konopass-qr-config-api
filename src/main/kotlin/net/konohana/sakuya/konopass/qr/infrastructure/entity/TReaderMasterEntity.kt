package net.konohana.sakuya.konopass.qr.infrastructure.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

/**
 * 端末の詳細マスタテーブル (t_reader_master) のJPA Entity。
 */
@Entity
@Table(name = "t_reader_master") // ★ テーブル名を小文字に修正
data class TReaderMasterEntity(
    @Id
    @Column(name = "id")
    val id: Int = 0,

    @Column(name = "reader_id", unique = true) // ★ カラム名は小文字スネークケース
    val readerId: String,

    @Column(name = "location_name") // ★ 小文字スネークケース
    val locationName: String,

    @Column(name = "is_active") // ★ 小文字スネークケース
    val isActive: Boolean,

    @Column(name = "registered_by") // ★ 小文字スネークケース
    val registeredBy: String?,

    @Column(name = "created_at") // ★ 小文字スネークケース
    val createdAt: LocalDateTime,

    @Column(name = "updated_at") // ★ 小文字スネークケース
    val updatedAt: LocalDateTime
)
