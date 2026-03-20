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
        // すでにデータがある場合はスキップ
        if (masterRepository.count() > 0) return

        val now = LocalDateTime.now()

        // 1. TReaderMasterEntity の作成
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
            locationName = "試験駅02"
        )

        masterRepository.saveAll(listOf(master1, master2))

        // 2. TReaderSettingsEntity の作成 (majorId を追加)
        val settings1 = TReaderSettingsEntity(
            readerId = "RD-OP-001",
            majorId = "01",
            mode = ReaderMode.ENTRY.code,
            fromStaCode = "FRNE0000",
            toStaCode = "TONE0000",
            sectorKbn = "NE"
        )

        val settings2 = TReaderSettingsEntity(
            readerId = "RD-OP-002",
            majorId = "02",
            mode = ReaderMode.EXIT.code,
            fromStaCode = "FRNUT0000",
            toStaCode = "TONUT0000",
            sectorKbn = "NUT"
        )

        settingsRepository.saveAll(listOf(settings1, settings2))

        println("--- Dummy data with MajorSystem has been initialized. ---")
    }
}
