package net.konohana.sakuya.konopass.qr.infrastructure.repository

import net.konohana.sakuya.konopass.qr.infrastructure.entity.TReaderSettingsEntity
import org.springframework.data.jpa.repository.JpaRepository

interface TReaderSettingsRepository : JpaRepository<TReaderSettingsEntity, Int> {

    /**
     * 端末IDをキーとして設定情報を検索します。
     */
    fun findByReaderId(readerId: String): TReaderSettingsEntity?

    fun existsByReaderId(readerId: String): Boolean
}
