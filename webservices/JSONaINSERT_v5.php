

<?php 
  $host = "localhost";
  $user = "user";
  $pass = "password";

  $con = mysql_connect($host,$user, $pass);
  
  if (!$con)
    {
      die('Could not connect: ' . mysql_error());
    }
  
  //mysql_select_db("iraq_tasawi", $con) or die ("error");
  mysql_select_db("tz_sis_schools", $con) or die ("error");
  
    
     $str = $_GET["str"];    
     $datos=explode("%",$str);
//echo $str;

    for($i=0;$i<count($datos);$i++){
      if($datos[$i]=="" || $datos[$i]=="null" ){
        $datos[$i]="0";
      }
     // $datos[$i]=str_replace("'","\'",$datos[$i]);
    }
    


     ?>
     
     <?php

//new data base

    function deposits_new ($datos){
       
          $sql= "INSERT INTO `tz_sis_schools`.`school_finance_deposits` (`emis_code`,`date`,`type`,`amount`,`comment`) VALUES ('".$datos[1]."','".$datos[2]."','".$datos[3]."','".$datos[4]."','".$datos[5]."')";
          //echo $sql;
          mysql_query($sql) or die ("error_sql");
        
     }

     function expenditures_new ($datos){
          $sql= "INSERT INTO `tz_sis_schools`.`school_finance_expenditures` (`emis_code`,`date`,`type`,`amount`,`comment`) VALUES ('".$datos[1]."','".$datos[2]."','".$datos[3]."','".$datos[4]."','".$datos[5]."')";
          mysql_query($sql) or die ("error_sql");
     }

    function teachers_new($datos){
          $base_teacher=array("emis_code","teacher_id","teacher_staff","surname","givenname","sex","year_birthday","check_number","position_head_school","position_deputy_head","position_registar","position_custodian","position_teacher_trainer","position_other","teaching_preprimary","teaching_primary","teaching_secondary_o","teaching_secondary_a","teaching_no_teaching","professional_qualifications","academic_qualifications","subject_teaching_language_arts","subject_teaching_mathematics","subjust_teaching_science","subject_teaching_social_stidies","subject_teaching_other","year_position","salary","phone","email","address","exit_t","yearexit");
          $data=$datos;
          if($data[0]=="teacher"){
            $tabla="tz_sis_schools.school_teachers_staff";
          }

          $sql="select * from ".$tabla." where emis_code=".$data[1]." and teacher_id=".$data[2];
          $result = mysql_query($sql) or die ("error_query");
          $numero_filas = mysql_num_rows($result);

          if ($numero_filas > 0)
          {
              $sql ="update ".$tabla." set ";
              for ($x=0;$x<count(${"base_".$data[0]});$x++){
                  if ($x==count(${"base_".$data[0]})-1){
                  $sql=$sql.${"base_".$data[0]}[$x]."='".$data[$x+1];
                  if ($data[0]=="teacher"){ $sql = $sql."' where emis_code=".$data[1]." and teacher_id=".$data[2];}
                  
                  }
                  else
                  {$sql=$sql.${"base_".$data[0]}[$x]."='".$data[$x+1]."',";}

              } 

              
          }
          else
          {
           $sql= "insert into ".$tabla." (";
              for ($x=0;$x<count(${"base_".$data[0]});$x++){
                  if ($x==count(${"base_".$data[0]})-1){
                    $sql=$sql.${"base_".$data[0]}[$x].") ";
                  }
                  else
                  {$sql=$sql.${"base_".$data[0]}[$x].",";}
                  
              }   
                  $sql=$sql." value (";

              for ($x=0;$x<count($data)-2;$x++){
                  if ($x==count($data)-3){
                    $sql=$sql."'".$data[$x+1]."') ";
                  }
                  else
                  {$sql=$sql."'".$data[$x+1]."',";}
                  
              }
          }
          mysql_query($sql) or die ("error_sql");
    }

    function attendance_new($datos){
       $base_attendance=array("date_submited","emis_code","teacher_id","teacher_present","person_charge","school_shift","school_level","school_grade","school_section","school_subject","student_present","student_absent");
      $data=$datos ;
      if($data[0]=="attendance"){
            $tabla="tz_sis_schools.student_attendance";
          }
          $sql="select * from ".$tabla." where date_submited='".$data[1]."' and emis_code=".$data[2]." and teacher_id=".$data[3]." and school_shift=".$data[6]." and school_level=".$data[7]." and school_grade=".$data[8]." and school_section=".$data[9]." and school_subject=".$data[10];
          $result = mysql_query($sql) or die ("error_query");
          $numero_filas = mysql_num_rows($result);

          if ($numero_filas > 0)
          {
              $sql ="update ".$tabla." set ";
              for ($x=0;$x<count(${"base_".$data[0]});$x++){
              if ($x==count(${"base_".$data[0]})-1){
              $sql=$sql.${"base_".$data[0]}[$x]."='".$data[$x+1];
              if ($data[0]=="attendance"){$sql = $sql."' where date_submited='".$data[1]."' and emis_code=".$data[2]." and teacher_id=".$data[3]." and school_shift=".$data[6]." and school_level=".$data[7]." and school_grade=".$data[8]." and school_section=".$data[9]." and school_subject=".$data[10];}
              }
              else
              {$sql=$sql.${"base_".$data[0]}[$x]."='".$data[$x+1]."',";}

              } 

              
          }

              /*fin nuevo query */
              else{

              $sql= "insert into ".$tabla." (";
              for ($x=0;$x<count(${"base_".$data[0]});$x++){
              if ($x==count(${"base_".$data[0]})-1){
                $sql=$sql.${"base_".$data[0]}[$x].") ";
              }
              else
              { $sql=$sql.${"base_".$data[0]}[$x].",";}
              
              }   
                $sql=$sql." value (";

                for ($x=0;$x<count($data)-2;$x++){
                    if ($x==count($data)-3){
                      $sql=$sql."'".$data[$x+1]."') ";
                    }
                    else
                    {$sql=$sql."'".$data[$x+1]."',";}
                    
                }
                }
                mysql_query($sql) or die ("error_sql");
    }

    function evaluation_new($datos){
       $base_evaluation=array("date","emis_code","teacher_code","school_shift","school_level","school_grade","school_section","school_subject","test_number","student_number","student_average_grade");
       $data=$datos;

        if ($data[0]=="evaluation"){
          $tabla = "tz_sis_schools.student_evaluation";
        }
          $sql1="select * from ".$tabla." where date='".$data[1]."' and emis_code=".$data[2]." and teacher_code=".$data[3]." and school_shift=".$data[4]." and school_level=".$data[5]." and school_grade=".$data[6]." and school_section=".$data[7]." and school_subject=".$data[8]." and test_number=".$data[9];
            $result1 = mysql_query($sql1) or die ("error_query1");
            $numero_filas1 = mysql_num_rows($result1);

            if ($numero_filas1 > 0)
            {
                $sql ="update ".$tabla." set ";
                for ($x=0;$x<count(${"base_".$data[0]});$x++){
                if ($x==count(${"base_".$data[0]})-1){
                  $sql=$sql.${"base_".$data[0]}[$x]."='".$data[$x+1];
                  if ($data[0]=="evaluation"){$sql = $sql."' where date='".$data[1]."' and emis_code=".$data[2]." and teacher_code=".$data[3]." and school_shift=".$data[4]." and school_level=".$data[5]." and school_grade=".$data[6]." and school_section=".$data[7]." and school_subject=".$data[8]." and test_number=".$data[9];}
                }
                else
                {$sql=$sql.${"base_".$data[0]}[$x]."='".$data[$x+1]."',";}

                } 
            }
            else
            {

             $sql= "insert into ".$tabla." (";
                for ($x=0;$x<count(${"base_".$data[0]});$x++){
                    if ($x==count(${"base_".$data[0]})-1){
                      $sql=$sql.${"base_".$data[0]}[$x].") ";
                    }
                    else
                    {$sql=$sql.${"base_".$data[0]}[$x].",";}
                    
                }   
                    $sql=$sql." value (";

                for ($x=0;$x<count($data)-2;$x++){
                    if ($x==count($data)-3){
                      $sql=$sql."'".$data[$x+1]."') ";
                    }
                    else
                    {$sql=$sql."'".$data[$x+1]."',";}
                    
                }
            }
           // echo $sql;
            mysql_query($sql) or die ("error_sql");
    }

    function behaviour_new($datos){
      $base_behaviour=array("date","emis_code","teacher_code","school_shift","school_level","school_grade","school_section","school_subject","student_reason1","student_reason2","student_reason3","student_reason4"); 
      $data=$datos;

      if ($data[0]=="behaviour"){
          $tabla = "tz_sis_schools.student_behaviour";
        }
            $sql1="select * from ".$tabla." where date='".$data[1]."' and emis_code=".$data[2]." and teacher_code=".$data[3]." and school_shift=".$data[4]." and school_level=".$data[5]." and school_grade=".$data[6]." and school_section=".$data[7]." and school_subject=".$data[8];
            $result1 = mysql_query($sql1) or die ("error_query1");
            $numero_filas1 = mysql_num_rows($result1);

            if ($numero_filas1 > 0)
            {
                $sql ="update ".$tabla." set ";
                for ($x=0;$x<count(${"base_".$data[0]});$x++){
                if ($x==count(${"base_".$data[0]})-1){
                  $sql=$sql.${"base_".$data[0]}[$x]."='".$data[$x+1];
                  if ($data[0]=="behaviour"){$sql = $sql."' where date='".$data[1]."' and emis_code=".$data[2]." and teacher_code=".$data[3]." and school_shift=".$data[4]." and school_level=".$data[5]." and school_grade=".$data[6]." and school_section=".$data[7]." and school_subject=".$data[8];}
                }
                else
                {$sql=$sql.${"base_".$data[0]}[$x]."='".$data[$x+1]."',";}

                } 
            }
            else
            {

             $sql= "insert into ".$tabla." (";
                for ($x=0;$x<count(${"base_".$data[0]});$x++){
                    if ($x==count(${"base_".$data[0]})-1){
                      $sql=$sql.${"base_".$data[0]}[$x].") ";
                    }
                    else
                    {$sql=$sql.${"base_".$data[0]}[$x].",";}
                    
                }   
                    $sql=$sql." value (";

                for ($x=0;$x<count($data)-2;$x++){
                    if ($x==count($data)-3){
                      $sql=$sql."'".$data[$x+1]."') ";
                    }
                    else
                    {$sql=$sql."'".$data[$x+1]."',";}
                    
                }
            }
            mysql_query($sql) or die ("error_sql");
    }

    function enrollment_new($datos){
     $base_disability=array("year","emis_code","school_shift","school_level","school_grade","student_sex","student_total","student_vision","student_hearing","student_phisical","student_handicap");
     $base_enrollment=array("year","emis_code","school_shift","school_level","school_grade","student_age","student_total","student_male","student_female");
     $base_repeater=array("year","emis_code","school_shift","school_level","school_grade","student_age","student_total","student_male","student_female");
     $base_newentrant=array("year","emis_code","school_shift","school_level","school_grade","student_age","student_total","student_male","student_female");
    
    $data=$datos;

        if ($data[0]=="disability"){
          $tabla = "tz_sis_schools.student_disability";
        }
        else if($data[0]=="enrollment"){
          $tabla = "tz_sis_schools.student_enrollment";
        }
        else if($data[0]=="repeater") {
          $tabla = "tz_sis_schools.student_repeater";
          }
        else {
            $tabla = "tz_sis_schools.student_newentrant";
          }
          $sql="select * from ".$tabla ;
                 if($data[0]=="disability"){
                    $sql=$sql." where year=".$data[1]." and emis_code =".$data[2]." and school_shift=".$data[3]." and school_level=".$data[4]." and school_grade=".$data[5]." and student_sex=".$data[6];
                  }
                  else{
                    $sql=$sql." where year=".$data[1]." and emis_code =".$data[2]." and school_shift=".$data[3]." and school_level=".$data[4]." and school_grade=".$data[5]." and student_age=".$data[6];

                  }
                 // echo $sql;
                  $result = mysql_query($sql) or die ("error_query_3");
                  $numero_filas = mysql_num_rows($result);
                  if ($numero_filas > 0)
                  {
                      $sql ="update ".$tabla." set ";
                      for ($x=0;$x<count(${"base_".$data[0]});$x++){
                        if ($x==count(${"base_".$data[0]})-1){
                            $sql=$sql.${"base_".$data[0]}[$x]."='".$data[$x+1];
                            if ($data[0]=="disability"){
                              $sql=$sql."' where year=".$data[1]." and emis_code =".$data[2]." and school_shift=".$data[3]." and school_level=".$data[4]." and school_grade=".$data[5]." and student_sex=".$data[6];
                            }
                            else { 
                              $sql=$sql."' where year=".$data[1]." and emis_code =".$data[2]." and school_shift=".$data[3]." and school_level=".$data[4]." and school_grade=".$data[5]." and student_age=".$data[6];
                            }
                        }
                        else
                        {
                          $sql=$sql.${"base_".$data[0]}[$x]."='".$data[$x+1]."',";
                        }

                      } 

                     // echo $sql;
                  }
                  else{

                  $sql= "insert into ".$tabla." (";
                  for ($x=0;$x<count(${"base_".$data[0]});$x++){
                      if ($x==count(${"base_".$data[0]})-1){
                        $sql=$sql.${"base_".$data[0]}[$x].") ";
                      }
                      else
                      {$sql=$sql.${"base_".$data[0]}[$x].",";}
                      
                  }   
                      $sql=$sql." value (";

                  for ($x=0;$x<count($data)-2;$x++){
                      if ($x==count($data)-3){
                        $sql=$sql."'".$data[$x+1]."') ";
                      }
                      else
                      {$sql=$sql."'".$data[$x+1]."',";}
                      
                  }
                }
              //  echo $sql;
                mysql_query($sql) or die ("error_sql");
        
    }

    function others_forms_new(array $datos){
      $base_a=array("emis_code","school_name","school_region_code","school_council_code","school_ward_code","school_zone","school_mobile_number","school_email_address","school_form4","school_form6","school_type","school_level","school_registration_number","school_year_established","gps_longitude","gps_latitude","school_student_count");
     $base_d=array("emis_code","classroom_solid","classroom_semi_solid","classroom_makeshift","classroom_partitioned","classroom_open_air","classroom_other","chairs","desk_1_seater","desk_2_seater","desk_3_seater","bench_1_seater","bench_2_seater","bench_3_seater","chalkboard","tables_count");
     $base_f =array("emis_code","have_national_curriculum","number_national_curriculum","math_standard_1","math_standard_2","math_standard_3","math_standard_4","math_standard_5","math_standard_6","math_standard_7","english_standard_1","english_standard_2","english_standard_3","english_standard_4","english_standard_5","english_standard_6","english_standard_7","kiswahili_standard_1","kiswahili_standard_2","kiswahili_standard_3","kiswahili_standard_4","kiswahili_standard_5","kiswahili_standard_6","kiswahili_standard_7","french_standard_1","french_standard_2","french_standard_3","french_standard_4","french_standard_5","french_standard_6","french_standard_7","science_standard_1","science_standard_2","science_standard_3","science_standard_4","science_standard_5","science_standard_6","science_standard_7","geography_standard_1","geography_standard_2","geography_standard_3","geography_standard_4","geography_standard_5","geography_standard_6","geography_standard_7","civics_standard_1","civics_standard_2","civics_standard_3","civics_standard_4","civics_standard_5","civics_standard_6","civics_standard_7","history_standard_1","history_standard_2","history_standard_3","history_standard_4","history_standard_5","history_standard_6","history_standard_7","vskills_standard_1","vskills_standard_2","vskills_standard_3","vskills_standard_4","vskills_standard_5","vskills_standard_6","vskills_standard_7","per_sports_standard_1","per_sports_standard_2","per_sports_standard_3","per_sports_standard_4","per_sports_standard_5","per_sports_standard_6","per_sports_standard_7","ict_standard_1","ict_standard_2","ict_standard_3","ict_standard_4","ict_standard_5","ict_standard_6","ict_standard_7","religion_standard_1","religion_standard_2","religion_standard_3","religion_standard_4","religion_standard_5","religion_standard_6","religion_standard_7","reading_standard_1","reading_standard_2","reading_standard_3","reading_standard_4","reading_standard_5","reading_standard_6","reading_standard_7","writing_standard_1","writing_standard_2","writing_standard_3","writing_standard_4","writing_standard_5","writing_standard_6","writing_standard_7","arithmetic_standard_1","arithmetic_standard_2","arithmetic_standard_3","arithmetic_standard_4","arithmetic_standard_5","arithmetic_standard_6","arithmetic_standard_7","health_standard_1","health_standard_2","health_standard_3","health_standard_4","health_standard_5","health_standard_6","health_standard_7","games_standard_1","games_standard_2","games_standard_3","games_standard_4","games_standard_5","games_standard_6","games_standard_7","other_standard_1","other_standard_2","other_standard_3","other_standard_4","other_standard_5","other_standard_6","other_standard_7","total_textbooks_nat_curr_la","total_textbooks_nat_curr_m","total_textbooks_nat_curr_s","total_textbooks_nat_curr_ss","total_textbooks_nat_curr_o","often_use_textbooks_la","often_use_textbooks_m","often_use_textbooks_s","often_use_textbooks_ss","often_use_textbooks_o","safe_location","social_standard_3","social_standard_4");
     $base_g = array("emis_code","classrooms_solid","classrooms_semi_solid","classrooms_makeshift","classrooms_partitioned","classrooms_open_air","classrooms_other","chairs","desk_1_seater","desk_2_seater","desk_3_seater","bench_1_seater","bench_2_seater","bench_3_seater","chalkboard","tables_count");
    $base_q=array("emis_code","school_solid","school_makeshift","school_semi_solid","school_open_air","school_other","school_library","school_librarian","school_reading_space","school_number_books","school_number_shelves","school_cellphone","school_fence_surrounding","school_drinking_water","school_latrine","school_boys_only","school_girls_only","school_boys_girls","school_male_staff","school_female_staff","school_male_female_staff","school_auditorium","school_health_clinic","school_head_office","school_farm","school_staff_room","school_hand_washing_facility","school_garden","school_admin_block","school_source_power","school_computer_lab","school_func_generator","school_regular_fuel","school_sports_playground","school_staff_housing","school_science_lab","school_transport","school_cafeteria","school_storage","school_wood_work","school_home_economic","school_metal_work","school_auto_mechanics","school_teacher_living_solid","school_teacher_living_semi_solid","school_teacher_living_makeshift","school_teacher_living_other","sdw","ssdw1","ssdw2","ssdw3","ssdw4","ssdw5");
     $base_r=array("emis_code","r1a1","r1a2","r1b1","r1b2","r1c1","r1c2","r1d1","r1d2","r1e1","r1e2","r1f1","r1f2","r1g1","r1g2","r2a1","r2a2","r2a3","r2b1","r2b2","r2b3","r2c1","r2c2","r2c3","r2d1","r2d2","r2d3","r2e1","r2e2","r2e3","r2e4","r2f1","r2f2","r2f3","r2f4","r3a1","r3a2","r3a3","r3b1","r3b2","r3b3","r3c1","r3c2","r3c3","r3d1","r3d2","r3d3","r3e1","r3e2","r3e3");
     $base_s=array("emis_code","ptm_functioning","ptm_times_meet_year","ptm_chairperson","ptm_chairperson_telephone","board_committee_functioning","board_committee_meet_year","board_committee_chairperson","board_committee_chairperson_telephone");
          $data=$datos ;
          if ($data[0]=="a"){
          $tabla = "tz_sis_schools.module_a_school_profile";
        }
        else if($data[0]=="d"){
          $tabla = "tz_sis_schools.module_d_preprimary_education_operations";
        }
        else if($data[0]=="f") {
          $tabla = "tz_sis_schools.module_f_primary_curriculum_instruction";
          }
          else if($data[0]=="g") {
          $tabla = "tz_sis_schools.module_g_primary_infrastructure_furniture";
          }
          else if($data[0]=="q") {
          $tabla = "tz_sis_schools.module_q_school_infrastructure";
          }
          else if($data[0]=="r") {
          $tabla = "tz_sis_schools.module_r_source_funds";
          }
        else {
            $tabla = "tz_sis_schools.module_s_management_teaching_staff";
          }
         $sql="select * from ".$tabla." where emis_code=".$data[1];
        //  echo $sql;
          $result = mysql_query($sql) or die ("error_query");
          $numero_filas = mysql_num_rows($result);

          if ($numero_filas > 0)
          {
          $sql ="update ".$tabla." set ";
          for ($x=0;$x<count(${"base_".$data[0]});$x++){
          if ($x==count(${"base_".$data[0]})-1){
            $sql=$sql.${"base_".$data[0]}[$x]."='".$data[$x+1];
            
             $sql = $sql."' where emis_code=".$data[1];
          }
          else
          {$sql=$sql.${"base_".$data[0]}[$x]."='".$data[$x+1]."',";}

          } 

          // echo "other_update".$sql;   
          }
          else
          {
           $sql= "insert into ".$tabla." (";
              for ($x=0;$x<count(${"base_".$data[0]});$x++){
                  if ($x==count(${"base_".$data[0]})-1){
                    $sql=$sql.${"base_".$data[0]}[$x].") ";
                  }
                  else
                  {$sql=$sql.${"base_".$data[0]}[$x].",";}
                  
              }   
                  $sql=$sql." value (";

              for ($x=0;$x<count($data)-2;$x++){
                  if ($x==count($data)-3){
                    $sql=$sql."'".$data[$x+1]."') ";
                  }
                  else
                  {$sql=$sql."'".$data[$x+1]."',";}
                  
              }
          }
         // echo "ultimo".$sql;
      mysql_query($sql) or die ("error_sql2");
          //echo "other".$sql;
    }

