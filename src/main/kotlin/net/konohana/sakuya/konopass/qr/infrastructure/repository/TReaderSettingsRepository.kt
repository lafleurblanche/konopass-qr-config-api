package net.konohana.sakuya.konopass.qr.infrastructure.repository

import net.konohana.sakuya.konopass.qr.infrastructure.entity.TReaderSettingsEntity
import org.springframework.data.jpa.repository.JpaRepository

interface TReaderSettingsRepository : JpaRepository<TReaderSettingsEntity, Int> {

    /**
     * 端末IDをキーとして設定情報を検索します。
     */
    fun findByReaderId(readerId: String): TReaderSettingsEntity?

    // ★ 新規実装: 指定された readerId を持つレコードが存在するか確認します。
    fun existsByReaderId(readerId: String): Boolean
}
