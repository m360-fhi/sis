package com.example.sergio.sistz;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.sergio.sistz.mysql.Conexion;
import com.example.sergio.sistz.util.toolsfncs;

import java.io.File;

/**
 * Created by Sergio on 2/26/2016.
 */
public class SettingsMenuInfra_2 extends Fragment implements View.OnClickListener{
    public static final String STATICS_ROOT = Environment.getExternalStorageDirectory() + File.separator + "sisdb";
    int fl_location = 1; // *********** Control change page
    FrameLayout fl_part1, fl_part2, fl_part3; // ************ FrameLayout ***************
    EditText _col2, _col3, _col4, _col5, _col6, _col7, _col8, _col9, _col10, _col11, _col12, _col13, _col14, _col15,_col16,_col17,_col18,_col19,_col20,_col21,_col22,_col23,_col24,_col25,_col26,_col27,_col28,_col29,_col30,_col31,_col32,_col33,_col34,_col35,_col36,_col37,_col38,_col39,_col40,_col41,_col42,_col43,_col44,_col45,_col46,_col47,_col48,_col49,_col50,_col51,_col52,_col53,_col54,_col55,_col56,_col57,_col58,_col59,_col60,_col61,_col62,_col63,_col64,_col65,_col66,_col67,_col68,_col69,_col70,_col71,_col72,_col73,_col74,_col75,_col76,_col77,_col78,_col79,_col80,_col81,_col82,_col83,_col84, _col85,_col86,_col87,_col88,_col89,_col90,_col91;
    EditText _col92, _col93, _col94, _col95, _col96, _col97, _col98, _col99, _col100, _col101, _col102, _col103, _col104, _col105, _col106, _col107, _col108, _col109, _col110, _col111, _col112, _col113, _col114, _col115, _col116, _col117, _col118, _col119, _col120, _col121, _col122, _col123, _col124, _col125, _col126, _col127, _col128,  _col129, _col130;
    RadioButton _colf1ay, _colf1an, rb_la1, rb_la2, rb_la3, rb_m1, rb_m2, rb_m3, rb_s1, rb_s2, rb_s3, rb_ss1, rb_ss2, rb_ss3, rb_o1, rb_o2, rb_o3, rb_f2dy, rb_f2dn;
    String _IU="U";

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (container == null) {

            return null;
        }
        LinearLayout mLinearLayout = (LinearLayout) inflater.inflate(R.layout.settings_menu_infra_2,
                container, false);

        // ********************** Global vars ******************
        _colf1ay = (RadioButton) mLinearLayout.findViewById(R.id.f1ay);
        _colf1an = (RadioButton) mLinearLayout.findViewById(R.id.f1an);
        _col2 = (EditText) mLinearLayout.findViewById(R.id.f1b);
        _col3 = (EditText) mLinearLayout.findViewById(R.id.et_pos1);
        _col4 = (EditText) mLinearLayout.findViewById(R.id.et_pos2);
        _col5 = (EditText) mLinearLayout.findViewById(R.id.et_pos3);
        _col6 = (EditText) mLinearLayout.findViewById(R.id.et_pos4);
        _col7 = (EditText) mLinearLayout.findViewById(R.id.et_pos5);
        _col8 = (EditText) mLinearLayout.findViewById(R.id.et_pos6);
        _col9 = (EditText) mLinearLayout.findViewById(R.id.et_pos7);
        _col10 = (EditText) mLinearLayout.findViewById(R.id.et_pos8);
        _col11 = (EditText) mLinearLayout.findViewById(R.id.et_pos9);
        _col12 = (EditText) mLinearLayout.findViewById(R.id.et_pos10);
        _col13 = (EditText) mLinearLayout.findViewById(R.id.et_pos11);
        _col14 = (EditText) mLinearLayout.findViewById(R.id.et_pos12);
        _col15 = (EditText) mLinearLayout.findViewById(R.id.et_pos13);
        _col16 = (EditText) mLinearLayout.findViewById(R.id.et_pos14);
        _col17 = (EditText) mLinearLayout.findViewById(R.id.et_pos15);
        _col18 = (EditText) mLinearLayout.findViewById(R.id.et_pos16);
        _col19 = (EditText) mLinearLayout.findViewById(R.id.et_pos17);
        _col20 = (EditText) mLinearLayout.findViewById(R.id.et_pos18);
        _col21 = (EditText) mLinearLayout.findViewById(R.id.et_pos19);
        _col22 = (EditText) mLinearLayout.findViewById(R.id.et_pos20);
        _col23 = (EditText) mLinearLayout.findViewById(R.id.et_pos21);
        _col24 = (EditText) mLinearLayout.findViewById(R.id.et_pos22);
        _col25 = (EditText) mLinearLayout.findViewById(R.id.et_pos23);
        _col26 = (EditText) mLinearLayout.findViewById(R.id.et_pos24);
        _col27 = (EditText) mLinearLayout.findViewById(R.id.et_pos25);
        _col28 = (EditText) mLinearLayout.findViewById(R.id.et_pos26);
        _col29 = (EditText) mLinearLayout.findViewById(R.id.et_pos27);
        _col30 = (EditText) mLinearLayout.findViewById(R.id.et_pos28);
        _col31 = (EditText) mLinearLayout.findViewById(R.id.et_pos29);
        _col32 = (EditText) mLinearLayout.findViewById(R.id.et_pos30);
        _col33 = (EditText) mLinearLayout.findViewById(R.id.et_pos31);
        _col34 = (EditText) mLinearLayout.findViewById(R.id.et_pos32);
        _col35 = (EditText) mLinearLayout.findViewById(R.id.et_pos33);
        _col36 = (EditText) mLinearLayout.findViewById(R.id.et_pos34);
        _col37 = (EditText) mLinearLayout.findViewById(R.id.et_pos35);
        _col38 = (EditText) mLinearLayout.findViewById(R.id.et_pos36);
        _col39 = (EditText) mLinearLayout.findViewById(R.id.et_pos37);
        _col40 = (EditText) mLinearLayout.findViewById(R.id.et_pos38);
        _col41 = (EditText) mLinearLayout.findViewById(R.id.et_pos39);
        _col42 = (EditText) mLinearLayout.findViewById(R.id.et_pos40);
        _col43 = (EditText) mLinearLayout.findViewById(R.id.et_pos41);
        _col44 = (EditText) mLinearLayout.findViewById(R.id.et_pos42);
        _col45 = (EditText) mLinearLayout.findViewById(R.id.et_pos43);
        _col46 = (EditText) mLinearLayout.findViewById(R.id.et_pos44);
        _col47 = (EditText) mLinearLayout.findViewById(R.id.et_pos45);
        _col48 = (EditText) mLinearLayout.findViewById(R.id.et_pos46);
        _col49 = (EditText) mLinearLayout.findViewById(R.id.et_pos47);
        _col50 = (EditText) mLinearLayout.findViewById(R.id.et_pos48);
        _col51 = (EditText) mLinearLayout.findViewById(R.id.et_pos49);
        _col52 = (EditText) mLinearLayout.findViewById(R.id.et_pos50);
        _col53 = (EditText) mLinearLayout.findViewById(R.id.et_pos51);
        _col54 = (EditText) mLinearLayout.findViewById(R.id.et_pos52);
        _col55 = (EditText) mLinearLayout.findViewById(R.id.et_pos53);
        _col56 = (EditText) mLinearLayout.findViewById(R.id.et_pos54);
        _col57 = (EditText) mLinearLayout.findViewById(R.id.et_pos55);
        _col58 = (EditText) mLinearLayout.findViewById(R.id.et_pos56);
        _col59 = (EditText) mLinearLayout.findViewById(R.id.et_pos57);
        _col60 = (EditText) mLinearLayout.findViewById(R.id.et_pos58);
        _col61 = (EditText) mLinearLayout.findViewById(R.id.et_pos59);
        _col62 = (EditText) mLinearLayout.findViewById(R.id.et_pos60);
        _col63 = (EditText) mLinearLayout.findViewById(R.id.et_pos61);
        _col64 = (EditText) mLinearLayout.findViewById(R.id.et_pos62);
        _col65 = (EditText) mLinearLayout.findViewById(R.id.et_pos63);
        _col66 = (EditText) mLinearLayout.findViewById(R.id.et_pos64);
        _col67 = (EditText) mLinearLayout.findViewById(R.id.et_pos65);
        _col68 = (EditText) mLinearLayout.findViewById(R.id.et_pos66);
        _col69 = (EditText) mLinearLayout.findViewById(R.id.et_pos67);
        _col70 = (EditText) mLinearLayout.findViewById(R.id.et_pos68);
        _col71 = (EditText) mLinearLayout.findViewById(R.id.et_pos69);
        _col72 = (EditText) mLinearLayout.findViewById(R.id.et_pos70);
        _col73 = (EditText) mLinearLayout.findViewById(R.id.et_pos71);
        _col74 = (EditText) mLinearLayout.findViewById(R.id.et_pos72);
        _col75 = (EditText) mLinearLayout.findViewById(R.id.et_pos73);
        _col76 = (EditText) mLinearLayout.findViewById(R.id.et_pos74);
        _col77 = (EditText) mLinearLayout.findViewById(R.id.et_pos75);
        _col78 = (EditText) mLinearLayout.findViewById(R.id.et_pos76);
        _col79 = (EditText) mLinearLayout.findViewById(R.id.et_pos77);
        _col80 = (EditText) mLinearLayout.findViewById(R.id.et_pos78);
        _col81 = (EditText) mLinearLayout.findViewById(R.id.et_pos79);
        _col82 = (EditText) mLinearLayout.findViewById(R.id.et_pos80);
        _col83 = (EditText) mLinearLayout.findViewById(R.id.et_pos81);
        _col84 = (EditText) mLinearLayout.findViewById(R.id.et_pos82);
        _col85 = (EditText) mLinearLayout.findViewById(R.id.et_pos83);
        _col86 = (EditText) mLinearLayout.findViewById(R.id.et_pos84);

