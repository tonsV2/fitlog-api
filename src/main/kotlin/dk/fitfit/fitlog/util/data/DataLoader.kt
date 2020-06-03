package dk.fitfit.fitlog.util.data

import dk.fitfit.fitlog.configuration.AuthenticationConfiguration
import dk.fitfit.fitlog.domain.Exercise
import dk.fitfit.fitlog.domain.Role
import dk.fitfit.fitlog.domain.User
import dk.fitfit.fitlog.domain.Workout
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
        val adminRole = roleService.save(Role(Role.ADMIN))
        val roles = roleService.findAll()

        val adminUser = User(authenticationConfiguration.adminUserEmail, "")
        adminUser.roles.add(adminRole)
        val savedAdminUser = userService.save(adminUser)

        val testUser = User(authenticationConfiguration.testUserEmail, "")
        val savedTest = userService.save(testUser)

        val users = userService.findAll()

        populate(savedAdminUser)

        logger.info {
            """
                Creating admin role
                Role: ${adminRole.name}
                All roles: ${roles.joinToString { it.name }}
                Creating admin user
                Email: ${savedAdminUser.email}
                Password: ${authenticationConfiguration.adminUserPassword}
                Creating test user
                Email: ${savedTest.email}
                Password: ${authenticationConfiguration.testUserPassword}
                All users: ${users.joinToString { it.email }}
            """.trimIndent()
        }
    }

    private fun populate(user: User) {
        // Exercises
        val jumpingJacks = Exercise("Jumping Jacks", "A jumping jack or star jump, also called side-straddle hop in the US military, is a physical jumping exercise performed by jumping to a position with the legs spread wide and the hands going overhead, sometimes in a clap, and then returning to a position with the feet together and the arms at the sides", user)
        exerciseService.save(jumpingJacks)
        val crunches = Exercise("Crunches", "The crunch is one of the most popular abdominal exercises. It involves the entire abs, but primarily it works the rectus abdominis muscle and also works the obliques. It allows both building six-pack abs, and tightening the belly", user)
        exerciseService.save(crunches)
        val lunges = Exercise("Lunges", "A lunge can refer to any position of the human body where one leg is positioned forward with knee bent and foot flat on the ground while the other leg is positioned behind", user)
        exerciseService.save(lunges)
        val burpees = Exercise("Burpees", "The burpee, or squat thrust, is a full body exercise used in strength training and as an aerobic exercise. The basic movement is performed in four steps and known as a \"four-count burpee\": Begin in a standing position. Move into a squat position with your hands on the ground", user)
        exerciseService.save(burpees)
        val airSquat = Exercise("Air Squat", "A squat is a strength exercise in which the trainee lowers their hips from a standing position and then stands back up. During the descent of a squat, the hip and knee joints flex while the ankle joint dorsiflexes; conversely the hip and knee joints extend and the ankle joint plantarflexes when standing up", user)
        exerciseService.save(airSquat)
        val situps = Exercise("Sit-up", "The sit-up is an abdominal endurance training exercise to strengthen, tighten and tone the abdominal muscles. It is similar to a crunch, but sit-ups have a fuller range of motion and condition additional muscles", user)
        exerciseService.save(situps)
        val doubleUnder = Exercise("Double Under", "A double under is a popular exercise done on a jump rope in which the rope makes two passes per jump instead of just one. It is significantly more effective than a single rope pass in that it allows for higher work capacity", user)
        exerciseService.save(doubleUnder)
        val highKnees = Exercise("High Knees", "High knees...", user)
        exerciseService.save(highKnees)
        val plank = Exercise("Plank", "The plank is an isometric core strength exercise that involves maintaining a position similar to a push-up for the maximum possible time", user)
        exerciseService.save(plank)
        val hangingLegRaises = Exercise("Hanging Leg Raises", "Raise your legs while hanging....", user)
        exerciseService.save(hangingLegRaises)
        val pushUps = Exercise("Push ups", "Push up....", user)
        exerciseService.save(pushUps)

        val deadlift = Exercise("Deadlift", "Deadlift....", user)
        exerciseService.save(deadlift)
        val benchPress = Exercise("Bench press", "Bench press....", user)
        exerciseService.save(benchPress)
        val shoulderPress = Exercise("Shoulder press", "Shoulder press....", user)
        exerciseService.save(shoulderPress)
        val pullUps = Exercise("Pull-Ups", "Pull-Ups....", user)
        exerciseService.save(pullUps)
        val squat = Exercise("Squat", "A squat is a strength exercise in which the trainee lowers their hips from a standing position and then stands back up. During the descent of a squat, the hip and knee joints flex while the ankle joint dorsiflexes; conversely the hip and knee joints extend and the ankle joint plantarflexes when standing up", user)
        exerciseService.save(squat)

        // Workouts
        // TODO: Add USMC and other workouts
        // Introduce some of these as challanges
        // https://officercandidatesschool.com/2014/08/11/challenge-marsoc-short-long-cards/
        signe(user, jumpingJacks, lunges, crunches, burpees)
        roskva(user, burpees, airSquat, situps)
        roskvaHardcore(user, burpees, airSquat, situps)
        bellyFlattener(user, doubleUnder, highKnees, situps, plank)
        bellyFlattenerHardcore(user, doubleUnder, highKnees, situps, plank)
        randi(user, burpees, airSquat, situps)
        anna(user, pushUps, hangingLegRaises, airSquat)
        zacharyTellier(user, burpees, pushUps, lunges, situps, airSquat)

        wednesdayWorkout(user, benchPress, squat, pullUps, deadlift)
        fiveExercisesToGetBuff(user, benchPress, squat, pullUps, deadlift, shoulderPress)

        // Source: https://www.lifehacker.com.au/2020/06/the-five-best-exercises-for-the-gym/
        fiveExercisesToGetBuffSplitDay1(user, benchPress, squat, pullUps)
        fiveExercisesToGetBuffSplitDay2(user, deadlift, shoulderPress, pullUps)
        fiveExercisesToGetBuffSplitDay3(user, deadlift, squat, pullUps)
    }

    private fun wednesdayWorkout(user: User, benchPress: Exercise, squat: Exercise, pullUps: Exercise, deadlift: Exercise) {
        val name = "Wednesday Workout"
        val description = "What I did every Wednesday for a while"

        val round1 = RoundBuilder(3)
                .addExercise(benchPress, 10, 0)

        val round2 = RoundBuilder(3)
                .addExercise(squat, 10, 0)

        val round3 = RoundBuilder(3)
                .addExercise(deadlift, 10, 0)

        val round4 = RoundBuilder(3)
                .addExercise(pullUps, 10, 0)

        createWorkout(name, description, user, round1, round2, round3, round4)
    }

    private fun fiveExercisesToGetBuff(user: User, benchPress: Exercise, squat: Exercise, pullUps: Exercise, deadlift: Exercise, shoulderPress: Exercise) {
        val name = "FiveBuff"
        val description = "Same as \"Wednesday workout\" plus shoulder press"

        val round1 = RoundBuilder(3)
                .addExercise(benchPress, 10, 0)

        val round2 = RoundBuilder(3)
                .addExercise(squat, 10, 0)

        val round3 = RoundBuilder(3)
                .addExercise(deadlift, 10, 0)

        val round4 = RoundBuilder(3)
                .addExercise(pullUps, 10, 0)

        val round5 = RoundBuilder(3)
                .addExercise(shoulderPress, 10, 0)

        createWorkout(name, description, user, round1, round2, round3, round4, round5)
    }

    private fun fiveExercisesToGetBuffSplitDay1(user: User, benchPress: Exercise, squat: Exercise, pullUps: Exercise) {
    }

    private fun fiveExercisesToGetBuffSplitDay2(user: User, deadlift: Exercise, shoulderPress: Exercise, pullUps: Exercise) {
    }

    private fun fiveExercisesToGetBuffSplitDay3(user: User, deadlift: Exercise, shoulderPress: Exercise, pullUps: Exercise) {
    }

    private fun zacharyTellier(user: User, burpees: Exercise, pushUps: Exercise, lunges: Exercise, situps: Exercise, airSquat: Exercise) {
        val name = "Zachary Tellier"
        val description = "https://www.boxrox.com/crossfit-bodyweight-workouts-that-will-put-you-in-the-pain-cave-scaled-options-included-advice/"

        val round1 = RoundBuilder(1)
                .addExercise(burpees, 10, 0)

        val round2 = RoundBuilder(1)
                .addExercise(burpees, 10, 0)
                .addExercise(pushUps, 25, 0)

        val round3 = RoundBuilder(1)
                .addExercise(burpees, 10, 0)
                .addExercise(pushUps, 25, 0)
                .addExercise(lunges, 50, 0)

        val round4 = RoundBuilder(1)
                .addExercise(burpees, 10, 0)
                .addExercise(pushUps, 25, 0)
                .addExercise(lunges, 50, 0)
                .addExercise(situps, 100, 0)

        val round5 = RoundBuilder(1)
                .addExercise(burpees, 10, 0)
                .addExercise(pushUps, 25, 0)
                .addExercise(lunges, 50, 0)
                .addExercise(situps, 100, 0)
                .addExercise(airSquat, 150, 0)

        createWorkout(name, description, user, round1, round2, round3, round4, round5)
    }

    private fun anna(user: User, pushUps: Exercise, hangingLegRaises: Exercise, airSquat: Exercise) {
        val name = "Anna"
        val description = "Chest, stomach and legs..."

        val roundBuilder = RoundBuilder(5)
                .addExercise(pushUps, 10, 0)
                .addExercise(hangingLegRaises, 10, 0)
                .addExercise(airSquat, 20, 0)

        createWorkout(name, description, user, roundBuilder)
    }

    private fun bellyFlattenerHardcore(user: User, doubleUnder: Exercise, highKnees: Exercise, situps: Exercise, plank: Exercise) {
        val name = "Belly Flattener Hardcore"
        val description = "If the normal \"Belly Flattener\" is too easy this edition should give you a sweat... If not, do it twice!"

        val round1 = RoundBuilder(1)
                .addExercise(doubleUnder, 50)
                .addExercise(highKnees, 50)
                .addExercise(situps, 50)
                .addExercise(plank, 1, 120)

        val round2 = RoundBuilder(1)
                .addExercise(doubleUnder, 40)
                .addExercise(highKnees, 40)
                .addExercise(situps, 40)
                .addExercise(plank, 1, 120)

        val round3 = RoundBuilder(1)
                .addExercise(doubleUnder, 30)
                .addExercise(highKnees, 30)
                .addExercise(situps, 30)
                .addExercise(plank, 1, 120)

        val round4 = RoundBuilder(1)
                .addExercise(doubleUnder, 20)
                .addExercise(highKnees, 20)
                .addExercise(situps, 20)
                .addExercise(plank, 1, 120)

        val round5 = RoundBuilder(1)
                .addExercise(doubleUnder, 10)
                .addExercise(highKnees, 10)
                .addExercise(situps, 10)
                .addExercise(plank, 1, 120)

        createWorkout(name, description, user, round1, round2, round3, round4, round5)
    }

    private fun bellyFlattener(user: User, doubleUnder: Exercise, highKnees: Exercise, situps: Exercise, plank: Exercise) {
        val name = "Belly Flattener"
        val description = "Burn calories and work your abs! While abs are revealed through your diet this exercise strengthen them"

        val round = RoundBuilder(5)
                .addExercise(doubleUnder, 20)
                .addExercise(highKnees, 20)
                .addExercise(situps, 20)
                .addExercise(plank, 1, 120)

        createWorkout(name, description, user, round)
    }

    private fun signe(user: User, jumpingJacks: Exercise, lunges: Exercise, crunches: Exercise, burpees: Exercise) {
        val name = "Signe"
        val description = "Good all round workout"

        val round = RoundBuilder(5)
                .addExercise(jumpingJacks, 50)
                .addExercise(lunges, 20)
                .addExercise(crunches, 20)
                .addExercise(burpees, 20)

        createWorkout(name, description, user, round)
    }

    private fun roskva(user: User, burpees: Exercise, airSquat: Exercise, situps: Exercise) {
        val name = "Roskva"
        val description = "bla bla"

        val round = RoundBuilder(5)
                .addExercise(burpees, 20)
                .addExercise(airSquat, 20)
                .addExercise(situps, 20)

        createWorkout(name, description, user, round)
    }

    private fun roskvaHardcore(user: User, burpees: Exercise, airSquat: Exercise, situps: Exercise) {
        val name = "Roskva Hardcore"
        val description = "bla bla"

        val round1 = RoundBuilder(1, 20)
                .addExercise(burpees, 50)
                .addExercise(airSquat, 50)
                .addExercise(situps, 50)

        val round2 = RoundBuilder(1, 20)
                .addExercise(burpees, 40)
                .addExercise(airSquat, 40)
                .addExercise(situps, 40)

        val round3 = RoundBuilder(1, 20)
                .addExercise(burpees, 30)
                .addExercise(airSquat, 30)
                .addExercise(situps, 30)

        val round4 = RoundBuilder(1, 20)
                .addExercise(burpees, 20)
                .addExercise(airSquat, 20)
                .addExercise(situps, 20)

        val round5 = RoundBuilder(1, 20)
                .addExercise(burpees, 10)
                .addExercise(airSquat, 10)
                .addExercise(situps, 10)

        createWorkout(name, description, user, round1, round2, round3, round4, round5)
    }

    private fun randi(user: User, burpees: Exercise, airSquat: Exercise, situps: Exercise) {
        val name = "Randi"
        val description = "To be honest this workout was created to test the mixing of repeating and multiple rounds"

        val round1 = RoundBuilder(2, 20)
                .addExercise(burpees, 10)
                .addExercise(airSquat, 10)
                .addExercise(situps, 10)

        val round2 = RoundBuilder(2, 20)
                .addExercise(burpees, 5)
                .addExercise(airSquat, 5)
                .addExercise(situps, 5)

        val round3 = RoundBuilder(2, 20)
                .addExercise(burpees, 10)
                .addExercise(airSquat, 10)
                .addExercise(situps, 10)

        val round4 = RoundBuilder(2, 20)
                .addExercise(burpees, 5)
                .addExercise(airSquat, 5)
                .addExercise(situps, 5)

        createWorkout(name, description, user, round1, round2, round3, round4)
    }

    private fun createWorkout(name: String, description: String, user: User, vararg rounds: RoundBuilder) {
        val workoutBuilder = WorkoutBuilder(name, description, user)

        rounds.forEach {
            workoutBuilder.addRound(it)
        }

        val workout = workoutBuilder.build()

        deepSaveWorkout(workout)
    }

    private fun deepSaveWorkout(workout: Workout) {
        val rounds = workout.rounds
        workout.rounds = null
        val workoutId = workoutService.save(workout).id

        rounds?.forEach {
            val exercises = it.exercises
            it.exercises = null
            val roundId = workoutService.save(workoutId, it).id
            exercises?.forEach {
                workoutService.save(roundId, it)
            }
        }
    }
}
