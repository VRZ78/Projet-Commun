var http = require('http');
var express = require('express');
var app = express();
var mysql = require('mysql');
var cors = require('express-cors');
var bodyParser = require('body-parser');
var userId;
var connection = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: 'root',
    database: 'revisator'
});

//connexion à la base de données
connection.connect(function(err){
    if(!err) {
        console.log("connexion à la bdd reussi");
    } else {
        console.log("Erreur lors de la tentative de connexion....");
    }
});

//config cors
app.use(cors({
    allowedOrigins : ['localhost:63342'],
    headers : ['Content-Type', 'Authorization']
}));

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));


//ajouter un quizz dans la base de données
app.post('/ajouterQuizz', function(req, resp) {
    var userId = req.headers.Auhtorization;
    var nomQuizz = req.body.title;
    var nomMatiere = req.body.matiere.name;
    var niveauEtude = req.body.year.name;
    var nombreQuestion = req.body.nbOfQuestions;
    var idMatiere = "";
    var idNiveauEtude = "";
    var idQuizz = "";
    var idQuestion = "";

    console.log(nomMatiere);
    connection.query("SELECT * FROM matiere WHERE nom = '"+nomMatiere+"';", function select(err, rows, fields) {
        if (err) {
            console.log(err);
            connection.end();
            return;
        }

        if (rows.length > 0) {
            var firstResult = rows[0];
            console.log(firstResult);
            idMatiere = firstResult["idMatiere"];
            console.log("L id de la matiere : " + idMatiere);
        } else {
            console.log("Pas de données Matiere");
        }

    });

    connection.query("SELECT * FROM niveau_etude WHERE niveau = '"+niveauEtude+"';", function select(err, rows, fields) {
        if (err) {
            console.log(err);
            connection.end();
            return;
        }

        if (rows.length > 0) {
            var firstResult = rows[0];
            idNiveauEtude = firstResult['idNiveau_etude'];
        } else {
            console.log("Pas de données nivieau d etude");
        }

    });

    console.log("L id de la matiere n 2: " + idMatiere);
    connection.query("INSERT INTO quizz(nom, Compte_idCompte, Matiere_idMatiere, Niveau_etude_idNiveau_etude)\n VALUES('"+nomQuizz+"',\n "+userId+",\n "+idMatiere+",\n "+idNiveauEtude+");",function(error, rows){
        if(error != null) {
            console.log(error);
        } else {
            resp.end("Success!");
        }

    });

    connection.query("SELECT * FROM quizz ORDER BY idQuizz desc LIMIT 1;", function select(err, rows, fields) {
        if (err) {
            console.log(err);
            connection.end();
            return;
        }

        if (rows.length > 0) {
            var firstResult = rows[0];
            idQuizz = firstResult['idQuizz'];
        } else {
            console.log("Pas de données idQuizz");
        }

    });

    for(i = 1; i <= nombreQuestion; i++) {

        nomQuestion = req.body.questions[i].questionTitle;
        nombreReponse = req.body.questions[i].nombreReponse;

        connection.query("INSERT INTO question(nom, Quizz_idQuizz) VALUES ('"+nomQuestion+"', "+idQuizz+");",function(error, rows){
            if(error != null) {
                resp.end("Query error:" + error);
            } else {
                resp.end("Success!");
            }
        });

        connection.query("SELECT * FROM question ORDER BY idQuestion desc LIMIT 1;", function select(err, rows, fields) {
            if (err) {
                console.log(err);
                return;
            }

            if (rows.length > 0) {
                var firstResult = rows[0];
                idQuestion = firstResult['idQuestion'];
            } else {
                console.log("Pas de données idQuestion");
            }


        });

        for(j = 1; j <= nombreReponse; j++) {

            proposition = req.body.question[i].questionAnswer[j];
            estValide = req.body.question[i].correctAnswer[j];

            connection.query("INSERT INTO proposition(proposition, estValide, Question_idQuestion) VALUES ('"+proposition+"', "+estValide+", "+idQuestion+");",function(error, rows){
                if(error != null) {
                    resp.end("Query error:" + error);
                } else {
                    resp.end("Success!");
                }

            });
        }
    }
});



//recuperer choix de matiere (en fonction des formations)
app.get('/listeMatieres', function(req, res) {
    userId = req.headers.Auhtorization; //recuperation de l'id
    connection.query("select matiere.nom matiere.idMatiere from quizz, matiere where Niveau_etude_idNiveau_etude = (select Niveau_etude_idNiveau_etude from compte where idCompte ="+userId+") and idMatiere =Matiere_idMatiere;", function(err, rows, fileds){
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