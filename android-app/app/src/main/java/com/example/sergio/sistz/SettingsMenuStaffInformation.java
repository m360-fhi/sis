package com.example.sergio.sistz;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.sergio.sistz.mysql.Conexion;
import com.example.sergio.sistz.util.DateUtility;
import com.example.sergio.sistz.util.toolsfncs;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Sergio on 3/13/2016.
 */
public class SettingsMenuStaffInformation extends Fragment implements View.OnClickListener{
    public static final String STATICS_ROOT = Environment.getExternalStorageDirectory() + File.separator + "sisdb";
    public static String Fullname = "";
    public static final String tmp_code="";
    public static int checked=0;
    ArrayList<String> list_1 = new ArrayList<>();
    ArrayList<String> list_code = new ArrayList<>();
    int fl_location = 1; // *********** Control change page
    FrameLayout  fl_part2; // ************ FrameLayout ***************
    TextView tv_col0;
    RadioGroup rgTeacherExit;
    EditText _col0, _col2, _col3, _col6, _col25, _col27, _col28, _col29;
    RadioButton _col1a, _col1b, _col1c, _col4a, _col4b, _col18a, _col18b, _col18c, _col18d, _col18e, _col18f, _col18g, _col18h, _col18i, _col19a, _col19b, _col19c, _col19d, _col26a, _col26b, _col26c, _col26d;
    RadioButton _col130a, _col130b, _col130c, _col130d, _col130e, _col130f;
    CheckBox _col7, _col8, _col9, _col10, _col11, _col12, _col13, _col14, _col15, _col16, _col17, _col20, _col21, _col22, _col23, _col24;
    DatePicker _col5, _col131;
    LinearLayout levels_teaching,teacher_subjects;
    ListView lv_list;
    String _IU="U", Dedo="0";
    FloatingActionButton add_reg, save_reg, erase_reg;
    String code;
    private DatePickerDialog fromDatePickerDialog;
    private SimpleDateFormat dateFormatter;
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat formatDisplay = new SimpleDateFormat("MMMM dd, yyyy  h:mm a");
    SimpleDateFormat formatDatabase = new SimpleDateFormat("yyyy-MM-dd");
    private ImageView mImageView;
    private Bitmap mImageBitmap;
    private static final String BITMAP_STORAGE_KEY = "viewbitmap";
    private static final String IMAGEVIEW_VISIBILITY_STORAGE_KEY = "imageviewvisibility";
    Button btn_unselect_teacher_has_exited; // 20180917 Add butto to unselect option


    private String mCurrentPhotoPath;


    private AlbumStorageDirFactory mAlbumStorageDirFactory = null;


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

    private File createImageFile(String code) throws IOException {

        File albumF = getAlbumDir();
        File imageF = new File(albumF, code +".jpg");
        return imageF;
    }

    private File setUpPhotoFile(String code) throws IOException {

        File f = createImageFile(code);
        mCurrentPhotoPath = f.getAbsolutePath();

        return f;
    }

    private void setPic() {


        int targetW = 200;
        int targetH = 178;


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

        mImageView.setImageBitmap(orientedBitmap);
        mImageView.setVisibility(View.VISIBLE);




    }

    private void load_pic(String code){
        int targetW = 200;
        int targetH = 178;

		/* Get the size of the image */
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        File albumF = getAlbumDir();

        BitmapFactory.decodeFile(albumF.toString()+"/"+code+".jpg", bmOptions);
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
        Bitmap bitmap = BitmapFactory.decodeFile(albumF.toString()+"/"+code+".jpg", bmOptions);
        Bitmap orientedBitmap = ExifUtil.rotateBitmap(albumF.toString()+"/"+code+".jpg", bitmap);
		/* Associate the Bitmap to the ImageView */
        if(bitmap!=null) {
            mImageView.setImageBitmap(orientedBitmap);
            mImageView.setVisibility(View.VISIBLE);
        }
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        getContext().sendBroadcast(mediaScanIntent);
    }

