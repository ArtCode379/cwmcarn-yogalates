package app.skeleton.sporttrackerskeleton.di

import androidx.room.Room
import app.skeleton.sporttrackerskeleton.data.database.AppDatabase
import app.skeleton.sporttrackerskeleton.data.datastore.UserManager
import app.skeleton.sporttrackerskeleton.data.mapper.CompleteWorkoutMapper
import app.skeleton.sporttrackerskeleton.data.mapper.CompleteWorkoutMapperImpl
import app.skeleton.sporttrackerskeleton.data.repository.CompleteWorkoutRepository
import app.skeleton.sporttrackerskeleton.data.repository.CompleteWorkoutRepositoryImpl
import app.skeleton.sporttrackerskeleton.data.repository.StatisticsRepository
import app.skeleton.sporttrackerskeleton.data.repository.StatisticsRepositoryImpl
import app.skeleton.sporttrackerskeleton.data.repository.UserRepository
import app.skeleton.sporttrackerskeleton.data.repository.UserRepositoryImpl
import app.skeleton.sporttrackerskeleton.data.repository.WorkoutRepository
import app.skeleton.sporttrackerskeleton.data.repository.WorkoutRepositoryImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataModule = module {
    single { UserManager(get()) }

    single {
        Room.databaseBuilder(
            context = get(),
            klass = AppDatabase::class.java,
            name = "database-name"
        ).build()
    }

    single { get<AppDatabase>().statisticsDao() }

    single<CompleteWorkoutMapper> {
        CompleteWorkoutMapperImpl()
    }

    single<WorkoutRepository> {
        WorkoutRepositoryImpl()
    }

    single<CompleteWorkoutRepository>  {
        CompleteWorkoutRepositoryImpl(
            completeWorkoutDao = get(),
            completeWorkoutMapper = get(),
            coroutineDispatcher = get(qualifier = named("IO")),
        )
    }

    single<StatisticsRepository> {
        StatisticsRepositoryImpl()
    }

    single<UserRepository> {
        UserRepositoryImpl(userManager = get())
    }
}