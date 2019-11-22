<?php
	$emis=$_GET['emis'];
	$target_path = "uploads/".$emis."/";
    $path = realpath($target_path);
    foreach (new RecursiveIteratorIterator(new RecursiveDirectoryIterator($path)) as $filename)
        {
            if (strpos($filename,'.sqlite') !== false)
            	$test[]=array('data'=>basename($filename));
            //$filenm=$filenm ."-". basename($filename);
        }
   // echo $filenm    
         echo  json_encode($test);
?>