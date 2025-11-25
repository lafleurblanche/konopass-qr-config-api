package net.konohana.sakuya.konopass.qr.infrastructure.repository

import net.konohana.sakuya.konopass.qr.infrastructure.entity.QrMappingEntity
import org.springframework.data.jpa.repository.JpaRepository

/**
 * QRマッピングテーブルへのアクセスを担うリポジトリ。
 */
interface QrMappingRepository : JpaRepository<QrMappingEntity, Int> {

    /**
     * QRIDをキーとしてマッピング情報を検索します。
     * Spring Data JPAにより、メソッド名からSQLクエリが自動生成されます。
     */
    fun findByQrId(qrId: String): QrMappingEntity?
}
