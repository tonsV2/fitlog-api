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
        // Exercises
        val jumpingJacks = Exercise("Jumping Jacks", "A jumping jack or star jump, also called side-straddle hop in the US military, is a physical jumping exercise performed by jumping to a position with the legs spread wide and the hands going overhead, sometimes in a clap, and then returning to a position with the feet together and the arms at the sides", adminUser)
        exerciseService.save(jumpingJacks)
        val crunches = Exercise("Crunches", "The crunch is one of the most popular abdominal exercises. It involves the entire abs, but primarily it works the rectus abdominis muscle and also works the obliques. It allows both building six-pack abs, and tightening the belly", adminUser)
        exerciseService.save(crunches)
        val lunges = Exercise("Lunges", "A lunge can refer to any position of the human body where one leg is positioned forward with knee bent and foot flat on the ground while the other leg is positioned behind", adminUser)
        exerciseService.save(lunges)
        val burpees = Exercise("Burpees", "The burpee, or squat thrust, is a full body exercise used in strength training and as an aerobic exercise. The basic movement is performed in four steps and known as a \"four-count burpee\": Begin in a standing position. Move into a squat position with your hands on the ground", adminUser)
        exerciseService.save(burpees)
        val squat = Exercise("Squat", "A squat is a strength exercise in which the trainee lowers their hips from a standing position and then stands back up. During the descent of a squat, the hip and knee joints flex while the ankle joint dorsiflexes; conversely the hip and knee joints extend and the ankle joint plantarflexes when standing up", adminUser)
        exerciseService.save(squat)
        val situps = Exercise("Sit-up", "The sit-up is an abdominal endurance training exercise to strengthen, tighten and tone the abdominal muscles. It is similar to a crunch, but sit-ups have a fuller range of motion and condition additional muscles", adminUser)
        exerciseService.save(situps)
        val doubleUnder = Exercise("Double Under", "A double under is a popular exercise done on a jump rope in which the rope makes two passes per jump instead of just one. It is significantly more effective than a single rope pass in that it allows for higher work capacity", adminUser)
        exerciseService.save(doubleUnder)
        val highKnees = Exercise("High Knees", "High knees...", adminUser)
        exerciseService.save(highKnees)
        val plank = Exercise("Plank", "The plank is an isometric core strength exercise that involves maintaining a position similar to a push-up for the maximum possible time", adminUser)
        exerciseService.save(plank)
        val hangingLegRaises = Exercise("Hanging Leg Raises", "Raise your legs while hanging....", adminUser)
        exerciseService.save(hangingLegRaises)
        val pushUps = Exercise("Push ups", "Push up....", adminUser)
        exerciseService.save(pushUps)

        // Workouts
        // TODO: Add USMC and other workouts
        // Introduce some of these as challanges
        // https://officercandidatesschool.com/2014/08/11/challenge-marsoc-short-long-cards/
        signe(adminUser, jumpingJacks, lunges, crunches, burpees)
        roskva(adminUser, burpees, squat, situps)
        roskvaHardcore(adminUser, burpees, squat, situps)
        bellyFlattener(adminUser, doubleUnder, highKnees, situps, plank)
        bellyFlattenerHardcore(adminUser, doubleUnder, highKnees, situps, plank)
        randi(adminUser, burpees, squat, situps)
        anna(adminUser, pushUps, hangingLegRaises, squat)
    }

    private fun anna(adminUser: User, pushUps: Exercise, hangingLegRaises: Exercise, squat: Exercise) {
        val annaWorkout = Workout("Anna", "Chest, stomach and legs...", null, adminUser)
        val annaWorkoutId = workoutService.save(annaWorkout).id

        val annaRound = Round(0, 5, 0, null)
        val annaRoundId = workoutService.save(annaWorkoutId, annaRound).id

        val pushUpsRoundExercise = RoundExercise(pushUps, 10, 0, 0)
        workoutService.save(annaRoundId, pushUpsRoundExercise)

        val hangingLegRaisesRoundExercise = RoundExercise(hangingLegRaises, 10, 0, 0)
        workoutService.save(annaRoundId, hangingLegRaisesRoundExercise)

        val squatRoundExercise = RoundExercise(squat, 20, 0, 0)
        workoutService.save(annaRoundId, squatRoundExercise)
    }

    private fun bellyFlattenerHardcore(adminUser: User, doubleUnder: Exercise, highKnees: Exercise, situps: Exercise, plank: Exercise) {
        val bellyFlattenerHardcoreWorkout = Workout("Belly Flattener Hardcore", "If the normal \"Belly Flattener\" is too easy this edition should give you a sweat... If not, do it twice!", null, adminUser)
        val bellyFlattenerHardcoreWorkoutId = workoutService.save(bellyFlattenerHardcoreWorkout).id

        // Round 0
        val bellyFlattenerHardcoreRound0 = Round(0, 1, 0, null)
        val bellyFlattenerHardcoreRound0Id = workoutService.save(bellyFlattenerHardcoreWorkoutId, bellyFlattenerHardcoreRound0).id

        val doubleUnderBellyFlattenerRound0Exercise = RoundExercise(doubleUnder, 50, 0, 0)
        workoutService.save(bellyFlattenerHardcoreRound0Id, doubleUnderBellyFlattenerRound0Exercise)
        val highKneesBellyFlattenerRound0Exercise = RoundExercise(highKnees, 50, 0, 1)
        workoutService.save(bellyFlattenerHardcoreRound0Id, highKneesBellyFlattenerRound0Exercise)
        val situpsBellyFlattenerRound0Exercise = RoundExercise(situps, 50, 0, 2)
        workoutService.save(bellyFlattenerHardcoreRound0Id, situpsBellyFlattenerRound0Exercise)
        val plankBellyFlattenerRound0Exercise = RoundExercise(plank, 1, 120, 3)
        workoutService.save(bellyFlattenerHardcoreRound0Id, plankBellyFlattenerRound0Exercise)

        // Round 1
        val bellyFlattenerHardcoreRound1 = Round(1, 1, 0, null)
        val bellyFlattenerHardcoreRound1Id = workoutService.save(bellyFlattenerHardcoreWorkoutId, bellyFlattenerHardcoreRound1).id

        val doubleUnderBellyFlattenerRound1Exercise = RoundExercise(doubleUnder, 40, 0, 0)
        workoutService.save(bellyFlattenerHardcoreRound1Id, doubleUnderBellyFlattenerRound1Exercise)
        val highKneesBellyFlattenerRound1Exercise = RoundExercise(highKnees, 40, 0, 1)
        workoutService.save(bellyFlattenerHardcoreRound1Id, highKneesBellyFlattenerRound1Exercise)
        val situpsBellyFlattenerRound1Exercise = RoundExercise(situps, 40, 0, 2)
        workoutService.save(bellyFlattenerHardcoreRound1Id, situpsBellyFlattenerRound1Exercise)
        val plankBellyFlattenerRound1Exercise = RoundExercise(plank, 1, 120, 3)
        workoutService.save(bellyFlattenerHardcoreRound1Id, plankBellyFlattenerRound1Exercise)

        // Round 2
        val bellyFlattenerHardcoreRound2 = Round(2, 1, 0, null)
        val bellyFlattenerHardcoreRound2Id = workoutService.save(bellyFlattenerHardcoreWorkoutId, bellyFlattenerHardcoreRound2).id

        val doubleUnderBellyFlattenerRound2Exercise = RoundExercise(doubleUnder, 30, 0, 0)
        workoutService.save(bellyFlattenerHardcoreRound2Id, doubleUnderBellyFlattenerRound2Exercise)
        val highKneesBellyFlattenerRound2Exercise = RoundExercise(highKnees, 30, 0, 1)
        workoutService.save(bellyFlattenerHardcoreRound2Id, highKneesBellyFlattenerRound2Exercise)
        val situpsBellyFlattenerRound2Exercise = RoundExercise(situps, 30, 0, 2)
        workoutService.save(bellyFlattenerHardcoreRound2Id, situpsBellyFlattenerRound2Exercise)
        val plankBellyFlattenerRound2Exercise = RoundExercise(plank, 1, 120, 3)
        workoutService.save(bellyFlattenerHardcoreRound2Id, plankBellyFlattenerRound2Exercise)

        // Round 3
        val bellyFlattenerHardcoreRound3 = Round(3, 1, 0, null)
        val bellyFlattenerHardcoreRound3Id = workoutService.save(bellyFlattenerHardcoreWorkoutId, bellyFlattenerHardcoreRound3).id

        val doubleUnderBellyFlattenerRound3Exercise = RoundExercise(doubleUnder, 20, 0, 0)
        workoutService.save(bellyFlattenerHardcoreRound3Id, doubleUnderBellyFlattenerRound3Exercise)
        val highKneesBellyFlattenerRound3Exercise = RoundExercise(highKnees, 20, 0, 1)
        workoutService.save(bellyFlattenerHardcoreRound3Id, highKneesBellyFlattenerRound3Exercise)
        val situpsBellyFlattenerRound3Exercise = RoundExercise(situps, 20, 0, 2)
        workoutService.save(bellyFlattenerHardcoreRound3Id, situpsBellyFlattenerRound3Exercise)
        val plankBellyFlattenerRound3Exercise = RoundExercise(plank, 1, 120, 3)
        workoutService.save(bellyFlattenerHardcoreRound3Id, plankBellyFlattenerRound3Exercise)

        // Round 4
        val bellyFlattenerHardcoreRound4 = Round(4, 1, 0, null)
        val bellyFlattenerHardcoreRound4Id = workoutService.save(bellyFlattenerHardcoreWorkoutId, bellyFlattenerHardcoreRound4).id

        val doubleUnderBellyFlattenerRound4Exercise = RoundExercise(doubleUnder, 10, 0, 0)
        workoutService.save(bellyFlattenerHardcoreRound4Id, doubleUnderBellyFlattenerRound4Exercise)
        val highKneesBellyFlattenerRound4Exercise = RoundExercise(highKnees, 10, 0, 1)
        workoutService.save(bellyFlattenerHardcoreRound4Id, highKneesBellyFlattenerRound4Exercise)
        val situpsBellyFlattenerRound4Exercise = RoundExercise(situps, 10, 0, 2)
        workoutService.save(bellyFlattenerHardcoreRound4Id, situpsBellyFlattenerRound4Exercise)
        val plankBellyFlattenerRound4Exercise = RoundExercise(plank, 1, 120, 3)
        workoutService.save(bellyFlattenerHardcoreRound4Id, plankBellyFlattenerRound4Exercise)
    }

    private fun bellyFlattener(adminUser: User, doubleUnder: Exercise, highKnees: Exercise, situps: Exercise, plank: Exercise) {
        val bellyFlattenerWorkout = Workout("Belly Flattener", "Burn calories and work your abs! While abs are revealed through your diet this exercise strengthen them", null, adminUser)
        val bellyFlattenerWorkoutId = workoutService.save(bellyFlattenerWorkout).id

        val bellyFlattenerRound = Round(0, 5, 0, null)
        val bellyFlattenerRoundId = workoutService.save(bellyFlattenerWorkoutId, bellyFlattenerRound).id

        val doubleUnderBellyFlattenerExercise = RoundExercise(doubleUnder, 20, 0, 0)
        workoutService.save(bellyFlattenerRoundId, doubleUnderBellyFlattenerExercise)
        val highKneesBellyFlattenerExercise = RoundExercise(highKnees, 20, 0, 1)
        workoutService.save(bellyFlattenerRoundId, highKneesBellyFlattenerExercise)
        val situpsBellyFlattenerExercise = RoundExercise(situps, 20, 0, 2)
        workoutService.save(bellyFlattenerRoundId, situpsBellyFlattenerExercise)
        val plankBellyFlattenerExercise = RoundExercise(plank, 1, 120, 3)
        workoutService.save(bellyFlattenerRoundId, plankBellyFlattenerExercise)
    }

    private fun signe(adminUser: User, jumpingJacks: Exercise, lunges: Exercise, crunches: Exercise, burpees: Exercise) {
        val workout = Workout("Signe", "Good all round workout", null, adminUser)
        val workoutId = workoutService.save(workout).id

        val round = Round(0, 5, 0, null)
        val roundId = workoutService.save(workoutId, round).id

        val jumpingJacksExercise = RoundExercise(jumpingJacks, 50, 0, 0)
        workoutService.save(roundId, jumpingJacksExercise)
        val lungesExercise = RoundExercise(lunges, 20, 0, 1)
        workoutService.save(roundId, lungesExercise)
        val crunchesExercise = RoundExercise(crunches, 20, 0, 2)
        workoutService.save(roundId, crunchesExercise)
        val burpeesExercise = RoundExercise(burpees, 20, 0, 3)
        workoutService.save(roundId, burpeesExercise)
    }

    private fun roskva(adminUser: User, burpees: Exercise, squat: Exercise, situps: Exercise) {
        val fullBodyBlasterWorkout = Workout("Roskva", "bla bla", null, adminUser)
        val fullBodyBlasterWorkoutId = workoutService.save(fullBodyBlasterWorkout).id

        val fullBodyBlasterRound0 = Round(0, 5, 0, null)
        val fullBodyBlasterRound0Id = workoutService.save(fullBodyBlasterWorkoutId, fullBodyBlasterRound0).id

        val burpeesFullBodyBlasterExercise0 = RoundExercise(burpees, 20, 0, 0)
        workoutService.save(fullBodyBlasterRound0Id, burpeesFullBodyBlasterExercise0)
        val squatFullBodyBlasterExercise0 = RoundExercise(squat, 20, 0, 1)
        workoutService.save(fullBodyBlasterRound0Id, squatFullBodyBlasterExercise0)
        val situpsFullBodyBlasterExercise0 = RoundExercise(situps, 20, 0, 3)
        workoutService.save(fullBodyBlasterRound0Id, situpsFullBodyBlasterExercise0)
    }

    private fun roskvaHardcore(adminUser: User, burpees: Exercise, squat: Exercise, situps: Exercise) {
        val fullBodyBlasterWorkout = Workout("Roskva Hardcore", "bla bla", null, adminUser)
        val fullBodyBlasterWorkoutId = workoutService.save(fullBodyBlasterWorkout).id

        // Round 0
        val fullBodyBlasterRound0 = Round(0, 1, 0, null)
        val fullBodyBlasterRound0Id = workoutService.save(fullBodyBlasterWorkoutId, fullBodyBlasterRound0).id

        val burpeesFullBodyBlasterExercise0 = RoundExercise(burpees, 50, 0, 0)
        workoutService.save(fullBodyBlasterRound0Id, burpeesFullBodyBlasterExercise0)
        val squatFullBodyBlasterExercise0 = RoundExercise(squat, 50, 0, 1)
        workoutService.save(fullBodyBlasterRound0Id, squatFullBodyBlasterExercise0)
        val situpsFullBodyBlasterExercise0 = RoundExercise(situps, 50, 0, 3)
        workoutService.save(fullBodyBlasterRound0Id, situpsFullBodyBlasterExercise0)

        // Round 1
        val fullBodyBlasterRound1 = Round(0, 1, 0, null)
        val fullBodyBlasterRound1Id = workoutService.save(fullBodyBlasterWorkoutId, fullBodyBlasterRound1).id

        val burpeesFullBodyBlasterExercise1 = RoundExercise(burpees, 40, 0, 0)
        workoutService.save(fullBodyBlasterRound1Id, burpeesFullBodyBlasterExercise1)
        val squatFullBodyBlasterExercise1 = RoundExercise(squat, 40, 0, 1)
        workoutService.save(fullBodyBlasterRound1Id, squatFullBodyBlasterExercise1)
        val situpsFullBodyBlasterExercise1 = RoundExercise(situps, 40, 0, 3)
        workoutService.save(fullBodyBlasterRound1Id, situpsFullBodyBlasterExercise1)

        // Round 2
        val fullBodyBlasterRound2 = Round(0, 1, 0, null)
        val fullBodyBlasterRound2Id = workoutService.save(fullBodyBlasterWorkoutId, fullBodyBlasterRound2).id

        val burpeesFullBodyBlasterExercise2 = RoundExercise(burpees, 30, 0, 0)
        workoutService.save(fullBodyBlasterRound2Id, burpeesFullBodyBlasterExercise2)
        val squatFullBodyBlasterExercise2 = RoundExercise(squat, 30, 0, 1)
        workoutService.save(fullBodyBlasterRound2Id, squatFullBodyBlasterExercise2)
        val situpsFullBodyBlasterExercise2 = RoundExercise(situps, 30, 0, 3)
        workoutService.save(fullBodyBlasterRound2Id, situpsFullBodyBlasterExercise2)

        // Round 3
        val fullBodyBlasterRound3 = Round(0, 1, 0, null)
        val fullBodyBlasterRound3Id = workoutService.save(fullBodyBlasterWorkoutId, fullBodyBlasterRound3).id

        val burpeesFullBodyBlasterExercise3 = RoundExercise(burpees, 20, 0, 0)
        workoutService.save(fullBodyBlasterRound3Id, burpeesFullBodyBlasterExercise3)
        val squatFullBodyBlasterExercise3 = RoundExercise(squat, 20, 0, 1)
        workoutService.save(fullBodyBlasterRound3Id, squatFullBodyBlasterExercise3)
        val situpsFullBodyBlasterExercise3 = RoundExercise(situps, 20, 0, 3)
        workoutService.save(fullBodyBlasterRound3Id, situpsFullBodyBlasterExercise3)

        // Round 4
        val fullBodyBlasterRound4 = Round(0, 1, 0, null)
        val fullBodyBlasterRound4Id = workoutService.save(fullBodyBlasterWorkoutId, fullBodyBlasterRound4).id

        val burpeesFullBodyBlasterExercise4 = RoundExercise(burpees, 10, 0, 0)
        workoutService.save(fullBodyBlasterRound4Id, burpeesFullBodyBlasterExercise4)
        val squatFullBodyBlasterExercise4 = RoundExercise(squat, 10, 0, 1)
        workoutService.save(fullBodyBlasterRound4Id, squatFullBodyBlasterExercise4)
        val situpsFullBodyBlasterExercise4 = RoundExercise(situps, 10, 0, 3)
        workoutService.save(fullBodyBlasterRound4Id, situpsFullBodyBlasterExercise4)
    }

    private fun randi(adminUser: User, burpees: Exercise, squat: Exercise, situps: Exercise) {
        val fullBodyBlasterWorkout = Workout("Randi", "To be honest this workout was created to test the mixing of repeating and multiple rounds", null, adminUser)
        val fullBodyBlasterWorkoutId = workoutService.save(fullBodyBlasterWorkout).id

        // Round 0
        val fullBodyBlasterRound0 = Round(0, 2, 20, null)
        val fullBodyBlasterRound0Id = workoutService.save(fullBodyBlasterWorkoutId, fullBodyBlasterRound0).id

        val burpeesFullBodyBlasterExercise0 = RoundExercise(burpees, 10, 0, 0)
        workoutService.save(fullBodyBlasterRound0Id, burpeesFullBodyBlasterExercise0)
        val squatFullBodyBlasterExercise0 = RoundExercise(squat, 10, 0, 1)
        workoutService.save(fullBodyBlasterRound0Id, squatFullBodyBlasterExercise0)
        val situpsFullBodyBlasterExercise0 = RoundExercise(situps, 10, 0, 3)
        workoutService.save(fullBodyBlasterRound0Id, situpsFullBodyBlasterExercise0)

        // Round 1
        val fullBodyBlasterRound1 = Round(0, 2, 20, null)
        val fullBodyBlasterRound1Id = workoutService.save(fullBodyBlasterWorkoutId, fullBodyBlasterRound1).id

        val burpeesFullBodyBlasterExercise1 = RoundExercise(burpees, 5, 0, 0)
        workoutService.save(fullBodyBlasterRound1Id, burpeesFullBodyBlasterExercise1)
        val squatFullBodyBlasterExercise1 = RoundExercise(squat, 5, 0, 1)
        workoutService.save(fullBodyBlasterRound1Id, squatFullBodyBlasterExercise1)
        val situpsFullBodyBlasterExercise1 = RoundExercise(situps, 5, 0, 3)
        workoutService.save(fullBodyBlasterRound1Id, situpsFullBodyBlasterExercise1)

        // Round 2
        val fullBodyBlasterRound2 = Round(0, 2, 20, null)
        val fullBodyBlasterRound2Id = workoutService.save(fullBodyBlasterWorkoutId, fullBodyBlasterRound2).id

        val burpeesFullBodyBlasterExercise2 = RoundExercise(burpees, 10, 0, 0)
        workoutService.save(fullBodyBlasterRound2Id, burpeesFullBodyBlasterExercise2)
        val squatFullBodyBlasterExercise2 = RoundExercise(squat, 10, 0, 1)
        workoutService.save(fullBodyBlasterRound2Id, squatFullBodyBlasterExercise2)
        val situpsFullBodyBlasterExercise2 = RoundExercise(situps, 10, 0, 3)
        workoutService.save(fullBodyBlasterRound2Id, situpsFullBodyBlasterExercise2)

        // Round 3
        val fullBodyBlasterRound3 = Round(0, 2, 20, null)
        val fullBodyBlasterRound3Id = workoutService.save(fullBodyBlasterWorkoutId, fullBodyBlasterRound3).id

        val burpeesFullBodyBlasterExercise3 = RoundExercise(burpees, 5, 0, 0)
        workoutService.save(fullBodyBlasterRound3Id, burpeesFullBodyBlasterExercise3)
        val squatFullBodyBlasterExercise3 = RoundExercise(squat, 5, 0, 1)
        workoutService.save(fullBodyBlasterRound3Id, squatFullBodyBlasterExercise3)
        val situpsFullBodyBlasterExercise3 = RoundExercise(situps, 5, 0, 3)
        workoutService.save(fullBodyBlasterRound3Id, situpsFullBodyBlasterExercise3)
    }

}
