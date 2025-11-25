package net.konohana.sakuya.konopass.qr.controller.requet

/**
 * 端末の動作モードを更新するためのリクエストボディ
 * @param newMode 新しいモード名 (例: "ENTRY", "EXIT")
 */
data class UpdateTerminalModeRequest(
    val newMode: String
)
