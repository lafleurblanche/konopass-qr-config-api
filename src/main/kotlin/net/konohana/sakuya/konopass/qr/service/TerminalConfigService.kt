package net.konohana.sakuya.konopass.qr.service

import net.konohana.sakuya.konopass.qr.infrastructure.entity.dto.TReaderSettingData
import net.konohana.sakuya.konopass.qr.infrastructure.repository.TReaderSettingsRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TerminalConfigService(
    private val settingsRepository: TReaderSettingsRepository // ReaderSettingsテーブルのリポジトリ
) {
    // 必須ではないが、更新操作には @Transactional を付与する
    @Transactional
    fun updateTerminalMode(readerId: String, newMode: String): TReaderSettingData {
        val entity = settingsRepository.findByReaderId(readerId)
            ?: throw NoSuchElementException("リーダーID '$readerId' の設定が見つかりません。")

        // 1. エンティティを更新
        // entity.mode = newMode // JPAの更新処理 (SetterまたはCopyで)
        val updatedEntity = entity.copy(mode = newMode)

        // 2. DBに保存
        val savedEntity = settingsRepository.save(updatedEntity)

        // 3. DTOに変換して返却
        return TReaderSettingData(
            id = savedEntity.id,
            mode = savedEntity.mode,
            readerId = savedEntity.readerId,
            fromStaCode = savedEntity.fromStaCode,
            toStaCode = savedEntity.toStaCode,
            sectorKbn = savedEntity.sectorKbn
        )
    }
}
