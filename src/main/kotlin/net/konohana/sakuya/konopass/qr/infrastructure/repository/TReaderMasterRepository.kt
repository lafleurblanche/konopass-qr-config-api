package net.konohana.sakuya.konopass.qr.infrastructure.repository

import net.konohana.sakuya.konopass.qr.infrastructure.entity.TReaderMasterEntity
import org.springframework.data.jpa.repository.JpaRepository

interface TReaderMasterRepository : JpaRepository<TReaderMasterEntity, Int> {

    // findByReaderId は、エンティティの readerId プロパティを参照します。
    fun findByReaderId(readerId: String): TReaderMasterEntity?

    // existsByReaderId も同様に readerId プロパティを参照します。
    fun existsByReaderId(readerId: String): Boolean
}
