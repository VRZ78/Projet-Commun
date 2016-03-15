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

        var idDepart=-1;
        console.log("nom "+response.data[0].nom);
        var position = 0;
        var question = {
            "question" : "",
            "idQuestion" : null,
            "reponse" :[]
        };

        //Injection des questions
        for(var i=0; i<response.data.length;i++){
            question.question= response.data[i].nom;
            question.idQuestion=response.data[i].idQuestion;
            if(idDepart!==response.data[i].idQuestion ){
                //On push la question et le tableau de questions
                questions.questions.push(question);
                //On change de question
                position++;
                idDepart=response.data[i].idQuestion;
                //remise à zero
                question = {
                    "question" : "",
                    "reponse" :[]
                };
            }
        }
        //Injection des reponses correspondant aux questions
        var idQuestionPourInjecterReponse=response.data[0].idQuestion;
        var positionReponse = 0;
        console.log(response.data.length);
        for(var i=0; i < response.data.length;i++){
            if(idQuestionPourInjecterReponse==response.data[i].idQuestion){
                console.log(i+" et injection "+response.data[i].proposition);
                questions.questions[positionReponse].reponse.push(response.data[i].proposition);
            }else{
                //console.log("position "+positionReponse);
                idQuestionPourInjecterReponse=response.data[i].idQuestion;
                positionReponse++;
                console.log(i+" et injection "+response.data[i].proposition);
                questions.questions[positionReponse].reponse.push(response.data[i].proposition);
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