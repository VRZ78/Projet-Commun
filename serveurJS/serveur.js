
var http = require('http');
var express = require('express');
var app = express();
var mysql = require('mysql');

var connection = mysql.createConnection({
	host: 'localhost',
	user: 'root',
	password: '',
	database: 'revisator',
});

//connexion à la base de données
connection.connect(function(err){
if(!err) {
    console.log("connexion à la bdd reussi");    
} else {
    console.log("Erreur lors de la tentative de connexion....");    
}
});


//pour récuperer la liste des quiz
app.get('/listeQuizz', function(req, res) {
	connection.query("select * from quizz;", function(err, rows, fileds){
	if(!err)
		res.status(200).json(rows);
	else
		res.send("error");
  });
});

//recuperer le quizz 

app.get('/listeQuizz/:id_quizz', function(req,res){
	res.status(200).json(req.current_quizz)
});

app.param('id_quizz', function(req, res, next, id){
	
connection.query("select * from quizz where idQuizz ="+id+";",function(err, rows, fileds){
	if(!err){
		
		req.current_quizz = rows[0];
		next();
	}else{
		res.status(404).send("error");
	}
  });

});
//
app.listen(8080);




























/*
var server = http.createServer(function(req, res) {

    res.writeHead(200, {"Content-Type": "text/html"});

    res.end('<p>Test test coucou je suis la!</p>');

});

server.listen(3000);*/