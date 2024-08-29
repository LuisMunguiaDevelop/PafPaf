package com.example.pafpaf.di;

import com.example.pafpaf.database.AppDatabaseNew;
import com.example.pafpaf.database.PhotoDAO;
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
public final class DatabaseModule_ProvidePhotoDaoFactory implements Factory<PhotoDAO> {
  private final Provider<AppDatabaseNew> appDatabaseProvider;

  public DatabaseModule_ProvidePhotoDaoFactory(Provider<AppDatabaseNew> appDatabaseProvider) {
    this.appDatabaseProvider = appDatabaseProvider;
  }

  @Override
  public PhotoDAO get() {
    return providePhotoDao(appDatabaseProvider.get());
  }

  public static DatabaseModule_ProvidePhotoDaoFactory create(
      Provider<AppDatabaseNew> appDatabaseProvider) {
    return new DatabaseModule_ProvidePhotoDaoFactory(appDatabaseProvider);
  }

  public static PhotoDAO providePhotoDao(AppDatabaseNew appDatabase) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.providePhotoDao(appDatabase));
  }
}
