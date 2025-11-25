package net.konohana.sakuya.konopass.qr.infrastructure.repository

import net.konohana.sakuya.konopass.qr.infrastructure.entity.TReaderSettingsEntity
import org.springframework.data.jpa.repository.JpaRepository

interface TReaderSettingsRepository : JpaRepository<TReaderSettingsEntity, Int> {
    fun findByReaderId(readerId: String): TReaderSettingsEntity?
}
