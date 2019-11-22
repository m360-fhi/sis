package com.example.sergio.sistz;

/**
 * Created by jlgarcia on 12/05/2016.
 */
import android.os.Environment;

import java.io.File;

public final class FroyoAlbumDirFactory extends AlbumStorageDirFactory {

    @Override
    public File getAlbumStorageDir(String albumName) {
        // TODO Auto-generated method stub
        return new File(
                Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES
                ),
                albumName
        );
    }
}

