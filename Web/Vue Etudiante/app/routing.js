myApp.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.when('/accueil', {
        templateUrl: 'app/views/accueil.html',
        controller: 'accueilCtrl'
    });
    $routeProvider.when('/login', {
        templateUrl: 'app/views/login.html',
        controller: 'loginCtrl'
    });
    $routeProvider.when('/inscription', {
        templateUrl: 'app/views/inscription.html',
        controller: 'inscriptionCtrl'
    });
    $routeProvider.when('/tableauDeBord', {
        templateUrl: 'app/views/tableauDeBord.html',
        controller: 'tableauDeBordCtrl'
    });
    $routeProvider.when('/listeMatiere', {
        templateUrl: 'app/views/listeMatiere.html',
        controller: 'listeMatiereCtrl'
    });
    $routeProvider.when('/listeQuizz/:idMatiere', {
        templateUrl: 'app/views/listeQuizz.html',
        controller: 'listeQuizzCtrl'
    });
    $routeProvider.when('/quizz/:idQuizz', {
        templateUrl: 'app/views/quizz.html',
        controller: 'quizzCtrl'
    });
    $routeProvider.otherwise({
        redirectTo: '/accueil'
    });

}]);