package dk.fitfit.fitlog.service

import io.micronaut.data.repository.CrudRepository
import java.io.Serializable
import javax.persistence.EntityNotFoundException

interface CrudService<T, ID : Serializable> {
    val repository: CrudRepository<T, ID>

    fun save(entity: T): T = repository.save(entity)

    fun findAll(): Iterable<T> = repository.findAll()

    fun get(id: ID): T {
        val entity = repository.findById(id)
        if (entity.isEmpty) throw EntityNotFoundException("Id: $id")
        return entity.get()
    }

    fun delete(id: ID) = repository.deleteById(id)
}
