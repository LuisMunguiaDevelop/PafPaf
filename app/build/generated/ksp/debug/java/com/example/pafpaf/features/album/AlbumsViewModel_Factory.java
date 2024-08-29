package com.example.pafpaf.features.album;

import android.content.Context;
import com.example.pafpaf.database.PhotoDAO;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class AlbumsViewModel_Factory implements Factory<AlbumsViewModel> {
  private final Provider<PhotoDAO> photoDAOProvider;

  private final Provider<Context> contextProvider;

  public AlbumsViewModel_Factory(Provider<PhotoDAO> photoDAOProvider,
      Provider<Context> contextProvider) {
    this.photoDAOProvider = photoDAOProvider;
    this.contextProvider = contextProvider;
  }

  @Override
  public AlbumsViewModel get() {
    return newInstance(photoDAOProvider.get(), contextProvider.get());
  }

  public static AlbumsViewModel_Factory create(Provider<PhotoDAO> photoDAOProvider,
      Provider<Context> contextProvider) {
    return new AlbumsViewModel_Factory(photoDAOProvider, contextProvider);
  }

  public static AlbumsViewModel newInstance(PhotoDAO photoDAO, Context context) {
    return new AlbumsViewModel(photoDAO, context);
  }
}
