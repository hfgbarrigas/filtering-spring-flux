// Styles
import 'bootstrap/less/bootstrap.less';
import '../../css/partials/tvg-registration.less';

// Vendors
import angular from 'angular';
import ngRoute from 'angular-route';
import GoogleAPIAutocomplete from '../app.js';

// Templates
import template from './templates/google-autocomplete-example.html';

export default angular
    .module('TVG.GoogleAPIAutocompleteDemo', [ngRoute, GoogleAPIAutocomplete])
    .config(['$routeProvider', function($routeProvider) {
        $routeProvider.when('/', { template: template });
    }]);
