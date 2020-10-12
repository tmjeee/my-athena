package com.tmjee.myathena.db

import androidx.room.*
import java.util.*


@Database(
    entities = [
        UserEntity::class
    ],
    version = 1,
    exportSchema = true,
)
@TypeConverters(
    UUIDTypeConverter::class,
    DateTypeConverter::class
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userEntityDao(): UserEntityDao
}


class DateTypeConverter {
    @TypeConverter()
    fun fromDate(date: Date): Long {
        return date.time
    }

    @TypeConverter
    fun toDate(date: Long): Date {
        return Date(date)
    }
}

class UUIDTypeConverter {
    @TypeConverter()
    fun fromUUID(uuid: UUID): String {
        return uuid.toString()
    }

    @TypeConverter()
    fun toUUID(uuid: String): UUID {
        return UUID.fromString(uuid)
    }
}

@Entity(tableName = "TBL_USER_ENTITY")
data class UserEntity (
    @PrimaryKey() @ColumnInfo(name="ID") val id: UUID = UUID.randomUUID(),
    @ColumnInfo(name="NAME") val name: String = "",
    @ColumnInfo(name="DESCRIPTION") val description: String = "",
    @ColumnInfo(name="DATE") val date: Date = Date()
);


@Dao()
interface UserEntityDao {
    @Query("SELECT * FROM TBL_USER_ENTITY")
    fun getMyFirstDatas(): List<UserEntity>

    @Insert(
        onConflict = OnConflictStrategy.REPLACE
    )
    fun insertUserEntities(vararg user: UserEntity)

    @Update()
    fun updateUserEntities(vararg user: UserEntity)

    @Delete()
    fun deleteUserEntity(user: UserEntity)
}