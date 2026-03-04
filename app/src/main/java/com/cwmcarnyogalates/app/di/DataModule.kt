package com.cwmcarnyogalates.app.di

import androidx.room.Room
import com.cwmcarnyogalates.app.data.database.AppDatabase
import com.cwmcarnyogalates.app.data.datastore.ThemeManager
import com.cwmcarnyogalates.app.data.datastore.UserManager
import com.cwmcarnyogalates.app.data.mapper.BookingMapper
import com.cwmcarnyogalates.app.data.mapper.BookingMapperImpl
import com.cwmcarnyogalates.app.data.mapper.CompleteWorkoutMapper
import com.cwmcarnyogalates.app.data.mapper.CompleteWorkoutMapperImpl
import com.cwmcarnyogalates.app.data.repository.BookingRepository
import com.cwmcarnyogalates.app.data.repository.BookingRepositoryImpl
import com.cwmcarnyogalates.app.data.repository.CompleteWorkoutRepository
import com.cwmcarnyogalates.app.data.repository.CompleteWorkoutRepositoryImpl
import com.cwmcarnyogalates.app.data.repository.UserRepository
import com.cwmcarnyogalates.app.data.repository.UserRepositoryImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataModule = module {
    single { UserManager(get()) }
    single { ThemeManager(get()) }

    single {
        Room.databaseBuilder(
            context = get(),
            klass = AppDatabase::class.java,
            name = "cwmyl_database"
        ).fallbackToDestructiveMigration().build()
    }

    single { get<AppDatabase>().completeWorkoutDao() }
    single { get<AppDatabase>().bookingDao() }

    single<CompleteWorkoutMapper> { CompleteWorkoutMapperImpl() }
    single<BookingMapper> { BookingMapperImpl() }

    single<CompleteWorkoutRepository> {
        CompleteWorkoutRepositoryImpl(
            dao = get(),
            mapper = get(),
            dispatcher = get(qualifier = named("IO")),
        )
    }

    single<BookingRepository> {
        BookingRepositoryImpl(
            dao = get(),
            mapper = get(),
            dispatcher = get(qualifier = named("IO")),
        )
    }

    single<UserRepository> {
        UserRepositoryImpl(userManager = get())
    }
}
