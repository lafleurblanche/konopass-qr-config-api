package net.konohana.sakuya.konopass.qr.service

import net.konohana.sakuya.konopass.qr.enums.QrMappingStatus
import net.konohana.sakuya.konopass.qr.infrastructure.repository.QrMappingRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class CerisierTicketUpdateService(
    private val mappingRepository: QrMappingRepository
) {
    /**
     * 入場取り消し時にマッピングステータスを IN_USE -> VALID に戻します。
     */
    @Transactional
    fun updateStatusToValidOnCancel(qrId: String): Boolean {
        val mappingEntity = mappingRepository.findByQrId(qrId) ?: return false

        // ステータスがIN_USEであることを確認（防御的プログラミング）
        if (mappingEntity.status != QrMappingStatus.IN_USE.name) {
            throw IllegalStateException("QRID '$qrId' はIN_USE状態ではないため、取り消しできません。")
        }

        val updatedEntity = mappingEntity.copy(
            status = QrMappingStatus.VALID.name,
            updatedAt = LocalDateTime.now()
        )
        mappingRepository.save(updatedEntity)
        return true
    }
}
