package net.konohana.sakuya.konopass.qr.service

import net.konohana.sakuya.konopass.qr.infrastructure.repository.TEntriesRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * 入場取り消し（Undo Entry）処理を担うトランザクションサービス。
 */
@Service
class EntryCancelService(
    private val entriesRepository: TEntriesRepository,
    private val ticketUpdateService: CerisierTicketUpdateService // ステータス更新サービスを仮定
) {

    /**
     * 入場記録を削除し、チケットステータスをIN_USEからVALIDに戻します。
     * 🚨 必須: @Transactionalにより、両方の操作の原子性(ACID)を保証します。
     */
    @Transactional
    fun cancelEntryByQrId(qrId: String): Boolean {

        // 1. 入場記録の検索と削除 (TEntriesテーブル)
        // 実際には、カスタムDELETEクエリを実行するのが効率的
        val entryRecord = entriesRepository.findByQrCodeId(qrId)
            ?: throw NoSuchElementException("QRID '$qrId' の入場記録が見つかりません。")

        // 記録を削除 (TEntriesServiceがExposedで実行する内容をJPAで代行)
        entriesRepository.delete(entryRecord) // 削除を実行

        // 2. チケットステータスをVALIDに戻す (マッピングテーブル)
        // updateStatusToValidOnCancel(qrId) は論理的な成功/失敗を返す想定
        val statusUpdateSuccess = ticketUpdateService.updateStatusToValidOnCancel(qrId)

        if (!statusUpdateSuccess) {
            throw IllegalStateException("チケットステータス更新失敗。データ不整合防止のためロールバックします。")
        }

        return true
    }
}
