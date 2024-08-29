package com.example.pafpaf.features.album_list;

import com.example.pafpaf.database.AlbumDAO;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class AlbumListViewModel_Factory implements Factory<AlbumListViewModel> {
  private final Provider<AlbumDAO> albumDAOProvider;

  public AlbumListViewModel_Factory(Provider<AlbumDAO> albumDAOProvider) {
    this.albumDAOProvider = albumDAOProvider;
  }

  @Override
  public AlbumListViewModel get() {
    return newInstance(albumDAOProvider.get());
  }

  public static AlbumListViewModel_Factory create(Provider<AlbumDAO> albumDAOProvider) {
    return new AlbumListViewModel_Factory(albumDAOProvider);
  }

  public static AlbumListViewModel newInstance(AlbumDAO albumDAO) {
    return new AlbumListViewModel(albumDAO);
  }
}
