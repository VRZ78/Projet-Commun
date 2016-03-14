var http = require('http');
var express = require('express');
var app = express();
var mysql = require('mysql');
var userId;
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

//recuperer choix de matiere (en fonction des formations)
app.get('/listeMatieres', function(req, res) {
	userId = req.headers.Auhtorization; //recuperation de l'id
	connection.query("select matiere.nom from quizz, matiere where Niveau_etude_idNiveau_etude = (select Niveau_etude_idNiveau_etude from compte where idCompte = 3) and idMatiere =Matiere_idMatiere;", function(err, rows, fileds){
	if(!err)
		res.status(200).json(rows);
	else
		res.status(404).json("error");	
  });	
});



//pour récuperer la liste des quiz correspondant à la matiere choisie
app.get('/listeQuizz/:id_matiere', function(req, res) {
		res.status(200).json(req.currents_quizz);
});

app.param('id_matiere', function(req, res, next, id){
	
	userId = req.headers.Auhtorization; //recuperation de l'id
	connection.query("select * from quizz where Niveau_etude_idNiveau_etude = (select Niveau_etude_idNiveau_etude from compte where idCompte = 3) and Matiere_idMatiere="+id+" ;", function(err, rows, fileds){
	if(!err){
		//"+userId+"
		req.currents_quizz = rows;
		next();
	}else{
		res.status(404).send("error");
	}
  });
 });


//recuperer les questions qui se rapportent au quizz choisi

app.get('/quizz/:id_quizz', function(req,res){
	res.status(200).json(req.current_quizz)
});

app.param('id_quizz', function(req, res, next, id){
	
connection.query("select * from question, proposition where Question_idQuestion=idQuestion and Quizz_idQuizz ="+id+";",function(err, rows, fileds){
	if(!err){
		
		req.current_quizz = rows;
		next();
	}else{
		
		res.status(404).send("error");
	}
  });

});

// page inscription 

app.get('/inscription/formation', function(req, res) {
	userId = req.headers.Auhtorization; //recuperation de l'id
	connection.query("select * from niveau_etude;", function(err, rows, fileds){
	if(!err)
		res.status(200).json(rows);
	else
		res.status(404).json("error");	
  });	
});

app.get('/inscription/etablissement', function(req, res) {
	userId = req.headers.Auhtorization; //recuperation de l'id
	connection.query("select * from etablissement;", function(err, rows, fileds){
	if(!err)
		res.status(200).json(rows);
	else
		res.status(404).json("error");	
  });	
});




/*app.post('/inscription', function(req,res){
	var etablissement = "select idEtablissement from etablissement where nom ="+req.body.etablissement;
	var etude="select idNiveau_etude from niveau_etude where niveau="+req.body.niveau_etude;
	connection.query("INSERT INTO `compte` (`pseudo`, `nom`, `prenom`, `date_naissance`, `password`, `Type_idType`, `Etablissement_idEtablissement`, `Niveau_etude_idNiveau_etude`) VALUES ("+req.body.pseudo+","+req.body.nom+","+req.body.prenom+","+req.body.date_naissance+","+req.body.password+",1,"+etablissement+","+etude+");"),function(err, rows, fields){
	 	if(!err)
	 		console.log("succes de l'ajout");
	 	else
	 		console.log("erreur lors de l'ajout");
	 });
});

app.post('/vuep/inscription', function(req,res){
	//changer type par 2...;
}); */

//
app.listen(8080);




























/*
var server = http.createServer(function(req, res) {

    res.writeHead(200, {"Content-Type": "text/html"});

    res.end('<p>Test test coucou je suis la!</p>');

});

server.listen(3000);*/