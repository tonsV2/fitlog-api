package dk.fitfit.fitlog.authentication

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import dk.fitfit.fitlog.configuration.AuthenticationConfiguration
import dk.fitfit.fitlog.domain.User
import dk.fitfit.fitlog.service.UserService
import dk.fitfit.fitlog.service.impl.UserNotFoundException
import io.micronaut.security.authentication.*
import io.reactivex.Flowable
import org.reactivestreams.Publisher
import javax.inject.Singleton

@Singleton
class AuthenticationProviderUserPassword(private val authenticationConfiguration: AuthenticationConfiguration,
                                         private val userService: UserService,
                                         private val googleTokenVerifier: GoogleTokenVerifier) : AuthenticationProvider {
    override fun authenticate(authenticationRequest: AuthenticationRequest<*, *>?): Publisher<AuthenticationResponse> {
        if (authenticationRequest != null) {
            if (authenticationRequest.identity == "google" && authenticationRequest.secret != null) {
                googleTokenVerifier.verifyToken(authenticationRequest.secret as String)?.let {
                    val email = it.email
                    val name = it["name"] as String
                    if (it.email != null && it.emailVerified) {
                        // TODO: If payload.emailVerified == false create AuthenticationFailureReason and pass to AuthenticationFailed
                        val user = createUserIfNotFound(email, name)
                        val roles = user.roles.map { role -> role.name }
                        return Flowable.just(UserDetails(email, roles))
                    }
                }
            }

            if (authenticationRequest.identity == authenticationConfiguration.adminUserEmail && authenticationRequest.secret == authenticationConfiguration.adminUserPassword) {
                val user = userService.getByEmail(authenticationConfiguration.adminUserEmail)
                val roles = user.roles.map { role -> role.name }
                return Flowable.just(UserDetails(authenticationConfiguration.adminUserEmail, roles))
            }

            if (authenticationRequest.identity == authenticationConfiguration.testUserEmail && authenticationRequest.secret == authenticationConfiguration.testUserPassword) {
                return Flowable.just(UserDetails(authenticationConfiguration.testUserEmail, listOf()))
            }
        }

        return Flowable.just(AuthenticationFailed())
    }

    private fun createUserIfNotFound(email: String, name: String): User = try {
        userService.getByEmail(email)
    } catch (e: UserNotFoundException) {
        userService.save(User(email, name))
    }
}

@Singleton
class GoogleTokenVerifier(private val authenticationConfiguration: AuthenticationConfiguration) {
    fun verifyToken(secret: String): GoogleIdToken.Payload? {
        val transport = NetHttpTransport()
        val jsonFactory = JacksonFactory()
        val verifier = GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(listOf(authenticationConfiguration.androidClientId))
                .build()
        val token = verifier.verify(secret)
        return token?.payload
    }
}
