
import template from '../templates/form.html';

function filteringFormDirective () {
    return {
        restrict: 'AE',
        controller: 'MatchesCtrl',
        controllerAs: 'matchesCtrl',
	    template: template,
        link: function (scope, element) {

        }
    };
}

filteringFormDirective.$inject=[];

export default filteringFormDirective;
