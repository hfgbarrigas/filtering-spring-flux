import template from '../templates/person-details.html';

function PersonDetailsDirective() {
    return {
        restrict: 'AE',
	    template: template,
	    scope: {
            personDetails: '='
        },
        link: function (scope, element) {
        }
    };
}

PersonDetailsDirective.$inject = [];

export default PersonDetailsDirective;