    private void dispatchTakePictureIntent(int actionCode, String Code) {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File f = null;
        switch(actionCode) {
            case 1:


                try {
                    f = setUpPhotoFile(code);
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



    private void handleBigCameraPhoto() {

        if (mCurrentPhotoPath != null) {
            setPic();
            galleryAddPic();
            mCurrentPhotoPath = null;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1: {
                if (resultCode == Activity.RESULT_OK) {
                    handleBigCameraPhoto();
                }
                break;
            } // ACTION_TAKE_PHOTO_B

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

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        // Make sure that we are currently visible
        if (this.isVisible()) {
            // If we are becoming invisible, then...
            if (!isVisibleToUser) {
                Dedo="1";
                aceptar(1);
                Dedo="0";
                // TODO stop audio playback
            }
        }
    }



    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (container == null) {
            return null;
        }

        LinearLayout mLinearLayout = (LinearLayout) inflater.inflate(R.layout.settings_menu_staff_information,
                container, false);

        // ********************** Global vars ******************
        teacher_subjects= (LinearLayout) mLinearLayout.findViewById(R.id.teacher_subjects);
        levels_teaching = (LinearLayout) mLinearLayout.findViewById(R.id.levels_teaching);
        rgTeacherExit = (RadioGroup) mLinearLayout.findViewById(R.id.rg_teacher_exit);
        _col0 = (EditText) mLinearLayout.findViewById(R.id._col0);
        tv_col0 = (TextView) mLinearLayout.findViewById(R.id.tv_col0);
        _col1a = (RadioButton) mLinearLayout.findViewById(R.id._col1a);
        _col1b = (RadioButton) mLinearLayout.findViewById(R.id._col1b);
        _col1c = (RadioButton) mLinearLayout.findViewById(R.id._col1c);
        _col2 = (EditText) mLinearLayout.findViewById(R.id._col2);
        _col3 = (EditText) mLinearLayout.findViewById(R.id._col3);
        _col4a = (RadioButton) mLinearLayout.findViewById(R.id._col4a);
        _col4b = (RadioButton) mLinearLayout.findViewById(R.id._col4b);
        _col5 = (DatePicker) mLinearLayout.findViewById(R.id._col5);
        _col6 = (EditText) mLinearLayout.findViewById(R.id._col6);
        _col7 = (CheckBox) mLinearLayout.findViewById(R.id._col7);
        _col8 = (CheckBox) mLinearLayout.findViewById(R.id._col8);
        _col9 = (CheckBox) mLinearLayout.findViewById(R.id._col9);
        _col10 = (CheckBox) mLinearLayout.findViewById(R.id._col10);
        _col11 = (CheckBox) mLinearLayout.findViewById(R.id._col11);
        _col12 = (CheckBox) mLinearLayout.findViewById(R.id._col12);
        _col13 = (CheckBox) mLinearLayout.findViewById(R.id._col13);
        _col14 = (CheckBox) mLinearLayout.findViewById(R.id._col14);
        _col15 = (CheckBox) mLinearLayout.findViewById(R.id._col15);
        _col16 = (CheckBox) mLinearLayout.findViewById(R.id._col16);
        _col17 = (CheckBox) mLinearLayout.findViewById(R.id._col17);
        _col18a = (RadioButton) mLinearLayout.findViewById(R.id._col18a);
        _col18b = (RadioButton) mLinearLayout.findViewById(R.id._col18b);
        _col18c = (RadioButton) mLinearLayout.findViewById(R.id._col18c);
        _col18d = (RadioButton) mLinearLayout.findViewById(R.id._col18d);
        _col18e = (RadioButton) mLinearLayout.findViewById(R.id._col18e);
        _col18f = (RadioButton) mLinearLayout.findViewById(R.id._col18f);
        _col18g = (RadioButton) mLinearLayout.findViewById(R.id._col18g);
        _col18h = (RadioButton) mLinearLayout.findViewById(R.id._col18h);
        _col18i = (RadioButton) mLinearLayout.findViewById(R.id._col18i);
        _col19a = (RadioButton) mLinearLayout.findViewById(R.id._col19a);
        _col19b = (RadioButton) mLinearLayout.findViewById(R.id._col19b);
        _col19c = (RadioButton) mLinearLayout.findViewById(R.id._col19c);
        _col19d = (RadioButton) mLinearLayout.findViewById(R.id._col19d);
        _col20 = (CheckBox) mLinearLayout.findViewById(R.id._col20);
        _col21 = (CheckBox) mLinearLayout.findViewById(R.id._col21);
        _col22 = (CheckBox) mLinearLayout.findViewById(R.id._col22);
        _col23 = (CheckBox) mLinearLayout.findViewById(R.id._col23);
        _col24 = (CheckBox) mLinearLayout.findViewById(R.id._col24);
        _col25 = (EditText) mLinearLayout.findViewById(R.id._col25);
        _col26a = (RadioButton) mLinearLayout.findViewById(R.id._col26a);
        _col26b = (RadioButton) mLinearLayout.findViewById(R.id._col26b);
        _col26c = (RadioButton) mLinearLayout.findViewById(R.id._col26c);
        _col26d = (RadioButton) mLinearLayout.findViewById(R.id._col26d);
        _col27 = (EditText) mLinearLayout.findViewById(R.id._col27);
        _col28 = (EditText) mLinearLayout.findViewById(R.id._col28);
        _col29 = (EditText) mLinearLayout.findViewById(R.id._col29);
        _col130a = (RadioButton) mLinearLayout.findViewById(R.id._col130a);
        _col130b = (RadioButton) mLinearLayout.findViewById(R.id._col130b);
        _col130c = (RadioButton) mLinearLayout.findViewById(R.id._col130c);
        _col130d = (RadioButton) mLinearLayout.findViewById(R.id._col130d);
        _col130e = (RadioButton) mLinearLayout.findViewById(R.id._col130e);
        _col130f = (RadioButton) mLinearLayout.findViewById(R.id._col130f);
        _col131 = (DatePicker) mLinearLayout.findViewById(R.id._col131);
        btn_unselect_teacher_has_exited = (Button) mLinearLayout.findViewById(R.id.btn_unselect);

        lv_list = (ListView) mLinearLayout.findViewById(R.id.lv_list);


        //  ************************ Objects assing *********************
        fl_part2 = (FrameLayout) mLinearLayout.findViewById(R.id.fl_part2);

        //  ************************ Objects Buttoms *********************

        add_reg = (FloatingActionButton) mLinearLayout.findViewById(R.id.add_reg);
        save_reg = (FloatingActionButton) mLinearLayout.findViewById(R.id.save_reg);
        erase_reg = (FloatingActionButton) mLinearLayout.findViewById(R.id.erase_reg);

        // **************** CLICK ON BUTTONS ********************
        save_reg.setOnClickListener(this);
        erase_reg.setOnClickListener(this);
        _col0.setOnClickListener(this);
        btn_unselect_teacher_has_exited.setOnClickListener(this);
        _col1a.setOnClickListener(this);
        _col1b.setOnClickListener(this);
        _col1c.setOnClickListener(this);
        _col17.setOnClickListener(this);
        save_reg.setVisibility(View.VISIBLE);
        erase_reg.setVisibility(View.VISIBLE);
        add_reg.setVisibility(View.GONE);

        // ***************** LOCAD INFORMATION *************************
        _col5.setMinDate(DateUtility.setDateMinMax(70));
        _col5.setMaxDate(DateUtility.setDateMinMax(18));
        initialize_upgrade();
        loadRecord(SettingsMenuStaff.TS_code);
        mImageView = (ImageView) mLinearLayout.findViewById(R.id.imageView3);
        Button picSBtn = (Button) mLinearLayout.findViewById(R.id.teacher_photo);
        picSBtn.setOnClickListener(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
            mAlbumStorageDirFactory = new FroyoAlbumDirFactory();
        } else {
            mAlbumStorageDirFactory = new BaseAlbumDirFactory();
        }
        StrictMode.VmPolicy.Builder newbuilder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(newbuilder.build());
        load_pic(SettingsMenuStaff.TS_code);

        if (ReportTS.TS_report_enable == "1") {picSBtn.setEnabled(false);}
        enableData();


        // Este es el focus para buscar un registro existente
        _col0.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus){
                    if (!_col0.getText().toString().isEmpty()) {
                        code = _col0.getText().toString();
                        loadRecord(code);
                    }
                }
            }
        });


