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

        // Affiche le nom et le prénom de l'utilisateur connecté dans la ToolBar
        $scope.nom = 'Nom Prenom';

        // Liste des domaines
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

        // Liste des matières
        $scope.matiere = [
            { name: 'Base de données'},
            { name: 'Socio 1'},
            { name: 'Programmation'},
            { name: 'Fiscalité des entreprises'},
            { name: 'Finance de marché'},
            { name: 'Médecine'},
            { name: 'Mathématiques pour l informatique'}
        ];

        // Permet d'enregistrer le domaine sélectionné
        $scope.selectedDomaine = '';

        // Lorsque l'utilisateur sélectionne un domaine, enregistre le domaine en mémoire
        $scope.setSelectedDomaine = function(domaine) {
            $scope.selectedDomaine = domaine;
            console.log(domaine);
        };

        // Nb de question que comporte le quiz, par défaut 1
        $scope.numberOfQuestions = 1;

        // Renvoie le nombre de question que comporte le quiz sous forme d'un tableau (pour ng-repeat)
        $scope.getNumberOfQuestions = function(num) {
            return new Array(num);
        };

        // Incrémente le nombre de questions
        $scope.increaseNumberOfQuestions = function() {
          $scope.numberOfQuestions = $scope.numberOfQuestions+1;
        };

        // Décrémente le nombre de questions
        $scope.decreaseNumberOfQuestions = function() {
            if($scope.numberOfQuestions != 1){
                $scope.numberOfQuestions = $scope.numberOfQuestions-1;
            }
        };

        // Liste des choix possibles pour le nombre de réponses
        $scope.nbAnswer = [
            { name: 2},
            { name: 3},
            { name: 4},
            { name: 5},
            { name: 6}
        ];

        // Stock le nombre de réponses pour chaque question, où le n° de la qu est l'index du tableau
        $scope.nbOfAnswer = [];
        $scope.showAnswetSelect = [];

        // Enregistre le nombre de réponse que l'utilisateur souhaite avoir pour une question
        $scope.setSelectedNbAnswer = function(QuestionNb, nbOfAnswer) {
            $scope.nbOfAnswer[QuestionNb] = nbOfAnswer;
        };

        // Retourne le nombre de réponses pour une question donnée sous la forme d'un tableau (ng-repeat)
        $scope.getNbOfAnswer = function(questionNb){
            return new Array($scope.nbOfAnswer[questionNb]);
        };

        // Affiche un Toast lors de la création d'un compte
        $scope.confirmCreation = function(quiz)
        {
            LxNotificationService.success('Votre quiz a bien été créé');
        };

    });