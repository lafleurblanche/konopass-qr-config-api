package net.konohana.sakuya.konopass.qr.config

import net.konohana.sakuya.konopass.qr.security.JwtAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtAuthenticationFilter: JwtAuthenticationFilter
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .cors { it.configurationSource(corsConfigurationSource()) }
            .csrf { it.disable() } // APIのためCSRF無効
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) } // JWTのためステートレス
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers("/actuator/**").permitAll() // ヘルスチェックは公開
                    .anyRequest().authenticated() // その他はすべて要認証
            }
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }

    @Bean
    fun corsConfigurationSource(): UrlBasedCorsConfigurationSource {
        val configuration = CorsConfiguration()

        // 既存の CorsConfig の設定を統合
        configuration.allowedOrigins = listOf(
            "http://localhost:5173",
            "http://localhost:3000"  // 3000番も追加
        )
        configuration.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS")
        configuration.allowedHeaders = listOf("*") // すべてのヘッダーを許可
        configuration.allowCredentials = true

        val source = UrlBasedCorsConfigurationSource()
        // 全パス、または特定のパス "/api/v1/**" に対して適用
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}
