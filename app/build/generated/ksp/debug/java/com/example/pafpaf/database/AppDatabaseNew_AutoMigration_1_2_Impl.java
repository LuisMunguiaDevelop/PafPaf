package com.example.pafpaf.database;

import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;
import java.lang.Override;
import java.lang.SuppressWarnings;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
final class AppDatabaseNew_AutoMigration_1_2_Impl extends Migration {
  public AppDatabaseNew_AutoMigration_1_2_Impl() {
    super(1, 2);
  }

  @Override
  public void migrate(@NonNull final SupportSQLiteDatabase db) {
    db.execSQL("CREATE TABLE IF NOT EXISTS `photos` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `albumId` INTEGER NOT NULL, `path` TEXT NOT NULL, `image` TEXT NOT NULL)");
  }
}
