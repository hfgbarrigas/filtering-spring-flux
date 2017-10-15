//angular
import angular from 'angular';
import ngRoute from 'angular-route';

//angular slider
import rzModuleCss from 'angularjs-slider/dist/rzslider.min.css';
import rzModule from 'angularjs-slider/dist/rzslider.min.js';

//boostrap
import bootstrapJs from 'bootstrap/dist/js/bootstrap.min.js';
import boostrapCss from 'bootstrap/dist/css/bootstrap.min.css';

//angular boostrap
import angularBootstrap from 'angular-bootstrap/ui-bootstrap.min.js';

// Templates
import template from './templates/form.html';

//app css
import css from '../static/css/app.css';

//app components
import filteringFormDirective from './directives/filteringFormDirective.js';
import personDetailsDirective from './directives/personDetailsDirective.js';
import MatchesCtrl from './controllers/MatchesCtrl.js';
import MatchDetails from './models/MatchDetails.js';
import MatchesFactory from './services/MatchesFactory.js';

export default angular
	.module('Filtering', [ngRoute, rzModule])
	.directive('filteringForm', filteringFormDirective)
	.directive('personDetails', personDetailsDirective)
	.controller('MatchesCtrl', MatchesCtrl)
	.factory('MatchesFactory', MatchesFactory)
	.factory('MatchDetails', MatchDetails)
	.name;
