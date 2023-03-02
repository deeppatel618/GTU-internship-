<?php

$ch= curl_init();
$secretKey = '6Ld-wr0kAAAAAGcs-uhgq2J5dzIw0HfluUZ5Gvuc';
$captcha= isset($_POST['recaptcha-response']) && !empty($_POST['recaptcha-response'])? $_POST['recaptcha-response']:'hello';

curl_setopt_array($ch,[
    CURLOPT_URL => 'https://www.google.com/recaptcha/api/siteverify',
    CURLOPT_POST => true,
    CURLOPT_POSTFIELDS =>
    [
        'secret' => $secretKey,
        'response' => $captcha,
        'remoteip' => $_SERVER['REMOTE_ADDR']
    ],
    CURLOPT_RETURNTRANSFER => TRUE
]);
$output= curl_exec($ch);
curl_close($ch);
$json= json_decode($output);
$res= array();
if($json -> success)
{
    $res['success'] = true;
    $res['message'] = 'Captcha verified successfully';
}
else{
    $res['success'] = false;
    $res['message'] = 'Captcha verified failed';
}
echo json_encode($res);
?>