        _col87 = (EditText) mLinearLayout.findViewById(R.id.et_pos85);
        _col88 = (EditText) mLinearLayout.findViewById(R.id.et_pos86);
        _col89 = (EditText) mLinearLayout.findViewById(R.id.et_pos87);
        _col90 = (EditText) mLinearLayout.findViewById(R.id.et_pos88);
        _col91 = (EditText) mLinearLayout.findViewById(R.id.et_pos89);
        _col92 = (EditText) mLinearLayout.findViewById(R.id.et_pos90);
        _col93 = (EditText) mLinearLayout.findViewById(R.id.et_pos91);

        _col94 = (EditText) mLinearLayout.findViewById(R.id.et_pos92);
        _col95 = (EditText) mLinearLayout.findViewById(R.id.et_pos93);
        _col96 = (EditText) mLinearLayout.findViewById(R.id.et_pos94);
        _col97 = (EditText) mLinearLayout.findViewById(R.id.et_pos95);
        _col98 = (EditText) mLinearLayout.findViewById(R.id.et_pos96);
        _col99 = (EditText) mLinearLayout.findViewById(R.id.et_pos97);
        _col100 = (EditText) mLinearLayout.findViewById(R.id.et_pos98);
        _col101 = (EditText) mLinearLayout.findViewById(R.id.et_pos99);
        _col102 = (EditText) mLinearLayout.findViewById(R.id.et_pos100);
        _col103 = (EditText) mLinearLayout.findViewById(R.id.et_pos101);
        _col104 = (EditText) mLinearLayout.findViewById(R.id.et_pos102);
        _col105 = (EditText) mLinearLayout.findViewById(R.id.et_pos103);
        _col106 = (EditText) mLinearLayout.findViewById(R.id.et_pos104);
        _col107 = (EditText) mLinearLayout.findViewById(R.id.et_pos105);
        _col108 = (EditText) mLinearLayout.findViewById(R.id.et_pos106);
        _col109 = (EditText) mLinearLayout.findViewById(R.id.et_pos107);
        _col110 = (EditText) mLinearLayout.findViewById(R.id.et_pos108);
        _col111 = (EditText) mLinearLayout.findViewById(R.id.et_pos109);
        _col112 = (EditText) mLinearLayout.findViewById(R.id.et_pos110);
        _col113 = (EditText) mLinearLayout.findViewById(R.id.et_pos111);
        _col114 = (EditText) mLinearLayout.findViewById(R.id.et_pos112);

        _col115 = (EditText) mLinearLayout.findViewById(R.id.et_pos113);
        _col116 = (EditText) mLinearLayout.findViewById(R.id.et_pos114);
        _col117 = (EditText) mLinearLayout.findViewById(R.id.et_pos115);
        _col118 = (EditText) mLinearLayout.findViewById(R.id.et_pos116);
        _col119 = (EditText) mLinearLayout.findViewById(R.id.et_pos117);
        _col120 = (EditText) mLinearLayout.findViewById(R.id.et_pos118);
        _col121 = (EditText) mLinearLayout.findViewById(R.id.et_pos119);

        _col122 = (EditText) mLinearLayout.findViewById(R.id.et_pos120);
        _col123 = (EditText) mLinearLayout.findViewById(R.id.et_pos121);
        _col124 = (EditText) mLinearLayout.findViewById(R.id.et_pos122);
        _col125 = (EditText) mLinearLayout.findViewById(R.id.et_pos123);
        _col126 = (EditText) mLinearLayout.findViewById(R.id.et_pos124);
        _col127 = (EditText) mLinearLayout.findViewById(R.id.et_pos125);
        _col128 = (EditText) mLinearLayout.findViewById(R.id.et_pos126);
        _col129 = (EditText) mLinearLayout.findViewById(R.id.et_pos127); // add 20181029 Social - STD 3
        _col130 = (EditText) mLinearLayout.findViewById(R.id.et_pos128); // add 20181029 Social - STD 4

