package net.konohana.sakuya.konopass.qr.services

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import net.konohana.sakuya.konopass.qr.infrastructure.entity.TEntriesEntity
import net.konohana.sakuya.konopass.qr.infrastructure.repository.TEntriesRepository
import net.konohana.sakuya.konopass.qr.service.CerisierTicketUpdateService
import net.konohana.sakuya.konopass.qr.service.EntryCancelService
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.LocalDateTime

@ExtendWith(SpringExtension::class) // Springの機能をテストで使用するために必要
class EntryCancelServiceTest {

    // 依存サービスのリポジトリをモック化
    private val entriesRepository: TEntriesRepository = mockk()
    private val ticketUpdateService: CerisierTicketUpdateService = mockk()

    // テスト対象サービス（モックを注入）
    private val entryCancelService = EntryCancelService(
        entriesRepository = entriesRepository,
        ticketUpdateService = ticketUpdateService
    )

    private val testQrId = "QR12345"
    private val mockEntryEntity = TEntriesEntity(
        id = 1,
        qrCodeId = testQrId,
        entryDate = LocalDateTime.now(),
        fromStaCode = "FRXX0100",
        fromStaName = "テスト駅",
        areaType = "TEST",
        exitDate = null,
        status = "IN_USE"
    )

    @Test
    fun `cancelEntryByQrId - should successfully cancel entry when status update succeeds`() {
        // 準備: 削除対象のレコードが存在し、ステータス更新が成功すると仮定
        every { entriesRepository.findByQrCodeId(testQrId) } returns mockEntryEntity
        // 削除メソッドが呼ばれることを検証するため、返り値はUnit (void)
        every { entriesRepository.delete(mockEntryEntity) } just Runs

        // チケットステータス更新が成功するとモック設定
        every { ticketUpdateService.updateStatusToValidOnCancel(testQrId) } returns true

        // 実行
        val result = entryCancelService.cancelEntryByQrId(testQrId)

        // 検証
        assertTrue(result)
        // 1. エントリーレコードが削除されたことを検証
        verify(exactly = 1) { entriesRepository.delete(mockEntryEntity) }
        // 2. チケットステータスが更新されたことを検証
        verify(exactly = 1) { ticketUpdateService.updateStatusToValidOnCancel(testQrId) }
    }

    @Test
    fun `cancelEntryByQrId - should throw exception if status update fails (rollback scenario)`() {
        // 準備: レコードは存在するが、ステータス更新が失敗すると仮定
        every { entriesRepository.findByQrCodeId(testQrId) } returns mockEntryEntity
        every { entriesRepository.delete(mockEntryEntity) } just Runs
        // ステータス更新が失敗したとモック設定 (falseを返す)
        every { ticketUpdateService.updateStatusToValidOnCancel(testQrId) } returns false

        // 実行と検証
        assertThrows<IllegalStateException> {
            entryCancelService.cancelEntryByQrId(testQrId)
        }

        // 🚨 データベースがモックのため、トランザクションのロールバック自体は検証できないが、
        // ロールバックを引き起こすための例外がスローされたことを検証できた
        verify(exactly = 1) { entriesRepository.delete(mockEntryEntity) } // deleteは呼ばれる
        verify(exactly = 1) { ticketUpdateService.updateStatusToValidOnCancel(testQrId) } // updateは呼ばれる
    }
}
