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

        $scope.confirm = function (quiz) {
            $http.post('http://localhost:8080/signup', JSON.stringify(quiz)).then(function (response) {
                array.splice(index, 1);
                LxNotificationService.success(quiz.name + ' a bien été supprimé');
            }, function () {
                LxNotificationService.error('Impossible de contacter le serveur');
            });
        };

    });