package net.konohana.sakuya.konopass.qr.service.master

import jakarta.transaction.Transactional
import net.konohana.sakuya.konopass.qr.domain.dtos.ReaderMasterDetailDto
import net.konohana.sakuya.konopass.qr.infrastructure.entity.TReaderMasterEntity
import net.konohana.sakuya.konopass.qr.infrastructure.repository.TEntriesRepository
import net.konohana.sakuya.konopass.qr.infrastructure.repository.TReaderMasterRepository
import net.konohana.sakuya.konopass.qr.infrastructure.repository.TReaderSettingsRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ReaderMasterService(
    private val masterRepository: TReaderMasterRepository,
    private val settingsRepository: TReaderSettingsRepository,
    private val entriesRepository: TEntriesRepository // 削除時の使用状況チェック用
) {

    /**
     * 端末マスタ情報と設定情報を統合して検索します (R)。
     */
    fun getReaderDetails(readerId: String): ReaderMasterDetailDto {
        // 1. マスタ情報を検索
        val masterEntity = masterRepository.findByReaderId(readerId)
            ?: throw NoSuchElementException("端末ID '$readerId' のマスタ情報が見つかりません。")

        // 2. 設定情報を検索 (見つからない場合はデフォルト値を使用)
        val settingEntity = settingsRepository.findByReaderId(readerId)

        // 3. 統合DTOに変換して返却
        return ReaderMasterDetailDto(
            readerId = masterEntity.readerId,
            locationName = masterEntity.locationName,
            isActive = masterEntity.isActive,
            mode = settingEntity?.mode ?: "UNKNOWN", // 設定がない場合はUNKNOWN
            fromStaCode = settingEntity?.fromStaCode,
            updatedAt = masterEntity.updatedAt
        )
    }

    /**
     * 登録されている全端末の情報をページングして取得します (Read All with Pagination)。
     * @param pageable ページ番号、ページサイズ、ソート情報を含むオブジェクト
     * @return ページングされた ReaderMasterDetailDto の Page オブジェクト
     */
    fun getAllReaderDetails(pageable: Pageable): Page<ReaderMasterDetailDto> {
        // 1. 全端末マスタ情報を取得 (Pageオブジェクトとして取得)
        val masterEntitiesPage: Page<TReaderMasterEntity> = masterRepository.findAll(pageable)

        // 2. ページに含まれるエンティティのリストから、関連する設定情報を取得
        //    (※ここでのN+1問題回避ロジックは複雑になるため、ここでは findAll() をそのまま利用する構造を維持)
        val settingEntities = settingsRepository.findAll() // 簡略化のため全件取得を維持
        val settingMap = settingEntities.associateBy { it.readerId }

        // 3. Page内のコンテンツをDTOリストに変換し、新しい Page オブジェクトでラップして返却
        val dtoList = masterEntitiesPage.content.map { master ->
            val setting = settingMap[master.readerId]
            ReaderMasterDetailDto(
                readerId = master.readerId,
                locationName = master.locationName,
                isActive = master.isActive,
                updatedAt = master.updatedAt,
                // 設定情報が存在しない場合はデフォルト値を使用
                mode = setting?.mode ?: "未設定",
                fromStaCode = setting?.fromStaCode
            )
        }
        // DTOリストを元の Page メタデータでラップして返す
        return PageImpl(dtoList, pageable, masterEntitiesPage.totalElements)
    }

    /**
     * 新しい端末マスタを登録します (C)。
     */
    @Transactional
    fun createReaderMaster(readerId: String, locationName: String, registeredBy: String): ReaderMasterDetailDto {
        if (masterRepository.existsByReaderId(readerId)) {
            throw IllegalArgumentException("端末ID '$readerId' は既に登録されています。")
        }

        val now = LocalDateTime.now()
        val newEntity = TReaderMasterEntity(
            readerId = readerId,
            locationName = locationName,
            isActive = true, // デフォルトでアクティブ
            registeredBy = registeredBy,
            createdAt = now,
            updatedAt = now
        )
        val savedEntity = masterRepository.save(newEntity)

        // 設定テーブル (TReaderSettings) が存在しない場合、ここで新規登録するロジックが必要となるが、ここでは省略

        return getReaderDetails(savedEntity.readerId)
    }

    /**
     * 端末を論理削除（無効化）します (U)。
     * isActiveフラグをfalseに更新します。
     */
    @Transactional
    fun deactivateReaderMaster(readerId: String): ReaderMasterDetailDto {
        val masterEntity = masterRepository.findByReaderId(readerId)
            ?: throw NoSuchElementException("端末ID '$readerId' のマスタ情報が見つかりません。")

        if (!masterEntity.isActive) {
            // 既に無効化済みであれば、処理をスキップまたはエラーとする
            throw IllegalStateException("端末ID '$readerId' は既に無効化されています。")
        }

        // isActiveをfalseに設定し、更新日時を更新
        val updatedEntity = masterEntity.copy(
            isActive = false,
            updatedAt = LocalDateTime.now()
        )
        masterRepository.save(updatedEntity)

        // 統合DTOを再取得して返却
        return getReaderDetails(readerId)
    }

    /**
     * 端末を物理削除します (D)。関連テーブルに使用記録がない場合にのみ許可されます。
     * ★ メソッド名を hardDeleteReaderMaster に修正
     */
    @Transactional
    fun hardDeleteReaderMaster(readerId: String) {

        // 1. 使用状況チェック
        if (settingsRepository.existsByReaderId(readerId)) {
            throw IllegalStateException("端末ID '$readerId' の端末設定情報が残存しているため、削除できません。")
        }
        // 🚨 TEntriesRepositoryのチェックもここに含める

        // 2. 端末設定テーブルの削除
        val settingEntity = settingsRepository.findByReaderId(readerId)
        if (settingEntity != null) {
            settingsRepository.delete(settingEntity)
        }

        // 3. マスタの物理削除の実行
        val deletedCount = masterRepository.deleteByReaderId(readerId)

        if (deletedCount == 0) {
            throw NoSuchElementException("端末ID '$readerId' のマスタ情報が見つかりません。")
        }
    }
}
