/**
 * Created by Victor on 21/02/2016.
 */

angular.module('RevisatorProfApp')
    .controller('NewEditQuizController', function($scope, $http, LxNotificationService){

        $http.get('data/quizlist.json').then(function(response){
            $scope.quizes = response.data;
        },function(reason){
            console.log(reason);
        });

        $scope.domaine = [
            { name: 'Economie'},
            { name: 'Histoire'},
            { name: 'Philosophie'},
            { name: 'Informatique'},
            { name: 'Sociologie'},
            { name: 'Médecine'},
            { name: 'Mathématiques'},
            { name: 'Arts'}
        ];

        $scope.matiere = [
            { name: 'Base de données'},
            { name: 'Socio 1'},
            { name: 'Programmation'},
            { name: 'Fiscalité des entreprises'},
            { name: 'Finance de marché'},
            { name: 'Médecine'},
            { name: 'Mathématiques pour l informatique'}
        ];

        $scope.selectedDomaine = 'letest';

        $scope.nom = 'Nom Prenom';

        $scope.setSelectedDomaine = function(domaine) {
            $scope.selectedDomaine = domaine;
            console.log(domaine);
        };

        $scope.number = 1;
        $scope.getNumber = function(num) {
            return new Array(num);
        };

        $scope.increaseNumber = function() {
          $scope.number = $scope.number+1;
        };

        $scope.decreaseNumber = function() {
            if($scope.number != 1){
                $scope.number = $scope.number-1;
            }
        };

        $scope.confirmCreation = function(quiz)
        {
            LxNotificationService.success('Votre quiz a bien été créé');
        };

    });