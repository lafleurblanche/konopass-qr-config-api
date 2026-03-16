package net.konohana.sakuya.konopass.qr.enums

// net.konohana.sakuya.konopass.qr.domain.enums (または適切なパッケージ)
enum class ReaderMode(val code: String, val label: String) {
    PREPARING("0", "設定待ち/準備中"),
    ENTRY("1", "入場"),
    EXIT("2", "出場"),
    SETTLEMENT("3", "精算"),
    MAINTENANCE("9", "メンテナンス");

    companion object {
        fun fromCode(code: String): ReaderMode {
            return ReaderMode.entries.find { it.code == code } ?: PREPARING
        }
    }
}
