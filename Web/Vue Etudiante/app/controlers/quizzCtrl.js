/*
TODO

 */


var quizzCtrl = angular.module('quizzCtrl', []);
quizzCtrl.controller('quizzCtrl', function ($scope, $http,$routeParams) {
    console.log("quizzCtrl");

    $http.get('http://localhost:8080/quizz/'+$routeParams.idQuizz).then(function (response) {
        $scope.quizz = response.data;
        console.log(response.data);


        var questions = {
            "questions" : [

            ]
        };

        var idDepart=response.data[0].idQuestion;
        console.log("nome "+response.data[0].nom);
        var position = 0;

        var question = {
            "question" : "",
            "reponse" :[]
        };
        for(var i=0; i<response.data.length;i++){
            question.question= response.data[i].nom;
            if(idDepart===response.data[i].idQuestion){
                question.reponse.push(response.data[i].proposition);
            }else{
                questions.questions.push(question);
                position++;
                idDepart=response.data[i].idQuestion;
                question = {
                    "question" : "",
                    "reponse" :[]
                };
            }
        }
        console.log("voici les questions");
        console.log(questions);

    }, function (reason) {
        console.log(reason);
    });

/*
    $http.get('app/requetes/quizz.json').then(function (response) {
        $scope.quizz = response.data;

        var nombreQuestion = $scope.quizz.questions.length;
        var questionCourante = 0;

        //Question que l'on va afficher à l'ecran
        $scope.question = $scope.quizz.questions[questionCourante].intituleQuestion;
        $scope.reponses = $scope.quizz.questions[questionCourante].reponses;
        //On sauvegarde les resultat
        var resultat = [];

        $scope.questionSuivante = function () {
            if (questionCourante < nombreQuestion - 1) {
                console.log("click");
                questionCourante++;
                $scope.question = $scope.quizz.questions[questionCourante].intituleQuestion;
                $scope.reponses = $scope.quizz.questions[questionCourante].reponses;
            }
        }


    }, function (reason) {
        console.log(reason);
    });
*/
});