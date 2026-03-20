package net.konohana.sakuya.konopass.qr.service

import net.konohana.sakuya.konopass.qr.infrastructure.entity.dto.TReaderSettingData
import net.konohana.sakuya.konopass.qr.infrastructure.repository.TReaderSettingsRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TerminalConfigService(
    private val settingsRepository: TReaderSettingsRepository
) {
    // 必須ではないが、更新操作には @Transactional を付与する
    @Transactional
    fun updateTerminalMode(readerId: String, newMode: String): TReaderSettingData {
        val entity = settingsRepository.findByReaderId(readerId)
            ?: throw NoSuchElementException("リーダーID '$readerId' の設定が見つかりません。")

        // エンティティの更新
        val updatedEntity = entity.copy(mode = newMode)
        val savedEntity = settingsRepository.save(updatedEntity)

        // ★ 元の名称 TReaderSettingData で返却
        return TReaderSettingData(
            id = savedEntity.id,
            readerId = savedEntity.readerId,
            majorId = savedEntity.majorId, // 追加分
            mode = savedEntity.mode,
            fromStaCode = savedEntity.fromStaCode,
            toStaCode = savedEntity.toStaCode,
            sectorKbn = savedEntity.sectorKbn
        )
    }
}
