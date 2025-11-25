package net.konohana.sakuya.konopass.qr.infrastructure.repository

import net.konohana.sakuya.konopass.qr.infrastructure.entity.TEntriesEntity
import org.springframework.data.jpa.repository.JpaRepository

interface TEntriesRepository : JpaRepository<TEntriesEntity, Int> {

    /**
     * QRIDに基づいて最新の入場記録を検索します。
     * TEntriesテーブルの構造に基づき、QRIDでの検索メソッドを定義。
     */
    fun findByQrCodeId(qrCodeId: String): TEntriesEntity?
}
