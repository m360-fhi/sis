package com.example.sergio.sistz;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sergio.sistz.mysql.Conexion;
import com.example.sergio.sistz.util.toolsfncs;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergio on 2/26/2016.
 */
public class SettingsMenuInfra_0 extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    public static String EMIS_code = "";
    public static final String STATICS_ROOT = Environment.getExternalStorageDirectory() + File.separator + "sisdb";
    public static final String STATICS_ROOT_DB = STATICS_ROOT + File.separator + "sisdb.sqlite";
    int fl_location = 1; // *********** Control change page
    FrameLayout fl_part1, fl_part2, fl_part3; // ************ FrameLayout ***************
    TextView _col1, _col2, necta_form4, necta_form7, txt_region, txt_district, txt_ward;
    EditText _col7, _col8, _col9, _col10, _col12, _col13, student_registered; // *** 20180915 add new column  student_registered
    String _col3="", _col4="", _col5="", _col6, _col11;
    private final static String[] lista_datos = { "Perro", "Gato", "Caballo", "Canario", "Vaca", "Cerdo" };
    private final static String[] lista_datos_accion = { "ladra", "maulla", "relincha", "canta", "muje", "no se" };
    private final String[] lista_datos_accion_hace = new String[4];
    private final String[] clearSpinner = new String[1];
    public Spinner sp_g1, sp_g2, sp_g3;
    RadioButton rb_rural, rb_urban, rb_gove, rb_priv, rb_comm, rb_reli, rb_othe;
    ArrayList<String> list_g1 = new ArrayList<>();
    ArrayList<String> list_g2 = new ArrayList<>();
    ArrayList<String> list_g3 = new ArrayList<>();
    String _IU="U", sqlbefore="";
    // ******************** GPS var **********
    TextView _msj_record, _msj_latitud, _msj_longitud;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private static final String BITMAP_STORAGE_KEY = "viewbitmap";
    private static final String IMAGEVIEW_VISIBILITY_STORAGE_KEY = "imageviewvisibility";
    private ImageView mImageView, mImageView2, mImageView3, mImageView4,mImageView5,mImageView6,mImageView7,mImageView8,mImageView9,mImageView10;
    private Bitmap mImageBitmap;

    private static final String VIDEO_STORAGE_KEY = "viewvideo";
    private static final String VIDEOVIEW_VISIBILITY_STORAGE_KEY = "videoviewvisibility";


    private String mCurrentPhotoPath;


    private AlbumStorageDirFactory mAlbumStorageDirFactory = null;
    private int MY_PERMISSIONS_REQUEST_FINE_LOCATION=1;

    /* Photo album for this application */
    private String getAlbumName() {
        return getString(R.string.album_name);
    }

    private File getAlbumDir() {
        File storageDir = null;

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {

            storageDir = mAlbumStorageDirFactory.getAlbumStorageDir(getAlbumName());

            if (storageDir != null) {
                if (! storageDir.mkdirs()) {
                    if (! storageDir.exists()){
                        Log.d("CameraSample", "failed to create directory");
                        return null;
                    }
                }
            }

        } else {
            Log.v(getString(R.string.app_name), "External storage is not mounted READ/WRITE.");
        }

        return storageDir;
    }

    private File createImageFile(String code, String n) throws IOException {

        File albumF = getAlbumDir();
        File imageF = new File(albumF, code + "_" + n+".jpg");
        return imageF;
    }

    private File setUpPhotoFile(String code, String id_photo) throws IOException {

        File f = createImageFile(code, id_photo);
        mCurrentPhotoPath = f.getAbsolutePath();

        return f;
    }

    private void setPic(int id) {


        int targetW = 100;
        int targetH = 100;


        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

		/* Figure out which way needs to be reduced less */
        int scaleFactor = 1;
        if ((targetW > 0) || (targetH > 0)) {
            scaleFactor = Math.min(photoW/targetW, photoH/targetH);
        }

        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

		/* Decode the JPEG file into a Bitmap */
        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        Bitmap orientedBitmap = ExifUtil.rotateBitmap(mCurrentPhotoPath, bitmap);

		/* Associate the Bitmap to the ImageView */
        if (id==1){
            mImageView.setImageBitmap(orientedBitmap);
            mImageView.setVisibility(View.VISIBLE);
        }
        else if(id==2){
            mImageView2.setImageBitmap(orientedBitmap);
            mImageView2.setVisibility(View.VISIBLE);
        }
        else if(id==3){
            mImageView3.setImageBitmap(orientedBitmap);
            mImageView3.setVisibility(View.VISIBLE);
        }
        else if(id==4){
            mImageView4.setImageBitmap(orientedBitmap);
            mImageView4.setVisibility(View.VISIBLE);
        }
        else if(id==5){
            mImageView5.setImageBitmap(orientedBitmap);
            mImageView5.setVisibility(View.VISIBLE);
        }
        else if(id==6){
            mImageView6.setImageBitmap(orientedBitmap);
            mImageView6.setVisibility(View.VISIBLE);
        }
        else if(id==7){
            mImageView7.setImageBitmap(orientedBitmap);
            mImageView7.setVisibility(View.VISIBLE);
        }
        else if(id==8){
            mImageView8.setImageBitmap(orientedBitmap);
            mImageView8.setVisibility(View.VISIBLE);
        }
        else if(id==9){
            mImageView9.setImageBitmap(orientedBitmap);
            mImageView9.setVisibility(View.VISIBLE);
        }
        else if(id==10){
            mImageView10.setImageBitmap(orientedBitmap);
            mImageView10.setVisibility(View.VISIBLE);
        }
        else{}


    }

    private void load_pic(String code, int id){
        int targetW = 100;
        int targetH = 100;

		/* Get the size of the image */
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        File albumF = getAlbumDir();

        BitmapFactory.decodeFile(albumF.toString()+"/"+code+ "_"+id+".jpg", bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

		/* Figure out which way needs to be reduced less */
        int scaleFactor = 1;
        if ((targetW > 0) || (targetH > 0)) {
            scaleFactor = Math.min(photoW/targetW, photoH/targetH);
        }

		/* Set bitmap options to scale the image decode target */
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

		/* Decode the JPEG file into a Bitmap */
        Bitmap bitmap = BitmapFactory.decodeFile(albumF.toString()+"/"+code+ "_"+id+".jpg", bmOptions);
        Bitmap orientedBitmap = ExifUtil.rotateBitmap(albumF.toString()+"/"+code+ "_"+id+".jpg", bitmap);
		/* Associate the Bitmap to the ImageView */
        if (id==1  && bitmap!=null ){
            mImageView.setImageBitmap(orientedBitmap);
            mImageView.setVisibility(View.VISIBLE);
        }
        else if(id==2 && bitmap!=null ){
            mImageView2.setImageBitmap(orientedBitmap);
            mImageView2.setVisibility(View.VISIBLE);
        }
        else if(id==3 && bitmap!=null ){
            mImageView3.setImageBitmap(orientedBitmap);
            mImageView3.setVisibility(View.VISIBLE);
        }
        else if(id==4 && bitmap!=null ){
            mImageView4.setImageBitmap(orientedBitmap);
            mImageView4.setVisibility(View.VISIBLE);
        }
        else if(id==5 && bitmap!=null ){
            mImageView5.setImageBitmap(orientedBitmap);
            mImageView5.setVisibility(View.VISIBLE);
        }
        else if(id==6 && bitmap!=null ){
            mImageView6.setImageBitmap(orientedBitmap);
            mImageView6.setVisibility(View.VISIBLE);
        }
        else if(id==7 && bitmap!=null ){
            mImageView7.setImageBitmap(orientedBitmap);
            mImageView7.setVisibility(View.VISIBLE);
        }
        else if(id==8 && bitmap!=null ){
            mImageView8.setImageBitmap(orientedBitmap);
            mImageView8.setVisibility(View.VISIBLE);
        }
        else if(id==9 && bitmap!=null ){
            mImageView9.setImageBitmap(orientedBitmap);
            mImageView9.setVisibility(View.VISIBLE);
        }
        else if(id==10 && bitmap!=null ){
            mImageView10.setImageBitmap(orientedBitmap);
            mImageView10.setVisibility(View.VISIBLE);
        }

        else{}
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        getContext().sendBroadcast(mediaScanIntent);
    }

    private void dispatchTakePictureIntent(int actionCode) {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File f = null;
        switch(actionCode) {
            case 1:


                try {
                    f = setUpPhotoFile(getEMIS_code(),"1");
                    mCurrentPhotoPath = f.getAbsolutePath();
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                } catch (IOException e) {
                    e.printStackTrace();
                    f = null;
                    mCurrentPhotoPath = null;
                }
                break;
            case 2:


                try {
                    f = setUpPhotoFile(getEMIS_code(),"2");
                    mCurrentPhotoPath = f.getAbsolutePath();
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                } catch (IOException e) {
                    e.printStackTrace();
                    f = null;
                    mCurrentPhotoPath = null;
                }
                break;
            case 3:


                try {
                    f = setUpPhotoFile(getEMIS_code(),"3");
                    mCurrentPhotoPath = f.getAbsolutePath();
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                } catch (IOException e) {
                    e.printStackTrace();
                    f = null;
                    mCurrentPhotoPath = null;
                }
                break;
            case 4:


                try {
                    f = setUpPhotoFile(getEMIS_code(),"4");
                    mCurrentPhotoPath = f.getAbsolutePath();
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                } catch (IOException e) {
                    e.printStackTrace();
                    f = null;
                    mCurrentPhotoPath = null;
                }
                break;
            case 5:


                try {
                    f = setUpPhotoFile(getEMIS_code(),"5");
                    mCurrentPhotoPath = f.getAbsolutePath();
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                } catch (IOException e) {
                    e.printStackTrace();
                    f = null;
                    mCurrentPhotoPath = null;
                }
                break;
            case 6:


                try {
                    f = setUpPhotoFile(getEMIS_code(),"6");
                    mCurrentPhotoPath = f.getAbsolutePath();
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                } catch (IOException e) {
                    e.printStackTrace();
                    f = null;
                    mCurrentPhotoPath = null;
                }
                break;
            case 7:


                try {
                    f = setUpPhotoFile(getEMIS_code(),"7");
                    mCurrentPhotoPath = f.getAbsolutePath();
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                } catch (IOException e) {
                    e.printStackTrace();
                    f = null;
                    mCurrentPhotoPath = null;
                }
                break;
            case 8:


                try {
                    f = setUpPhotoFile(getEMIS_code(),"8");
                    mCurrentPhotoPath = f.getAbsolutePath();
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                } catch (IOException e) {
                    e.printStackTrace();
                    f = null;
                    mCurrentPhotoPath = null;
                }
                break;
            case 9:


                try {
                    f = setUpPhotoFile(getEMIS_code(),"9");
                    mCurrentPhotoPath = f.getAbsolutePath();
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                } catch (IOException e) {
                    e.printStackTrace();
                    f = null;
                    mCurrentPhotoPath = null;
                }
                break;
            case 10:


                try {
                    f = setUpPhotoFile(getEMIS_code(),"10");
                    mCurrentPhotoPath = f.getAbsolutePath();
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                } catch (IOException e) {
                    e.printStackTrace();
                    f = null;
                    mCurrentPhotoPath = null;
                }
                break;
            default:
                break;
        } // switch

        startActivityForResult(takePictureIntent, actionCode);
    }



    private void handleBigCameraPhoto(int id) {

        if (mCurrentPhotoPath != null) {
            setPic(id);
            galleryAddPic();
            mCurrentPhotoPath = null;
        }

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (container == null) {

            return null;
        }

        LinearLayout mLinearLayout = (LinearLayout) inflater.inflate(R.layout.settings_menu_infra_0,
                container, false);

        _col1 = (TextView) mLinearLayout.findViewById(R.id.et_col1);
        _col2 = (TextView) mLinearLayout.findViewById(R.id.et_col2);
        _col7 = (EditText) mLinearLayout.findViewById(R.id.et_col7);
        _col8 = (EditText) mLinearLayout.findViewById(R.id.et_col8);
        _col9 = (EditText) mLinearLayout.findViewById(R.id.et_col9);
        _col10 = (EditText) mLinearLayout.findViewById(R.id.et_col10);
        _col12 = (EditText) mLinearLayout.findViewById(R.id.et_col12);
        _col13 = (EditText) mLinearLayout.findViewById(R.id.et_col13);
        student_registered = (EditText) mLinearLayout.findViewById(R.id.et_students_registered);


        this.sp_g1 = (Spinner) mLinearLayout.findViewById(R.id.sp_g1); // ************ col 3, 4 y 5
        this.sp_g2 = (Spinner) mLinearLayout.findViewById(R.id.sp_g2);
        this.sp_g3 = (Spinner) mLinearLayout.findViewById(R.id.sp_g3);
        txt_region = (TextView) mLinearLayout.findViewById(R.id.txt_region);
        txt_district = (TextView) mLinearLayout.findViewById(R.id.txt_district);
        txt_ward = (TextView) mLinearLayout.findViewById(R.id.txt_ward);

        rb_rural = (RadioButton)mLinearLayout.findViewById(R.id.rb_rural); // ********* col 6
        rb_urban = (RadioButton)mLinearLayout.findViewById(R.id.rb_urban);
        rb_gove = (RadioButton)mLinearLayout.findViewById(R.id.rb_gove); // ************** col 11
        rb_priv = (RadioButton)mLinearLayout.findViewById(R.id.rb_priv);
        rb_reli = (RadioButton)mLinearLayout.findViewById(R.id.rb_reli);

        necta_form4 = (TextView) mLinearLayout.findViewById(R.id.necta_form4);
        necta_form7 = (TextView) mLinearLayout.findViewById(R.id.necta_form7);
        // *************** DISABILITY NECTA Form 4 & 7 - Question and Answers -
        necta_form4.setVisibility(View.GONE);
        necta_form7.setVisibility(View.GONE);
        _col9.setVisibility(View.GONE);
        _col10.setVisibility(View.GONE);

        // ****************** GPS set ******************
        _msj_record = (TextView) mLinearLayout.findViewById(R.id.msj_location);
        _msj_latitud = (TextView) mLinearLayout.findViewById(R.id.msj_latitud);
        _msj_longitud = (TextView) mLinearLayout.findViewById(R.id.msj_longitud);

        Button btn_geo = (Button) mLinearLayout.findViewById(R.id.RLocation);
        Button btn_geo_desct = (Button) mLinearLayout.findViewById(R.id.RLocationDesc);
        btn_geo.setOnClickListener(this);
        btn_geo_desct.setOnClickListener(this);

        //  ************************ Objects assing *********************
        fl_part1 = (FrameLayout) mLinearLayout.findViewById(R.id.fl_part1);

        //  ************************ Objects Buttoms *********************
        ImageButton btn_save = (ImageButton) mLinearLayout.findViewById(R.id.btn_save);

        //************* Start FrameLayout **************************
        fl_part1.setVisibility(View.VISIBLE);


        // **************** CLICK ON BUTTONS ********************
        btn_save.setOnClickListener(this);

        // **************** llenando el spiner *****************
        loadSpinner();
        sp_g1.setEnabled(false);
        sp_g2.setEnabled(false);
        sp_g3.setEnabled(false);
        sp_g1.setVisibility(View.GONE);
        sp_g2.setVisibility(View.GONE);
        sp_g3.setVisibility(View.GONE);
        txt_region.setEnabled(false);
        txt_district.setEnabled(false);
        txt_ward.setEnabled(false);


        // ***************** LOCAD INFORMATION *************************
        loadRecord();
        mImageView = (ImageView) mLinearLayout.findViewById(R.id.imageButton);
        mImageView2 = (ImageView) mLinearLayout.findViewById(R.id.imageButton2);
        mImageView3 = (ImageView) mLinearLayout.findViewById(R.id.imageButton3);
        mImageView4 = (ImageView) mLinearLayout.findViewById(R.id.imageButton4);
        mImageView5 = (ImageView) mLinearLayout.findViewById(R.id.imageButton5);
        mImageView6 = (ImageView) mLinearLayout.findViewById(R.id.imageButton6);
        mImageView7 = (ImageView) mLinearLayout.findViewById(R.id.imageButton7);
        mImageView8 = (ImageView) mLinearLayout.findViewById(R.id.imageButton8);
        mImageView9 = (ImageView) mLinearLayout.findViewById(R.id.imageButton9);
        mImageView10 = (ImageView) mLinearLayout.findViewById(R.id.imageButton10);


        Button picSBtn = (Button) mLinearLayout.findViewById(R.id.photo1);
        Button picSBtn2 = (Button) mLinearLayout.findViewById(R.id.photo2);
        Button picSBtn3 = (Button) mLinearLayout.findViewById(R.id.photo3);
        Button picSBtn4 = (Button) mLinearLayout.findViewById(R.id.photo4);
        Button picSBtn5 = (Button) mLinearLayout.findViewById(R.id.photo5);
        Button picSBtn6 = (Button) mLinearLayout.findViewById(R.id.photo6);
        Button picSBtn7 = (Button) mLinearLayout.findViewById(R.id.photo7);
        Button picSBtn8 = (Button) mLinearLayout.findViewById(R.id.photo8);
        Button picSBtn9 = (Button) mLinearLayout.findViewById(R.id.photo9);
        Button picSBtn10 = (Button) mLinearLayout.findViewById(R.id.photo10);

        picSBtn.setOnClickListener(this);
        picSBtn2.setOnClickListener(this);
        picSBtn3.setOnClickListener(this);
        picSBtn4.setOnClickListener(this);
        picSBtn5.setOnClickListener(this);
        picSBtn6.setOnClickListener(this);
        picSBtn7.setOnClickListener(this);
        picSBtn8.setOnClickListener(this);
        picSBtn9.setOnClickListener(this);
        picSBtn10.setOnClickListener(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
            mAlbumStorageDirFactory = new FroyoAlbumDirFactory();
        } else {
            mAlbumStorageDirFactory = new BaseAlbumDirFactory();
        }
        StrictMode.VmPolicy.Builder newbuilder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(newbuilder.build());
        load_pic(getEMIS_code(), 1);
        load_pic(getEMIS_code(), 2);
        load_pic(getEMIS_code(), 3);
        load_pic(getEMIS_code(), 4);
        load_pic(getEMIS_code(), 5);
        load_pic(getEMIS_code(), 6);
        load_pic(getEMIS_code(), 7);
        load_pic(getEMIS_code(), 8);
        load_pic(getEMIS_code(), 9);
        load_pic(getEMIS_code(), 10);
        return mLinearLayout;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1: {
                if (resultCode == Activity.RESULT_OK) {
                    handleBigCameraPhoto(1);
                }
                break;
            } // ACTION_TAKE_PHOTO_B
            case 2: {
                if (resultCode == Activity.RESULT_OK) {
                    handleBigCameraPhoto(2);
                }
                break;
            }
            case 3: {
                if (resultCode == Activity.RESULT_OK) {
                    handleBigCameraPhoto(3);
                }
                break;
            }
            case 4: {
                if (resultCode == Activity.RESULT_OK) {
                    handleBigCameraPhoto(4);
                }
                break;
            }
            case 5: {
                if (resultCode == Activity.RESULT_OK) {
                    handleBigCameraPhoto(5);
                }
                break;
            }

            case 6: {
                if (resultCode == Activity.RESULT_OK) {
                    handleBigCameraPhoto(6);
                }
                break;
            }
            case 7: {
                if (resultCode == Activity.RESULT_OK) {
                    handleBigCameraPhoto(7);
                }
                break;
            }
            case 8: {
                if (resultCode == Activity.RESULT_OK) {
                    handleBigCameraPhoto(8);
                }
                break;
            }
            case 9: {
                if (resultCode == Activity.RESULT_OK) {
                    handleBigCameraPhoto(9);
                }
                break;
            }
            case 10: {
                if (resultCode == Activity.RESULT_OK) {
                    handleBigCameraPhoto(10);
                }
                break;
            }



        } // switch
    }

    // Some lifecycle callbacks so that the image can survive orientation change
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(BITMAP_STORAGE_KEY, mImageBitmap);

        outState.putBoolean(IMAGEVIEW_VISIBILITY_STORAGE_KEY, (mImageBitmap != null));

        super.onSaveInstanceState(outState);
    }



    public static boolean isIntentAvailable(Context context, String action) {
        final PackageManager packageManager = context.getPackageManager();
        final Intent intent = new Intent(action);
        List<ResolveInfo> list =
                packageManager.queryIntentActivities(intent,
                        PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    // **************** Load DATA *************************
    public void loadRecord() {
        Conexion cnSET = new Conexion(getContext(),STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        String sql1 ="SELECT DISTINCT(_id), a1, a2, a3a, a3b, a3c, a3d, a4a, a4b, a5a, a5b, b1, b3, b4, lat, lon,  regionname AS name1, councilname AS name2, warname AS name3, students   \n" +
                "FROM a  \n" +
                "left JOIN EMIS_CATALOG ON (a1=emiscode) \n" +
                "WHERE _id=1";
        Cursor cur_data = dbSET.rawQuery(sql1,null);
        cur_data.moveToFirst();
        if (cur_data.getCount() > 0) {
            EMIS_code = getEMIS_code();
            _col1.setText(getEMIS_code());       // a1
            _col2.setText(getSchool_name());
            _col3 = String.valueOf(cur_data.getString(3));            // a3a
            txt_region.setText(cur_data.getString(16));
            _col4 = cur_data.getString(4);            // a3b
            txt_district.setText(cur_data.getString(17));
            _col5 = cur_data.getString(5);            // a3c
            txt_ward.setText(cur_data.getString(18));
            _col6 = cur_data.getString(6);            // a3d
            _col7.setText(cur_data.getString(7));       // a4a
            _col8.setText(cur_data.getString(8));       // a4b
            _col9.setText(cur_data.getString(9));       // a5a
            _col10.setText(cur_data.getString(10));     // a5b
            _col11 = cur_data.getString(11);            // b1
            _col12.setText(cur_data.getString(12));     // b3
            _col13.setText(cur_data.getString(13));     // b4
            _msj_latitud.setText(cur_data.getString(14));
            _msj_longitud.setText(cur_data.getString(15));
            student_registered.setText(cur_data.getString(19));
            if (!_msj_latitud.getText().toString().isEmpty() && !_msj_longitud.getText().toString().isEmpty())
            {_msj_record.setText("   Latitude: " + _msj_latitud.getText().toString() + "\n   Longitude: " + _msj_longitud.getText().toString());}

            sp_g1.setSelection(getIndex(sp_g1, sp_g1_Find(_col3)));
            sp_g2.setSelection(getIndex(sp_g2, sp_g2_1_Find(_col3,_col4)));

            if(_col6 != null) {
                switch (_col6) {          // a3d
                    case "1":
                        rb_rural.setChecked(true);
                        break;
                    case "2":
                        rb_urban.setChecked(true);
                        break;
                    default:
                        break;
                }
            }
            if (_col11 != null) {
                switch (_col11) {         // b1
                    case "1":
                        rb_gove.setChecked(true);
                        break;
                    case "2":
                        rb_priv.setChecked(true);
                        break;
                    case "4":
                        rb_reli.setChecked(true);
                        break;
                    default:
                        break;
                }
            }
            _IU = "U";
        } else {_IU = "I"; _col1.setText(getEMIS_code()); _col2.setText(getSchool_name());}

        cur_data.close();
        dbSET.close();
        cnSET.close();
        load_sp_g1("1");
    }

    public void updateRecord () {
        Conexion cnSET = new Conexion(getContext(),STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        String sql = "", delimit="%", s1;
        // ***************** CONTENT TO RECORD-SET **************************
        ContentValues reg = new ContentValues();
        if (!_col1.getText().toString().isEmpty()) {
            EMIS_code =  _col1.getText().toString();
            if (_IU == "I") {reg.put("_id", 1);}
            reg.put("flag", _IU); //sql = sql + _IU + ";a;" + delimit;
            sql = sql + "a" + delimit;
            if (!_col1.getText().toString().isEmpty()) {reg.put("a1", Integer.parseInt(_col1.getText().toString()));} sql = sql + _col1.getText().toString() + delimit;
            if (!_col2.getText().toString().isEmpty()) {reg.put("a2", _col2.getText().toString());} sql = sql + _col2.getText().toString() + delimit;
            reg.put("a3a", _col3); sql = sql + _col3 + delimit;
            reg.put("a3b", _col4); sql = sql + _col4 + delimit;
            reg.put("a3c", _col5); sql = sql + _col5 + delimit;


            if (rb_rural.isChecked() == true) {reg.put("a3d", 1); sql = sql + "1" + delimit;}
            else if (rb_urban.isChecked() == true) {reg.put("a3d", 2); sql = sql + "2" + delimit;}
            else {sql = sql + "" + delimit;}
            if (!_col7.getText().toString().isEmpty()) {reg.put("a4a", _col7.getText().toString());} sql = sql + _col7.getText().toString() + delimit;
            if (!_col8.getText().toString().isEmpty()) {reg.put("a4b", _col8.getText().toString());} sql = sql + _col8.getText().toString() + delimit;
            if (!_col9.getText().toString().isEmpty()) {reg.put("a5a", Integer.parseInt(_col9.getText().toString()));} sql = sql + _col9.getText().toString() + delimit;
            if (!_col10.getText().toString().isEmpty()) {reg.put("a5b", Integer.parseInt(_col10.getText().toString()));} sql = sql + _col10.getText().toString() + delimit;
            if (rb_gove.isChecked() == true) {reg.put("b1", 1); sql = sql + "1" + delimit;}
            else if (rb_priv.isChecked() == true) {reg.put("b1", 2); sql = sql + "2" + delimit;}
            else if (rb_reli.isChecked() == true) {reg.put("b1", 4); sql = sql + "4" + delimit;}
            else {sql = sql + "" + delimit;}
            reg.put("b2", "Todo carga muy bien"); sql = sql + "Todo carga muy bien" + delimit;
            if (!_col12.getText().toString().isEmpty()) {reg.put("b3", _col12.getText().toString());} sql = sql + _col12.getText().toString() + delimit;
            if (!_col13.getText().toString().isEmpty()) {reg.put("b4", Integer.parseInt(_col13.getText().toString()));} sql = sql + _col13.getText().toString() + delimit;
            if (!_msj_latitud.getText().toString().isEmpty()) {reg.put("lat", _msj_latitud.getText().toString());} sql = sql + _msj_latitud.getText().toString() + delimit;
            if (!_msj_longitud.getText().toString().isEmpty()) {reg.put("lon", _msj_longitud.getText().toString());} sql = sql + _msj_longitud.getText().toString() + delimit;

            // 20180915 add new column "How many students are registered? xxxx"
            if (!student_registered.getText().toString().isEmpty() && Integer.parseInt(student_registered.getText().toString()) != 0) {
                reg.put("students", Integer.parseInt(student_registered.getText().toString()));
                sql = sql + student_registered.getText().toString() + delimit;
            } else {
                sql = sql + "0" + delimit;
            }

            sql = sql + _IU;
            try {
                // ****************** Fill Bitacora
                ContentValues Bitacora = new ContentValues();
                Bitacora.put("sis_sql", sql);
                dbSET.insert("sisupdate", null, Bitacora);  sql = "";
                // ********************* Fill TABLE a
                if (_IU == "I") {dbSET.insert("a", null, reg);_IU = "U";}
                else {dbSET.update("a", reg, "_id=1", null);}
                toolsfncs.dialogAlertConfirm(getContext(),getResources(),9);
            } catch (Exception e) {
                Toast.makeText(getContext(), getResources().getString(R.string.str_bl_msj6), Toast.LENGTH_SHORT).show();
            }
        }
        dbSET.close();
        cnSET.close();
    }

    // **************** CLICK ON BUTTONS ********************
    //@Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_save:
                dialogAlert(1);
                break;
            case R.id.RLocation:
                actualizarposition();
                break;
            case R.id.RLocationDesc:
                if (ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                locationManager.removeUpdates(locationListener);
                break;
            case R.id.photo1:
                dispatchTakePictureIntent(1);
                break;
            case R.id.photo2:
                dispatchTakePictureIntent(2);
                break;
            case R.id.photo3:
                dispatchTakePictureIntent(3);
                break;
            case R.id.photo4:
                dispatchTakePictureIntent(4);
                break;
            case R.id.photo5:
                dispatchTakePictureIntent(5);
                break;
            case R.id.photo6:
                dispatchTakePictureIntent(6);
                break;
            case R.id.photo7:
                dispatchTakePictureIntent(7);
                break;
            case R.id.photo8:
                dispatchTakePictureIntent(8);
                break;
            case R.id.photo9:
                dispatchTakePictureIntent(9);
                break;
            case R.id.photo10:
                dispatchTakePictureIntent(10);
                break;
        }
    }

    // *********** Control Alerts ************************
    public void dialogAlert(int v){
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getContext());
        dialogo1.setTitle(getResources().getString(R.string.str_bl_msj1));
        if (v == 1){dialogo1.setMessage(getResources().getString(R.string.str_bl_msj2));}
        if (v == 2){dialogo1.setMessage("Are you sure to quit?");}
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton(getResources().getString(R.string.str_bl_msj3), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                aceptar();
            }
        });
        dialogo1.setNegativeButton(getResources().getString(R.string.str_bl_msj4), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                cancelar();
            }
        });
        dialogo1.show();
    }

    public void dialogAlertNotification(int v){
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getContext());
        dialogo1.setTitle(getResources().getString(R.string.str_g_notification)); // Notification
        if (v == 1){dialogo1.setMessage(getResources().getString(R.string.str_bl_msj2));}
        if (v == 9){dialogo1.setMessage(getResources().getString(R.string.str_bl_msj5));} // The information has been update...
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton(getResources().getString(R.string.str_g_ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                aceptar1();
            }
        });
        dialogo1.show();
    }
    public void aceptar() {updateRecord();}
    public void cancelar() {} //getActivity().finish();}
    public void aceptar1() {}
    // *********** END Control Alerts ************************


    // ************** Load Spinner GEO ***********************
    public void loadSpinner(){
        list_g1.clear();
        ArrayAdapter adap_g1_clear = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, list_g1);
        sp_g1.setAdapter(adap_g1_clear);
        Conexion cnGEO1 = new Conexion(getContext(),STATICS_ROOT_DB, null);
        SQLiteDatabase dbGEO1 = cnGEO1.getWritableDatabase();
        Cursor cur_g1 = dbGEO1.rawQuery("SELECT  g1,name1 FROM set_geo_codes GROUP BY g1, name1 ORDER BY name1", null);
        String col_g1;
        cur_g1.moveToFirst();
        if (cur_g1.moveToFirst()) {
            do {
                col_g1 = cur_g1.getString(1);
                list_g1.add(col_g1);
            } while (cur_g1.moveToNext());
        }
        ArrayAdapter adap_g1 = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, list_g1);
        sp_g1.setAdapter(adap_g1);
        cur_g1.close();
        dbGEO1.close();
        cnGEO1.close();

        this.sp_g1.setOnItemSelectedListener(this);
        this.sp_g2.setOnItemSelectedListener(this);
        this.sp_g3.setOnItemSelectedListener(this);

    }

    public void load_sp_g1(String g1) {
        Conexion cnGEO1 = new Conexion(getContext(),STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbGEO1 = cnGEO1.getWritableDatabase();
        Cursor cur_g1 = dbGEO1.rawQuery("SELECT  name1 FROM set_geo_codes WHERE g1='" + g1 + "' GROUP BY name1 ", null);
        cur_g1.moveToFirst();
    }

    //test
    public void load_sp_g2_1(String g1, String g2) {
        Conexion cnGEO1 = new Conexion(getContext(),STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbGEO1 = cnGEO1.getWritableDatabase();
        Cursor cur_g1 = dbGEO1.rawQuery("SELECT  name2 FROM set_geo_codes WHERE g1='" + g1 + "' and g2='"+ g2 +"'  GROUP BY name2 ", null);
        cur_g1.moveToFirst();
    }

    private void load_sp_g2(String g2) {
        list_g2.clear();
        ArrayAdapter<String> adap_g2_clear = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, list_g2);
        sp_g2.setAdapter(adap_g2_clear);
        Conexion cnGEO2 = new Conexion(getContext(),STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbGEO2 = cnGEO2.getWritableDatabase();
        Cursor cur_g2 = dbGEO2.rawQuery("SELECT  g2,name2 FROM set_geo_codes WHERE name1='" + g2 + "' GROUP BY g2, name2 ORDER BY name2", null);
        String col_g2;
        cur_g2.moveToFirst();
        if (cur_g2.moveToFirst()) {
            do {
                col_g2 = cur_g2.getString(1);
                list_g2.add(col_g2);
            } while (cur_g2.moveToNext());
        }
        ArrayAdapter<String> adap_g2 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, list_g2);
        sp_g2.setAdapter(adap_g2);
        sp_g2.setSelection(getIndex(sp_g2,sp_g2_1_Find(_col3,_col4)));
        cur_g2.close();
        dbGEO2.close();
        cnGEO2.close();
    }

    private void load_sp_g3(String g2, String g3) {
        list_g3.clear();
        ArrayAdapter<String> adap_g3_clear = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, list_g3);
        sp_g3.setAdapter(adap_g3_clear);
        Conexion cnGEO3 = new Conexion(getContext(),STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbGEO3 = cnGEO3.getWritableDatabase();
        Cursor cur_g3 = dbGEO3.rawQuery("SELECT  g3,name3 FROM set_geo_codes WHERE name1='" + g2 + "' AND name2='" + g3 + "' GROUP BY g3, name3 ORDER BY name3", null);
        String col_g3;
        cur_g3.moveToFirst();
        if (cur_g3.moveToFirst()) {
            do {
                col_g3 = cur_g3.getString(1);
                list_g3.add(col_g3);
            } while (cur_g3.moveToNext());
        }
        ArrayAdapter<String> adap_g3 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, list_g3);
        sp_g3.setAdapter(adap_g3);
        sp_g3.setSelection(getIndex(sp_g3, sp_g3_Find(_col3, _col4, _col5)));
        cur_g3.close();
        cnGEO3.close();

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.sp_g1:
                try {
                    String spg1 = sp_g1.getSelectedItem().toString();
                    load_sp_g2(spg1);
                } catch (Exception e) {}
                break;
            case R.id.sp_g2:
                try {
                    String spg1 = sp_g1.getSelectedItem().toString();
                    String spg2 = sp_g2.getSelectedItem().toString();
                    load_sp_g3(spg1, spg2);
                } catch (Exception e){}
                break;
            case R.id.sp_g3:
                if (!sp_g3.getSelectedItem().toString().isEmpty()) {
                }

                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    // ************** Find and SET Spinner find ********************************
    public int getIndex(Spinner spinner, String myString){
        int index = 0;
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).equals(myString)){
                index = i;
            }
        }
        return index;
    }
    // ********************* Find record save G1***********************
    public String  sp_g1_Find(String v) {
        String spinnerFind="";
        Conexion cnGEO = new Conexion(getContext(),STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbGEO = cnGEO.getWritableDatabase();
        Cursor cur_g = dbGEO.rawQuery("SELECT  name1 FROM set_geo_codes WHERE g1='" + v + "' GROUP BY name1", null);
        cur_g.moveToFirst();
        //spinnerFind = cur_g.getString(0);
        if (cur_g.moveToFirst()){ spinnerFind = cur_g.getString(0);} else{spinnerFind = "0";}
        cur_g.close();
        dbGEO.close();
        cnGEO.close();
        return spinnerFind;
    }
    public String  sp_g2_Find(String v) {
        String spinnerFind="";
        Conexion cnGEO = new Conexion(getContext(),STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbGEO = cnGEO.getWritableDatabase();
        Cursor cur_g = dbGEO.rawQuery("SELECT  name2 FROM set_geo_codes WHERE g2='" + v + "' GROUP BY name2", null);
        cur_g.moveToFirst();
        //spinnerFind = cur_g.getString(0);
        if (cur_g.moveToFirst()){ spinnerFind = cur_g.getString(0);} else{spinnerFind = "0";}
        cur_g.close();
        dbGEO.close();
        cnGEO.close();
        return spinnerFind;
    }

    //test
    public String  sp_g2_1_Find(String v1, String v2) {
        String spinnerFind="";
        Conexion cnGEO = new Conexion(getContext(),STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbGEO = cnGEO.getWritableDatabase();
        Cursor cur_g = dbGEO.rawQuery("SELECT  name2 FROM set_geo_codes WHERE g1= '"+v1 +"' and g2='" + v2 + "' GROUP BY name2", null);
        cur_g.moveToFirst();
        if (cur_g.moveToFirst()){ spinnerFind = cur_g.getString(0);} else{spinnerFind = "0";}
        cur_g.close();
        dbGEO.close();
        cnGEO.close();
        return spinnerFind;
    }
    //test
    public String  sp_g3_Find(String v1, String v2, String v3) {
        String spinnerFind="";
        Conexion cnGEO = new Conexion(getContext(),STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbGEO = cnGEO.getWritableDatabase();
        Cursor cur_g = dbGEO.rawQuery("SELECT  name3 FROM set_geo_codes WHERE g1= '"+v1+"' and g2='"+v2+"' and g3='" + v3 + "' GROUP BY name3", null);
        cur_g.moveToFirst();
        if (cur_g.moveToFirst()){ spinnerFind = cur_g.getString(0);} else{spinnerFind = "0";}
        cur_g.close();
        dbGEO.close();
        cnGEO.close();
        return spinnerFind;
    }
    public String sp_g1_Update(String v) {
        String spinnerUpdate="";
        Conexion cnGEO = new Conexion(getContext(),STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbGEO = cnGEO.getWritableDatabase();
        Cursor cur_g = dbGEO.rawQuery("SELECT  g1 FROM set_geo_codes WHERE name1='" + v + "' GROUP BY g1", null);
        cur_g.moveToFirst();
        spinnerUpdate = cur_g.getString(0);
        cur_g.close();
        dbGEO.close();
        cnGEO.close();
        return spinnerUpdate;
    }
    public String sp_g2_Update(String v1, String v2) {
        String spinnerUpdate="";
        Conexion cnGEO = new Conexion(getContext(),STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbGEO = cnGEO.getWritableDatabase();
        Cursor cur_g = dbGEO.rawQuery("SELECT  g2 FROM set_geo_codes WHERE name1='" + v1 + "'  AND name2='" + v2+ "' GROUP BY g2", null);
        cur_g.moveToFirst();
        spinnerUpdate = cur_g.getString(0);
        cur_g.close();
        dbGEO.close();
        cnGEO.close();
        return spinnerUpdate;
    }
    public String sp_g3_Update(String v1, String v2, String v3) {
        String spinnerUpdate="";
        Conexion cnGEO = new Conexion(getContext(),STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbGEO = cnGEO.getWritableDatabase();

        Cursor cur_g = dbGEO.rawQuery("SELECT  g3 FROM set_geo_codes WHERE name1='" + v1.replace("'","''") + "'  AND name2='" + v2.replace("'","''") + "'  AND name3='" + v3.replace("'","''")+ "' GROUP BY g3", null);
        cur_g.moveToFirst();
        spinnerUpdate = cur_g.getString(0);
        cur_g.close();
        dbGEO.close();
        cnGEO.close();
        return spinnerUpdate;
    }

    public String[] spinnerLoad(String v1, String v2, String v3) {
        String [] spinnerUpdate= {"","",""};
        Conexion cnGEO = new Conexion(getContext(),STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbGEO = cnGEO.getWritableDatabase();
        Cursor cur_g = dbGEO.rawQuery("SELECT  name1,g3 FROM set_geo_codes WHERE name1='" + v1 + "'  AND name2='" + v2 + "'  AND name3='" + v3+ "' GROUP BY g3", null);
        cur_g.moveToFirst();
        spinnerUpdate[0] = cur_g.getString(0);
        cur_g.close();
        dbGEO.close();
        cnGEO.close();
        return spinnerUpdate;
    }


    // ************************ GPS function *******************
    private void actualizarposition() {
        if (ContextCompat.checkSelfPermission(this.getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this.getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an expanation to the user asynchronously -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.


                ActivityCompat.requestPermissions(this.getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_FINE_LOCATION);

                // MY_PERMISSIONS_REQUEST_FINE_LOCATION is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

        locationManager =
                (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);


        Location location =
                locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);


        muestraPosicion(location);


        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                muestraPosicion(location);
            }

            public void onProviderDisabled(String provider) {
                _msj_record.setText("Provider OFF");
            }

            public void onProviderEnabled(String provider) {
                _msj_record.setText("Provider ON");
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }
        };

        if (ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 15000, 0, locationListener);


    }
    private void muestraPosicion(Location loc) {
        if(loc != null)
        {
            _msj_latitud.setText(String.valueOf(loc.getLatitude()));
            _msj_longitud.setText(String.valueOf(loc.getLongitude()));
            _msj_record.setText("   Latitude: " + _msj_latitud.getText().toString() + "\n   Longitude: " + _msj_longitud.getText().toString());

        }
        else
        {
            _msj_record.setText("Latitud: (sin_datos)");

        }
    }
    double roundTwoDecimals(double d) {
        DecimalFormat twoDForm = new DecimalFormat("###.######");
        return Double.valueOf(twoDForm.format(d));
    }

    public String getEMIS_code(){
        String getemiscode="";
        Conexion cnSET = new Conexion(getContext(), STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        try {
            Cursor cur_data = dbSET.rawQuery("SELECT emis FROM ms_0", null);
            cur_data.moveToFirst();
            if (cur_data.getCount() > 0) {getemiscode = cur_data.getString(0);} else {getemiscode = "";}
        }catch (Exception e) {}

        return getemiscode;
    }

    public String getSchool_name(){
        String getSchoolName="";
        Conexion cnSET = new Conexion(getContext(), STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        try {
            Cursor cur_data = dbSET.rawQuery("SELECT flag FROM ms_0", null);
            cur_data.moveToFirst();
            if (cur_data.getCount() > 0) {getSchoolName = cur_data.getString(0);} else {getSchoolName = "";}
        }catch (Exception e) {}

        return getSchoolName;
    }

    public void new_set_geo_codes() {
        Conexion cnSET = new Conexion(getContext(), STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        Cursor cur_data = dbSET.rawQuery("SELECT COUNT(*) FROM set_geo_codes", null);
        cur_data.moveToFirst();

        if (!cur_data.getString(0).equals("1122")) {
            Toast.makeText(getContext(), getResources().getString(R.string.synchronizing), Toast.LENGTH_LONG).show();
            dbSET.execSQL("DELETE FROM set_geo_codes");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(0,0,0,'REGION','DISTRICT','WARD')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,6,31,'DODOMA','BAHI','BABAYU')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,6,71,'DODOMA','BAHI','BAHI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,6,141,'DODOMA','BAHI','CHALI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,6,181,'DODOMA','BAHI','CHIBELELA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,6,61,'DODOMA','BAHI','CHIFUTUKA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,6,121,'DODOMA','BAHI','CHIKOLA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,6,131,'DODOMA','BAHI','CHIPANGA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,6,91,'DODOMA','BAHI','IBIHWA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,6,171,'DODOMA','BAHI','IBUGULE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,6,101,'DODOMA','BAHI','ILINDI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,6,111,'DODOMA','BAHI','KIGWE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,6,21,'DODOMA','BAHI','LAMAITI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,6,11,'DODOMA','BAHI','MAKANDA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,6,161,'DODOMA','BAHI','MPALANGA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,6,81,'DODOMA','BAHI','MPAMANTWA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,6,71,'DODOMA','BAHI','MPINGA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,6,51,'DODOMA','BAHI','MSISI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,6,201,'DODOMA','BAHI','MTITAA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,6,61,'DODOMA','BAHI','MUNDEMU')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,6,191,'DODOMA','BAHI','MWITIKIRA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,6,151,'DODOMA','BAHI','NONDWA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,6,41,'DODOMA','BAHI','ZANKA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,4,83,'DODOMA','CHAMWINO','BUIGIRI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,4,32,'DODOMA','CHAMWINO','CHAMWINO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,4,301,'DODOMA','CHAMWINO','CHIBOLI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,4,71,'DODOMA','CHAMWINO','CHILONWA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,4,261,'DODOMA','CHAMWINO','CHINUGULU')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,4,41,'DODOMA','CHAMWINO','DABALO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,4,221,'DODOMA','CHAMWINO','FUFU')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,4,161,'DODOMA','CHAMWINO','HANDALI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,4,11,'DODOMA','CHAMWINO','HANETI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,4,281,'DODOMA','CHAMWINO','HUZI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,4,181,'DODOMA','CHAMWINO','IDIFU')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,4,131,'DODOMA','CHAMWINO','IGANDU')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,4,131,'DODOMA','CHAMWINO','IKOMBOLINGA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,4,111,'DODOMA','CHAMWINO','IKOWA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,4,171,'DODOMA','CHAMWINO','IRINGA MVUMI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,4,31,'DODOMA','CHAMWINO','ITISO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,4,291,'DODOMA','CHAMWINO','LOJE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,4,91,'DODOMA','CHAMWINO','MAJELEKO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,4,191,'DODOMA','CHAMWINO','MAKANG''WA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,4,101,'DODOMA','CHAMWINO','MANCHALI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,4,271,'DODOMA','CHAMWINO','MANDA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,4,211,'DODOMA','CHAMWINO','MANZASE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,4,51,'DODOMA','CHAMWINO','MEMBE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,4,41,'DODOMA','CHAMWINO','MLOWA BARABARANI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,4,231,'DODOMA','CHAMWINO','MLOWA BWAWANI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,4,241,'DODOMA','CHAMWINO','MPWAYUNGU')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,4,121,'DODOMA','CHAMWINO','MSAMALO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,4,61,'DODOMA','CHAMWINO','MSANGA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,4,41,'DODOMA','CHAMWINO','MUUNGANO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,4,151,'DODOMA','CHAMWINO','MVUMI MAKULU')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,4,151,'DODOMA','CHAMWINO','MVUMI MISHENI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,4,101,'DODOMA','CHAMWINO','NGHAHELEZI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,4,251,'DODOMA','CHAMWINO','NGHAMBAKU')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,4,311,'DODOMA','CHAMWINO','NHINHI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,4,21,'DODOMA','CHAMWINO','SEGALA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,4,321,'DODOMA','CHAMWINO','ZAJILWA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,7,31,'DODOMA','CHEMBA','BABAYU')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,7,91,'DODOMA','CHEMBA','BEREKO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,7,111,'DODOMA','CHEMBA','CHANDAMA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,7,131,'DODOMA','CHEMBA','CHEMBA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,7,51,'DODOMA','CHEMBA','CHURUKU')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,7,81,'DODOMA','CHEMBA','DALAI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,7,161,'DODOMA','CHEMBA','FARKWA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,7,121,'DODOMA','CHEMBA','GOIMA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,7,151,'DODOMA','CHEMBA','GWANDI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,7,91,'DODOMA','CHEMBA','JANGALO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,7,191,'DODOMA','CHEMBA','KIDOKA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,7,41,'DODOMA','CHEMBA','KIMAHA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,7,121,'DODOMA','CHEMBA','KINYAMSINDO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,7,191,'DODOMA','CHEMBA','KWAMTORO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,7,111,'DODOMA','CHEMBA','LAHODA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,7,201,'DODOMA','CHEMBA','LALTA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,7,11,'DODOMA','CHEMBA','MAKORONGO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,7,51,'DODOMA','CHEMBA','MONDO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,7,171,'DODOMA','CHEMBA','MPENDO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,7,103,'DODOMA','CHEMBA','MRIJO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,7,31,'DODOMA','CHEMBA','MSAADA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,7,21,'DODOMA','CHEMBA','OVADA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,7,141,'DODOMA','CHEMBA','PARANGA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,7,181,'DODOMA','CHEMBA','SANZAWA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,7,51,'DODOMA','CHEMBA','SONGOLO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,7,33,'DODOMA','CHEMBA','SOYA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,7,51,'DODOMA','CHEMBA','TUMBAKOSE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,5,32,'DODOMA','DODOMA URBAN','CHAMWINO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,5,91,'DODOMA','DODOMA URBAN','CHIHANGA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,5,41,'DODOMA','DODOMA URBAN','DODOMA-MAKULU')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,5,252,'DODOMA','DODOMA URBAN','HAZINA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,5,101,'DODOMA','DODOMA URBAN','HOMBOLO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,5,111,'DODOMA','DODOMA URBAN','IPALA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,5,151,'DODOMA','DODOMA URBAN','KIKOMBO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,5,111,'DODOMA','DODOMA URBAN','KIKUYU')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,5,42,'DODOMA','DODOMA URBAN','KIWANJA CHA NDEGE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,5,282,'DODOMA','DODOMA URBAN','KIZOTA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,5,52,'DODOMA','DODOMA URBAN','MAKOLE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,5,81,'DODOMA','DODOMA URBAN','MAKUTUPORA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,5,231,'DODOMA','DODOMA URBAN','MBABALA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,5,301,'DODOMA','DODOMA URBAN','MBALAWALA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,5,62,'DODOMA','DODOMA URBAN','MIYUJI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,5,221,'DODOMA','DODOMA URBAN','MKONZE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,5,171,'DODOMA','DODOMA URBAN','MPUNGUZI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,5,71,'DODOMA','DODOMA URBAN','MSALATO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,5,141,'DODOMA','DODOMA URBAN','MTUMBA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,5,291,'DODOMA','DODOMA URBAN','NALA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,5,161,'DODOMA','DODOMA URBAN','NG''HONGHONHA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,5,121,'DODOMA','DODOMA URBAN','NZUGUNI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,5,52,'DODOMA','DODOMA URBAN','TAMBUKARELI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,5,22,'DODOMA','DODOMA URBAN','UHURU')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,5,241,'DODOMA','DODOMA URBAN','ZUZU')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,91,'DODOMA','KONDOA','BEREKO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,161,'DODOMA','KONDOA','BOLISA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,11,'DODOMA','KONDOA','BUMBUTA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,31,'DODOMA','KONDOA','BUSI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,251,'DODOMA','KONDOA','CHANGAA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,42,'DODOMA','KONDOA','CHEMCHEM')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,41,'DODOMA','KONDOA','HAUBI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,151,'DODOMA','KONDOA','HONDOMAIRO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,201,'DODOMA','KONDOA','ITASWI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,191,'DODOMA','KONDOA','ITOLOLO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,51,'DODOMA','KONDOA','KALAMBA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,41,'DODOMA','KONDOA','KEIKEI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,81,'DODOMA','KONDOA','KIKILO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,111,'DODOMA','KONDOA','KIKORE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,192,'DODOMA','KONDOA','KILIMANI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,221,'DODOMA','KONDOA','KINGALE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,171,'DODOMA','KONDOA','KINYASI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,101,'DODOMA','KONDOA','KISESE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,241,'DODOMA','KONDOA','KOLO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,232,'DODOMA','KONDOA','KONDOA MJINI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,61,'DODOMA','KONDOA','KWADELO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,71,'DODOMA','KONDOA','MASANGE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,271,'DODOMA','KONDOA','MNENIA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,21,'DODOMA','KONDOA','PAHI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,181,'DODOMA','KONDOA','SALANKA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,121,'DODOMA','KONDOA','SERYA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,281,'DODOMA','KONDOA','SOERA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,211,'DODOMA','KONDOA','SURUKE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,261,'DODOMA','KONDOA','THAWI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,3,141,'DODOMA','KONGWA','CHAMKOROMA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,3,161,'DODOMA','KONGWA','CHITEGO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,3,201,'DODOMA','KONGWA','CHIWE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,3,31,'DODOMA','KONGWA','HOGORO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,3,101,'DODOMA','KONGWA','IDUO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,3,123,'DODOMA','KONGWA','KIBAIGWA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,3,13,'DODOMA','KONGWA','KONGWA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,3,211,'DODOMA','KONGWA','LENJULU')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,3,151,'DODOMA','KONGWA','MAKAWA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,3,171,'DODOMA','KONGWA','MATONGORO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,3,131,'DODOMA','KONGWA','MKOKA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,3,91,'DODOMA','KONGWA','MLALI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,3,183,'DODOMA','KONGWA','MPWAPWA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,3,71,'DODOMA','KONGWA','MTANANA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,3,221,'DODOMA','KONGWA','NGHUMBI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,3,181,'DODOMA','KONGWA','NGOMAI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,3,61,'DODOMA','KONGWA','NJOGE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,3,81,'DODOMA','KONGWA','PANDAMBILI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,3,111,'DODOMA','KONGWA','SAGARA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,3,21,'DODOMA','KONGWA','SEJELI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,3,51,'DODOMA','KONGWA','SONGAMBELE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,3,261,'DODOMA','KONGWA','THAWI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,3,133,'DODOMA','KONGWA','UGOGONI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,3,41,'DODOMA','KONGWA','ZOISSA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,141,'DODOMA','MPWAPWA','BEREGE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,291,'DODOMA','MPWAPWA','CHIPOGORO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,221,'DODOMA','MPWAPWA','CHITEMO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,141,'DODOMA','MPWAPWA','CHUNYU')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,271,'DODOMA','MPWAPWA','GALIGALI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,171,'DODOMA','MPWAPWA','GODEGODE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,201,'DODOMA','MPWAPWA','GULWE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,211,'DODOMA','MPWAPWA','IGOVU')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,91,'DODOMA','MPWAPWA','IPERA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,231,'DODOMA','MPWAPWA','IWONDO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,53,'DODOMA','MPWAPWA','KIBAKWE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,41,'DODOMA','MPWAPWA','KIMAGAI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,241,'DODOMA','MPWAPWA','KINGITI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,251,'DODOMA','MPWAPWA','LUFU')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,71,'DODOMA','MPWAPWA','LUHUNDWA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,61,'DODOMA','MPWAPWA','LUMUMA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,191,'DODOMA','MPWAPWA','LUPETA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,91,'DODOMA','MPWAPWA','LWIHOMELO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,132,'DODOMA','MPWAPWA','MALOLO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,191,'DODOMA','MPWAPWA','MANG''ALIZA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,81,'DODOMA','MPWAPWA','MASSA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,31,'DODOMA','MPWAPWA','MATOMONDO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,11,'DODOMA','MPWAPWA','MAZAE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,161,'DODOMA','MPWAPWA','MBUGA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,131,'DODOMA','MPWAPWA','MIMA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,91,'DODOMA','MPWAPWA','MLEMBULE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,111,'DODOMA','MPWAPWA','MLUNDUZI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,183,'DODOMA','MPWAPWA','MPWAPWA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,281,'DODOMA','MPWAPWA','MTERA DAM')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,211,'DODOMA','MPWAPWA','NGHAMBI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,261,'DODOMA','MPWAPWA','PWAGA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,101,'DODOMA','MPWAPWA','RUDI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,91,'DODOMA','MPWAPWA','VINGHAWE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(1,2,121,'DODOMA','MPWAPWA','WOTTA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,6,21,'KIGOMA','BUHIGWE','BIHARU')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,6,81,'KIGOMA','BUHIGWE','BUHIGWE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,6,211,'KIGOMA','BUHIGWE','BUKUBA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,6,101,'KIGOMA','BUHIGWE','JANDA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,6,41,'KIGOMA','BUHIGWE','KAJANA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,6,91,'KIGOMA','BUHIGWE','KIBANDE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,6,111,'KIGOMA','BUHIGWE','KIBWIGWA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,6,61,'KIGOMA','BUHIGWE','KILELEMA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,6,201,'KIGOMA','BUHIGWE','KINAZI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,6,61,'KIGOMA','BUHIGWE','MKATANGA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,6,261,'KIGOMA','BUHIGWE','MUBANGA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,6,51,'KIGOMA','BUHIGWE','MUGERA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,6,131,'KIGOMA','BUHIGWE','MUHINDA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,6,141,'KIGOMA','BUHIGWE','MUNANILA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,6,71,'KIGOMA','BUHIGWE','MUNYEGERA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,6,111,'KIGOMA','BUHIGWE','MUNZEZE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,6,31,'KIGOMA','BUHIGWE','MUYAMA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,6,151,'KIGOMA','BUHIGWE','MWAYAYA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,6,11,'KIGOMA','BUHIGWE','NYAMUGALI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,6,121,'KIGOMA','BUHIGWE','RUSABA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,7,151,'KIGOMA','KAKONKO','GWANUMPU')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,7,151,'KIGOMA','KAKONKO','GWARAMA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,7,53,'KIGOMA','KAKONKO','KAKONKO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,7,12,'KIGOMA','KAKONKO','KANYONZA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,7,81,'KIGOMA','KAKONKO','KASANDA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,7,41,'KIGOMA','KAKONKO','KASUNGA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,7,101,'KIGOMA','KAKONKO','KATANGA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,7,61,'KIGOMA','KAKONKO','KIZIGUZIGU')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,7,111,'KIGOMA','KAKONKO','MUGUNZU')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,7,31,'KIGOMA','KAKONKO','MUHANGE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,7,11,'KIGOMA','KAKONKO','NYABIBUYE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,7,21,'KIGOMA','KAKONKO','NYAMTUKUZA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,7,71,'KIGOMA','KAKONKO','RUGENGE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,2,121,'KIGOMA','KASULU DC','ASANTE NYERERE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,2,31,'KIGOMA','KASULU DC','BUGAGA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,2,21,'KIGOMA','KASULU DC','BUHORO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,2,252,'KIGOMA','KASULU DC','HERU SHINGO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,2,62,'KIGOMA','KASULU DC','KAGERANKANDA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,2,171,'KIGOMA','KASULU DC','KIGEMBE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,2,181,'KIGOMA','KASULU DC','KITAGATA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,8,252,'KIGOMA','KASULU TOWNSHIP AUTHORITY','HERU JUU')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,8,91,'KIGOMA','KASULU TOWNSHIP AUTHORITY','KABANGA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,8,21,'KIGOMA','KASULU TOWNSHIP AUTHORITY','KIGONDO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,8,81,'KIGOMA','KASULU TOWNSHIP AUTHORITY','KUMSENGA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,8,241,'KIGOMA','KASULU TOWNSHIP AUTHORITY','MARUMBA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,8,31,'KIGOMA','KASULU TOWNSHIP AUTHORITY','MSAMBARA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,8,11,'KIGOMA','KASULU TOWNSHIP AUTHORITY','MUGANZA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,8,81,'KIGOMA','KASULU TOWNSHIP AUTHORITY','MURUBONA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,8,61,'KIGOMA','KASULU TOWNSHIP AUTHORITY','MURUFITI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,8,41,'KIGOMA','KASULU TOWNSHIP AUTHORITY','MURUSI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,8,71,'KIGOMA','KASULU TOWNSHIP AUTHORITY','NYANSHA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,8,51,'KIGOMA','KASULU TOWNSHIP AUTHORITY','NYUMBIGWA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,8,61,'KIGOMA','KASULU TOWNSHIP AUTHORITY','RUHITA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,1,21,'KIGOMA','KIBONDO','BITARE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,1,21,'KIGOMA','KIBONDO','BITURANA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,1,111,'KIGOMA','KIBONDO','BUNYAMBO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,1,51,'KIGOMA','KIBONDO','BUSAGARA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,1,71,'KIGOMA','KIBONDO','BUSUNZU')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,1,121,'KIGOMA','KIBONDO','ITABA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,1,62,'KIGOMA','KIBONDO','KAGEZI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,1,33,'KIGOMA','KIBONDO','KIBONDO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,1,131,'KIGOMA','KIBONDO','KITAHANA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,1,91,'KIGOMA','KIBONDO','KIZAZI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,1,81,'KIGOMA','KIBONDO','KUMSENGA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,1,81,'KIGOMA','KIBONDO','KUMWAMBU')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,1,103,'KIGOMA','KIBONDO','MABAMBA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,1,11,'KIGOMA','KIBONDO','MISEZERO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,1,91,'KIGOMA','KIBONDO','MUKABUYE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,1,41,'KIGOMA','KIBONDO','MURUNGU')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,1,111,'KIGOMA','KIBONDO','NYARUYOBA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,1,61,'KIGOMA','KIBONDO','RUGONGWE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,1,132,'KIGOMA','KIBONDO','RUSOHOKO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,4,172,'KIGOMA','KIGOMA MUNICIPAL-UJIJI','BANGWE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,4,32,'KIGOMA','KIGOMA MUNICIPAL-UJIJI','BUHANDA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,4,42,'KIGOMA','KIGOMA MUNICIPAL-UJIJI','BUSINDE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,4,142,'KIGOMA','KIGOMA MUNICIPAL-UJIJI','BUZEBAZEBA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,4,12,'KIGOMA','KIGOMA MUNICIPAL-UJIJI','GUNGU')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,4,62,'KIGOMA','KIGOMA MUNICIPAL-UJIJI','KAGERA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,4,72,'KIGOMA','KIGOMA MUNICIPAL-UJIJI','KASIMBU')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,4,72,'KIGOMA','KIGOMA MUNICIPAL-UJIJI','KASINGILIMA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,4,192,'KIGOMA','KIGOMA MUNICIPAL-UJIJI','KATUBUKA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,4,22,'KIGOMA','KIGOMA MUNICIPAL-UJIJI','KIBIRIZI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,4,162,'KIGOMA','KIGOMA MUNICIPAL-UJIJI','KIGOMA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,4,122,'KIGOMA','KIGOMA MUNICIPAL-UJIJI','KIPAMPA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,4,112,'KIGOMA','KIGOMA MUNICIPAL-UJIJI','KITONGONI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,4,52,'KIGOMA','KIGOMA MUNICIPAL-UJIJI','MACHINJIONI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,4,163,'KIGOMA','KIGOMA MUNICIPAL-UJIJI','MAJENGO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,4,182,'KIGOMA','KIGOMA MUNICIPAL-UJIJI','MWANGA KASKAZINI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,4,152,'KIGOMA','KIGOMA MUNICIPAL-UJIJI','MWANGA KUSINI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,4,82,'KIGOMA','KIGOMA MUNICIPAL-UJIJI','RUBUGA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,4,132,'KIGOMA','KIGOMA MUNICIPAL-UJIJI','RUSIMBI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,3,51,'KIGOMA','KIGOMA RURAL','BITALE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,3,101,'KIGOMA','KIGOMA RURAL','KAGONGO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,3,11,'KIGOMA','KIGOMA RURAL','KAGUNGA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,3,41,'KIGOMA','KIGOMA RURAL','KALINZI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,3,191,'KIGOMA','KIGOMA RURAL','KIDAHWE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,3,71,'KIGOMA','KIGOMA RURAL','MAHEMBE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,3,81,'KIGOMA','KIGOMA RURAL','MATENDO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,3,61,'KIGOMA','KIGOMA RURAL','MKONGORO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,3,12,'KIGOMA','KIGOMA RURAL','MUKIGO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,3,91,'KIGOMA','KIGOMA RURAL','MUNGONYA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,3,31,'KIGOMA','KIGOMA RURAL','MWAMGONGO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,3,113,'KIGOMA','KIGOMA RURAL','MWANDIGA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,3,33,'KIGOMA','KIGOMA RURAL','NKUNGWE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,3,111,'KIGOMA','KIGOMA RURAL','NYARUBANDA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,3,71,'KIGOMA','KIGOMA RURAL','SIMBO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,3,181,'KIGOMA','KIGOMA RURAL','ZIWANI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,3,51,'KIGOMA','NEW WARD','KALELA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,3,11,'KIGOMA','NEW WARD','KITANGA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,3,101,'KIGOMA','NEW WARD','KURUGONGO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,3,61,'KIGOMA','NEW WARD','KWAGA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,3,151,'KIGOMA','NEW WARD','MAKERE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,3,151,'KIGOMA','NEW WARD','MUZYE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,3,51,'KIGOMA','NEW WARD','NYACHENDA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,3,81,'KIGOMA','NEW WARD','NYAKITONTO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,3,141,'KIGOMA','NEW WARD','NYAMIDAHO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,3,71,'KIGOMA','NEW WARD','NYAMNYUSI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,3,151,'KIGOMA','NEW WARD','NYENGE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,3,111,'KIGOMA','NEW WARD','RUNGWE MPYA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,3,121,'KIGOMA','NEW WARD','RUSESA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,3,141,'KIGOMA','NEW WARD','SHUNGULIBA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,3,131,'KIGOMA','NEW WARD','TITYE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,5,61,'KIGOMA','UVINZA','BASANZA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,5,21,'KIGOMA','UVINZA','BUHINGU')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,5,252,'KIGOMA','UVINZA','HEREMBE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,5,31,'KIGOMA','UVINZA','IGALULA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,5,61,'KIGOMA','UVINZA','ILAGALA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,5,141,'KIGOMA','UVINZA','ITEBULA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,5,11,'KIGOMA','UVINZA','KALYA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,5,81,'KIGOMA','UVINZA','KANDAGA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,5,91,'KIGOMA','UVINZA','KAZURAMIMBA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,5,111,'KIGOMA','UVINZA','MGANZA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,5,121,'KIGOMA','UVINZA','MTEGO WA NOTI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,5,71,'KIGOMA','UVINZA','MWAKIZEGA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,5,133,'KIGOMA','UVINZA','NGURUKA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,5,41,'KIGOMA','UVINZA','SIGUNGA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,5,51,'KIGOMA','UVINZA','SUNUKA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(16,5,103,'KIGOMA','UVINZA','UVINZA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,1,51,'LINDI','KILWA','CHUMO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,1,71,'LINDI','KILWA','KANDAWALE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,1,211,'LINDI','KILWA','KIBATA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,1,171,'LINDI','KILWA','KIKOLE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,1,41,'LINDI','KILWA','KINJUMBI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,1,61,'LINDI','KILWA','KIPATIMU')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,1,91,'LINDI','KILWA','KIRANJERANJE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,1,71,'LINDI','KILWA','KIVINJE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,1,151,'LINDI','KILWA','LIHIMALYAO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,1,111,'LINDI','KILWA','LIKAWAGE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,1,121,'LINDI','KILWA','MANDAWA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,1,202,'LINDI','KILWA','MASOKO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,1,101,'LINDI','KILWA','MIGURUWE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,1,31,'LINDI','KILWA','MINGUMBI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,1,21,'LINDI','KILWA','MITEJA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,1,91,'LINDI','KILWA','MITOLE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,1,251,'LINDI','KILWA','NAMAYUNI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,1,121,'LINDI','KILWA','NANJIRINJI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,1,81,'LINDI','KILWA','NJINJO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,1,161,'LINDI','KILWA','PANDE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,1,73,'LINDI','KILWA','SOMANGA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,1,191,'LINDI','KILWA','SONGOSONGO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,1,11,'LINDI','KILWA','TINGI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,2,201,'LINDI','LINDI RURAL','CHIPONDA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,2,51,'LINDI','LINDI RURAL','KILANGALA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,2,41,'LINDI','LINDI RURAL','KILOLAMBWANI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,2,21,'LINDI','LINDI RURAL','KITOMANGA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,2,63,'LINDI','LINDI RURAL','KIWALALA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,2,251,'LINDI','LINDI RURAL','KIWAWA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,2,221,'LINDI','LINDI RURAL','LONGA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,2,163,'LINDI','LINDI RURAL','MAJENGO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,2,181,'LINDI','LINDI RURAL','MANDWANGA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,2,271,'LINDI','LINDI RURAL','MATIMBA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,2,31,'LINDI','LINDI RURAL','MCHINGA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,2,241,'LINDI','LINDI RURAL','MILOLA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,2,11,'LINDI','LINDI RURAL','MIPINGO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,2,191,'LINDI','LINDI RURAL','MNARA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,2,81,'LINDI','LINDI RURAL','MNOLELA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,2,113,'LINDI','LINDI RURAL','MTAMA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,2,211,'LINDI','LINDI RURAL','MTUA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,2,261,'LINDI','LINDI RURAL','MTUMBYA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,2,151,'LINDI','LINDI RURAL','MVULENI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,2,101,'LINDI','LINDI RURAL','NACHUNYU')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,2,161,'LINDI','LINDI RURAL','NAHUKAHUKA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,2,301,'LINDI','LINDI RURAL','NAMANGALE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,2,131,'LINDI','LINDI RURAL','NAMUPA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,2,281,'LINDI','LINDI RURAL','NANGARU')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,2,71,'LINDI','LINDI RURAL','NAVANGA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,2,171,'LINDI','LINDI RURAL','NYANGAMARA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,2,123,'LINDI','LINDI RURAL','NYANGAO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,2,141,'LINDI','LINDI RURAL','NYENGEDI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,2,211,'LINDI','LINDI RURAL','PANGATENA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,2,231,'LINDI','LINDI RURAL','RUTAMBA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,2,91,'LINDI','LINDI RURAL','SUDI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,6,172,'LINDI','LINDI URBAN','CHIKONJI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,6,112,'LINDI','LINDI URBAN','KITUMBIKWELA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,6,142,'LINDI','LINDI URBAN','MINGOYO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,6,191,'LINDI','LINDI URBAN','MNAZIMMOJA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,6,132,'LINDI','LINDI URBAN','MSINJAHILI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,6,152,'LINDI','LINDI URBAN','NG''APA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,6,52,'LINDI','LINDI URBAN','RAHALEO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,6,102,'LINDI','LINDI URBAN','RASBURA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,6,162,'LINDI','LINDI URBAN','TANDANGONGORO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,6,82,'LINDI','LINDI URBAN','WAILES')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,4,61,'LINDI','LIWALE','BARIKIWA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,4,101,'LINDI','LIWALE','KIANGARA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,4,111,'LINDI','LIWALE','KIBUTUKA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,4,191,'LINDI','LIWALE','KICHONDA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,4,91,'LINDI','LIWALE','KIMAMBI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,4,111,'LINDI','LIWALE','LIKONGOWELE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,4,201,'LINDI','LIWALE','LILOMBE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,4,151,'LINDI','LIWALE','LIWALE ''B''')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,4,13,'LINDI','LIWALE','LIWALE MJINI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,4,51,'LINDI','LIWALE','MAKATA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,4,161,'LINDI','LIWALE','MANGIRIKITI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,4,81,'LINDI','LIWALE','MBAYA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,4,21,'LINDI','LIWALE','MIHUMO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,4,141,'LINDI','LIWALE','MIRUI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,4,71,'LINDI','LIWALE','MKUTANO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,4,41,'LINDI','LIWALE','MLEMBWE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,4,131,'LINDI','LIWALE','MPIGAMITI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,4,61,'LINDI','LIWALE','NANGANDO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,4,121,'LINDI','LIWALE','NANGANO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,4,31,'LINDI','LIWALE','NGONGOWELE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,3,141,'LINDI','NACHINGWEA','CHIOLA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,3,121,'LINDI','NACHINGWEA','KIEGEI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,3,101,'LINDI','NACHINGWEA','KILIMA RONDO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,3,23,'LINDI','NACHINGWEA','KILIMANIHEWA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,3,61,'LINDI','NACHINGWEA','KIPARA MNERO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,3,61,'LINDI','NACHINGWEA','KIPARA MTUA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,3,71,'LINDI','NACHINGWEA','LIONJA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,3,241,'LINDI','NACHINGWEA','MARAMBO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,3,231,'LINDI','NACHINGWEA','MATEKWE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,3,111,'LINDI','NACHINGWEA','MBONDO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,3,31,'LINDI','NACHINGWEA','MCHONDA ')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,3,41,'LINDI','NACHINGWEA','MINERO MIEMBENI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,3,221,'LINDI','NACHINGWEA','MINERONGONGO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,3,91,'LINDI','NACHINGWEA','MITUMBATI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,3,131,'LINDI','NACHINGWEA','MKOKA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,3,171,'LINDI','NACHINGWEA','MKOTOKUYANA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,3,151,'LINDI','NACHINGWEA','MPIRUKA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,3,211,'LINDI','NACHINGWEA','MTUA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,3,163,'LINDI','NACHINGWEA','NACHINGWEA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,3,183,'LINDI','NACHINGWEA','NAIPANGA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,3,201,'LINDI','NACHINGWEA','NAIPINGO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,3,51,'LINDI','NACHINGWEA','NAMAPWIA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,3,251,'LINDI','NACHINGWEA','NAMATULA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,3,12,'LINDI','NACHINGWEA','NAMBAMBO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,3,81,'LINDI','NACHINGWEA','NAMIKANGO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,3,281,'LINDI','NACHINGWEA','NANGONDO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,3,161,'LINDI','NACHINGWEA','NANGOWE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,3,91,'LINDI','NACHINGWEA','NDITI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,3,261,'LINDI','NACHINGWEA','NDOMONI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,3,103,'LINDI','NACHINGWEA','NGUNICHILE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,3,31,'LINDI','NACHINGWEA','RUPONDA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,3,191,'LINDI','NACHINGWEA','STESHENI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,3,101,'LINDI','NACHINGWEA','UGAWAJI ')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,5,181,'LINDI','RUANGWA','CHIBULA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,5,61,'LINDI','RUANGWA','CHIENJELE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,5,211,'LINDI','RUANGWA','CHINONGWE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,5,141,'LINDI','RUANGWA','CHUNYU')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,5,101,'LINDI','RUANGWA','LIKUNJA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,5,51,'LINDI','RUANGWA','LUCHELEGWA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,5,91,'LINDI','RUANGWA','MAKANJIRO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,5,132,'LINDI','RUANGWA','MALOLO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,5,151,'LINDI','RUANGWA','MANDARAWE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,5,121,'LINDI','RUANGWA','MANDAWA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,5,171,'LINDI','RUANGWA','MATAMBARALE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,5,23,'LINDI','RUANGWA','MBEKENYERA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,5,81,'LINDI','RUANGWA','MBWEMKULU')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,5,111,'LINDI','RUANGWA','MNACHO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,5,163,'LINDI','RUANGWA','NACHINGWEA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,5,131,'LINDI','RUANGWA','NAMBILANJE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,5,71,'LINDI','RUANGWA','NAMICHIGA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,5,191,'LINDI','RUANGWA','NANDAGALA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,5,201,'LINDI','RUANGWA','NANGANGA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,5,81,'LINDI','RUANGWA','NARUNG''OMBE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,5,33,'LINDI','RUANGWA','NKOWE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(8,5,13,'LINDI','RUANGWA','RUANGWA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,4,243,'MARA','BUNDA DC','BALILI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,4,93,'MARA','BUNDA DC','BUNDA MJINI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,4,253,'MARA','BUNDA DC','BUNDA STOO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,4,111,'MARA','BUNDA DC','BUTIMBA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,4,221,'MARA','BUNDA DC','CHITENGULE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,4,101,'MARA','BUNDA DC','GUTA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,4,51,'MARA','BUNDA DC','HUNYARI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,4,161,'MARA','BUNDA DC','IGUNDU')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,4,171,'MARA','BUNDA DC','IRAMBA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,4,201,'MARA','BUNDA DC','KABASA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,4,231,'MARA','BUNDA DC','KASUGUTI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,4,281,'MARA','BUNDA DC','KETARE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,4,133,'MARA','BUNDA DC','KIBARA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,4,151,'MARA','BUNDA DC','KISORYA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,4,81,'MARA','BUNDA DC','KUNZUGU')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,4,61,'MARA','BUNDA DC','MCHARO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,4,31,'MARA','BUNDA DC','MIHINGO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,4,41,'MARA','BUNDA DC','MUGETA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,4,131,'MARA','BUNDA DC','NAMHULA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,4,211,'MARA','BUNDA DC','NAMPINDI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,4,141,'MARA','BUNDA DC','NANSIMO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,4,121,'MARA','BUNDA DC','NERUMA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,4,271,'MARA','BUNDA DC','NYAMANG''UTA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,4,141,'MARA','BUNDA DC','NYAMIHYOLO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,4,13,'MARA','BUNDA DC','NYAMUSWA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,4,263,'MARA','BUNDA DC','NYASURA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,4,21,'MARA','BUNDA DC','SALAMA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,4,71,'MARA','BUNDA DC','SAZIRA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,4,191,'MARA','BUNDA DC','WARIKU')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,7,171,'MARA','BUTIAMA','BISUMWA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,7,51,'MARA','BUTIAMA','BUHEMBA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,7,181,'MARA','BUTIAMA','BUKABWA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,7,121,'MARA','BUTIAMA','BURUMA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,7,201,'MARA','BUTIAMA','BUSEGWE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,7,21,'MARA','BUTIAMA','BUSWAHILI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,7,83,'MARA','BUTIAMA','BUTIAMA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,7,191,'MARA','BUTIAMA','BUTUGURI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,7,11,'MARA','BUTIAMA','BWIREGI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,7,81,'MARA','BUTIAMA','KAMUGEGI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,7,113,'MARA','BUTIAMA','KUKIRANGO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,7,101,'MARA','BUTIAMA','KYANYARI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,7,91,'MARA','BUTIAMA','MASABA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,7,61,'MARA','BUTIAMA','MIRWA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,7,71,'MARA','BUTIAMA','MURIAZA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,7,173,'MARA','BUTIAMA','NYAKANGA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,7,31,'MARA','BUTIAMA','NYAMIMANGE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,7,41,'MARA','BUTIAMA','SIRORISIMBA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,23,'MARA','MUSOMA DC','BUGOJI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,71,'MARA','MUSOMA DC','BUGWEMA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,51,'MARA','MUSOMA DC','BUKIMA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,11,'MARA','MUSOMA DC','BUKUMI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,111,'MARA','MUSOMA DC','BULINGA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,131,'MARA','MUSOMA DC','BUSAMBARA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,31,'MARA','MUSOMA DC','BWASI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,141,'MARA','MUSOMA DC','ETARO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,241,'MARA','MUSOMA DC','IFULIFU')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,121,'MARA','MUSOMA DC','KIRIBA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,21,'MARA','MUSOMA DC','MAKOJO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,143,'MARA','MUSOMA DC','MUGANGO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,61,'MARA','MUSOMA DC','MURANGI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,41,'MARA','MUSOMA DC','MUSANJA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,131,'MARA','MUSOMA DC','NYAKATENDE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,91,'MARA','MUSOMA DC','NYAMBONO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,201,'MARA','MUSOMA DC','NYAMURANDIRIRA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,151,'MARA','MUSOMA DC','NYEGINA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,132,'MARA','MUSOMA DC','RUSOLI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,101,'MARA','MUSOMA DC','SUGUTI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,111,'MARA','MUSOMA DC','TEGERUKA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,122,'MARA','MUSOMA MC','BUHARE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,62,'MARA','MUSOMA MC','BWERI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,32,'MARA','MUSOMA MC','IRINGO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,92,'MARA','MUSOMA MC','KAMUNYONGE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,82,'MARA','MUSOMA MC','KIGERA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,42,'MARA','MUSOMA MC','KITAJI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,191,'MARA','MUSOMA MC','KWANGWA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,132,'MARA','MUSOMA MC','MAKOKO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,61,'MARA','MUSOMA MC','MSHIKAMANO ')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,12,'MARA','MUSOMA MC','MUKENDO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,22,'MARA','MUSOMA MC','MWIGOBERO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,112,'MARA','MUSOMA MC','MWISENGE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,72,'MARA','MUSOMA MC','NYAKATO ')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,102,'MARA','MUSOMA MC','NYAMATARE ')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,52,'MARA','MUSOMA MC','NYASHO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,231,'MARA','MUSOMA MC','RWAMLIMI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,6,172,'MARA','RORYA','BARAKI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,6,81,'MARA','RORYA','BUKURA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,6,151,'MARA','RORYA','BUKWE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,6,121,'MARA','RORYA','GORIBE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,6,131,'MARA','RORYA','IKOMA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,6,11,'MARA','RORYA','KIGUNGA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,6,171,'MARA','RORYA','KINYENCHE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,6,21,'MARA','RORYA','KIROGO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,6,181,'MARA','RORYA','KISUMWA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,6,101,'MARA','RORYA','KITEMBE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,6,191,'MARA','RORYA','KOMUGE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,6,143,'MARA','RORYA','KORYO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,6,171,'MARA','RORYA','KYANGASAGA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,6,211,'MARA','RORYA','KYANG''OMBE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,6,111,'MARA','RORYA','MIRARE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,6,63,'MARA','RORYA','MKOMA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,6,201,'MARA','RORYA','NYABURONGO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,6,51,'MARA','RORYA','NYAHONGO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,6,41,'MARA','RORYA','NYAMAGARO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,6,31,'MARA','RORYA','NYAMTINGA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,6,201,'MARA','RORYA','NYAMUNGA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,6,161,'MARA','RORYA','NYATHOROGO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,6,171,'MARA','RORYA','RABOUR')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,6,52,'MARA','RORYA','RARANYA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,6,91,'MARA','RORYA','ROCHE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,6,71,'MARA','RORYA','TAI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,21,'MARA','SERENGETI','BUSAWE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,261,'MARA','SERENGETI','GEITASAMO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,131,'MARA','SERENGETI','IKOMA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,101,'MARA','SERENGETI','ISENYE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,91,'MARA','SERENGETI','KEBANCHA BANCHA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,11,'MARA','SERENGETI','KENYAMONTA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,31,'MARA','SERENGETI','KISAKA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,81,'MARA','SERENGETI','KISANGURA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,171,'MARA','SERENGETI','KYAMBAHI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,71,'MARA','SERENGETI','MACHOCHWE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,201,'MARA','SERENGETI','MAGANGE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,191,'MARA','SERENGETI','MAJIMOTO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,161,'MARA','SERENGETI','MANCHIRA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,171,'MARA','SERENGETI','MATARE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,241,'MARA','SERENGETI','MBALIBALI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,273,'MARA','SERENGETI','MOROTONGA      ')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,221,'MARA','SERENGETI','MOSONGO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,92,'MARA','SERENGETI','MUGUMU')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,101,'MARA','SERENGETI','NAGUSI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,111,'MARA','SERENGETI','NATTA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,102,'MARA','SERENGETI','NYAMATARE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,141,'MARA','SERENGETI','NYAMBURETI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,151,'MARA','SERENGETI','NYAMOKO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,211,'MARA','SERENGETI','NYANSURURA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,131,'MARA','SERENGETI','RIGICHA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,51,'MARA','SERENGETI','RING''WANI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,61,'MARA','SERENGETI','RUNG''ABURE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,231,'MARA','SERENGETI','SEDECO ')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,252,'MARA','SERENGETI','STENDI KUU')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,2,281,'MARA','SERENGETI','UWANJA WA NDEGE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,1,141,'MARA','TARIME DC','BINAGI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,1,11,'MARA','TARIME DC','BUMERA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,1,141,'MARA','TARIME DC','GANYANGE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,1,101,'MARA','TARIME DC','GORONG''A')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,1,151,'MARA','TARIME DC','GWITIRYO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,1,301,'MARA','TARIME DC','ITIRYO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,1,121,'MARA','TARIME DC','KEMAMBO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,1,131,'MARA','TARIME DC','KIBASUKA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,1,221,'MARA','TARIME DC','KIORE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,1,211,'MARA','TARIME DC','KOMASWA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,1,191,'MARA','TARIME DC','KWIHANCHA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,1,191,'MARA','TARIME DC','MANGA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,1,201,'MARA','TARIME DC','MATONGO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,1,271,'MARA','TARIME DC','MBOGI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,1,81,'MARA','TARIME DC','MURIBA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,1,21,'MARA','TARIME DC','MWEMA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,1,51,'MARA','TARIME DC','NYAKONGA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,1,71,'MARA','TARIME DC','NYAMWAGA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,1,291,'MARA','TARIME DC','NYANSINCHA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,1,91,'MARA','TARIME DC','NYANUNGU')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,1,61,'MARA','TARIME DC','NYARERO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,1,111,'MARA','TARIME DC','NYAROKOBA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,1,41,'MARA','TARIME DC','PEMBA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,1,102,'MARA','TARIME DC','REGICHERI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,1,33,'MARA','TARIME DC','SIRARI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,1,11,'MARA','TARIME DC','SUSUNI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,1,162,'MARA','TARIME TC','BOMANI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,1,251,'MARA','TARIME TC','KENYAMANYORI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,1,281,'MARA','TARIME TC','KETARE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,1,61,'MARA','TARIME TC','NKENDE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,1,242,'MARA','TARIME TC','NYAMISANGURA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,1,171,'MARA','TARIME TC','NYANDOTO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,1,232,'MARA','TARIME TC','SABASABA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(20,1,153,'MARA','TARIME TC','TURWA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,5,171,'SHINYANGA','KAHAMA TOWNSHIP AUTHORITY','BUSOKA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,5,41,'SHINYANGA','KAHAMA TOWNSHIP AUTHORITY','ISAGEHE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,5,191,'SHINYANGA','KAHAMA TOWNSHIP AUTHORITY','IYENZE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,5,63,'SHINYANGA','KAHAMA TOWNSHIP AUTHORITY','KAGONGWA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,5,152,'SHINYANGA','KAHAMA TOWNSHIP AUTHORITY','KAHAMA MJINI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,5,181,'SHINYANGA','KAHAMA TOWNSHIP AUTHORITY','KILAGO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,5,31,'SHINYANGA','KAHAMA TOWNSHIP AUTHORITY','KINAGA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,5,163,'SHINYANGA','KAHAMA TOWNSHIP AUTHORITY','MAJENGO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,5,83,'SHINYANGA','KAHAMA TOWNSHIP AUTHORITY','MALUNGA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,5,93,'SHINYANGA','KAHAMA TOWNSHIP AUTHORITY','MHONGOLO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,5,123,'SHINYANGA','KAHAMA TOWNSHIP AUTHORITY','MHUNGULA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,5,51,'SHINYANGA','KAHAMA TOWNSHIP AUTHORITY','MONDO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,5,103,'SHINYANGA','KAHAMA TOWNSHIP AUTHORITY','MWENDAKULIMA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,5,11,'SHINYANGA','KAHAMA TOWNSHIP AUTHORITY','NGOGWA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,5,73,'SHINYANGA','KAHAMA TOWNSHIP AUTHORITY','NYAHANGA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,5,201,'SHINYANGA','KAHAMA TOWNSHIP AUTHORITY','NYANDEKWA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,5,143,'SHINYANGA','KAHAMA TOWNSHIP AUTHORITY','NYASUBI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,5,132,'SHINYANGA','KAHAMA TOWNSHIP AUTHORITY','NYIHOGO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,5,21,'SHINYANGA','KAHAMA TOWNSHIP AUTHORITY','WENDELE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,5,111,'SHINYANGA','KAHAMA TOWNSHIP AUTHORITY','ZONGOMERA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,21,'SHINYANGA','KISHAPU','BUBIKI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,11,'SHINYANGA','KISHAPU','BUNAMBIYU')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,81,'SHINYANGA','KISHAPU','BUSANGWA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,181,'SHINYANGA','KISHAPU','IDUKILO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,201,'SHINYANGA','KISHAPU','ITILIMA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,41,'SHINYANGA','KISHAPU','KILOLELI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,83,'SHINYANGA','KISHAPU','KISHAPU')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,141,'SHINYANGA','KISHAPU','LAGANA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,261,'SHINYANGA','KISHAPU','M/LOHUMBO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,201,'SHINYANGA','KISHAPU','MAGANZO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,131,'SHINYANGA','KISHAPU','MASANGA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,51,'SHINYANGA','KISHAPU','MONDO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,91,'SHINYANGA','KISHAPU','MWAKIPOYA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,121,'SHINYANGA','KISHAPU','MWAMALASA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,151,'SHINYANGA','KISHAPU','MWAMASHELE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,51,'SHINYANGA','KISHAPU','MWATAGA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,101,'SHINYANGA','KISHAPU','MWAWEJA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,161,'SHINYANGA','KISHAPU','NGOFILA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,41,'SHINYANGA','KISHAPU','SEKE BUGORO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,101,'SHINYANGA','KISHAPU','SHAGIHILU')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,111,'SHINYANGA','KISHAPU','SOMAGEDI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,33,'SHINYANGA','KISHAPU','SONGWA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,191,'SHINYANGA','KISHAPU','TALAGA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,71,'SHINYANGA','KISHAPU','UCHUNGA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,181,'SHINYANGA','KISHAPU','UKENYENGE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,23,'SHINYANGA','MSALALA','BUGARAMA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,111,'SHINYANGA','MSALALA','BULIGE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,13,'SHINYANGA','MSALALA','BULYANHULU')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,81,'SHINYANGA','MSALALA','BUSANGI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,71,'SHINYANGA','MSALALA','CHELA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,171,'SHINYANGA','MSALALA','IKINDA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,163,'SHINYANGA','MSALALA','ISAKA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,151,'SHINYANGA','MSALALA','JANA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,121,'SHINYANGA','MSALALA','KASHISHI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,31,'SHINYANGA','MSALALA','LUNGUYA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,61,'SHINYANGA','MSALALA','MEGA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,41,'SHINYANGA','MSALALA','MWAKATA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,141,'SHINYANGA','MSALALA','MWALUGULU')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,131,'SHINYANGA','MSALALA','MWANASE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,101,'SHINYANGA','MSALALA','NGAYA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,91,'SHINYANGA','MSALALA','NTOBO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,53,'SHINYANGA','MSALALA','SEGESE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,41,'SHINYANGA','MSALALA','SHILELA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,62,'SHINYANGA','SHINYANGA MC','CHAMAGUHA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,133,'SHINYANGA','SHINYANGA MC','CHIBE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,43,'SHINYANGA','SHINYANGA MC','IBADAKULI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,72,'SHINYANGA','SHINYANGA MC','IBINZAMATA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,122,'SHINYANGA','SHINYANGA MC','KAMBARAGE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,82,'SHINYANGA','SHINYANGA MC','KITANGILI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,91,'SHINYANGA','SHINYANGA MC','KIZUMBI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,21,'SHINYANGA','SHINYANGA MC','KOLANDOTO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,142,'SHINYANGA','SHINYANGA MC','LUBAGA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,162,'SHINYANGA','SHINYANGA MC','MASEKELO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,61,'SHINYANGA','SHINYANGA MC','MJINI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,11,'SHINYANGA','SHINYANGA MC','MWAMALILI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,101,'SHINYANGA','SHINYANGA MC','MWAWAZA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,112,'SHINYANGA','SHINYANGA MC','NDALA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,152,'SHINYANGA','SHINYANGA MC','NDEMBEZI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,32,'SHINYANGA','SHINYANGA MC','NGOKOLO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,2,173,'SHINYANGA','SHINYANGA MC','OLD SHINYANGA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,3,201,'SHINYANGA','SHINYANGA RURAL','BUKENE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,3,41,'SHINYANGA','SHINYANGA RURAL','DIDIA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,3,31,'SHINYANGA','SHINYANGA RURAL','ILOLA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,3,11,'SHINYANGA','SHINYANGA RURAL','IMESELA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,3,101,'SHINYANGA','SHINYANGA RURAL','ISELAMAGAZI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,3,51,'SHINYANGA','SHINYANGA RURAL','ITWANGI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,3,111,'SHINYANGA','SHINYANGA RURAL','LYABUKANDE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,3,231,'SHINYANGA','SHINYANGA RURAL','LYABUSALU''A''')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,3,231,'SHINYANGA','SHINYANGA RURAL','LYABUSALU''B''')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,3,261,'SHINYANGA','SHINYANGA RURAL','LYAMIDATI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,3,211,'SHINYANGA','SHINYANGA RURAL','MASENGWA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,3,231,'SHINYANGA','SHINYANGA RURAL','MWAKITOLYO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,3,241,'SHINYANGA','SHINYANGA RURAL','MWALUKWA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,3,141,'SHINYANGA','SHINYANGA RURAL','MWAMALA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,3,121,'SHINYANGA','SHINYANGA RURAL','MWANTINI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,3,221,'SHINYANGA','SHINYANGA RURAL','MWENGE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,3,191,'SHINYANGA','SHINYANGA RURAL','NSALALA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,3,251,'SHINYANGA','SHINYANGA RURAL','NYAMALOGO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,3,181,'SHINYANGA','SHINYANGA RURAL','NYIDA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,3,131,'SHINYANGA','SHINYANGA RURAL','PANDAGICHIZA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,3,171,'SHINYANGA','SHINYANGA RURAL','PUNI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,3,81,'SHINYANGA','SHINYANGA RURAL','SALAWE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,3,151,'SHINYANGA','SHINYANGA RURAL','SAMUYE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,3,91,'SHINYANGA','SHINYANGA RURAL','SOLWA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,3,63,'SHINYANGA','SHINYANGA RURAL','TINDE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,3,161,'SHINYANGA','SHINYANGA RURAL','USANDA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,3,21,'SHINYANGA','SHINYANGA RURAL','USULE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,4,211,'SHINYANGA','USHETU','BUKOMELA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,4,291,'SHINYANGA','USHETU','BULUNGWA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,4,181,'SHINYANGA','USHETU','CHAMBO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,4,171,'SHINYANGA','USHETU','CHONA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,4,281,'SHINYANGA','USHETU','IDAHINA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,4,231,'SHINYANGA','USHETU','IGUNDA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,4,251,'SHINYANGA','USHETU','IGWAMANONI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,4,241,'SHINYANGA','USHETU','KINAMAPULA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,4,191,'SHINYANGA','USHETU','KISUKE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,4,201,'SHINYANGA','USHETU','MAPAMBA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,4,261,'SHINYANGA','USHETU','MPUNZE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,4,141,'SHINYANGA','USHETU','NYAMILANGANO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,4,301,'SHINYANGA','USHETU','NYANKENDE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,4,271,'SHINYANGA','USHETU','SABASABINI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,4,11,'SHINYANGA','USHETU','UBAGWE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,4,221,'SHINYANGA','USHETU','UKUNE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,4,311,'SHINYANGA','USHETU','ULEWE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,4,343,'SHINYANGA','USHETU','ULOWA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,4,321,'SHINYANGA','USHETU','USHETU')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(17,4,331,'SHINYANGA','USHETU','UYOGO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,1,243,'SIMIYU','BARIADI DC','BANEMHI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,1,183,'SIMIYU','BARIADI DC','DUTWA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,1,141,'SIMIYU','BARIADI DC','GAMBOSI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,1,281,'SIMIYU','BARIADI DC','GIBISHI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,1,211,'SIMIYU','BARIADI DC','GILYA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,1,101,'SIMIYU','BARIADI DC','IHUSI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,1,161,'SIMIYU','BARIADI DC','IKUNGULYABASHASHI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,1,161,'SIMIYU','BARIADI DC','ITUBUKILO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,1,131,'SIMIYU','BARIADI DC','KASOLI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,1,121,'SIMIYU','BARIADI DC','KILALO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,1,211,'SIMIYU','BARIADI DC','MASEWA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,1,201,'SIMIYU','BARIADI DC','MATONGO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,1,111,'SIMIYU','BARIADI DC','MWADOBANA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,1,171,'SIMIYU','BARIADI DC','MWASUBUYA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,1,21,'SIMIYU','BARIADI DC','MWAUBINGI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,1,41,'SIMIYU','BARIADI DC','MWAUMATONDO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,1,103,'SIMIYU','BARIADI DC','NGULYATI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,1,11,'SIMIYU','BARIADI DC','NKINDWABIYE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,1,33,'SIMIYU','BARIADI DC','NKOLOLO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,1,91,'SIMIYU','BARIADI DC','SAKWE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,1,191,'SIMIYU','BARIADI DC','SAPIWI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,1,172,'SIMIYU','BARIADI TC','BARADI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,1,233,'SIMIYU','BARIADI TC','BARIADI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,1,81,'SIMIYU','BARIADI TC','BUNAMHALA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,1,31,'SIMIYU','BARIADI TC','GUDUW')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,1,151,'SIMIYU','BARIADI TC','GUDUWI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,1,141,'SIMIYU','BARIADI TC','ISANGA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,1,243,'SIMIYU','BARIADI TC','MALAMBO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,1,111,'SIMIYU','BARIADI TC','MHANGO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,1,173,'SIMIYU','BARIADI TC','NYAKABINDI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,1,61,'SIMIYU','BARIADI TC','NYANGOKOLWA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,1,151,'SIMIYU','BARIADI TC','SANGA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,1,253,'SIMIYU','BARIADI TC','SIMA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,1,73,'SIMIYU','BARIADI TC','SOMANDA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,5,21,'SIMIYU','BUSEGA','BADUGU')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,5,131,'SIMIYU','BUSEGA','IGALUKILO ''A''')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,5,131,'SIMIYU','BUSEGA','IGALUKILO ''B''')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,5,131,'SIMIYU','BUSEGA','IGALUKILO'' B''')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,5,141,'SIMIYU','BUSEGA','IMALAMATE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,5,63,'SIMIYU','BUSEGA','KABITA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,5,73,'SIMIYU','BUSEGA','KALEMELA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,5,41,'SIMIYU','BUSEGA','KILOLELI ''A''')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,5,41,'SIMIYU','BUSEGA','KILOLELI ''B''')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,5,83,'SIMIYU','BUSEGA','LAMADI ''A''')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,5,83,'SIMIYU','BUSEGA','LAMADI ''B''')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,5,91,'SIMIYU','BUSEGA','LUTUBIGA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,5,121,'SIMIYU','BUSEGA','MALILI ''A''')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,5,121,'SIMIYU','BUSEGA','MALILI ''B''')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,5,101,'SIMIYU','BUSEGA','MKULA ''A''')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,5,101,'SIMIYU','BUSEGA','MKULA ''B''')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,5,51,'SIMIYU','BUSEGA','MWAMANYILI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,5,111,'SIMIYU','BUSEGA','NGASAMO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,5,31,'SIMIYU','BUSEGA','NYALUHANDE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,5,111,'SIMIYU','BUSEGA','NYASHIMO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,5,11,'SIMIYU','BUSEGA','SHIGALA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,2,121,'SIMIYU','ITILIMA','BUDALABUJIGA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,2,11,'SIMIYU','ITILIMA','BUMERA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,2,91,'SIMIYU','ITILIMA','CHINAMILI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,2,171,'SIMIYU','ITILIMA','IKINDILO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,2,31,'SIMIYU','ITILIMA','KINAG''WELI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,2,111,'SIMIYU','ITILIMA','LAGANGABILILI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,2,173,'SIMIYU','ITILIMA','LUGURU')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,2,211,'SIMIYU','ITILIMA','MBITA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,2,71,'SIMIYU','ITILIMA','MHUNZE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,2,81,'SIMIYU','ITILIMA','MIGATO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,2,153,'SIMIYU','ITILIMA','MWAMAPALALA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,2,31,'SIMIYU','ITILIMA','MWAMTANI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,2,51,'SIMIYU','ITILIMA','MWASWALE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,2,91,'SIMIYU','ITILIMA','NDOLELEJI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,2,71,'SIMIYU','ITILIMA','NGWALUSHU')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,2,181,'SIMIYU','ITILIMA','NHOBORA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,2,21,'SIMIYU','ITILIMA','NKOMA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,2,61,'SIMIYU','ITILIMA','NKUYU')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,2,161,'SIMIYU','ITILIMA','NYAMALAPA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,2,41,'SIMIYU','ITILIMA','SAGATA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,2,221,'SIMIYU','ITILIMA','SAWIDA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,2,191,'SIMIYU','ITILIMA','ZAGAYU')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,4,241,'SIMIYU','MASWA','BADI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,4,263,'SIMIYU','MASWA','BINZA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,4,171,'SIMIYU','MASWA','BUCHAMBI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,4,101,'SIMIYU','MASWA','BUDEKWA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,4,23,'SIMIYU','MASWA','BUGARAMA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,4,81,'SIMIYU','MASWA','BUSANGI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,4,111,'SIMIYU','MASWA','BUSILILI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,4,81,'SIMIYU','MASWA','DAKAMA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,4,31,'SIMIYU','MASWA','IPILILO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,4,141,'SIMIYU','MASWA','ISANGA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,4,91,'SIMIYU','MASWA','JIJA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,4,181,'SIMIYU','MASWA','KADOTO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,4,221,'SIMIYU','MASWA','KULIMI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,4,93,'SIMIYU','MASWA','LALAGO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,4,233,'SIMIYU','MASWA','MALAMPAKA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,4,131,'SIMIYU','MASWA','MASELA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,4,81,'SIMIYU','MASWA','MATABA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,4,182,'SIMIYU','MASWA','MBARAGANE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,4,71,'SIMIYU','MASWA','MPINDO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,4,173,'SIMIYU','MASWA','MWABARATURU')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,4,173,'SIMIYU','MASWA','MWABAYANDA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,4,51,'SIMIYU','MASWA','MWAMANENGE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,4,161,'SIMIYU','MASWA','MWAMASHIMBA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,4,152,'SIMIYU','MASWA','MWANGHONOLI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,4,21,'SIMIYU','MASWA','NGULIGULI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,4,11,'SIMIYU','MASWA','NG''WIGWA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,4,201,'SIMIYU','MASWA','NYABUBINZA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,4,253,'SIMIYU','MASWA','NYALIKUNGU')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,4,151,'SIMIYU','MASWA','SANGAMWALUGESHA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,4,41,'SIMIYU','MASWA','SENANI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,4,121,'SIMIYU','MASWA','SENG''WA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,4,101,'SIMIYU','MASWA','SHANWA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,4,191,'SIMIYU','MASWA','SHISHIYU')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,4,281,'SIMIYU','MASWA','SOLA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,4,61,'SIMIYU','MASWA','SUKUMA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,4,151,'SIMIYU','MASWA','ZANZUI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,3,191,'SIMIYU','MEATU','BUKUNDI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,3,101,'SIMIYU','MEATU','IMALASEKO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,3,51,'SIMIYU','MEATU','ITINJE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,3,31,'SIMIYU','MEATU','KIMALI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,3,61,'SIMIYU','MEATU','KISESA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,3,81,'SIMIYU','MEATU','LINGEKA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,3,161,'SIMIYU','MEATU','LUBIGA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,3,141,'SIMIYU','MEATU','MWABUMA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,3,151,'SIMIYU','MEATU','MWABUSALU')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,3,111,'SIMIYU','MEATU','MWABUZO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,3,231,'SIMIYU','MEATU','MWAKISANDU')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,3,121,'SIMIYU','MEATU','MWAMALOLE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,3,211,'SIMIYU','MEATU','MWAMANIMBA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,3,171,'SIMIYU','MEATU','MWAMANONGU')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,3,41,'SIMIYU','MEATU','MWAMISHALI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,3,71,'SIMIYU','MEATU','MWANDOYA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,3,231,'SIMIYU','MEATU','MWANGUDO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,3,13,'SIMIYU','MEATU','MWANHUZI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,3,131,'SIMIYU','MEATU','MWANJOLO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,3,251,'SIMIYU','MEATU','MWANYAHINA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,3,201,'SIMIYU','MEATU','MWASENGELA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,3,181,'SIMIYU','MEATU','NGHOBOKO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,3,21,'SIMIYU','MEATU','NKOMA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,3,91,'SIMIYU','MEATU','SAKASAKA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(24,3,221,'SIMIYU','MEATU','TINDABULIGI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,2,31,'TABORA','IGUNGA DC','BUKOKO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,2,181,'TABORA','IGUNGA DC','CHAMBUTWA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,2,163,'TABORA','IGUNGA DC','CHOMA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,2,101,'TABORA','IGUNGA DC','IBOROGERO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,2,211,'TABORA','IGUNGA DC','IGOWEKO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,2,13,'TABORA','IGUNGA DC','IGUNGA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,2,103,'TABORA','IGUNGA DC','IGURUBI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,2,41,'TABORA','IGUNGA DC','ISAKAMALIWA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,2,21,'TABORA','IGUNGA DC','ITUMBA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,2,141,'TABORA','IGUNGA DC','ITUNDURU')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,2,241,'TABORA','IGUNGA DC','KININGINILA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,2,121,'TABORA','IGUNGA DC','KINUNGU')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,2,82,'TABORA','IGUNGA DC','KITANGILI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,2,251,'TABORA','IGUNGA DC','LUGUBU')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,2,81,'TABORA','IGUNGA DC','MBUTU')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,2,261,'TABORA','IGUNGA DC','MTUNGULU')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,2,141,'TABORA','IGUNGA DC','MWAMAKONA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,2,141,'TABORA','IGUNGA DC','MWAMALA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,2,151,'TABORA','IGUNGA DC','MWAMASHIGA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,2,161,'TABORA','IGUNGA DC','MWAMASHIMBA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,2,171,'TABORA','IGUNGA DC','MWASHIKU')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,2,241,'TABORA','IGUNGA DC','MWISI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,2,61,'TABORA','IGUNGA DC','NANGA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,2,152,'TABORA','IGUNGA DC','NDEMBEZI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,2,211,'TABORA','IGUNGA DC','NGULU')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,2,71,'TABORA','IGUNGA DC','NGUVUMOJA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,2,201,'TABORA','IGUNGA DC','NKINGA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,2,91,'TABORA','IGUNGA DC','NTOBO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,2,201,'TABORA','IGUNGA DC','NYANDEKWA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,2,71,'TABORA','IGUNGA DC','SIMBO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,2,261,'TABORA','IGUNGA DC','SUNGWIZI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,2,191,'TABORA','IGUNGA DC','TAMBALALE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,2,81,'TABORA','IGUNGA DC','UGAKA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,2,171,'TABORA','IGUNGA DC','USWAYA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,2,181,'TABORA','IGUNGA DC','ZIBA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,7,161,'TABORA','KALIUA DC','ICHEMBA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,7,71,'TABORA','KALIUA DC','IGALALA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,7,211,'TABORA','KALIUA DC','IGOMBE MKULU')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,7,101,'TABORA','KALIUA DC','IGWISI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,7,61,'TABORA','KALIUA DC','ILEGE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,7,101,'TABORA','KALIUA DC','IMALAUPINA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,7,53,'TABORA','KALIUA DC','KALIUA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,7,81,'TABORA','KALIUA DC','KAMSEKWA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,7,191,'TABORA','KALIUA DC','KANINDO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,7,181,'TABORA','KALIUA DC','KANOGE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,7,121,'TABORA','KALIUA DC','KASHISHI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,7,91,'TABORA','KALIUA DC','KAZAROHO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,7,191,'TABORA','KALIUA DC','KONANNE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,7,151,'TABORA','KALIUA DC','MAKINGI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,7,201,'TABORA','KALIUA DC','MILAMBO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,7,171,'TABORA','KALIUA DC','MWONGOZO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,7,71,'TABORA','KALIUA DC','NG''WANDE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,7,141,'TABORA','KALIUA DC','SASU')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,7,151,'TABORA','KALIUA DC','SELELI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,7,121,'TABORA','KALIUA DC','SILAMBO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,7,341,'TABORA','KALIUA DC','UFUKUTWA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,7,41,'TABORA','KALIUA DC','UGUNGA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,7,11,'TABORA','KALIUA DC','UKUMBI SIGANGA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,7,161,'TABORA','KALIUA DC','USENYE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,7,31,'TABORA','KALIUA DC','USHOKOLA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,7,31,'TABORA','KALIUA DC','USIMBA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,7,61,'TABORA','KALIUA DC','USINGE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,7,111,'TABORA','KALIUA DC','UYOWA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,7,21,'TABORA','KALIUA DC','ZUGIMLOLE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,1,31,'TABORA','NZEGA DC','BUDUSHI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,1,201,'TABORA','NZEGA DC','BUKENE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,1,261,'TABORA','NZEGA DC','IGUSULE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,1,371,'TABORA','NZEGA DC','IKINDWA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,1,361,'TABORA','NZEGA DC','ISAGENHE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,1,211,'TABORA','NZEGA DC','ISANZU')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,1,221,'TABORA','NZEGA DC','ITOBO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,1,11,'TABORA','NZEGA DC','KAMANHALANGA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,1,291,'TABORA','NZEGA DC','KARITU')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,1,281,'TABORA','NZEGA DC','KASELA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,1,191,'TABORA','NZEGA DC','LUSU')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,1,81,'TABORA','NZEGA DC','MAGENGATI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,1,321,'TABORA','NZEGA DC','MAMBALI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,1,231,'TABORA','NZEGA DC','MBAGWA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,1,81,'TABORA','NZEGA DC','MBUTU')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,1,71,'TABORA','NZEGA DC','MILAMBO ITOBO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,1,61,'TABORA','NZEGA DC','MIZIBAZIBA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,1,311,'TABORA','NZEGA DC','MOGWA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,1,151,'TABORA','NZEGA DC','MUHUGI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,1,41,'TABORA','NZEGA DC','MWAKASHANHALA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,1,141,'TABORA','NZEGA DC','MWAMALA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,1,31,'TABORA','NZEGA DC','MWAMTUNDU')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,1,231,'TABORA','NZEGA DC','MWANGOYE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,1,251,'TABORA','NZEGA DC','MWASALA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,1,201,'TABORA','NZEGA DC','NATA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,1,112,'TABORA','NZEGA DC','NDALA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,1,21,'TABORA','NZEGA DC','NKINIZIWA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,1,11,'TABORA','NZEGA DC','PUGE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,1,351,'TABORA','NZEGA DC','SEMEMBELA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,1,271,'TABORA','NZEGA DC','SHIGAMBA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,1,241,'TABORA','NZEGA DC','SIGILI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,1,51,'TABORA','NZEGA DC','TONGI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,1,341,'TABORA','NZEGA DC','UDUKA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,1,101,'TABORA','NZEGA DC','UGEMBE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,1,161,'TABORA','NZEGA DC','UTWIGU')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,1,111,'TABORA','NZEGA DC','WELA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,1,171,'TABORA','NZEGA TOWN','IJANIJA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,1,141,'TABORA','NZEGA TOWN','ITILO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,1,82,'TABORA','NZEGA TOWN','KITANGILI ')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,1,121,'TABORA','NZEGA TOWN','MBOGWE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,1,131,'TABORA','NZEGA TOWN','MIGUWA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,1,251,'TABORA','NZEGA TOWN','MWANZOLI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,1,51,'TABORA','NZEGA TOWN','NZEGA MAGHARIBI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,1,51,'TABORA','NZEGA TOWN','NZEGA MASHARIKI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,1,102,'TABORA','NZEGA TOWN','NZEGA NDOGO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,1,11,'TABORA','NZEGA TOWN','UCHAMA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,5,21,'TABORA','SIKONGE DC','CHABUTWA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,5,61,'TABORA','SIKONGE DC','IGIGWA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,5,111,'TABORA','SIKONGE DC','IPOLE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,5,41,'TABORA','SIKONGE DC','KILOLELI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,5,81,'TABORA','SIKONGE DC','KILOLI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,5,81,'TABORA','SIKONGE DC','KILUMBI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,5,41,'TABORA','SIKONGE DC','KIPANGA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,5,91,'TABORA','SIKONGE DC','KIPILI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,5,131,'TABORA','SIKONGE DC','KISANGA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,5,71,'TABORA','SIKONGE DC','KITUNDA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,5,141,'TABORA','SIKONGE DC','MISHENI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,5,131,'TABORA','SIKONGE DC','MKOLYE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,5,151,'TABORA','SIKONGE DC','MOLE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,5,151,'TABORA','SIKONGE DC','MPOMBWE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,5,121,'TABORA','SIKONGE DC','NGOYWA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,5,51,'TABORA','SIKONGE DC','NYAHUA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,5,101,'TABORA','SIKONGE DC','PANGALE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,5,53,'TABORA','SIKONGE DC','SIKONGE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,5,191,'TABORA','SIKONGE DC','TUMBILI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,5,11,'TABORA','SIKONGE DC','TUTUO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,5,171,'TABORA','SIKONGE DC','USUNGA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,5,42,'TABORA','TABORA MUNICIPAL','CHEMCHEM')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,5,102,'TABORA','TABORA MUNICIPAL','CHEYO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,5,22,'TABORA','TABORA MUNICIPAL','GONGONI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,5,241,'TABORA','TABORA MUNICIPAL','IFUCHA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,5,231,'TABORA','TABORA MUNICIPAL','IKOMWA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,5,92,'TABORA','TABORA MUNICIPAL','IPULI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,5,82,'TABORA','TABORA MUNICIPAL','ISEVYA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,5,181,'TABORA','TABORA MUNICIPAL','ITETEMIA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,5,161,'TABORA','TABORA MUNICIPAL','ITONJANDA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,5,91,'TABORA','TABORA MUNICIPAL','K/CHEKUNDU')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,5,221,'TABORA','TABORA MUNICIPAL','KABILA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,5,141,'TABORA','TABORA MUNICIPAL','KAKOLA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,5,201,'TABORA','TABORA MUNICIPAL','KALUNDE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,5,12,'TABORA','TABORA MUNICIPAL','KANYENYE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,5,62,'TABORA','TABORA MUNICIPAL','KILOLENI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,5,112,'TABORA','TABORA MUNICIPAL','KITETE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,5,132,'TABORA','TABORA MUNICIPAL','MALOLO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,5,201,'TABORA','TABORA MUNICIPAL','MAPAMBANO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,5,32,'TABORA','TABORA MUNICIPAL','MBUGANI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,5,211,'TABORA','TABORA MUNICIPAL','MISHA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,5,171,'TABORA','TABORA MUNICIPAL','MPERA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,5,72,'TABORA','TABORA MUNICIPAL','MTENDENI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,5,22,'TABORA','TABORA MUNICIPAL','MWINYI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,5,171,'TABORA','TABORA MUNICIPAL','NDEVELWA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,5,121,'TABORA','TABORA MUNICIPAL','NG''AMBO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,5,251,'TABORA','TABORA MUNICIPAL','NTALIKWA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,5,11,'TABORA','TABORA MUNICIPAL','T/RELI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,5,191,'TABORA','TABORA MUNICIPAL','TUMBI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,5,151,'TABORA','TABORA MUNICIPAL','UYUI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,4,141,'TABORA','URAMBO DC','IMALAMAKOYE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,4,21,'TABORA','URAMBO DC','ITUNDU')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,4,31,'TABORA','URAMBO DC','KALOLENI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,4,11,'TABORA','URAMBO DC','KAPILULA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,4,131,'TABORA','URAMBO DC','KASISI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,4,251,'TABORA','URAMBO DC','KIYUNGI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,4,61,'TABORA','URAMBO DC','MCHIKICHINI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,4,41,'TABORA','URAMBO DC','MUUNGANO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,4,151,'TABORA','URAMBO DC','NSENDA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,4,51,'TABORA','URAMBO DC','SONGAMBELE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,4,101,'TABORA','URAMBO DC','UGALLA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,4,161,'TABORA','URAMBO DC','UKONDAMOYO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,4,23,'TABORA','URAMBO DC','URAMBO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,4,111,'TABORA','URAMBO DC','USISYA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,4,81,'TABORA','URAMBO DC','USSOKE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,4,331,'TABORA','URAMBO DC','UYOGO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,4,91,'TABORA','URAMBO DC','UYUMBU')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,4,31,'TABORA','URAMBO DC','VUMILIA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,3,111,'TABORA','UYUI DC','BUKUMBI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,3,31,'TABORA','UYUI DC','GOWEKO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,3,241,'TABORA','UYUI DC','IBELAMILUNDI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,3,101,'TABORA','UYUI DC','IBIRI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,3,31,'TABORA','UYUI DC','IGALULA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,3,231,'TABORA','UYUI DC','IGULUNGU')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,3,121,'TABORA','UYUI DC','IKONGOLO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,3,151,'TABORA','UYUI DC','ISIKIZYA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,3,151,'TABORA','UYUI DC','ISILA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,3,53,'TABORA','UYUI DC','KALOLA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,3,211,'TABORA','UYUI DC','KIGWA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,3,21,'TABORA','UYUI DC','KIZENGI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,3,291,'TABORA','UYUI DC','LOLANGULU')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,3,171,'TABORA','UYUI DC','LOYA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,3,11,'TABORA','UYUI DC','LUTENDE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,3,63,'TABORA','UYUI DC','MABAMA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,3,141,'TABORA','UYUI DC','MAGIRI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,3,151,'TABORA','UYUI DC','MAKAZI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,3,181,'TABORA','UYUI DC','MISWAKI')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,3,221,'TABORA','UYUI DC','MIYENZE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,3,111,'TABORA','UYUI DC','MMALE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,3,71,'TABORA','UYUI DC','NDONO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,3,231,'TABORA','UYUI DC','NSIMBO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,3,201,'TABORA','UYUI DC','NSOLOLO')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,3,181,'TABORA','UYUI DC','NZUBUKA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,3,161,'TABORA','UYUI DC','SHITAGE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,3,191,'TABORA','UYUI DC','TURA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,3,81,'TABORA','UYUI DC','UFULUMA')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,3,131,'TABORA','UYUI DC','UPUGE')");
            dbSET.execSQL("INSERT INTO set_geo_codes(g1, g2, g3, name1, name2, name3) VALUES(14,3,91,'TABORA','UYUI DC','USAGARI')");
        }
    }

}
