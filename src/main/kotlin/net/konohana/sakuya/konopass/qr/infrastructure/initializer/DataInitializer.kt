package net.konohana.sakuya.konopass.qr.infrastructure.initializer

import net.konohana.sakuya.konopass.qr.enums.ReaderMode
import net.konohana.sakuya.konopass.qr.infrastructure.entity.TReaderMasterEntity
import net.konohana.sakuya.konopass.qr.infrastructure.entity.TReaderSettingsEntity
import net.konohana.sakuya.konopass.qr.infrastructure.repository.TReaderMasterRepository
import net.konohana.sakuya.konopass.qr.infrastructure.repository.TReaderSettingsRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class DataInitializer(
    private val masterRepository: TReaderMasterRepository,
    private val settingsRepository: TReaderSettingsRepository
) : CommandLineRunner {

    override fun run(vararg args: String?) {
        // すでにデータがある場合はスキップ（二重投入防止）
        if (masterRepository.count() > 0) return

        val now = LocalDateTime.now()

        // 1. TReaderMasterEntity のダミーデータ作成
        val master1 = TReaderMasterEntity(
            readerId = "RD-OP-001",
            locationName = "試験駅01",
            isActive = true,
            registeredBy = "ADMIN_INIT",
            createdAt = now,
            updatedAt = now
        )

        val master2 = master1.copy(
            readerId = "RD-OP-002",
            locationName = "試験駅02",
            isActive = true
        )

        masterRepository.saveAll(listOf(master1, master2))

        // 2. TReaderSettingsEntity のダミーデータ作成
        val settings1 = TReaderSettingsEntity(
            readerId = "RD-OP-001",
            mode = ReaderMode.ENTRY.code,
            fromStaCode = "TEST",
            toStaCode = "TEST", // Entityのプロパティ名に合わせる
            sectorKbn = "TST"
        )

        val settings2 = settings1.copy(
            readerId = "RD-OP-002",
            mode = ReaderMode.EXIT.code,
            fromStaCode = "TEST",
            toStaCode = "TEST"
        )

        settingsRepository.saveAll(listOf(settings1, settings2))

        println("--- Dummy data has been initialized. ---")
    }
}
