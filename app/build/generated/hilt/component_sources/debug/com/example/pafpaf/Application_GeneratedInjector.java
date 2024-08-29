package com.example.pafpaf;

import dagger.hilt.InstallIn;
import dagger.hilt.codegen.OriginatingElement;
import dagger.hilt.components.SingletonComponent;
import dagger.hilt.internal.GeneratedEntryPoint;

@OriginatingElement(
    topLevelClass = Application.class
)
@GeneratedEntryPoint
@InstallIn(SingletonComponent.class)
public interface Application_GeneratedInjector {
  void injectApplication(Application application);
}