//old data base
     function deposits ($datos){
       
          $sql= "INSERT INTO `tanzania_school`.`school_finance_deposits` (`emis_code`,`date`,`type`,`amount`,`comment`) VALUES ('".$datos[1]."','".$datos[2]."','".$datos[3]."','".$datos[4]."','".$datos[5]."')";
          mysql_query($sql) or die ("error_sql");
        
     }

     function expenditures ($datos){
          $sql= "INSERT INTO `tanzania_school`.`school_finance_expenditures` (`emis_code`,`date`,`type`,`amount`,`comment`) VALUES ('".$datos[1]."','".$datos[2]."','".$datos[3]."','".$datos[4]."','".$datos[5]."')";
          mysql_query($sql) or die ("error_sql");
     }

    function teachers($datos){
          $base_teacher=array("a1","_id","t_s","surname","givenname","sex","yearob","checkno","cp1","cp2","cp3","cp4","cp5","cp6","lt1","lt2","lt3","lt4","lt5","prof_q","acad_q"," sub_t1",
            "sub_t2","sub_t3","sub_t4","sub_t5","year_pos","salary","phone","email","addrs","exit_t","yearexit");
          $data=$datos;
          $sql="select * from ".$data[0]." where a1=".$data[1]." and _id=".$data[2];
          $result = mysql_query($sql) or die ("error_query");
          $numero_filas = mysql_num_rows($result);

          if ($numero_filas > 0)
          {
              $sql ="update ".$data[0]." set ";
              for ($x=0;$x<count(${"base_".$data[0]});$x++){
                  if ($x==count(${"base_".$data[0]})-1){
                  $sql=$sql.${"base_".$data[0]}[$x]."='".$data[$x+1];
                  if ($data[0]=="teacher"){ $sql = $sql."' where a1=".$data[1]." and _id=".$data[2];}
                  
                  }
                  else
                  {$sql=$sql.${"base_".$data[0]}[$x]."='".$data[$x+1]."',";}

              } 

              
          }
          else
          {
           $sql= "insert into ".$data[0]." (";
              for ($x=0;$x<count(${"base_".$data[0]});$x++){
                  if ($x==count(${"base_".$data[0]})-1){
                    $sql=$sql.${"base_".$data[0]}[$x].") ";
                  }
                  else
                  {$sql=$sql.${"base_".$data[0]}[$x].",";}
                  
              }   
                  $sql=$sql." value (";

              for ($x=0;$x<count($data)-2;$x++){
                  if ($x==count($data)-3){
                    $sql=$sql."'".$data[$x+1]."') ";
                  }
                  else
                  {$sql=$sql."'".$data[$x+1]."',";}
                  
              }
          }
          mysql_query($sql) or die ("error_sql");
    }

    function attendance($datos){
       $base_attendance=array("date","emis","ts","t_present","charge","shift","level","grade","section","subject","present","absent");
      $data=$datos ;
          $sql="select * from ".$data[0]." where date='".$data[1]."' and emis=".$data[2]." and ts=".$data[3]." and shift=".$data[6]." and level=".$data[7]." and grade=".$data[8]." and section=".$data[9]." and subject=".$data[10];
          $result = mysql_query($sql) or die ("error_query");
          $numero_filas = mysql_num_rows($result);

          if ($numero_filas > 0)
          {
              $sql ="update ".$data[0]." set ";
              for ($x=0;$x<count(${"base_".$data[0]});$x++){
              if ($x==count(${"base_".$data[0]})-1){
              $sql=$sql.${"base_".$data[0]}[$x]."='".$data[$x+1];
              if ($data[0]=="attendance"){$sql = $sql."' where date='".$data[1]."' and emis=".$data[2]." and ts=".$data[3]." and shift=".$data[6]." and level=".$data[7]." and grade=".$data[8]." and section=".$data[9]." and subject=".$data[10];}
              }
              else
              {$sql=$sql.${"base_".$data[0]}[$x]."='".$data[$x+1]."',";}

              } 

              
          }

              /*fin nuevo query */
              else{

              $sql= "insert into ".$data[0]." (";
              for ($x=0;$x<count(${"base_".$data[0]});$x++){
              if ($x==count(${"base_".$data[0]})-1){
                $sql=$sql.${"base_".$data[0]}[$x].") ";
              }
              else
              { $sql=$sql.${"base_".$data[0]}[$x].",";}
              
              }   
                $sql=$sql." value (";

                for ($x=0;$x<count($data)-2;$x++){
                    if ($x==count($data)-3){
                      $sql=$sql."'".$data[$x+1]."') ";
                    }
                    else
                    {$sql=$sql."'".$data[$x+1]."',";}
                    
                }
                }
                mysql_query($sql) or die ("error_sql");
    }

    function evaluation($datos){
       $base_evaluation=array("date","emis","ts","shift","level","grade","section","subject","test","n","note");
       $data=$datos;
          $sql1="select * from ".$data[0]." where date='".$data[1]."' and emis=".$data[2]." and ts=".$data[3]." and shift=".$data[4]." and level=".$data[5]." and grade=".$data[6]." and section=".$data[7]." and subject=".$data[8]." and test=".$data[9];
            $result1 = mysql_query($sql1) or die ("error_query1");
            $numero_filas1 = mysql_num_rows($result1);

            if ($numero_filas1 > 0)
            {
                $sql ="update ".$data[0]." set ";
                for ($x=0;$x<count(${"base_".$data[0]});$x++){
                if ($x==count(${"base_".$data[0]})-1){
                  $sql=$sql.${"base_".$data[0]}[$x]."='".$data[$x+1];
                  if ($data[0]=="evaluation"){$sql = $sql."' where date='".$data[1]."' and emis=".$data[2]." and ts=".$data[3]." and shift=".$data[4]." and level=".$data[5]." and grade=".$data[6]." and section=".$data[7]." and subject=".$data[8]." and test=".$data[9];}
                }
                else
                {$sql=$sql.${"base_".$data[0]}[$x]."='".$data[$x+1]."',";}

                } 
            }
            else
            {

             $sql= "insert into ".$data[0]." (";
                for ($x=0;$x<count(${"base_".$data[0]});$x++){
                    if ($x==count(${"base_".$data[0]})-1){
                      $sql=$sql.${"base_".$data[0]}[$x].") ";
                    }
                    else
                    {$sql=$sql.${"base_".$data[0]}[$x].",";}
                    
                }   
                    $sql=$sql." value (";

                for ($x=0;$x<count($data)-2;$x++){
                    if ($x==count($data)-3){
                      $sql=$sql."'".$data[$x+1]."') ";
                    }
                    else
                    {$sql=$sql."'".$data[$x+1]."',";}
                    
                }
            }
            mysql_query($sql) or die ("error_sql");
    }

    function behaviour($datos){
      $base_behaviour=array("date","emis","ts","shift","level","grade","section","subject","reason1","reason2","reason3","reason4"); 
      $data=$datos;
            $sql1="select * from ".$data[0]." where date='".$data[1]."' and emis=".$data[2]." and ts=".$data[3]." and shift=".$data[4]." and level=".$data[5]." and grade=".$data[6]." and section=".$data[7]." and subject=".$data[8];
            $result1 = mysql_query($sql1) or die ("error_query1");
            $numero_filas1 = mysql_num_rows($result1);

            if ($numero_filas1 > 0)
            {
                $sql ="update ".$data[0]." set ";
                for ($x=0;$x<count(${"base_".$data[0]});$x++){
                if ($x==count(${"base_".$data[0]})-1){
                  $sql=$sql.${"base_".$data[0]}[$x]."='".$data[$x+1];
                  if ($data[0]=="behaviour"){$sql = $sql."' where date='".$data[1]."' and emis=".$data[2]." and ts=".$data[3]." and shift=".$data[4]." and level=".$data[5]." and grade=".$data[6]." and section=".$data[7]." and subject=".$data[8];}
                }
                else
                {$sql=$sql.${"base_".$data[0]}[$x]."='".$data[$x+1]."',";}

                } 
            }
            else
            {

             $sql= "insert into ".$data[0]." (";
                for ($x=0;$x<count(${"base_".$data[0]});$x++){
                    if ($x==count(${"base_".$data[0]})-1){
                      $sql=$sql.${"base_".$data[0]}[$x].") ";
                    }
                    else
                    {$sql=$sql.${"base_".$data[0]}[$x].",";}
                    
                }   
                    $sql=$sql." value (";

                for ($x=0;$x<count($data)-2;$x++){
                    if ($x==count($data)-3){
                      $sql=$sql."'".$data[$x+1]."') ";
                    }
                    else
                    {$sql=$sql."'".$data[$x+1]."',";}
                    
                }
            }
            mysql_query($sql) or die ("error_sql");
    }

    function enrollment($datos){
     $base_disability=array("year","emis","shift","level","grade","sex","total","vision","hearing","phisical","handicap");
     $base_enrollment=array("year","emis","shift","level","grade","studentage","total","male","female");
     $base_repeater=array("year","emis","shift","level","grade","studentage","total","male","female");
     $base_newentrant=array("year","emis","shift","level","grade","studentage","total","male","female");
      $data=$datos;
          $sql="select * from ".$data[0] ;
                 if($data[0]=="disability"){
                    $sql=$sql." where year=".$data[1]." and emis =".$data[2]." and shift=".$data[3]." and level=".$data[4]." and grade=".$data[5]." and sex=".$data[6];
                  }
                  else{
                    $sql=$sql." where year=".$data[1]." and emis =".$data[2]." and shift=".$data[3]." and level=".$data[4]." and grade=".$data[5]." and studentage=".$data[6];

                  }
                 // echo $sql;
                  $result = mysql_query($sql) or die ("error_query_3");
                  $numero_filas = mysql_num_rows($result);
                  if ($numero_filas > 0)
                  {
                      $sql ="update ".$data[0]." set ";
                      for ($x=0;$x<count(${"base_".$data[0]});$x++){
                        if ($x==count(${"base_".$data[0]})-1){
                            $sql=$sql.${"base_".$data[0]}[$x]."='".$data[$x+1];
                            if ($data[0]=="disability"){
                              $sql=$sql."' where year=".$data[1]." and emis =".$data[2]." and shift=".$data[3]." and level=".$data[4]." and grade=".$data[5]." and sex=".$data[6];
                            }
                            else { 
                              $sql=$sql."' where year=".$data[1]." and emis =".$data[2]." and shift=".$data[3]." and level=".$data[4]." and grade=".$data[5]." and studentage=".$data[6];
                            }
                        }
                        else
                        {
                          $sql=$sql.${"base_".$data[0]}[$x]."='".$data[$x+1]."',";
                        }

                      } 

                      
                  }
                  else{

                  $sql= "insert into ".$data[0]." (";
                  for ($x=0;$x<count(${"base_".$data[0]});$x++){
                      if ($x==count(${"base_".$data[0]})-1){
                        $sql=$sql.${"base_".$data[0]}[$x].") ";
                      }
                      else
                      {$sql=$sql.${"base_".$data[0]}[$x].",";}
                      
                  }   
                      $sql=$sql." value (";

                  for ($x=0;$x<count($data)-2;$x++){
                      if ($x==count($data)-3){
                        $sql=$sql."'".$data[$x+1]."') ";
                      }
                      else
                      {$sql=$sql."'".$data[$x+1]."',";}
                      
                  }
                }
               // echo $sql;
                mysql_query($sql) or die ("error_sql");
    }

    function others_forms(array $datos){
     $base_a=array("a1","a2","a3a","a3b","a3c","a3d","a4a","a4b","a5a","a5b","b1","b2","b3","b4","lon","lat","students");
     $base_d=array("a1","d1a","d1b","d1c","d1d","d1e","d1f","d2a","d2b","d2c","d2d","d2e","d2f","d2g","d24","tables_count");
     $base_f =array("a1","f1a","f1b","f2a1","f2a2","f2a3","f2a4","f2a5","f2a6","f2a7","f2a8","f2a9","f2a10","f2a11","f2a12","f2a13","f2a14","f2a15","f2a16","f2a17","f2a18","f2a19",
            "f2a20","f2a21","f2a22","f2a23","f2a24","f2a25","f2a26","f2a27","f2a28","f2a29","f2a30","f2a31","f2a32","f2a33","f2a34","f2a35","f2a36","f2a37","f2a38","f2a39","f2a40",
            "f2a41","f2a42","f2a43","f2a44", "f2a45","f2a46","f2a47","f2a48","f2a49","f2a50","f2a51","f2a52","f2a53","f2a54","f2a55","f2a56","f2a57","f2a58",
            "f2a59","f2a60","f2a61","f2a62","f2a63","f2a64","f2a65","f2a66","f2a67","f2a68","f2a69","f2a70","f2a71","f2a72","f2a73","f2a74","f2a75","f2a76","f2a77","f2a78","f2a79","f2a80","f2a81","f2a82",
            "f2a83","f2a84","f2a85","f2a86","f2a87","f2a88","f2a89","f2a90","f2a91","f2a92","f2a93","f2a94","f2a95","f2a96","f2a97","f2a98","f2a99","f2a100","f2a101","f2a102","f2a103","f2a104","f2a105",
            "f2a106","f2a107","f2a108","f2a109","f2a110","f2a111","f2a112","f2a113","f2a114","f2a115","f2a116","f2a117","f2a118","f2a119","f2a120","f2a121","f2a122","f2a123","f2a124","f2a125","f2a126",
            "f2b1","f2b2","f2b3","f2b4","f2b5","f2c1","f2c2","f2c3","f2c4","f2c5","f2d","f2a127","f2a128");
     $base_g = array("a1","g1a", "g1b", "g1c", "g1d", "g1e", "g1f", "g2a", "g2b", "g2c", "g2d", "g2e", "g2f", "g2g", "g24","tables_count");
     $base_q=array("a1","q1a","q1b","q1c","q1d","q1e","q2a","q2b","q2c","q2d","q2e","q3a","q3b","q3c","q3d","q3e1","q3e2","q3e3","q3e4","q3e5","q3e6","q3f1","q3f2","q3f3","q3f4","q3f5","q3f6",
            "q3f7","q3f8","q3f9","q3f10","q3f11","q3f12","q3f13","q3f14","q3f15","q3f16","q3f17","q3f18","q3f19","q3f20","q3f21","q3f22","q3g1","q3g2","q3g3","q3g4","sdw","ssdw1","ssdw2","ssdw3","ssdw4","ssdw5");
     $base_r=array("a1" ,"r1a1" ,"r1a2" ,"r1b1" ,"r1b2" ,"r1c1" ,"r1c2" ,"r1d1" ,"r1d2" ,"r1e1" ,"r1e2" ,"r1f1" ,"r1f2" ,"r1g1" ,"r1g2" ,"r2a1" ,"r2a2" ,"r2a3" ,"r2b1" ,"r2b2" ,"r2b3" ,"r2c1" ,
            "r2c2" ,"r2c3" ,"r2d1" ,"r2d2" ,"r2d3" ,"r2e1" ,"r2e2" ,"r2e3" ,"r2e4" ,"r2f1" ,"r2f2" ,"r2f3" ,"r2f4" ,"r3a1" ,"r3a2" ,"r3a3" ,"r3b1" ,"r3b2" ,"r3b3" ,"r3c1" ,"r3c2" ,"r3c3" ,"r3d1" ,"r3d2" ,
            "r3d3" ,"r3e1" ,"r3e2" ,"r3e3");
     $base_s=array("a1","s1a","s1b","s1c","s1d","s2a","s2b","s2c","s2d");
          $data=$datos ;
         $sql="select * from ".$data[0]." where a1=".$data[1];
         // echo $sql;
          $result = mysql_query($sql) or die ("error_query");
          $numero_filas = mysql_num_rows($result);

          if ($numero_filas > 0)
          {
          $sql ="update ".$data[0]." set ";
          for ($x=0;$x<count(${"base_".$data[0]});$x++){
          if ($x==count(${"base_".$data[0]})-1){
            $sql=$sql.${"base_".$data[0]}[$x]."='".$data[$x+1];
             $sql = $sql."' where a1=".$data[1];
          }
          else
          {$sql=$sql.${"base_".$data[0]}[$x]."='".$data[$x+1]."',";}

          } 

          // echo "other_update".$sql;   
          }
          else
          {
           $sql= "insert into ".$data[0]." (";
              for ($x=0;$x<count(${"base_".$data[0]});$x++){
                  if ($x==count(${"base_".$data[0]})-1){
                    $sql=$sql.${"base_".$data[0]}[$x].") ";
                  }
                  else
                  {$sql=$sql.${"base_".$data[0]}[$x].",";}
                  
              }   
                  $sql=$sql." value (";

              for ($x=0;$x<count($data)-2;$x++){
                  if ($x==count($data)-3){
                    $sql=$sql."'".$data[$x+1]."') ";
                  }
                  else
                  {$sql=$sql."'".$data[$x+1]."',";}
                  
              }
          }
         // echo "ultimo".$sql;
      mysql_query($sql) or die ("error_sql");
          //echo "other".$sql;
    }
      
      if ($datos[count($datos)-1]=="I"){
          if($datos[0]=="deposits"){
              //deposits($datos);
              deposits_new($datos);
          }
          else if($datos[0]=="expenditures"){
              //expenditures($datos);
              expenditures_new($datos);
          }
          else if($datos[0]=="teacher"){
              //teachers($datos);
              teachers_new($datos);
          }
          else if($datos[0]=="attendance"){
             //attendance($datos);
             attendance_new($datos);
          }
          else if($datos[0]=="evaluation"){
             // evaluation($datos);
              evaluation_new($datos);
          }
          else if($datos[0]=="behaviour"){
             // behaviour($datos);
              behaviour_new($datos);
          }
          else if ($datos[0]=="enrollment" || $datos[0]=="repeater"|| $datos[0]=="disability" || $datos[0]=="newentrant"){
             // enrollment($datos);
              enrollment_new($datos);
          }
          else {
             // others_forms($datos);
              others_forms_new($datos);
          }
      }
      else if ($datos[count($datos)-1]=="D"){
        if ($datos[0]=="disability"){
          $tabla = "tz_sis_schools.student_disability";
        }
        else if($datos[0]=="enrollment"){
          $tabla = "tz_sis_schools.student_enrollment";
        }
        else if($datos[0]=="repeater") {
          $tabla = "tz_sis_schools.student_repeater";
          }
        else {
            $tabla = "tz_sis_schools.student_newentrant";
          }
          //echo $datos[0];
          $sql="delete from ".$tabla." where `year`=".$datos[1]." and emis_code=".$datos[2];
          //echo $sql;
          mysql_query($sql) or die ("error_sql");
          //$sql="delete from ".$datos[0]." where `year`=".$datos[1]." and emis=".$datos[2];
          //mysql_query($sql) or die ("error_sql");
      }
      else{
          if ($datos[0]=="deposits"){
            //$sql="UPDATE `tanzania_school`.`school_finance_deposits` SET `amount` = ".$datos[4].",`comment`= ".$datos[5]." WHERE `emis_code` = '".$datos[1]."' and date= '".$datos[2]."'  and type ='".$datos[3]."'";
            //mysql_query($sql) or die ("error_sql");
            $sql="UPDATE `tz_sis_schools`.`school_finance_deposits` SET `amount` = ".$datos[4].",`comment`= ".$datos[5]." WHERE `emis_code` = '".$datos[1]."' and date= '".$datos[2]."'  and type ='".$datos[3]."'";
            mysql_query($sql) or die ("error_sql");
          }
          else if ($datos[0]=="expenditures"){
            //$sql="UPDATE `tanzania_school`.`school_finance_expenditures` SET `amount` = ".$datos[4].",`comment`= ".$datos[5]." WHERE `emis_code` = '".$datos[1]."' and date= '".$datos[2]."'  and type ='".$datos[3]."'";
            //mysql_query($sql) or die ("error_sql");
             $sql="UPDATE `tz_sis_schools`.`school_finance_expenditures` SET `amount` = ".$datos[4].",`comment`= ".$datos[5]." WHERE `emis_code` = '".$datos[1]."' and date= '".$datos[2]."'  and type ='".$datos[3]."'";
            mysql_query($sql) or die ("error_sql");
          }

          else if($datos[0]=="teacher"){
             // teachers($datos);
              teachers_new($datos);
          }
          else if($datos[0]=="attendance"){
              //attendance($datos);
              attendance_new($datos);
          }
          else if($datos[0]=="evaluation"){
              //evaluation($datos);
              evaluation_new($datos);
          }
          else if($datos[0]=="behaviour"){
              //behaviour($datos);
              behaviour_new($datos);
          }
          else {
              //others_forms($datos);
              others_forms_new($datos);
          }
      }
      
      //echo 'SQL.... '.$sql.'<br>';
      
    

  mysql_close($con);
  
?>
 
    