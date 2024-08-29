package com.example.pafpaf.di;

import com.example.pafpaf.database.AlbumDAO;
import com.example.pafpaf.database.AppDatabaseNew;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation"
})
public final class DatabaseModule_ProvideAlbumDaoFactory implements Factory<AlbumDAO> {
  private final Provider<AppDatabaseNew> appDatabaseProvider;

  public DatabaseModule_ProvideAlbumDaoFactory(Provider<AppDatabaseNew> appDatabaseProvider) {
    this.appDatabaseProvider = appDatabaseProvider;
  }

  @Override
  public AlbumDAO get() {
    return provideAlbumDao(appDatabaseProvider.get());
  }

  public static DatabaseModule_ProvideAlbumDaoFactory create(
      Provider<AppDatabaseNew> appDatabaseProvider) {
    return new DatabaseModule_ProvideAlbumDaoFactory(appDatabaseProvider);
  }

  public static AlbumDAO provideAlbumDao(AppDatabaseNew appDatabase) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideAlbumDao(appDatabase));
  }
}
