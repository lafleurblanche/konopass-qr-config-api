package net.konohana.sakuya.konopass.qr.infrastructure.repository

import net.konohana.sakuya.konopass.qr.infrastructure.entity.TReaderMasterEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional

interface TReaderMasterRepository : JpaRepository<TReaderMasterEntity, Int> {

    fun findByReaderId(readerId: String): TReaderMasterEntity?

    fun existsByReaderId(readerId: String): Boolean

    @Modifying
    @Transactional
    @Query("DELETE FROM TReaderMasterEntity t WHERE t.readerId = :readerId")
    fun deleteByReaderId(@Param("readerId") readerId: String): Int
}