        return mLinearLayout;
    }


    private void enableData() {
        if (ReportTS.TS_report_enable == "1") {
            save_reg.setVisibility(View.GONE);
            erase_reg.setVisibility(View.GONE);
            _col1a.setEnabled(false);
            _col1b.setEnabled(false);
            _col1c.setEnabled(false);
            _col2.setFocusable(false);
            _col3.setFocusable(false);
            _col4a.setEnabled(false);
            _col4b.setEnabled(false);
            _col5.setEnabled(false);
            _col6.setFocusable(false);
            _col7.setEnabled(false);
            _col8.setEnabled(false);
            _col9.setEnabled(false);
            _col10.setEnabled(false);
            _col11.setEnabled(false);
            _col12.setEnabled(false);
            _col13.setEnabled(false);
            _col14.setEnabled(false);
            _col15.setEnabled(false);
            _col16.setEnabled(false);
            _col17.setEnabled(false);
            _col18a.setEnabled(false);
            _col18b.setEnabled(false);
            _col18c.setEnabled(false);
            _col18d.setEnabled(false);
            _col18e.setEnabled(false);
            _col18f.setEnabled(false);
            _col18g.setEnabled(false);
            _col18h.setEnabled(false);
            _col18i.setEnabled(false);
            _col19a.setEnabled(false);
            _col19b.setEnabled(false);
            _col19c.setEnabled(false);
            _col19d.setEnabled(false);
            _col20.setEnabled(false);
            _col21.setEnabled(false);
            _col22.setEnabled(false);
            _col23.setEnabled(false);
            _col24.setEnabled(false);
            _col25.setFocusable(false);
            _col26a.setEnabled(false);
            _col26b.setEnabled(false);
            _col26c.setEnabled(false);
            _col26d.setEnabled(false);
            _col27.setFocusable(false);
            _col28.setFocusable(false);
            _col29.setFocusable(false);
            _col130a.setFocusable(false);
            _col130b.setFocusable(false);
            _col130c.setFocusable(false);
            _col130d.setFocusable(false);
            _col130e.setFocusable(false);
            _col130f.setFocusable(false);
            _col131.setEnabled(false);
        } else {
            save_reg.setVisibility(View.VISIBLE);
            erase_reg.setVisibility(View.VISIBLE);
            //picSBtn.setEnabled(true);
            _col1a.setEnabled(true);
            _col1b.setEnabled(true);
        }
    }


    // **************** Load DATA *************************
    public void loadRecord(String code) {
        if (code.equals("")) {
            _IU = "I";
        } else {
            tv_col0.setText("");
            Conexion cnSET = new Conexion(getContext(), STATICS_ROOT + File.separator + "sisdb.sqlite", null);
            SQLiteDatabase dbSET = cnSET.getReadableDatabase();
            Cursor cur_data = dbSET.rawQuery("SELECT _id, t_s, surname, givenname, sex, yearob, checkno, cp1, cp2, cp3, cp4, cp5, cp6, lt1 , lt2 , lt3 , lt4 , lt5 , prof_q, acad_q,  sub_t1, sub_t2, sub_t3, sub_t4, sub_t5, year_pos, salary, phone, email, addrs ,exit,yearexit FROM teacher WHERE _id=" + code, null);
            cur_data.moveToFirst();
            if (cur_data.getCount() > 0) {
                _col0.setText(cur_data.getString(0));
                tv_col0.setText(cur_data.getString(0));
                _col0.setVisibility(View.GONE);
                tv_col0.setVisibility(View.VISIBLE);
                switch (cur_data.getInt(1)) {
                    case 1: _col1a.setChecked(true); break;
                    case 2: _col1b.setChecked(true); break;
                    case 3: _col1c.setChecked(true); break;
                }
                _col2.setText(cur_data.getString(2));
                _col3.setText(cur_data.getString(3));
                switch (cur_data.getInt(4)) {
                    case 1: _col4a.setChecked(true); break;
                    case 2: _col4b.setChecked(true); break;
                }

                //_col5.setMaxDate(System.currentTimeMillis());
                _col5.updateDate(Integer.parseInt(cur_data.getString(5).substring(0,4)),Integer.parseInt(cur_data.getString(5).substring(5,7)) - 1, Integer.parseInt(cur_data.getString(5).substring(8,10)));
                _col6.setText(cur_data.getString(6));
                if (cur_data.getInt(7)==1) {_col7.setChecked(true);}
                if (cur_data.getInt(8)==1) {_col8.setChecked(true);}
                if (cur_data.getInt(9)==1) {_col9.setChecked(true);}
                if (cur_data.getInt(10)==1) {_col10.setChecked(true);}
                if (cur_data.getInt(11)==1) {_col11.setChecked(true);}
                if (cur_data.getInt(12)==1) {_col12.setChecked(true);}
                if (cur_data.getInt(13)==1) {_col13.setChecked(true);}
                if (cur_data.getInt(14)==1) {_col14.setChecked(true);}
                if (cur_data.getInt(15)==1) {_col15.setChecked(true);}
                if (cur_data.getInt(16)==1) {_col16.setChecked(true);}
                if (cur_data.getInt(17)==1) {_col17.setChecked(true);}

                switch (cur_data.getInt(18)) {
                    case 1: _col18a.setChecked(true); break;
                    case 2: _col18b.setChecked(true); break;
                    case 3: _col18c.setChecked(true); break;
                    case 4: _col18d.setChecked(true); break;
                    case 5: _col18e.setChecked(true); break;
                    case 6: _col18f.setChecked(true); break;
                    case 7: _col18g.setChecked(true); break;
                    case 8: _col18g.setChecked(true); break;
                    case 9: _col18i.setChecked(true); break;
                }
                switch (cur_data.getInt(19)) {
                    case 1: _col19a.setChecked(true); break;
                    case 2: _col19b.setChecked(true); break;
                    case 3: _col19c.setChecked(true); break;
                    case 4: _col19d.setChecked(true); break;
                }
                if (cur_data.getInt(20)==1) {_col20.setChecked(true);}
                if (cur_data.getInt(21)==1) {_col21.setChecked(true);}
                if (cur_data.getInt(22)==1) {_col22.setChecked(true);}
                if (cur_data.getInt(23)==1) {_col23.setChecked(true);}
                if (cur_data.getInt(24)==1) {_col24.setChecked(true);}
                _col25.setText(cur_data.getString(25));
                switch (cur_data.getInt(26)) {
                    case 1: _col26a.setChecked(true); break;
                    case 2: _col26b.setChecked(true); break;
                    case 3: _col26c.setChecked(true); break;
                    case 4: _col26d.setChecked(true); break;
                }
                _col27.setText(cur_data.getString(27));
                _col28.setText(cur_data.getString(28));
                _col29.setText(cur_data.getString(29));
                switch (cur_data.getInt(30)) {
                    case 1: _col130a.setChecked(true); break;
                    case 2: _col130b.setChecked(true); break;
                    case 3: _col130c.setChecked(true); break;
                    case 4: _col130d.setChecked(true); break;
                    case 5: _col130e.setChecked(true); break;
                    case 6: _col130f.setChecked(true); break;
                }
                _col131.setMaxDate(System.currentTimeMillis());
                if (cur_data.getString(31)==null || cur_data.getString(31).equals("")){
                    // Nothing TO DO
                }
                else{
                _col131.updateDate(
                        Integer.parseInt(cur_data.getString(31).substring(0,4)),
                        Integer.parseInt(cur_data.getString(31).substring(5,7)) - 1,
                        Integer.parseInt(cur_data.getString(31).substring(8,10)));
                }
                _IU = "U";
                _col0.requestFocus();
                erase_reg.setVisibility(View.VISIBLE);
            } else {
            }
            cur_data.close();
            dbSET.close();
            cnSET.close();
        }

    }

    public void updateRecord () {
        Conexion cnSET = new Conexion(getContext(),STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        String sql = "", delimit="%", tableName="teacher", dateofbirth="";
        // ***************** CONTENT TO RECORD-SET **************************
        ContentValues reg = new ContentValues();
        if (!_col0.getText().toString().isEmpty()){
            tv_col0.setText(_col0.getText().toString());
            SettingsMenuStaff.TS_code = _col0.getText().toString();

            reg.put("flag", _IU); sql = sql + tableName + delimit + getEMIS_code() + delimit;
            reg.put("_id",Integer.parseInt(_col0.getText().toString())); sql = sql + _col0.getText().toString() + delimit;

            if (_col1a.isChecked()==true) {reg.put("t_s",1); sql = sql + "1" + delimit;}
            else if (_col1b.isChecked()==true) {reg.put("t_s",2); sql = sql + "2" + delimit;}
            else if (_col1c.isChecked()==true) {reg.put("t_s",3); sql = sql + "3" + delimit;}
            else {sql = sql + "" + delimit;}

            if (!_col2.getText().toString().isEmpty()){reg.put("surname", _col2.getText().toString());} sql = sql + _col2.getText().toString() + delimit;
            if (!_col3.getText().toString().isEmpty()){reg.put("givenname", _col3.getText().toString());} sql = sql + _col3.getText().toString() + delimit;

            if (_col4a.isChecked()==true) {reg.put("sex",1); sql = sql + "1" + delimit;}
            else if (_col4b.isChecked()==true) {reg.put("sex",2); sql = sql + "2" + delimit;}
            else {sql = sql + "" + delimit;}

            int day = _col5.getDayOfMonth();
            int month = _col5.getMonth();
            int year = _col5.getYear();
            calendar.set(year, month, day);
            String strDate = formatDatabase.format(calendar.getTime());
            reg.put("yearob", strDate); sql = sql + strDate + delimit;

            if (!_col6.getText().toString().isEmpty()){reg.put("checkno", _col6.getText().toString());} sql = sql + _col6.getText().toString() + delimit;

            if (_col7.isChecked()==true){reg.put("cp1", "1"); sql = sql + "1" + delimit;} else {reg.put("cp1", "0"); sql = sql + "" + delimit;}
            if (_col8.isChecked()==true){reg.put("cp2", "1"); sql = sql + "1" + delimit;} else {reg.put("cp2", "0"); sql = sql + "" + delimit;}
            if (_col9.isChecked()==true){reg.put("cp3", "1"); sql = sql + "1" + delimit;} else {reg.put("cp3", "0"); sql = sql + "" + delimit;}
            if (_col10.isChecked()==true){reg.put("cp4", "1"); sql = sql + "1" + delimit;} else {reg.put("cp4", "0"); sql = sql + "" + delimit;}
            if (_col11.isChecked()==true){reg.put("cp5", "1"); sql = sql + "1" + delimit;} else {reg.put("cp5", "0"); sql = sql + "" + delimit;}
            if (_col12.isChecked()==true){reg.put("cp6", "1"); sql = sql + "1" + delimit;} else {reg.put("cp6", "0"); sql = sql + "" + delimit;}
            if (_col13.isChecked()==true){reg.put("lt1", "1"); sql = sql + "1" + delimit;} else {reg.put("lt1", "0"); sql = sql + "" + delimit;}
            if (_col14.isChecked()==true){reg.put("lt2", "1"); sql = sql + "1" + delimit;} else {reg.put("lt2", "0"); sql = sql + "" + delimit;}
            if (_col15.isChecked()==true){reg.put("lt3", "1"); sql = sql + "1" + delimit;} else {reg.put("lt3", "0"); sql = sql + "" + delimit;}
            if (_col16.isChecked()==true){reg.put("lt4", "1"); sql = sql + "1" + delimit;} else {reg.put("lt4", "0"); sql = sql + "" + delimit;}
            if (_col17.isChecked()==true){reg.put("lt5", "1"); sql = sql + "1" + delimit;} else {reg.put("lt5", "0"); sql = sql + "" + delimit;}

            if (_col18a.isChecked()==true) {reg.put("prof_q",1); sql = sql + "1" + delimit;}
            else if (_col18b.isChecked()==true) {reg.put("prof_q",2); sql = sql + "2" + delimit;}
            else if (_col18c.isChecked()==true) {reg.put("prof_q",3); sql = sql + "3" + delimit;}
            else if (_col18d.isChecked()==true) {reg.put("prof_q",4); sql = sql + "4" + delimit;}
            else if (_col18e.isChecked()==true) {reg.put("prof_q",5); sql = sql + "5" + delimit;}
            else if (_col18f.isChecked()==true) {reg.put("prof_q",6); sql = sql + "6" + delimit;}
            else if (_col18g.isChecked()==true) {reg.put("prof_q",7); sql = sql + "7" + delimit;}
            else if (_col18h.isChecked()==true) {reg.put("prof_q",8); sql = sql + "8" + delimit;}
            else if (_col18i.isChecked()==true) {reg.put("prof_q",9); sql = sql + "9" + delimit;}
            else {sql = sql + "" + delimit;}

            if (_col19a.isChecked()==true) {reg.put("acad_q",1); sql = sql + "1" + delimit;}
            else if (_col19b.isChecked()==true) {reg.put("acad_q",2); sql = sql + "2" + delimit;}
            else if (_col19c.isChecked()==true) {reg.put("acad_q",3); sql = sql + "3" + delimit;}
            else if (_col19d.isChecked()==true) {reg.put("acad_q",4); sql = sql + "4" + delimit;}
            else {sql = sql + "" + delimit;}

            if (_col20.isChecked()==true){reg.put("sub_t1", "1"); sql = sql + "1" + delimit;} else {reg.put("sub_t1", "0"); sql = sql + "" + delimit;}
            if (_col21.isChecked()==true){reg.put("sub_t2", "1"); sql = sql + "1" + delimit;} else {reg.put("sub_t2", "0"); sql = sql + "" + delimit;}
            if (_col22.isChecked()==true){reg.put("sub_t3", "1"); sql = sql + "1" + delimit;} else {reg.put("sub_t3", "0"); sql = sql + "" + delimit;}
            if (_col23.isChecked()==true){reg.put("sub_t4", "1"); sql = sql + "1" + delimit;} else {reg.put("sub_t4", "0"); sql = sql + "" + delimit;}
            if (_col24.isChecked()==true){reg.put("sub_t5", "1"); sql = sql + "1" + delimit;} else {reg.put("sub_t5", "0"); sql = sql + "" + delimit;}

            if (!_col25.getText().toString().isEmpty()){reg.put("year_pos", _col25.getText().toString());} sql = sql + _col25.getText().toString() + delimit;

            if (_col26a.isChecked()==true) {reg.put("salary",1); sql = sql + "1" + delimit;}
            else if (_col26b.isChecked()==true) {reg.put("salary",2); sql = sql + "2" + delimit;}
            else if (_col26c.isChecked()==true) {reg.put("salary",3); sql = sql + "3" + delimit;}
            else if (_col26d.isChecked()==true) {reg.put("salary",4); sql = sql + "4" + delimit;}
            else {sql = sql + "" + delimit;}

            if (!_col27.getText().toString().isEmpty()){reg.put("phone", _col27.getText().toString());} sql = sql + _col27.getText().toString() + delimit;
            if (!_col28.getText().toString().isEmpty()){reg.put("email", _col28.getText().toString());} sql = sql + _col28.getText().toString() + delimit;
            if (!_col29.getText().toString().isEmpty()){reg.put("addrs", _col29.getText().toString());} sql = sql + _col29.getText().toString() + delimit;

            int day_te = _col131.getDayOfMonth();
            int month_te = _col131.getMonth();
            int year_te = _col131.getYear();
            calendar.set(year_te, month_te, day_te);
            String strDate_te = formatDatabase.format(calendar.getTime());
            if (_col130a.isChecked()==true) {reg.put("exit",1); sql = sql + "1" + delimit;}
            else if (_col130b.isChecked()==true) {reg.put("exit",2); sql = sql + "2" + delimit;}
            else if (_col130c.isChecked()==true) {reg.put("exit",3); sql = sql + "3" + delimit;}
            else if (_col130d.isChecked()==true) {reg.put("exit",4); sql = sql + "4" + delimit;}
            else if (_col130e.isChecked()==true) {reg.put("exit",5); sql = sql + "5" + delimit;}
            else if (_col130f.isChecked()==true) {reg.put("exit",6); sql = sql + "6" + delimit;}
            else {reg.put("exit",0); sql = sql + "0" + delimit; strDate_te="";}
            reg.put("yearexit", strDate_te); sql = sql + strDate_te + delimit;

            sql = sql + _IU;
            try {
                // ****************** Fill Bitacora
                if (Dedo.equals("0")) {
                    ContentValues Bitacora = new ContentValues();
                    Bitacora.put("sis_sql",sql);
                    dbSET.insert("sisupdate",null,Bitacora);
                }
                // ********************* Fill TABLE d
                if (_IU=="I") {dbSET.insert("teacher", null, reg); _IU="U";}
                else {dbSET.update("teacher", reg, "_id=" + _col0.getText().toString(), null);}
                if (Dedo.equals("0")) {
                    toolsfncs.dialogAlertConfirm(getContext(),getResources(),9);
                }
                _col0.setVisibility(View.GONE);
                tv_col0.setVisibility(View.VISIBLE);
            }catch (Exception e) {
            }
        }
        dbSET.close();
        cnSET.close();
    }

    public void initialize_upgrade() {
        _col0.setText("");
        _col2.setText("");
        _col3.setText("");
        _col6.setText("");
        _col25.setText("");
        _col27.setText("");
        _col28.setText("");
        _col29.setText("");
    }

    // **************** CLICK ON BUTTONS ********************
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_reg:
                dialogAlert(1);
                break;
            case R.id.erase_reg:
                dialogAlert(3);
                break;
            case R.id.save_reg:
                dialogAlert(1);
                break;
            case R.id.btn_exit:
                dialogAlert(2);
                break;
            case R.id._col0:
                if (!_col0.getText().toString().isEmpty()) {
                    code = _col0.getText().toString();
                    loadRecord(code);
                }
                break;
            case R.id.teacher_photo:
                if (_col0.getText().toString().isEmpty()) {
                    dialogAlert2(1);
                }
                else{
                    code = _col0.getText().toString();
                    dispatchTakePictureIntent(1,code);
                }
                break;
            case R.id.btn_unselect: //R.id.btn_unselect_teacher_has_exited:
                rgTeacherExit.clearCheck();
                break;
            case R.id._col1c:
                checked = 1;

                levels_teaching.setVisibility(View.GONE);
                teacher_subjects.setVisibility(View.GONE);
                break;
            case R.id._col1a:
                checked = 0;

                levels_teaching.setVisibility(View.VISIBLE);
                teacher_subjects.setVisibility(View.VISIBLE);
                break;
            case R.id._col1b:
                checked = 0;

                levels_teaching.setVisibility(View.VISIBLE);
                teacher_subjects.setVisibility(View.VISIBLE);
                break;
                case R.id._col17:
                    if (_col17.isChecked()==true)
                        checked=1;
                    else
                        checked=0;
                    break;

        }

    }

    // *********** Control Alerts ************************
    public void dialogAlert(int v){
        final int val = v;
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getContext());
        dialogo1.setTitle(getResources().getString(R.string.str_bl_msj1));
        if (v == 1){dialogo1.setMessage(getResources().getString(R.string.str_bl_msj2));}
        if (v == 2){dialogo1.setMessage(getResources().getString(R.string.str_bl_msj7));}
        if (v == 3){dialogo1.setMessage(getResources().getString(R.string.str_bl_msj8));}

        dialogo1.setCancelable(false);
        dialogo1.setNegativeButton(getResources().getString(R.string.str_bl_msj4), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                cancelar();
            }
        });
        dialogo1.setPositiveButton(getResources().getString(R.string.str_bl_msj3), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                aceptar(val);
            }
        });
        dialogo1.show();
    }
    public void dialogAlert2(int v){
        final int val = v;
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getContext());
        dialogo1.setTitle(getResources().getString(R.string.str_bl_msj1));
        if (v == 1){dialogo1.setMessage(getResources().getString(R.string.str_bl_msj11));}
        if (v == 4){dialogo1.setMessage(getResources().getString(R.string.str_bl_msj10));}
        if (v == 5){dialogo1.setMessage(getResources().getString(R.string.str_bl_msj9));}
        if (v == 6){dialogo1.setMessage(getResources().getString(R.string.str_bl_msj9));}

        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton(getResources().getString(R.string.str_bl_msj4), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {

            }
        });
        dialogo1.setNegativeButton(getResources().getString(R.string.str_bl_msj3), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {

            }
        });
        dialogo1.show();
    }

    public void aceptar(int action) {
        switch (action) {
            case 1:
                updateRecord();
                break;
            case 3:
                if (eraseTeacherAssing(SettingsMenuStaff.TS_code) == "0") {
                    try {
                        Conexion cnSET = new Conexion(getContext(),STATICS_ROOT + File.separator + "sisdb.sqlite", null);
                        SQLiteDatabase dbSET = cnSET.getWritableDatabase();
                        dbSET.delete("teacher", "_id=" + String.valueOf(_col0.getText()), null);
                        dbSET.close();
                        cnSET.close();
                        SettingsMenuStaff.TS_code="";
                    }catch (Exception e) {}
                    getActivity().finish();
                } else {
                    dialogAlert2(4);
                }
                break;
        }
   }
    public void cancelar() {
    }

    // *********** END Control Alerts ************************

    public String getEMIS_code(){
        String getemiscode="";
        Conexion cnSET = new Conexion(getContext(),STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
       Cursor cur_data = dbSET.rawQuery("SELECT emis FROM ms_0", null);
        cur_data.moveToFirst();
        if (cur_data.getCount() > 0) {getemiscode = cur_data.getString(0);} else {getemiscode = "";}
        cur_data.close();
        dbSET.close();
        cnSET.close();
        return getemiscode;
    }

    public String eraseTeacherAssing(String code){
        String getcode="0";
        Conexion cnSET = new Conexion(getContext(),STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        try {
            Cursor cur_data = dbSET.rawQuery("SELECT emis, tc, shift,level,grade,section,subject FROM _ta WHERE tc=" + code, null);
            cur_data.moveToFirst();
            if (cur_data.getCount() > 0) {getcode = "1";}
        } catch (Exception e){}

        return getcode;
    }

}
