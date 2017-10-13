import angular from 'angular';

import tvgGoogleAutocompleteDirective from './googleapiautocomplete/directives/googleApiAutocomplete.js';
import GoogleAPIAutocompleteCtrl from './googleapiautocomplete/controllers/GoogleAPIAutocompleteCtrl.js';
import PlaceDetailsFactory from './googleapiautocomplete/models/PlaceDetails.js';
import GoogleAPIPlacesFactory from './googleapiautocomplete/providers/GoogleAPIFactory.js';

export default angular
  .module('TVG.GoogleAPIAutocomplete', [])
  .directive('tvgGoogleAutocomplete', tvgGoogleAutocompleteDirective)
  .controller('GoogleAPIAutocompleteCtrl', GoogleAPIAutocompleteCtrl)
  .factory('GoogleAPIPlacesFactory', GoogleAPIPlacesFactory)
  .factory('PlaceDetails', PlaceDetailsFactory)
  .name;
