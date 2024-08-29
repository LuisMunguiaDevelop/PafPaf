package com.example.pafpaf.database;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.collection.LongSparseArray;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.room.util.RelationUtil;
import androidx.room.util.StringUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.pafpaf.database.entities.AlbumEntity;
import com.example.pafpaf.database.entities.AlbumWithPhoto;
import com.example.pafpaf.database.entities.PhotoEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AlbumDAO_Impl implements AlbumDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<AlbumEntity> __insertionAdapterOfAlbumEntity;

  private final EntityDeletionOrUpdateAdapter<AlbumEntity> __deletionAdapterOfAlbumEntity;

  public AlbumDAO_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfAlbumEntity = new EntityInsertionAdapter<AlbumEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `albums` (`id`,`album`,`title`) VALUES (nullif(?, 0),?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final AlbumEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getAlbum());
        statement.bindString(3, entity.getTitle());
      }
    };
    this.__deletionAdapterOfAlbumEntity = new EntityDeletionOrUpdateAdapter<AlbumEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `albums` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final AlbumEntity entity) {
        statement.bindLong(1, entity.getId());
      }
    };
  }

  @Override
  public void insert(final AlbumEntity... album) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfAlbumEntity.insert(album);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final AlbumEntity album) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfAlbumEntity.handle(album);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public Flow<List<AlbumEntity>> getAll() {
    final String _sql = "SELECT * FROM albums";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"albums"}, new Callable<List<AlbumEntity>>() {
      @Override
      @NonNull
      public List<AlbumEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfAlbum = CursorUtil.getColumnIndexOrThrow(_cursor, "album");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final List<AlbumEntity> _result = new ArrayList<AlbumEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final AlbumEntity _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final int _tmpAlbum;
            _tmpAlbum = _cursor.getInt(_cursorIndexOfAlbum);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            _item = new AlbumEntity(_tmpId,_tmpAlbum,_tmpTitle);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<AlbumWithPhoto>> getAlbumWithPhotos() {
    final String _sql = "SELECT * FROM albums";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, true, new String[] {"photos",
        "albums"}, new Callable<List<AlbumWithPhoto>>() {
      @Override
      @NonNull
      public List<AlbumWithPhoto> call() throws Exception {
        __db.beginTransaction();
        try {
          final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
          try {
            final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
            final int _cursorIndexOfAlbum = CursorUtil.getColumnIndexOrThrow(_cursor, "album");
            final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
            final LongSparseArray<ArrayList<PhotoEntity>> _collectionPhotos = new LongSparseArray<ArrayList<PhotoEntity>>();
            while (_cursor.moveToNext()) {
              final long _tmpKey;
              _tmpKey = _cursor.getLong(_cursorIndexOfId);
              if (!_collectionPhotos.containsKey(_tmpKey)) {
                _collectionPhotos.put(_tmpKey, new ArrayList<PhotoEntity>());
              }
            }
            _cursor.moveToPosition(-1);
            __fetchRelationshipphotosAscomExamplePafpafDatabaseEntitiesPhotoEntity(_collectionPhotos);
            final List<AlbumWithPhoto> _result = new ArrayList<AlbumWithPhoto>(_cursor.getCount());
            while (_cursor.moveToNext()) {
              final AlbumWithPhoto _item;
              final AlbumEntity _tmpAlbum;
              final int _tmpId;
              _tmpId = _cursor.getInt(_cursorIndexOfId);
              final int _tmpAlbum_1;
              _tmpAlbum_1 = _cursor.getInt(_cursorIndexOfAlbum);
              final String _tmpTitle;
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
              _tmpAlbum = new AlbumEntity(_tmpId,_tmpAlbum_1,_tmpTitle);
              final ArrayList<PhotoEntity> _tmpPhotosCollection;
              final long _tmpKey_1;
              _tmpKey_1 = _cursor.getLong(_cursorIndexOfId);
              _tmpPhotosCollection = _collectionPhotos.get(_tmpKey_1);
              _item = new AlbumWithPhoto(_tmpAlbum,_tmpPhotosCollection);
              _result.add(_item);
            }
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            _cursor.close();
          }
        } finally {
          __db.endTransaction();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public AlbumEntity getAlbumById(final int albumId) {
    final String _sql = "SELECT * FROM albums WHERE id LIKE ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, albumId);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfAlbum = CursorUtil.getColumnIndexOrThrow(_cursor, "album");
      final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
      final AlbumEntity _result;
      if (_cursor.moveToFirst()) {
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        final int _tmpAlbum;
        _tmpAlbum = _cursor.getInt(_cursorIndexOfAlbum);
        final String _tmpTitle;
        _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
        _result = new AlbumEntity(_tmpId,_tmpAlbum,_tmpTitle);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public PhotoEntity getAlbumCover(final int albumId) {
    final String _sql = "SELECT * FROM photos WHERE albumId LIKE ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, albumId);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfAlbumId = CursorUtil.getColumnIndexOrThrow(_cursor, "albumId");
      final int _cursorIndexOfPath = CursorUtil.getColumnIndexOrThrow(_cursor, "path");
      final int _cursorIndexOfImage = CursorUtil.getColumnIndexOrThrow(_cursor, "image");
      final PhotoEntity _result;
      if (_cursor.moveToFirst()) {
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        final int _tmpAlbumId;
        _tmpAlbumId = _cursor.getInt(_cursorIndexOfAlbumId);
        final String _tmpPath;
        _tmpPath = _cursor.getString(_cursorIndexOfPath);
        final String _tmpImage;
        _tmpImage = _cursor.getString(_cursorIndexOfImage);
        _result = new PhotoEntity(_tmpId,_tmpAlbumId,_tmpPath,_tmpImage);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public void delete(final List<Integer> ids) {
    __db.assertNotSuspendingTransaction();
    final StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("DELETE FROM albums WHERE id IN (");
    final int _inputSize = ids.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final SupportSQLiteStatement _stmt = __db.compileStatement(_sql);
    int _argIndex = 1;
    for (int _item : ids) {
      _stmt.bindLong(_argIndex, _item);
      _argIndex++;
    }
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }

  private void __fetchRelationshipphotosAscomExamplePafpafDatabaseEntitiesPhotoEntity(
      @NonNull final LongSparseArray<ArrayList<PhotoEntity>> _map) {
    if (_map.isEmpty()) {
      return;
    }
    if (_map.size() > RoomDatabase.MAX_BIND_PARAMETER_CNT) {
      RelationUtil.recursiveFetchLongSparseArray(_map, true, (map) -> {
        __fetchRelationshipphotosAscomExamplePafpafDatabaseEntitiesPhotoEntity(map);
        return Unit.INSTANCE;
      });
      return;
    }
    final StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT `id`,`albumId`,`path`,`image` FROM `photos` WHERE `albumId` IN (");
    final int _inputSize = _map.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _stmt = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    for (int i = 0; i < _map.size(); i++) {
      final long _item = _map.keyAt(i);
      _stmt.bindLong(_argIndex, _item);
      _argIndex++;
    }
    final Cursor _cursor = DBUtil.query(__db, _stmt, false, null);
    try {
      final int _itemKeyIndex = CursorUtil.getColumnIndex(_cursor, "albumId");
      if (_itemKeyIndex == -1) {
        return;
      }
      final int _cursorIndexOfId = 0;
      final int _cursorIndexOfAlbumId = 1;
      final int _cursorIndexOfPath = 2;
      final int _cursorIndexOfImage = 3;
      while (_cursor.moveToNext()) {
        final long _tmpKey;
        _tmpKey = _cursor.getLong(_itemKeyIndex);
        final ArrayList<PhotoEntity> _tmpRelation = _map.get(_tmpKey);
        if (_tmpRelation != null) {
          final PhotoEntity _item_1;
          final int _tmpId;
          _tmpId = _cursor.getInt(_cursorIndexOfId);
          final int _tmpAlbumId;
          _tmpAlbumId = _cursor.getInt(_cursorIndexOfAlbumId);
          final String _tmpPath;
          _tmpPath = _cursor.getString(_cursorIndexOfPath);
          final String _tmpImage;
          _tmpImage = _cursor.getString(_cursorIndexOfImage);
          _item_1 = new PhotoEntity(_tmpId,_tmpAlbumId,_tmpPath,_tmpImage);
          _tmpRelation.add(_item_1);
        }
      }
    } finally {
      _cursor.close();
    }
  }
}
