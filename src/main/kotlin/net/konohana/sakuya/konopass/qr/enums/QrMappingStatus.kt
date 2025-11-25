package net.konohana.sakuya.konopass.qr.enums

enum class QrMappingStatus(
    /**
     * このステータスの日本語表示名。
     */
    val displayName: String
) {
    /** 有効・利用前 */
    VALID("有効"), // NOT_USEDよりも「利用可能」であることが明確な名称に変更

    /** 利用中（入場済み、出場待ち） */
    IN_USE("利用中"),

    /** 利用済（入場・出場完了） */
    USED("利用済"),

    // --- 追加ステータス ---

    /** 期限切れ */
    EXPIRED("期限切れ"),

    /** キャンセル済み */
    CANCELED("キャンセル済"),

    /** 利用停止（不正利用やシステムロック） */
    LOCKED("利用停止");

    // ... (ファクトリメソッドはそのまま) ...
    companion object {
        /**
         * 指定された表示名（日本語）に一致する[QrMappingStatus]を返します。
         * @param displayName 検索する表示名。
         * @return 対応する[QrMappingStatus]、または見つからない場合はnull。
         */
        fun fromDisplayName(displayName: String): QrMappingStatus? {
            return entries.find { it.displayName == displayName }
        }

        /**
         * 指定されたEnum名（英数字の定数名）に一致する[QrMappingStatus]を返します。
         * @param name 検索するEnum名 (例: "VALID")。
         * @return 対応する[QrMappingStatus]、または見つからない場合はnull。
         */
        fun fromNameIgnoreCase(name: String): QrMappingStatus? {
            return entries.find { it.name.equals(name, ignoreCase = true) }
        }
    }
}
