package net.konohana.sakuya.konopass.qr.controller

import net.konohana.sakuya.konopass.qr.controller.requet.UpdateTerminalModeRequest
import net.konohana.sakuya.konopass.qr.infrastructure.entity.dto.TReaderSettingData
import net.konohana.sakuya.konopass.qr.service.EntryCancelService
import net.konohana.sakuya.konopass.qr.service.TerminalConfigService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/config")
class ConfigController(
    private val cancelService: EntryCancelService,
    private val configService: TerminalConfigService
) {
    // リクエストDTO (例: UpdateModeRequest) は別途定義が必要です

    /**
     * 入場記録を取り消し、チケットステータスをリセットします。
     * DELETE /api/v1/config/entry/{qrId}
     */
    @DeleteMapping("/entry/{qrId}")
    fun cancelEntry(@PathVariable qrId: String): ResponseEntity<*> {
        return try {
            cancelService.cancelEntryByQrId(qrId)
            ResponseEntity.ok(mapOf("message" to "QRID '$qrId' の入場取り消しが完了しました。"))
        } catch (e: NoSuchElementException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapOf("error" to e.message))
        } catch (e: IllegalStateException) {
            ResponseEntity.badRequest().body(mapOf("error" to e.message))
        }
    }

    // ★ 端末設定更新エンドポイントの実装
    /**
     * 2. 端末の動作モードを更新します。
     * POST /api/v1/config/terminal/{readerId}/mode
     */
    @PostMapping("/terminal/{readerId}/mode")
    fun updateTerminalMode(
        @PathVariable readerId: String,
        @RequestBody request: UpdateTerminalModeRequest
    ): ResponseEntity<*> {
        return try {
            // Serviceを呼び出し、設定を更新
            val updatedSettingDto: TReaderSettingData = configService.updateTerminalMode(
                readerId = readerId,
                newMode = request.newMode
            )
            // 更新後のDTOを返却
            ResponseEntity.ok(updatedSettingDto)

        } catch (e: NoSuchElementException) {
            // 指定されたリーダーIDの設定が見つからない場合
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapOf("error" to e.message))
        } catch (e: Exception) {
            // その他の予期せぬエラー
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mapOf("error" to "端末設定更新中に予期せぬエラーが発生しました。"))
        }
    }
}
