<?php
	error_reporting(0);
    try{
        $name = time().rand(1000, 9999);
//        if(!isset($_REQUEST['filename']))
//        {
//            exit('No file');
//        }

        $upload_path = dirname(__FILE__). '/';
		//$upload_filename = $upload_path."/rec/".$name;
		$upload_filename =	$upload_path.$name;
        $fp = fopen($upload_filename . '.mp3', "wb");

        fwrite($fp, file_get_contents('php://input'));

        fclose($fp);


//        $cmd = "/usr/bin/lame {$upload_filename}.wav {$upload_filename}.mp3";
//        $output = exec($cmd,$op);

        exit($name . ".mp3");
    }catch (Exception $e) {
        exit;
    }


?>