        //
        rb_la1 = (RadioButton) mLinearLayout.findViewById(R.id.rb_la1);
        rb_la2 = (RadioButton) mLinearLayout.findViewById(R.id.rb_la2);
        rb_la3 = (RadioButton) mLinearLayout.findViewById(R.id.rb_la3);
        rb_m1 = (RadioButton) mLinearLayout.findViewById(R.id.rb_m1);
        rb_m2 = (RadioButton) mLinearLayout.findViewById(R.id.rb_m2);
        rb_m3 = (RadioButton) mLinearLayout.findViewById(R.id.rb_m3);
        rb_s1 = (RadioButton) mLinearLayout.findViewById(R.id.rb_s1);
        rb_s2 = (RadioButton) mLinearLayout.findViewById(R.id.rb_s2);
        rb_s3 = (RadioButton) mLinearLayout.findViewById(R.id.rb_s3);
        rb_ss1 = (RadioButton) mLinearLayout.findViewById(R.id.rb_ss1);
        rb_ss2 = (RadioButton) mLinearLayout.findViewById(R.id.rb_ss2);
        rb_ss3 = (RadioButton) mLinearLayout.findViewById(R.id.rb_ss3);
        rb_o1 = (RadioButton) mLinearLayout.findViewById(R.id.rb_o1);
        rb_o2 = (RadioButton) mLinearLayout.findViewById(R.id.rb_o2);
        rb_o3 = (RadioButton) mLinearLayout.findViewById(R.id.rb_o3);

        rb_f2dy = (RadioButton) mLinearLayout.findViewById(R.id.rb_f2dy);
        rb_f2dn = (RadioButton) mLinearLayout.findViewById(R.id.rb_f2dn);



        //  ************************ Objects assing *********************
        fl_part1 = (FrameLayout) mLinearLayout.findViewById(R.id.fl_part1);

        //  ************************ Objects Buttoms *********************
        ImageButton btn_save = (ImageButton) mLinearLayout.findViewById(R.id.btn_save);

        //************* Start FrameLayout **************************
        fl_part1.setVisibility(View.VISIBLE);

        // **************** CLICK ON BUTTONS ********************

        btn_save.setOnClickListener(this);


        // ***************** LOCAD INFORMATION *************************
        loadRecord();

