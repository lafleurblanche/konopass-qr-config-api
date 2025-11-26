package net.konohana.sakuya.konopass.qr.infrastructure.repository

import net.konohana.sakuya.konopass.qr.infrastructure.entity.TReaderMasterEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional

interface TReaderMasterRepository : JpaRepository<TReaderMasterEntity, Int> {

    // findByReaderId は、エンティティの readerId プロパティを参照します。
    fun findByReaderId(readerId: String): TReaderMasterEntity?

    // existsByReaderId も同様に readerId プロパティを参照します。
    fun existsByReaderId(readerId: String): Boolean

    // ★ 新規実装: 端末IDをキーとして物理削除するカスタムメソッド
    @Modifying // データ変更クエリであることを示す
    @Transactional // トランザクションを保証
    @Query("DELETE FROM TReaderMasterEntity t WHERE t.readerId = :readerId")
    fun deleteByReaderId(@Param("readerId") readerId: String): Int
}
