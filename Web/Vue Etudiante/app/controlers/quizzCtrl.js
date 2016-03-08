/*
TODO

 */


var quizzCtrl = angular.module('quizzCtrl', []);
quizzCtrl.controller('quizzCtrl', function ($scope, $http) {
    console.log("quizzCtrl");


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

});