        return mLinearLayout;
    }

   


    // **************** Load DATA *************************
    public void loadRecord() {
        Conexion cnSET = new Conexion(getContext(),STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        Cursor cur_data = dbSET.rawQuery("SELECT _id, f1a, f1b, f2a1, f2a2, f2a3, f2a4, f2a5, f2a6, f2a7, f2a8, f2a9, f2a10, f2a11, f2a12, f2a13, f2a14, f2a15, f2a16, f2a17, f2a18," + // 20
                "f2a19, f2a20, f2a21, f2a22, f2a23, f2a24, f2a25, f2a26, f2a27, f2a28, f2a29, f2a30, f2a31, f2a32, f2a33, f2a34, f2a35, f2a36, f2a37, f2a38, " + //40
                "f2a39, f2a40, f2a41, f2a42, f2a43, f2a44, f2a45, f2a46, f2a47, f2a48, f2a49, f2a50, f2a51, f2a52, f2a53, f2a54, f2a55, f2a56, f2a57, f2a58, " +  // 60
                "f2a59, f2a60, f2a61, f2a62, f2a63, f2a64, f2a65, f2a66, f2a67, f2a68, f2a69, f2a70, f2a71, f2a72, f2a73, f2a74, f2a75, f2a76, f2a77, f2a78, " +  // 80
                "f2a79, f2a80, f2a81, f2a82, f2a83, f2a84, f2a85, f2a86, f2a87, f2a88, f2a89, f2a90, f2a91, f2a92, f2a93, f2a94, f2a95, f2a96, f2a97, f2a98, " +  // 100
                "f2a99, f2a100, f2a101, f2a102, f2a103, f2a104, f2a105, f2a106, f2a107, f2a108, f2a109, f2a110, f2a111, f2a112, f2a113, f2a114, f2a115, f2a116, f2a117, " + // 119
                "f2a118, f2a119, f2a120, f2a121, f2a122, f2a123, f2a124, f2a125, f2a126 " +  // 128
                "f2b1, f2b2, f2b3, f2b4, f2b5, f2c1, f2c2, f2c3, f2c4, f2c5, f2d, flag, f2a127, f2a128  FROM f WHERE _id=1",null); // 140 + 2

        cur_data.moveToFirst();
        if (cur_data.getCount() > 0) {
            // ********************* Part 1
            if (cur_data.getInt(1)==1) {_colf1ay.setChecked(true);} if (cur_data.getInt(1)==2) {_colf1an.setChecked(true);}
            _col2.setText(cur_data.getString(2));
            // ********************* Part 2
            _col3.setText(cur_data.getString(3));
            _col4.setText(cur_data.getString(4));
            _col5.setText(cur_data.getString(5));
            _col6.setText(cur_data.getString(6));
            _col7.setText(cur_data.getString(7));
            _col8.setText(cur_data.getString(8));
            _col9.setText(cur_data.getString(9));
            _col10.setText(cur_data.getString(10));
            _col11.setText(cur_data.getString(11));
            _col12.setText(cur_data.getString(12));
            _col13.setText(cur_data.getString(13));
            _col14.setText(cur_data.getString(14));
            _col15.setText(cur_data.getString(15));
            _col16.setText(cur_data.getString(16));
            _col17.setText(cur_data.getString(17));
            _col18.setText(cur_data.getString(18));
            _col19.setText(cur_data.getString(19));
            _col20.setText(cur_data.getString(20));
            _col21.setText(cur_data.getString(21));
            _col22.setText(cur_data.getString(22));
            _col23.setText(cur_data.getString(23));
            _col24.setText(cur_data.getString(24));
            _col25.setText(cur_data.getString(25));
            _col26.setText(cur_data.getString(26));
            _col27.setText(cur_data.getString(27));
            _col28.setText(cur_data.getString(28));
            _col29.setText(cur_data.getString(29));
            _col30.setText(cur_data.getString(30));
            _col31.setText(cur_data.getString(31));
            _col32.setText(cur_data.getString(32));
            _col33.setText(cur_data.getString(33));
            _col34.setText(cur_data.getString(34));
            _col35.setText(cur_data.getString(35));
            _col36.setText(cur_data.getString(36));
            _col37.setText(cur_data.getString(37));
            _col38.setText(cur_data.getString(38));
            _col39.setText(cur_data.getString(39));
            _col40.setText(cur_data.getString(40));
            _col41.setText(cur_data.getString(41));
            _col42.setText(cur_data.getString(42));
            _col43.setText(cur_data.getString(43));
            _col44.setText(cur_data.getString(44));
            _col45.setText(cur_data.getString(45));
            _col46.setText(cur_data.getString(46));
            _col47.setText(cur_data.getString(47));
            _col48.setText(cur_data.getString(48));
            _col49.setText(cur_data.getString(49));
            _col50.setText(cur_data.getString(50));
            _col51.setText(cur_data.getString(51));
            _col52.setText(cur_data.getString(52));
            _col53.setText(cur_data.getString(53));
            _col54.setText(cur_data.getString(54));
            _col55.setText(cur_data.getString(55));
            _col56.setText(cur_data.getString(56));
            _col57.setText(cur_data.getString(57));
            _col58.setText(cur_data.getString(58));
            _col59.setText(cur_data.getString(59));
            _col60.setText(cur_data.getString(60));
            _col61.setText(cur_data.getString(61));
            _col62.setText(cur_data.getString(62));
            _col63.setText(cur_data.getString(63));
            _col64.setText(cur_data.getString(64));
            _col65.setText(cur_data.getString(65));
            _col66.setText(cur_data.getString(66));
            _col67.setText(cur_data.getString(67));
            _col68.setText(cur_data.getString(68));
            _col69.setText(cur_data.getString(69));
            _col70.setText(cur_data.getString(70));
            _col71.setText(cur_data.getString(71));
            _col72.setText(cur_data.getString(72));
            _col73.setText(cur_data.getString(73));
            _col74.setText(cur_data.getString(74));
            _col75.setText(cur_data.getString(75));
            _col76.setText(cur_data.getString(76));
            _col77.setText(cur_data.getString(77));
            _col78.setText(cur_data.getString(78));
            _col79.setText(cur_data.getString(79));
            _col80.setText(cur_data.getString(80));
            _col81.setText(cur_data.getString(81));
            _col82.setText(cur_data.getString(82));
            _col83.setText(cur_data.getString(83));
            _col84.setText(cur_data.getString(84));
            _col85.setText(cur_data.getString(85));
            _col86.setText(cur_data.getString(86));

            _col87.setText(cur_data.getString(87));
            _col88.setText(cur_data.getString(88));
            _col89.setText(cur_data.getString(89));
            _col90.setText(cur_data.getString(90));
            _col91.setText(cur_data.getString(91));
            _col92.setText(cur_data.getString(92));
            _col93.setText(cur_data.getString(93));
            // ************* WRITING ***********
            _col94.setText(cur_data.getString(94));
            _col95.setText(cur_data.getString(95));
            _col96.setText(cur_data.getString(96));
            _col97.setText(cur_data.getString(97));
            _col98.setText(cur_data.getString(98));
            _col99.setText(cur_data.getString(99));
            _col100.setText(cur_data.getString(100));

            _col101.setText(cur_data.getString(101));
            _col102.setText(cur_data.getString(102));
            _col103.setText(cur_data.getString(103));
            _col104.setText(cur_data.getString(104));
            _col105.setText(cur_data.getString(105));
            _col106.setText(cur_data.getString(106));
            _col107.setText(cur_data.getString(107));

            // ********************** HEALTH AND ENV. EDUCATION
            _col108.setText(cur_data.getString(108));
            _col109.setText(cur_data.getString(109));
            _col110.setText(cur_data.getString(110));
            _col111.setText(cur_data.getString(111));
            _col112.setText(cur_data.getString(112));
            _col113.setText(cur_data.getString(113));
            _col114.setText(cur_data.getString(114));

            _col115.setText(cur_data.getString(115));
            _col116.setText(cur_data.getString(116));
            _col117.setText(cur_data.getString(117));
            _col118.setText(cur_data.getString(118));
            _col119.setText(cur_data.getString(119));
            _col120.setText(cur_data.getString(120));
            _col121.setText(cur_data.getString(121));

            // ************* OTHER ********
            _col122.setText(cur_data.getString(122));
            _col123.setText(cur_data.getString(123));
            _col124.setText(cur_data.getString(124));
            _col125.setText(cur_data.getString(125));
            _col126.setText(cur_data.getString(126));
            _col127.setText(cur_data.getString(127));
            _col128.setText(cur_data.getString(128));
            _col129.setText(cur_data.getString(140));
            _col130.setText(cur_data.getString(141));


            switch (cur_data.getInt(133)) {case 1: rb_la1.setChecked(true);break;case 2: rb_la2.setChecked(true);break;case 3: rb_la3.setChecked(true);break;}
            switch (cur_data.getInt(134)) {case 1: rb_m1.setChecked(true);break;case 2: rb_m2.setChecked(true);break;case 3: rb_m3.setChecked(true);break;}
            switch (cur_data.getInt(135)) {case 1: rb_s1.setChecked(true);break;case 2: rb_s2.setChecked(true);break;case 3: rb_s3.setChecked(true);break;}
            switch (cur_data.getInt(136)) {case 1: rb_ss1.setChecked(true);break;case 2: rb_ss2.setChecked(true);break;case 3: rb_ss3.setChecked(true);break;}
            switch (cur_data.getInt(137)) {case 1: rb_o1.setChecked(true);break;case 2: rb_o2.setChecked(true);break;case 3: rb_o3.setChecked(true);break;}
            switch (cur_data.getInt(138)) {case 1: rb_f2dy.setChecked(true);break;case 2: rb_f2dn.setChecked(true);break;}
            //if (cur_data.getInt(139)==1) {rb_f2dy.setChecked(true);} if (cur_data.getInt(139)==2) {rb_f2dn.setChecked(true);}

            _IU = "U";
        } else {_IU = "I";}
        cur_data.close();
        dbSET.close();
        cnSET.close();
    }

    public void updateRecord () {
        Conexion cnSET = new Conexion(getContext(),STATICS_ROOT + File.separator + "sisdb.sqlite", null);
        SQLiteDatabase dbSET = cnSET.getReadableDatabase();
        String sql = "", delimit="%", s1;
        // ***************** CONTENT TO RECORD-SET **************************
        ContentValues reg = new ContentValues();
            if (_IU == "I" ) {reg.put("_id",1);}
            reg.put("flag", _IU); //sql = sql + _IU + ";f;" + SettingsMenuInfra_0.EMIS_code + delimit;
            sql = sql + "f" + delimit + SettingsMenuInfra_0.EMIS_code + delimit;
            // **************** Frame 1  ******************
            if (_colf1ay.isChecked() == true) {reg.put("f1a", 1); sql = sql + "1" + delimit;}
            else if (_colf1an.isChecked() == true) {reg.put("f1a", 2); sql = sql + "2" + delimit;}
            else {sql = sql + "" + delimit;}

            if (!_col2.getText().toString().isEmpty()){reg.put("f1b", Integer.parseInt(_col2.getText().toString()));} sql = sql + _col2.getText().toString() + delimit;
            // **************** Frame 2  ******************
            if (!_col3.getText().toString().isEmpty()){reg.put("f2a1", Integer.parseInt(_col3.getText().toString()));} sql = sql + _col3.getText().toString() + delimit;
            if (!_col4.getText().toString().isEmpty()){reg.put("f2a2", Integer.parseInt(_col4.getText().toString()));} sql = sql + _col4.getText().toString() + delimit;
            if (!_col5.getText().toString().isEmpty()){reg.put("f2a3", Integer.parseInt(_col5.getText().toString()));} sql = sql + _col5.getText().toString() + delimit;
            if (!_col6.getText().toString().isEmpty()){reg.put("f2a4", Integer.parseInt(_col6.getText().toString()));} sql = sql + _col6.getText().toString() + delimit;
            if (!_col7.getText().toString().isEmpty()){reg.put("f2a5", Integer.parseInt(_col7.getText().toString()));} sql = sql + _col7.getText().toString() + delimit;
            if (!_col8.getText().toString().isEmpty()){reg.put("f2a6", Integer.parseInt(_col8.getText().toString()));} sql = sql + _col8.getText().toString() + delimit;
            if (!_col9.getText().toString().isEmpty()){reg.put("f2a7", Integer.parseInt(_col9.getText().toString()));} sql = sql + _col9.getText().toString() + delimit;
            if (!_col10.getText().toString().isEmpty()){reg.put("f2a8", Integer.parseInt(_col10.getText().toString()));} sql = sql + _col10.getText().toString() + delimit;
            if (!_col11.getText().toString().isEmpty()){reg.put("f2a9", Integer.parseInt(_col11.getText().toString()));} sql = sql + _col11.getText().toString() + delimit;
            if (!_col12.getText().toString().isEmpty()){reg.put("f2a10", Integer.parseInt(_col12.getText().toString()));} sql = sql + _col12.getText().toString() + delimit;
            if (!_col13.getText().toString().isEmpty()){reg.put("f2a11", Integer.parseInt(_col13.getText().toString()));} sql = sql + _col13.getText().toString() + delimit;
            if (!_col14.getText().toString().isEmpty()){reg.put("f2a12", Integer.parseInt(_col14.getText().toString()));} sql = sql + _col14.getText().toString() + delimit;
            if (!_col15.getText().toString().isEmpty()){reg.put("f2a13", Integer.parseInt(_col15.getText().toString()));} sql = sql + _col15.getText().toString() + delimit;
            if (!_col16.getText().toString().isEmpty()){reg.put("f2a14", Integer.parseInt(_col16.getText().toString()));} sql = sql + _col16.getText().toString() + delimit;
            if (!_col17.getText().toString().isEmpty()){reg.put("f2a15", Integer.parseInt(_col17.getText().toString()));} sql = sql + _col17.getText().toString() + delimit;
            if (!_col18.getText().toString().isEmpty()){reg.put("f2a16", Integer.parseInt(_col18.getText().toString()));} sql = sql + _col18.getText().toString() + delimit;
            if (!_col19.getText().toString().isEmpty()){reg.put("f2a17", Integer.parseInt(_col19.getText().toString()));} sql = sql + _col19.getText().toString() + delimit;
            if (!_col20.getText().toString().isEmpty()){reg.put("f2a18", Integer.parseInt(_col20.getText().toString()));} sql = sql + _col20.getText().toString() + delimit;
            if (!_col21.getText().toString().isEmpty()){reg.put("f2a19", Integer.parseInt(_col21.getText().toString()));} sql = sql + _col21.getText().toString() + delimit;
            if (!_col22.getText().toString().isEmpty()){reg.put("f2a20", Integer.parseInt(_col22.getText().toString()));} sql = sql + _col22.getText().toString() + delimit;
            if (!_col23.getText().toString().isEmpty()){reg.put("f2a21", Integer.parseInt(_col23.getText().toString()));} sql = sql + _col23.getText().toString() + delimit;
            if (!_col24.getText().toString().isEmpty()){reg.put("f2a22", Integer.parseInt(_col24.getText().toString()));} sql = sql + _col24.getText().toString() + delimit;
            if (!_col25.getText().toString().isEmpty()){reg.put("f2a23", Integer.parseInt(_col25.getText().toString()));} sql = sql + _col25.getText().toString() + delimit;
            if (!_col26.getText().toString().isEmpty()){reg.put("f2a24", Integer.parseInt(_col26.getText().toString()));} sql = sql + _col26.getText().toString() + delimit;
            if (!_col27.getText().toString().isEmpty()){reg.put("f2a25", Integer.parseInt(_col27.getText().toString()));} sql = sql + _col27.getText().toString() + delimit;
            if (!_col28.getText().toString().isEmpty()){reg.put("f2a26", Integer.parseInt(_col28.getText().toString()));} sql = sql + _col28.getText().toString() + delimit;
            if (!_col29.getText().toString().isEmpty()){reg.put("f2a27", Integer.parseInt(_col29.getText().toString()));} sql = sql + _col29.getText().toString() + delimit;
            if (!_col30.getText().toString().isEmpty()){reg.put("f2a28", Integer.parseInt(_col30.getText().toString()));} sql = sql + _col30.getText().toString() + delimit;
            if (!_col31.getText().toString().isEmpty()){reg.put("f2a29", Integer.parseInt(_col31.getText().toString()));} sql = sql + _col31.getText().toString() + delimit;
            if (!_col32.getText().toString().isEmpty()){reg.put("f2a30", Integer.parseInt(_col32.getText().toString()));} sql = sql + _col32.getText().toString() + delimit;
            if (!_col33.getText().toString().isEmpty()){reg.put("f2a31", Integer.parseInt(_col33.getText().toString()));} sql = sql + _col33.getText().toString() + delimit;
            if (!_col34.getText().toString().isEmpty()){reg.put("f2a32", Integer.parseInt(_col34.getText().toString()));} sql = sql + _col34.getText().toString() + delimit;
            if (!_col35.getText().toString().isEmpty()){reg.put("f2a33", Integer.parseInt(_col35.getText().toString()));} sql = sql + _col35.getText().toString() + delimit;
            if (!_col36.getText().toString().isEmpty()){reg.put("f2a34", Integer.parseInt(_col36.getText().toString()));} sql = sql + _col36.getText().toString() + delimit;
            if (!_col37.getText().toString().isEmpty()){reg.put("f2a35", Integer.parseInt(_col37.getText().toString()));} sql = sql + _col37.getText().toString() + delimit;
            if (!_col38.getText().toString().isEmpty()){reg.put("f2a36", Integer.parseInt(_col38.getText().toString()));} sql = sql + _col38.getText().toString() + delimit;
            if (!_col39.getText().toString().isEmpty()){reg.put("f2a37", Integer.parseInt(_col39.getText().toString()));} sql = sql + _col39.getText().toString() + delimit;
            if (!_col40.getText().toString().isEmpty()){reg.put("f2a38", Integer.parseInt(_col40.getText().toString()));} sql = sql + _col40.getText().toString() + delimit;
            if (!_col41.getText().toString().isEmpty()){reg.put("f2a39", Integer.parseInt(_col41.getText().toString()));} sql = sql + _col41.getText().toString() + delimit;
            if (!_col42.getText().toString().isEmpty()){reg.put("f2a40", Integer.parseInt(_col42.getText().toString()));} sql = sql + _col42.getText().toString() + delimit;
            if (!_col43.getText().toString().isEmpty()){reg.put("f2a41", Integer.parseInt(_col43.getText().toString()));} sql = sql + _col43.getText().toString() + delimit;
            if (!_col44.getText().toString().isEmpty()){reg.put("f2a42", Integer.parseInt(_col44.getText().toString()));} sql = sql + _col44.getText().toString() + delimit;
            if (!_col45.getText().toString().isEmpty()){reg.put("f2a43", Integer.parseInt(_col45.getText().toString()));} sql = sql + _col45.getText().toString() + delimit;
            if (!_col46.getText().toString().isEmpty()){reg.put("f2a44", Integer.parseInt(_col46.getText().toString()));} sql = sql + _col46.getText().toString() + delimit;
            if (!_col47.getText().toString().isEmpty()){reg.put("f2a45", Integer.parseInt(_col47.getText().toString()));} sql = sql + _col47.getText().toString() + delimit;
            if (!_col48.getText().toString().isEmpty()){reg.put("f2a46", Integer.parseInt(_col48.getText().toString()));} sql = sql + _col48.getText().toString() + delimit;
            if (!_col49.getText().toString().isEmpty()){reg.put("f2a47", Integer.parseInt(_col49.getText().toString()));} sql = sql + _col49.getText().toString() + delimit;
            if (!_col50.getText().toString().isEmpty()){reg.put("f2a48", Integer.parseInt(_col50.getText().toString()));} sql = sql + _col50.getText().toString() + delimit;
            if (!_col51.getText().toString().isEmpty()){reg.put("f2a49", Integer.parseInt(_col51.getText().toString()));} sql = sql + _col51.getText().toString() + delimit;
            if (!_col52.getText().toString().isEmpty()){reg.put("f2a50", Integer.parseInt(_col52.getText().toString()));} sql = sql + _col52.getText().toString() + delimit;
            if (!_col53.getText().toString().isEmpty()){reg.put("f2a51", Integer.parseInt(_col53.getText().toString()));} sql = sql + _col53.getText().toString() + delimit;
            if (!_col54.getText().toString().isEmpty()){reg.put("f2a52", Integer.parseInt(_col54.getText().toString()));} sql = sql + _col54.getText().toString() + delimit;
            if (!_col55.getText().toString().isEmpty()){reg.put("f2a53", Integer.parseInt(_col55.getText().toString()));} sql = sql + _col55.getText().toString() + delimit;
            if (!_col56.getText().toString().isEmpty()){reg.put("f2a54", Integer.parseInt(_col56.getText().toString()));} sql = sql + _col56.getText().toString() + delimit;
            if (!_col57.getText().toString().isEmpty()){reg.put("f2a55", Integer.parseInt(_col57.getText().toString()));} sql = sql + _col57.getText().toString() + delimit;
            if (!_col58.getText().toString().isEmpty()){reg.put("f2a56", Integer.parseInt(_col58.getText().toString()));} sql = sql + _col58.getText().toString() + delimit;
            if (!_col59.getText().toString().isEmpty()){reg.put("f2a57", Integer.parseInt(_col59.getText().toString()));} sql = sql + _col59.getText().toString() + delimit;
            if (!_col60.getText().toString().isEmpty()){reg.put("f2a58", Integer.parseInt(_col60.getText().toString()));} sql = sql + _col60.getText().toString() + delimit;
            if (!_col61.getText().toString().isEmpty()){reg.put("f2a59", Integer.parseInt(_col61.getText().toString()));} sql = sql + _col61.getText().toString() + delimit;
            if (!_col62.getText().toString().isEmpty()){reg.put("f2a60", Integer.parseInt(_col62.getText().toString()));} sql = sql + _col62.getText().toString() + delimit;
            if (!_col63.getText().toString().isEmpty()){reg.put("f2a61", Integer.parseInt(_col63.getText().toString()));} sql = sql + _col63.getText().toString() + delimit;
            if (!_col64.getText().toString().isEmpty()){reg.put("f2a62", Integer.parseInt(_col64.getText().toString()));} sql = sql + _col64.getText().toString() + delimit;
            if (!_col65.getText().toString().isEmpty()){reg.put("f2a63", Integer.parseInt(_col65.getText().toString()));} sql = sql + _col65.getText().toString() + delimit;
            if (!_col66.getText().toString().isEmpty()){reg.put("f2a64", Integer.parseInt(_col66.getText().toString()));} sql = sql + _col66.getText().toString() + delimit;
            if (!_col67.getText().toString().isEmpty()){reg.put("f2a65", Integer.parseInt(_col67.getText().toString()));} sql = sql + _col67.getText().toString() + delimit;
            if (!_col68.getText().toString().isEmpty()){reg.put("f2a66", Integer.parseInt(_col68.getText().toString()));} sql = sql + _col68.getText().toString() + delimit;
            if (!_col69.getText().toString().isEmpty()){reg.put("f2a67", Integer.parseInt(_col69.getText().toString()));} sql = sql + _col69.getText().toString() + delimit;
            if (!_col70.getText().toString().isEmpty()){reg.put("f2a68", Integer.parseInt(_col70.getText().toString()));} sql = sql + _col70.getText().toString() + delimit;
            if (!_col71.getText().toString().isEmpty()){reg.put("f2a69", Integer.parseInt(_col71.getText().toString()));} sql = sql + _col71.getText().toString() + delimit;
            if (!_col72.getText().toString().isEmpty()){reg.put("f2a70", Integer.parseInt(_col72.getText().toString()));} sql = sql + _col72.getText().toString() + delimit;
            if (!_col73.getText().toString().isEmpty()){reg.put("f2a71", Integer.parseInt(_col73.getText().toString()));} sql = sql + _col73.getText().toString() + delimit;
            if (!_col74.getText().toString().isEmpty()){reg.put("f2a72", Integer.parseInt(_col74.getText().toString()));} sql = sql + _col74.getText().toString() + delimit;
            if (!_col75.getText().toString().isEmpty()){reg.put("f2a73", Integer.parseInt(_col75.getText().toString()));} sql = sql + _col75.getText().toString() + delimit;
            if (!_col76.getText().toString().isEmpty()){reg.put("f2a74", Integer.parseInt(_col76.getText().toString()));} sql = sql + _col76.getText().toString() + delimit;
            if (!_col77.getText().toString().isEmpty()){reg.put("f2a75", Integer.parseInt(_col77.getText().toString()));} sql = sql + _col77.getText().toString() + delimit;
            if (!_col78.getText().toString().isEmpty()){reg.put("f2a76", Integer.parseInt(_col78.getText().toString()));} sql = sql + _col78.getText().toString() + delimit;
            if (!_col79.getText().toString().isEmpty()){reg.put("f2a77", Integer.parseInt(_col79.getText().toString()));} sql = sql + _col79.getText().toString() + delimit;

            if (!_col80.getText().toString().isEmpty()){reg.put("f2a78", Integer.parseInt(_col80.getText().toString()));} sql = sql + _col80.getText().toString() + delimit;
            if (!_col81.getText().toString().isEmpty()){reg.put("f2a79", Integer.parseInt(_col81.getText().toString()));} sql = sql + _col81.getText().toString() + delimit;
            if (!_col82.getText().toString().isEmpty()){reg.put("f2a80", Integer.parseInt(_col82.getText().toString()));} sql = sql + _col82.getText().toString() + delimit;
            if (!_col83.getText().toString().isEmpty()){reg.put("f2a81", Integer.parseInt(_col83.getText().toString()));} sql = sql + _col83.getText().toString() + delimit;
            if (!_col84.getText().toString().isEmpty()){reg.put("f2a82", Integer.parseInt(_col84.getText().toString()));} sql = sql + _col84.getText().toString() + delimit;
            if (!_col85.getText().toString().isEmpty()){reg.put("f2a83", Integer.parseInt(_col85.getText().toString()));} sql = sql + _col85.getText().toString() + delimit;
            if (!_col86.getText().toString().isEmpty()){reg.put("f2a84", Integer.parseInt(_col86.getText().toString()));} sql = sql + _col86.getText().toString() + delimit;

            if (!_col87.getText().toString().isEmpty()){reg.put("f2a85", Integer.parseInt(_col87.getText().toString()));} sql = sql + _col87.getText().toString() + delimit;
            if (!_col88.getText().toString().isEmpty()){reg.put("f2a86", Integer.parseInt(_col88.getText().toString()));} sql = sql + _col88.getText().toString() + delimit;
            if (!_col89.getText().toString().isEmpty()){reg.put("f2a87", Integer.parseInt(_col89.getText().toString()));} sql = sql + _col89.getText().toString() + delimit;
            if (!_col90.getText().toString().isEmpty()){reg.put("f2a88", Integer.parseInt(_col90.getText().toString()));} sql = sql + _col90.getText().toString() + delimit;
            if (!_col91.getText().toString().isEmpty()){reg.put("f2a89", Integer.parseInt(_col91.getText().toString()));} sql = sql + _col91.getText().toString() + delimit;
            if (!_col92.getText().toString().isEmpty()){reg.put("f2a90", Integer.parseInt(_col92.getText().toString()));} sql = sql + _col92.getText().toString() + delimit;
            if (!_col93.getText().toString().isEmpty()){reg.put("f2a91", Integer.parseInt(_col93.getText().toString()));} sql = sql + _col93.getText().toString() + delimit;

            if (!_col94.getText().toString().isEmpty()){reg.put("f2a92", Integer.parseInt(_col94.getText().toString()));} sql = sql + _col94.getText().toString() + delimit;
            if (!_col95.getText().toString().isEmpty()){reg.put("f2a93", Integer.parseInt(_col95.getText().toString()));} sql = sql + _col95.getText().toString() + delimit;
            if (!_col96.getText().toString().isEmpty()){reg.put("f2a94", Integer.parseInt(_col96.getText().toString()));} sql = sql + _col96.getText().toString() + delimit;
            if (!_col97.getText().toString().isEmpty()){reg.put("f2a95", Integer.parseInt(_col97.getText().toString()));} sql = sql + _col97.getText().toString() + delimit;
            if (!_col98.getText().toString().isEmpty()){reg.put("f2a96", Integer.parseInt(_col98.getText().toString()));} sql = sql + _col98.getText().toString() + delimit;
            if (!_col99.getText().toString().isEmpty()){reg.put("f2a97", Integer.parseInt(_col99.getText().toString()));} sql = sql + _col99.getText().toString() + delimit;
            if (!_col100.getText().toString().isEmpty()){reg.put("f2a98", Integer.parseInt(_col100.getText().toString()));} sql = sql + _col100.getText().toString() + delimit;

// ********************** ARITHMETIC
            if (!_col101.getText().toString().isEmpty()){reg.put("f2a99", Integer.parseInt(_col101.getText().toString()));} sql = sql + _col101.getText().toString() + delimit;
            if (!_col102.getText().toString().isEmpty()){reg.put("f2a100", Integer.parseInt(_col102.getText().toString()));} sql = sql + _col102.getText().toString() + delimit;
            if (!_col103.getText().toString().isEmpty()){reg.put("f2a101", Integer.parseInt(_col103.getText().toString()));} sql = sql + _col103.getText().toString() + delimit;
            if (!_col104.getText().toString().isEmpty()){reg.put("f2a102", Integer.parseInt(_col104.getText().toString()));} sql = sql + _col104.getText().toString() + delimit;
            if (!_col105.getText().toString().isEmpty()){reg.put("f2a103", Integer.parseInt(_col105.getText().toString()));} sql = sql + _col105.getText().toString() + delimit;
            if (!_col106.getText().toString().isEmpty()){reg.put("f2a104", Integer.parseInt(_col106.getText().toString()));} sql = sql + _col106.getText().toString() + delimit;
            if (!_col107.getText().toString().isEmpty()){reg.put("f2a105", Integer.parseInt(_col107.getText().toString()));} sql = sql + _col107.getText().toString() + delimit;

// ********************** HEALTH AND EVN. EDUCATION
            if (!_col108.getText().toString().isEmpty()){reg.put("f2a106", Integer.parseInt(_col108.getText().toString()));} sql = sql + _col108.getText().toString() + delimit;
            if (!_col109.getText().toString().isEmpty()){reg.put("f2a107", Integer.parseInt(_col109.getText().toString()));} sql = sql + _col109.getText().toString() + delimit;
            if (!_col110.getText().toString().isEmpty()){reg.put("f2a108", Integer.parseInt(_col110.getText().toString()));} sql = sql + _col110.getText().toString() + delimit;
            if (!_col111.getText().toString().isEmpty()){reg.put("f2a109", Integer.parseInt(_col111.getText().toString()));} sql = sql + _col111.getText().toString() + delimit;
            if (!_col112.getText().toString().isEmpty()){reg.put("f2a110", Integer.parseInt(_col112.getText().toString()));} sql = sql + _col112.getText().toString() + delimit;
            if (!_col113.getText().toString().isEmpty()){reg.put("f2a111", Integer.parseInt(_col113.getText().toString()));} sql = sql + _col113.getText().toString() + delimit;
            if (!_col114.getText().toString().isEmpty()){reg.put("f2a112", Integer.parseInt(_col114.getText().toString()));} sql = sql + _col114.getText().toString() + delimit;

            if (!_col115.getText().toString().isEmpty()){reg.put("f2a113", Integer.parseInt(_col115.getText().toString()));} sql = sql + _col115.getText().toString() + delimit;
            if (!_col116.getText().toString().isEmpty()){reg.put("f2a114", Integer.parseInt(_col116.getText().toString()));} sql = sql + _col116.getText().toString() + delimit;
            if (!_col117.getText().toString().isEmpty()){reg.put("f2a115", Integer.parseInt(_col117.getText().toString()));} sql = sql + _col117.getText().toString() + delimit;
            if (!_col118.getText().toString().isEmpty()){reg.put("f2a116", Integer.parseInt(_col118.getText().toString()));} sql = sql + _col118.getText().toString() + delimit;
            if (!_col119.getText().toString().isEmpty()){reg.put("f2a117", Integer.parseInt(_col119.getText().toString()));} sql = sql + _col119.getText().toString() + delimit;
            if (!_col120.getText().toString().isEmpty()){reg.put("f2a118", Integer.parseInt(_col120.getText().toString()));} sql = sql + _col120.getText().toString() + delimit;
            if (!_col121.getText().toString().isEmpty()){reg.put("f2a119", Integer.parseInt(_col121.getText().toString()));} sql = sql + _col121.getText().toString() + delimit;

        // ************* OTHER ********
            if (!_col122.getText().toString().isEmpty()){reg.put("f2a120", Integer.parseInt(_col122.getText().toString()));} sql = sql + _col122.getText().toString() + delimit;
            if (!_col123.getText().toString().isEmpty()){reg.put("f2a121", Integer.parseInt(_col123.getText().toString()));} sql = sql + _col123.getText().toString() + delimit;
            if (!_col124.getText().toString().isEmpty()){reg.put("f2a122", Integer.parseInt(_col124.getText().toString()));} sql = sql + _col124.getText().toString() + delimit;
            if (!_col125.getText().toString().isEmpty()){reg.put("f2a123", Integer.parseInt(_col125.getText().toString()));} sql = sql + _col125.getText().toString() + delimit;
            if (!_col126.getText().toString().isEmpty()){reg.put("f2a124", Integer.parseInt(_col126.getText().toString()));} sql = sql + _col126.getText().toString() + delimit;
            if (!_col127.getText().toString().isEmpty()){reg.put("f2a125", Integer.parseInt(_col127.getText().toString()));} sql = sql + _col127.getText().toString() + delimit;
            if (!_col128.getText().toString().isEmpty()){reg.put("f2a126", Integer.parseInt(_col128.getText().toString()));} sql = sql + _col128.getText().toString() + delimit;



            // **************** Frame 3  ******************
            sql = sql + "" + delimit;
            sql = sql + "" + delimit;
            sql = sql + "" + delimit;
            sql = sql + "" + delimit;
            sql = sql + "" + delimit;

            if (rb_la1.isChecked() == true) {reg.put("f2c1", 1); sql = sql + "1" + delimit;}
            else if (rb_la2.isChecked() == true) {reg.put("f2c1", 2); sql = sql + "2" + delimit;}
            else if (rb_la3.isChecked() == true) {reg.put("f2c1", 3); sql = sql + "3" + delimit;}
            else {sql = sql + "" + delimit;}

            if (rb_m1.isChecked() == true) {reg.put("f2c2", 1); sql = sql + "1" + delimit;}
            else if (rb_m2.isChecked() == true) {reg.put("f2c2", 2); sql = sql + "2" + delimit;}
            else if (rb_m3.isChecked() == true) {reg.put("f2c2", 3); sql = sql + "3" + delimit;}
            else {sql = sql + "" + delimit;}


            if (rb_s1.isChecked() == true) {reg.put("f2c3", 1); sql = sql + "1" + delimit;}
            else if (rb_s2.isChecked() == true) {reg.put("f2c3", 2); sql = sql + "2" + delimit;}
            else if (rb_s3.isChecked() == true) {reg.put("f2c3", 3); sql = sql + "3" + delimit;}
            else {sql = sql + "" + delimit;}

            if (rb_ss1.isChecked() == true) {reg.put("f2c4", 1); sql = sql + "1" + delimit;}
            else if (rb_ss2.isChecked() == true) {reg.put("f2c4", 2); sql = sql + "2" + delimit;}
            else if (rb_ss3.isChecked() == true) {reg.put("f2c4", 3); sql = sql + "3" + delimit;}
            else {sql = sql + "" + delimit;}

            if (rb_o1.isChecked() == true) {reg.put("f2c5", 1); sql = sql + "1" + delimit;}
            else if (rb_o2.isChecked() == true) {reg.put("f2c5", 2); sql = sql + "2" + delimit;}
            else if (rb_o3.isChecked() == true) {reg.put("f2c5", 3); sql = sql + "3" + delimit;}
            else {sql = sql + "" + delimit;}

            if (rb_f2dy.isChecked() == true) {reg.put("f2d", 1); sql = sql + "1" + delimit;}
            else if (rb_f2dn.isChecked() == true) {reg.put("f2d", 2); sql = sql + "2" + delimit;}
            else {sql = sql + "" + delimit;}

        if (!_col129.getText().toString().isEmpty()){reg.put("f2a127", Integer.parseInt(_col129.getText().toString()));} sql = sql + _col129.getText().toString() + delimit;
        if (!_col130.getText().toString().isEmpty()){reg.put("f2a128", Integer.parseInt(_col130.getText().toString()));} sql = sql + _col130.getText().toString() + delimit;

        sql = sql + _IU;
            try {
                // ****************** Fill Bitacora
                ContentValues Bitacora = new ContentValues();
                Bitacora.put("sis_sql",sql);
                dbSET.insert("sisupdate",null,Bitacora);
                // ********************* Fill TABLE d
                if (_IU=="I") {dbSET.insert("f", null, reg); _IU="U";}
                else {dbSET.update("f", reg, "_id=1", null);}

                toolsfncs.dialogAlertConfirm(getContext(),getResources(),9);
                //Toast.makeText(getContext(), getResources().getString(R.string.str_bl_msj5), Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(getContext(), getResources().getString(R.string.str_bl2_msj1), Toast.LENGTH_SHORT).show();
            }
        dbSET.close();
        cnSET.close();
    }

    // **************** CLICK ON BUTTONS ********************
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_save:
                dialogAlert(1);
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
    public void aceptar() {updateRecord();}
    public void cancelar() {} //getActivity().finish();}
    // *********** END Control Alerts ************************


}
