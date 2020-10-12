package com.tmjee.myathena

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.RoomDatabase.Callback
import androidx.sqlite.db.SupportSQLiteDatabase
import com.squareup.moshi.*
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tmjee.myathena.db.AppDatabase
import com.tmjee.myathena.db.UserEntityDao
import com.tmjee.myathena.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.*
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Singleton


object MoshiDateAdapter {
    val standardRfcDateAdapter = Rfc3339DateJsonAdapter()
    val format: SimpleDateFormat = SimpleDateFormat("dd-MM-yyyy")

    @ToJson
    fun dateToJson(date: Date): String {
        return format.format(date)
    }

    @FromJson
    fun jsonToDate(json: String): Date {
        return try {
            format.parse(json)
        } catch (e: ParseException) {
            standardRfcDateAdapter.fromJson(json)!!
        }
    }

}




@Module
@InstallIn(value = [ApplicationComponent::class])
object App1ApplicationModule {

    @Provides
    @Singleton
    fun okHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            })
            .build()

    @Provides
    @Singleton
    fun moshi(): Moshi =
        Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .add(MoshiDateAdapter)
        .build()

    @Provides
    @Singleton
    fun retrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit =
        Retrofit.Builder()
            .baseUrl("http://192.168.122.1:9999")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun appDatabase(@ApplicationContext applicationContext: Context): AppDatabase {
        return Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "APP_DATABASE")
        .addCallback(object: Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                // prepupulate db
            }
        })
        .enableMultiInstanceInvalidation()
        .build()
    }

    @Provides
    @Singleton
    fun userEntityDao(appDatabase: AppDatabase): UserEntityDao {
       return appDatabase.userEntityDao()
    }

    @Provides
    @Singleton
    fun loginRepo(retrofit: Retrofit): LoginRepo = retrofit.create(LoginRepo::class.java)

    @Provides
    @Singleton
    fun pinValidationRepo(retrofit: Retrofit): PinValidationRepo = retrofit.create(PinValidationRepo::class.java)

    @Provides
    @Singleton
    fun accountRepo(retrofit: Retrofit): AccountRepo = retrofit.create(AccountRepo::class.java)

    @Provides
    @Singleton
    fun transactionsRepo(retrofit: Retrofit): TransactionsRepo = retrofit.create(TransactionsRepo::class.java)

    @Provides
    @Singleton
    fun loanRepo(retrofit: Retrofit): LoanRepo = retrofit.create(LoanRepo::class.java)

    @Provides
    @Singleton
    fun statementRepo(retrofit: Retrofit): StatementRepo = retrofit.create(StatementRepo::class.java)

    @Provides
    @Singleton
    fun paymentsRepo(retrofit: Retrofit): PaymentsRepo = retrofit.create(PaymentsRepo::class.java)

    @Provides
    @Singleton
    fun photosRepo(retrofit: Retrofit): PhotosRepo = retrofit.create(PhotosRepo::class.java)
}


@Module
@InstallIn(value = [ActivityRetainedComponent::class])
object App1HiltActivityRetainedModule {
}

@Module
@InstallIn(value = [ActivityComponent::class])
object App1HiltActivityModule {
}

@Module
@InstallIn(value = [FragmentComponent::class])
object App1HiltFragmentModule {
}

@Module
@InstallIn(value = [ViewComponent::class])
object App1HiltViewModule {}

@Module
@InstallIn(value = [ViewWithFragmentComponent::class])
object App1HiltViewWithFragmentModule {}

@Module
@InstallIn(value = [ServiceComponent::class])
object App1HiltServiceModule {}


