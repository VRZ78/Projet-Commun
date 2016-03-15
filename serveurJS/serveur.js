var http = require('http');
var express = require('express');
var app = express();
var mysql = require('mysql');
var cors = require('express-cors');
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

//ajouter un quizz dans la base de données
app.post('/ajouterQuizz', function(req, res) {
	userId = req.headers.Auhtorization;
	nomQuizz = req.body.title;
	nomMatiere = req.body.matiere.name;
	niveauEtude = req.body.year.name;
	nombreQuestion = req.body.nbOfQuestions;
	
	connection.query("SELECT * FROM Matiere WHERE nom = '"+nomMatiere+"';", function select(err, rows, fields) {
		if (err) {
		  console.log(err);
		  connection.end();
		  return;
		}
	 
		if (rows.length > 0) { 
			var firstResult = rows[0];
			var idMatiere = firstResult['idMatiere'];
		} else {
			console.log("Pas de données");
		}
		
		connection.end();
	});
	
	connection.query("SELECT * FROM Niveau_etude WHERE niveau = '"+niveauEtude+"';", function select(err, rows, fields) {
		if (err) {
		  console.log(err);
		  connection.end();
		  return;
		}
	 
		if (rows.length > 0) { 
			var firstResult = rows[0];
			var idNiveauEtude = firstResult['idNiveau_etude'];
		} else {
			console.log("Pas de données");
		}
		
		connection.end();
	});
	
	connection.query("INSERT INTO Quizz(nom, Compte_idCompte, Matiere_idMatiere, Niveau_etude_idNiveau_etude) VALUES ("+nomQuizz+", "+userId+", "+idMatiere+", "+idNiveauEtude+");",function(error, rows){         
        if(error != null) {
            resp.end("Query error:" + error);
        } else {
            resp.end("Success!");
        }
		
        connection.end();
	});
	
	connection.query("SELECT * FROM Quizz ORDER BY idQuizz desc LIMIT 1;", function select(err, rows, fields) {
		if (err) {
		  console.log(err);
		  connection.end();
		  return;
		}
	 
		if (rows.length > 0) { 
			var firstResult = rows[0];
			var idQuizz = firstResult['idQuizz'];
		} else {
			console.log("Pas de données");
		}
		
		connection.end();
	});
	
	for(i = 1; i <= nombreQuestion; i++) {
		
		nomQuestion = req.body.questions[i].questionTitle;
		nombreReponse = req.body.questions[i].nombreReponse;
		
		connection.query("INSERT INTO Question(nom, Quizz_idQuizz) VALUES ("+nomQuestion+", "+idQuizz+");",function(error, rows){         
			if(error != null) {
				resp.end("Query error:" + error);
			} else {
				resp.end("Success!");
			}
			
			connection.end();
		});

		connection.query("SELECT * FROM Question ORDER BY idQestion desc LIMIT 1;", function select(err, rows, fields) {
			if (err) {
			  console.log(err);
			  connection.end();
			  return;
			}
		 
			if (rows.length > 0) { 
				var firstResult = rows[0];
				var idQuestion = firstResult['idQuestion'];
			} else {
				console.log("Pas de données");
			}
			
			connection.end();
		});
		
		for(j = 1; j <= nombreReponse; j++) {
			
			proposition = req.body.question[i].questionAnswer[j];
			estValide = req.body.question[i].correctAnswer[j];
			
			connection.query("INSERT INTO Proposition(proposition, estValide, Question_idQuestion) VALUES ("+proposition+", "+estValide+", "+idQuestion+");",function(error, rows){         
				if(error != null) {
					resp.end("Query error:" + error);
				} else {
					resp.end("Success!");
				}
				
				connection.end();
			});
		}
	}	
});

//config cors
app.use(cors({
	allowedOrigins : ['localhost'],
	headers : ['Content-Type', 'Authorization']
}));


//recuperer choix de matiere (en fonction des formations)
app.get('/listeMatieres', function(req, res) {
	userId = req.headers.Auhtorization; //recuperation de l'id
	connection.query("select matiere.nom, matiere.idMatiere from quizz, matiere where Niveau_etude_idNiveau_etude = (select Niveau_etude_idNiveau_etude from compte where idCompte ="+userId+") and idMatiere =Matiere_idMatiere;", function(err, rows, fileds){
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
	connection.query("select * from quizz where Niveau_etude_idNiveau_etude = (select Niveau_etude_idNiveau_etude from compte where idCompte = "+userId+") and Matiere_idMatiere="+id+" ;", function(err, rows, fileds){
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
	
connection.query("select idQuestion, proposition, estValide, nom from proposition, question where Quizz_idQuizz = "+id+" and question.idQuestion= Question_idQuestion group by idQuestion, nom, proposition, estValide",function(err, rows, fileds){
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