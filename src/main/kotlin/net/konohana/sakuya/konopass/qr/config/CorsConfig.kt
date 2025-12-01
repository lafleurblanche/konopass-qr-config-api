package net.konohana.sakuya.konopass.qr.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

/**
 * **CORS (Cross-Origin Resource Sharing)** 設定を行うための**Spring設定クラス**です。
 *
 * [WebMvcConfigurer]インターフェースを実装し、**クロスオリジンリクエスト**を許可するためのルールを定義します。
 * これにより、異なるドメイン（特にフロントエンドアプリケーション）からのAPIアクセスが可能になります。
 */
@Configuration
class CorsConfig : WebMvcConfigurer {

    /**
     * CORSマッピングを登録し、どのオリジンからのアクセスを許可するかを設定します。
     *
     * @param registry CORS設定を登録するためのレジストリ。
     */
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/api/v1/**") // 照会APIの全パスを対象とする
            .allowedOrigins(
                "http://localhost:3000",
                "http://localhost:5173"
            ) // ★ 許可するオリジン: 開発環境のフロントエンドのオリジンを許可しています。
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 許可するHTTPメソッド
            .allowedHeaders("*") // すべてのヘッダーを許可 (例: Authorization, Content-Typeなど)
            .allowCredentials(true) // クッキーやHTTP認証などの資格情報を含むリクエストを許可
    }
}
