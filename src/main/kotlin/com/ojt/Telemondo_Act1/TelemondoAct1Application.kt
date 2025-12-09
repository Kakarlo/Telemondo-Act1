package com.ojt.Telemondo_Act1

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication //
class TelemondoAct1Application {}

fun main(args: Array<String>) {
    // TODO: Create a user entity and try to mimic simple login
    // TODO: Experiment with joins and unique annotations in Model
    // TODO: Experiment with uuid either in the model or service
    // TODO: Try to implement auditing features based on events

    runApplication<TelemondoAct1Application>(*args)
}
