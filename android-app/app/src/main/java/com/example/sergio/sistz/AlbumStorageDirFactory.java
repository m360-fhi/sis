package com.example.sergio.sistz;

/**
 * Created by jlgarcia on 12/05/2016.
 */
import java.io.File;

abstract class AlbumStorageDirFactory {
    public abstract File getAlbumStorageDir(String albumName);
}
