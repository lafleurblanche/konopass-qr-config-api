package net.konohana.sakuya.konopass.qr.controller

import net.konohana.sakuya.konopass.qr.domain.dtos.ReaderMasterCreateRequest
import net.konohana.sakuya.konopass.qr.service.master.ReaderMasterService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/master/reader")
class ReaderMasterController(
    private val masterService: ReaderMasterService
) {

    /**
     * 1. 端末マスタ情報を検索 (R)
     * GET /api/v1/master/reader/{readerId}
     */
    @GetMapping("/{readerId}")
    fun getReaderDetails(@PathVariable readerId: String): ResponseEntity<*> {
        return try {
            val details = masterService.getReaderDetails(readerId)
            ResponseEntity.ok(details)
        } catch (e: NoSuchElementException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapOf("error" to e.message))
        }
    }

    /**
     * 2. 新規端末マスタの登録 (C)
     * POST /api/v1/master/reader
     */
    @PostMapping
    fun createReaderMaster(@RequestBody request: ReaderMasterCreateRequest): ResponseEntity<*> {
        return try {
            val details = masterService.createReaderMaster(
                readerId = request.readerId,
                locationName = request.locationName,
                registeredBy = request.registeredBy
            )
            ResponseEntity.status(HttpStatus.CREATED).body(details)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.status(HttpStatus.CONFLICT).body(mapOf("error" to e.message))
        }
    }

    /**
     * 3. 端末マスタの削除 (D)
     * DELETE /api/v1/master/reader/{readerId}
     */
    @DeleteMapping("/{readerId}")
    fun deleteReaderMaster(@PathVariable readerId: String): ResponseEntity<*> {
        return try {
            masterService.deleteReaderMaster(readerId)
            ResponseEntity.ok(mapOf("message" to "端末ID '$readerId' の削除を試行しました。"))
        } catch (e: NoSuchElementException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapOf("error" to "端末マスタ情報が見つかりません。"))
        } catch (e: IllegalStateException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapOf("error" to e.message))
        }
    }
}
