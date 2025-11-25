package net.konohana.sakuya.konopass.qr

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories
class KonopassQrConfigApiApplication

fun main(args: Array<String>) {
	runApplication<KonopassQrConfigApiApplication>(*args)
}
