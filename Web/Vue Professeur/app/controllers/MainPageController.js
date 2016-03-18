/**
 * Created by Victor on 20/02/2016.
 */
angular.module('RevisatorProfApp')
    .controller('MainPageController', function ($scope, $http, LxNotificationService) {

        $http.get('http://localhost:8080/listeQuizzProf').then(function (response) {
            $scope.quizList = response.data;
        }, function (reason) {
            console.log(reason);
        });

        $http.get('http://localhost:8080/listeMatiereProf').then(function (response) {
            $scope.matiere = response.data;
            console.log($scope.reponse);
        }, function (reason) {
            console.log(reason);
        });

        $scope.matiere = "";

        $scope.nom = "";

        $scope.confirm = function (quiz, $index) {
            $http.delete('http://localhost:8080/suppQuizz/'+ quiz.idQuizz +'', JSON.stringify(quiz)).then(function (response) {
                LxNotificationService.success(quiz.nom + ' a bien été supprimé');
                $scope.removeQuiz($scope.quizList, $index);
            }, function () {
                LxNotificationService.error('Impossible de contacter le serveur');
            });
        };


        $scope.removeQuiz = function(array, index){
            array.splice(index, 1);
        }

    });