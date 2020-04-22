package dk.fitfit.fitlog.util

import dk.fitfit.fitlog.configuration.AuthenticationConfiguration
import dk.fitfit.fitlog.domain.*
import dk.fitfit.fitlog.service.ExerciseService
import dk.fitfit.fitlog.service.RoleService
import dk.fitfit.fitlog.service.UserService
import dk.fitfit.fitlog.service.WorkoutService
import io.micronaut.context.event.ApplicationEventListener
import io.micronaut.discovery.event.ServiceStartedEvent
import io.micronaut.scheduling.annotation.Async
import mu.KotlinLogging
import javax.inject.Singleton

private val logger = KotlinLogging.logger {}

@Singleton
open class DataLoader(private val authenticationConfiguration: AuthenticationConfiguration,
                      private val userService: UserService,
                      private val roleService: RoleService,
                      private val exerciseService: ExerciseService,
                      private val workoutService: WorkoutService) : ApplicationEventListener<ServiceStartedEvent> {
    @Async
    override fun onApplicationEvent(event: ServiceStartedEvent) {
        val role = roleService.save(Role(Role.ADMIN))
        val roles = roleService.findAll()

        val adminUser = User(authenticationConfiguration.adminUserEmail, "")
        adminUser.roles.add(role)
        val savedAdmin = userService.save(adminUser)

        val testUser = User(authenticationConfiguration.testUserEmail, "")
        val savedTest = userService.save(testUser)

        val users = userService.findAll()

        populate(adminUser)

        logger.info {
            """
                Creating admin role
                Role: ${role.name}
                All roles: ${roles.joinToString { it.name }}
                Creating admin user
                Email: ${savedAdmin.email}
                Password: ${authenticationConfiguration.adminUserPassword}
                Creating test user
                Email: ${savedTest.email}
                Password: ${authenticationConfiguration.testUserPassword}
                All users: ${users.joinToString { it.email }}
            """.trimIndent()
        }
    }

    private fun populate(adminUser: User) {
        // All round
        val workout = Workout("All round", "bla bla", null, adminUser)
        val workoutId = workoutService.save(workout).id

        val round = Round(0, 5, 0, null)
        val roundId = workoutService.save(workoutId, round).id

        val jumpingJacks = Exercise("Jumping Jacks", "A jumping jack or star jump, also called side-straddle hop in the US military, is a physical jumping exercise performed by jumping to a position with the legs spread wide and the hands going overhead, sometimes in a clap, and then returning to a position with the feet together and the arms at the sides", adminUser)
        exerciseService.save(jumpingJacks)
        val crunches = Exercise("Crunches", "The crunch is one of the most popular abdominal exercises. It involves the entire abs, but primarily it works the rectus abdominis muscle and also works the obliques. It allows both building six-pack abs, and tightening the belly", adminUser)
        exerciseService.save(crunches)
        val lunges = Exercise("Lunges", "A lunge can refer to any position of the human body where one leg is positioned forward with knee bent and foot flat on the ground while the other leg is positioned behind", adminUser)
        exerciseService.save(lunges)
        val burpees = Exercise("Burpees", "The burpee, or squat thrust, is a full body exercise used in strength training and as an aerobic exercise. The basic movement is performed in four steps and known as a \"four-count burpee\": Begin in a standing position. Move into a squat position with your hands on the ground", adminUser)
        exerciseService.save(burpees)

        val jumpingJacksExercise = RoundExercise(jumpingJacks, 50, 0, 0)
        workoutService.save(roundId, jumpingJacksExercise)
        val lungesExercise = RoundExercise(lunges, 20, 0, 1)
        workoutService.save(roundId, lungesExercise)
        val crunchesExercise = RoundExercise(crunches, 20, 0, 2)
        workoutService.save(roundId, crunchesExercise)
        val burpeesExercise = RoundExercise(burpees, 20, 0, 3)
        workoutService.save(roundId, burpeesExercise)

        // Full body blaster
        val aphroditeWorkout = Workout("Full body blaster", "bla bla", null, adminUser)
        val aphroditeWorkoutId = workoutService.save(aphroditeWorkout).id

        val aphroditeRound = Round(0, 5, 0, null)
        val aphroditeRoundId = workoutService.save(aphroditeWorkoutId, aphroditeRound).id

        val squat = Exercise("Squat", "A squat is a strength exercise in which the trainee lowers their hips from a standing position and then stands back up. During the descent of a squat, the hip and knee joints flex while the ankle joint dorsiflexes; conversely the hip and knee joints extend and the ankle joint plantarflexes when standing up", adminUser)
        exerciseService.save(squat)
        val situps = Exercise("Sit-up", "The sit-up is an abdominal endurance training exercise to strengthen, tighten and tone the abdominal muscles. It is similar to a crunch, but sit-ups have a fuller range of motion and condition additional muscles", adminUser)
        exerciseService.save(situps)

        val burpeesAphroditeExercise = RoundExercise(burpees, 50, 0, 0)
        workoutService.save(aphroditeRoundId, burpeesAphroditeExercise)
        val squatAphroditeExercise = RoundExercise(squat, 50, 0, 1)
        workoutService.save(aphroditeRoundId, squatAphroditeExercise)
        val situpsAphroditeExercise = RoundExercise(situps, 50, 0, 3)
        workoutService.save(aphroditeRoundId, situpsAphroditeExercise)
    }
}
