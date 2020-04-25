package dk.fitfit.fitlog.domain.core

import io.micronaut.data.annotation.DateCreated
import io.micronaut.data.annotation.DateUpdated
import java.time.LocalDateTime
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class BaseEntity(
        @Id @GeneratedValue(strategy = GenerationType.SEQUENCE) override val id: Long = 0,
        @DateUpdated override var updated: LocalDateTime = LocalDateTime.MIN,
        @DateCreated override var created: LocalDateTime = LocalDateTime.MIN
) : IdentifiableEntity<Long>, DateUpdatedEntity<LocalDateTime>, DateCreatedEntity<LocalDateTime>
