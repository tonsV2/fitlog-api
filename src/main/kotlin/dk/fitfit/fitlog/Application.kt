package dk.fitfit.fitlog

import io.micronaut.runtime.Micronaut

object Application {
    @JvmStatic
    fun main(args: Array<String>) {
        Micronaut.build()
                .packages("dk.fitfit.fitlog")
                .mainClass(Application.javaClass)
                .start()
    }
}
