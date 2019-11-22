<?php
// Where the file is going to be placed 
$emis=$_GET['emis'];
mkdir("uploads/".$emis,0777); 
$target_path = "uploads/".$emis."/";

/* Add the original filename to our target path.  
Result is "uploads/filename.extension" */
$target_path = $target_path . date("Y-m-d")."_".basename( $_FILES['uploadedfile']['name']); 

if(move_uploaded_file($_FILES['uploadedfile']['tmp_name'], $target_path)) {
    echo "The file ".  basename( $_FILES['uploadedfile']['name']). 
    " has been uploaded";
    chmod ("uploads/".basename( $_FILES['uploadedfile']['name']), 0644);
} else{
    echo "There was an error uploading the file, please try again!";
    echo "filename: " .  basename( $_FILES['uploadedfile']['name']);
    echo "target_path: " .$target_path;
}
?>