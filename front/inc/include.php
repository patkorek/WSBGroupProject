<?php

define('PATH', './');

//include(PATH.'/_app_mysql.php');
/*
class Database {
    private $host = '';
    private $user = '';
    private $pass = '';
    private $name = '';
   // private $encoding = 'utf8';
    public $dbh;

    private function encoding() {
        $encoding = 'utf8';
        return $encoding;
    }

    public function __construct(){
        // Set DSN
    $dsn = 'mysql:host='.$this->host.'; dbname='.$this->name.'; charset='.$this->encoding();
        // Set options
        $options = array(
            PDO::ATTR_PERSISTENT => true, 
            PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION,
            PDO::MYSQL_ATTR_INIT_COMMAND => 'SET NAMES '. $this->encoding()
        );
        // Create a new PDO instanace
        try {
            $this->dbh = new PDO($dsn, $this->user, $this->pass, $options);
        }
        // Catch any errors
        catch(PDOException $e){
            echo $e->getMessage();
        }
    }
};

//------------------------------------------- połączenie z baza po PDO
$db = new Database();
*/

$title = 'Markiet';
?>
