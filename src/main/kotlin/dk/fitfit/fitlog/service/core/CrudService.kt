package dk.fitfit.fitlog.service.core

import io.micronaut.data.repository.CrudRepository
import java.io.Serializable
import javax.persistence.EntityNotFoundException

interface CrudService<T, ID : Serializable> {
    val crudRepository: CrudRepository<T, ID>

    fun save(entity: T): T = crudRepository.save(entity)

    fun findAll(): Iterable<T> = crudRepository.findAll()

    fun get(id: ID): T {
        val entity = crudRepository.findById(id)
        if (entity.isEmpty) throw EntityNotFoundException("Id: $id")
        return entity.get()
    }

    fun delete(id: ID) = crudRepository.deleteById(id)
}
