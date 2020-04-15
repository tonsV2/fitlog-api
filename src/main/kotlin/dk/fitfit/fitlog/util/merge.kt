package dk.fitfit.fitlog.util

import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.primaryConstructor

/*
fun <T> mergeValues(property: KProperty1<out T, Any?>, left: T, right: T): Any? {
    val leftValue = property.getter.call(left)
    val rightValue = property.getter.call(right)
    return rightValue?.let { leftValue?.merge(it) } ?: rightValue ?: leftValue
}

fun <T> lastNonNull(property: KProperty1<out T, Any?>, left: T, right: T) =
        property.getter.call(right) ?: property.getter.call(left)

fun <T : Any> T.merge(other: T): T {
    val nameToProperty = this::class.declaredMemberProperties.associateBy { it.name }
    val primaryConstructor = this::class.primaryConstructor!!
    val args: Map<KParameter, Any?> = primaryConstructor.parameters.associateWith { parameter ->
        val property = nameToProperty[parameter.name]!!
        val type = property.returnType.classifier as KClass<*>
        if (type.isData) mergeValues(property, this, other) else lastNonNull(property, this, other)
    }
    return primaryConstructor.callBy(args)
}
*/

// Source: https://gist.github.com/josdejong/fbb43ae33fcdd922040dac4ffc31aeaf

/**
 * Merge two data classes
 *
 * The resulting data class will contain:
 * - all fields of `other` which are non null
 * - the fields of `this` for the fields which are null in `other`
 *
 * The function is immutable, the original data classes are not changed
 * and a new data class instance is returned.
 *
 * Example usage:
 *
 *     val a = MyDataClass(...)
 *     val b = MyDataClass(...)
 *     val c = a merge b
 */

inline fun <reified T : Any> T.merge(other: T): T {
    val nameToProperty = T::class.declaredMemberProperties.associateBy { it.name }
    val primaryConstructor = T::class.primaryConstructor!!
    val args = primaryConstructor.parameters.associateWith {
        val property = nameToProperty[it.name]!!
        property.get(other) ?: property.get(this)
    }
    return primaryConstructor.callBy(args)
}
