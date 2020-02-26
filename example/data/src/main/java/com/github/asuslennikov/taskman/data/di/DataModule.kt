package com.github.asuslennikov.taskman.data.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.github.asuslennikov.taskman.data.TaskManagerImpl
import com.github.asuslennikov.taskman.data.database.TaskDao
import com.github.asuslennikov.taskman.data.database.TaskmanDatabase
import com.github.asuslennikov.taskman.data.database.entity.TaskEntity
import com.github.asuslennikov.taskman.domain.TaskManager
import dagger.Binds
import dagger.Module
import dagger.Provides
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import org.threeten.bp.ZonedDateTime
import java.util.concurrent.TimeUnit
import javax.inject.Provider

@Module(includes = [DataModule.DataModuleBindings::class])
object DataModule {

    @Provides
    @DataScope
    @JvmStatic
    fun database(context: Context, prePopulateCallback: RoomDatabase.Callback): TaskmanDatabase {
        /*
        DO NOT USE this callback in production, just for development
        https://medium.com/androiddevelopers/packing-the-room-pre-populate-your-database-with-this-one-method-333ae190e680
         */
        return Room.databaseBuilder(context, TaskmanDatabase::class.java, "taskman_database")
            .fallbackToDestructiveMigration()
            .addCallback(prePopulateCallback)
            .build()
    }

    @Provides
    @DataScope
    @JvmStatic
    fun databasePrePopulateCallback(database: Provider<TaskmanDatabase>): RoomDatabase.Callback =
        object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                Completable.fromRunnable {
                    /*
                     never use dao without delay, or you will receive the 'getDatabase called recursively'
                     it is here just for development to get a correctly filled database file with minimum efforts
                     */
                    database.get().taskDao.run {
                        insert(
                            TaskEntity(
                                1,
                                "Add room database",
                                "Apply room compiler and other staff",
                                ZonedDateTime.now().minusDays(1),
                                true
                            )
                        )
                        insert(
                            TaskEntity(
                                2,
                                "Set correct converters",
                                "",
                                ZonedDateTime.now().minusHours(3),
                                true
                            )
                        )
                        insert(
                            TaskEntity(
                                3,
                                "Apply migrations",
                                "",
                                ZonedDateTime.now()
                            )
                        )
                    }
                }
                    .delay(5, TimeUnit.SECONDS)
                    .subscribeOn(Schedulers.io())
                    .subscribe()
            }
        }

    @Provides
    @DataScope
    @JvmStatic
    fun taskDao(database: TaskmanDatabase): TaskDao = database.taskDao.apply {
        getById(0L) // force database creation
    }

    @Module
    internal interface DataModuleBindings {

        @Binds
        @DataScope
        fun bindsTaskRepository(impl: TaskManagerImpl): TaskManager
    }
}