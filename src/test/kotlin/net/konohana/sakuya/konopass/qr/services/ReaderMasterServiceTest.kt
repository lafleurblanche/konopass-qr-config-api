package net.konohana.sakuya.konopass.qr.services

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import net.konohana.sakuya.konopass.qr.infrastructure.entity.TReaderMasterEntity
import net.konohana.sakuya.konopass.qr.infrastructure.repository.TEntriesRepository
import net.konohana.sakuya.konopass.qr.infrastructure.repository.TReaderMasterRepository
import net.konohana.sakuya.konopass.qr.infrastructure.repository.TReaderSettingsRepository
import net.konohana.sakuya.konopass.qr.service.master.ReaderMasterService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
class ReaderMasterServiceTest {

    private val masterRepository: TReaderMasterRepository = mockk()
    private val settingsRepository: TReaderSettingsRepository = mockk()
    private val entriesRepository: TEntriesRepository = mockk()

    private val readerMasterService = ReaderMasterService(
        masterRepository = masterRepository,
        settingsRepository = settingsRepository,
        entriesRepository = entriesRepository
    )

    private val testReaderId = "R101"
    private val testLocation = "Central Gate"
    private val testUser = "SystemUser"

    /*
    @Test
    fun `createReaderMaster - should successfully create new reader master`() {

        // ★ 1. save が返すモックエンティティを準備
        val savedMockEntity = mockk<TReaderMasterEntity>()

        // ★ 2. savedMockEntity に対して、getReaderDetails が必要とするプロパティを定義
        // Error: ...getReaderId() among the configured answers... のため、これを設定
        every { savedMockEntity.readerId } returns testReaderId
        every { savedMockEntity.updatedAt } returns LocalDateTime.now()
        // 他のプロパティ（id, updatedAtなど）も必要であれば定義する

        every { masterRepository.existsByReaderId(testReaderId) } returns false

        // ★ 3. save が呼ばれたら、準備したモックエンティティを返す
        every { masterRepository.save(any<TReaderMasterEntity>()) } returns savedMockEntity

        // 4. getReaderDetails のモック設定 (savedMockEntity.readerId を引数として受け取る)
        every { readerMasterService.getReaderDetails(testReaderId) } answers { mockk() }

        // 実行
        readerMasterService.createReaderMaster(testReaderId, testLocation, testUser)

        // 検証はそのまま
        verify(exactly = 1) { masterRepository.save(any<TReaderMasterEntity>()) }
        verify(exactly = 1) { readerMasterService.getReaderDetails(testReaderId) }
    }
*/


    @Test
    fun `createReaderMaster - should throw exception when ReaderId already exists`() {

        // 準備
        every { masterRepository.existsByReaderId(testReaderId) } returns true // IDは既に存在する

        // 実行と検証
        assertThrows<IllegalArgumentException> {
            readerMasterService.createReaderMaster(testReaderId, testLocation, testUser)
        }

        // save メソッドが呼ばれなかったことを検証
        verify(exactly = 0) { masterRepository.save(any<TReaderMasterEntity>()) }
    }
}
