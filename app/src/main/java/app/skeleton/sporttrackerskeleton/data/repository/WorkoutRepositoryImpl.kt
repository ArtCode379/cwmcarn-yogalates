package app.skeleton.sporttrackerskeleton.data.repository

import app.skeleton.sporttrackerskeleton.data.model.ExerciseModel
import app.skeleton.sporttrackerskeleton.data.model.WorkoutModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class WorkoutRepositoryImpl() : WorkoutRepository {
    private val exercises: List<ExerciseModel> = listOf(
        /*
        ExerciseModel("Push-ups", 3, 15, 0.0, 60.0),
        ExerciseModel("Pull-ups", 4, 10, 0.0, 90.0),
        ExerciseModel("Squats", 4, 20, 0.0, 60.0),
        ExerciseModel("Lunges", 3, 12, 0.0, 45.0),
        ExerciseModel("Bench Press", 4, 8, 50.0, 90.0),
        ExerciseModel("Deadlift", 4, 6, 70.0, 120.0),
        ExerciseModel("Shoulder Press", 3, 10, 20.0, 60.0),
        ExerciseModel("Bicep Curls", 3, 12, 15.0, 45.0),
        ExerciseModel("Tricep Dips", 3, 15, 0.0, 45.0),
        ExerciseModel("Plank", 3, 1, 0.0, 30.0)
         */
    )

    private val workouts: List<WorkoutModel> = listOf(
        /*
        WorkoutModel(
            id = 1,
            name = "Full Body Beginner",
            duration = 45.0,
            type = WorkoutType.Strength,
            level = WorkoutLevel.Beginner,
            exercises = listOf(
                exerciseList[0], // Push-ups
                exerciseList[2], // Squats
                exerciseList[3], // Lunges
                exerciseList[9]  // Plank
            )
        ),
        WorkoutModel(
            id = 2,
            name = "Upper Body Blast",
            duration = 50.0,
            type = WorkoutType.Strength,
            level = WorkoutLevel.Intermediate,
            exercises = listOf(
                exerciseList[0], // Push-ups
                exerciseList[1], // Pull-ups
                exerciseList[4], // Bench Press
                exerciseList[6], // Shoulder Press
                exerciseList[7]  // Bicep Curls
            )
        ),
        WorkoutModel(
            id = 3,
            name = "Leg Day Extreme",
            duration = 55.0,
            type = WorkoutType.Strength,
            level = WorkoutLevel.Advanced,
            exercises = listOf(
                exerciseList[2], // Squats
                exerciseList[3], // Lunges
                exerciseList[5], // Deadlift
                exerciseList[9]  // Plank
            )
        )
         */
    )

    override fun observeAll(): Flow<List<WorkoutModel>> = flowOf(workouts)

    override suspend fun getAll(): List<WorkoutModel> = workouts

    override fun observeById(id: Int): Flow<WorkoutModel?> {
        return flowOf(workouts.firstOrNull { workout -> workout.id == id })
    }

    override suspend fun getById(id: Int): WorkoutModel? {
        return workouts.firstOrNull { workout -> workout.id == id }
    }
}