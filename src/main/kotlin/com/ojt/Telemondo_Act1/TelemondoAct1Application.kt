package com.ojt.Telemondo_Act1

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TelemondoAct1Application {

//    @Bean
//    fun commandLineRunner(ctx: ApplicationContext) = CommandLineRunner {
//        println("Let's inspect the beans provided by Spring Boot:")
//        val beanNames = ctx.beanDefinitionNames
//        beanNames.sorted().forEach { println(it) }
//    }
}

fun main(args: Array<String>) {
	runApplication<TelemondoAct1Application>(*args)
}
