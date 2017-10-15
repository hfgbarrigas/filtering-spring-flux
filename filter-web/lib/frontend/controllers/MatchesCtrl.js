function GoogleAPIAutocompleteCtrl(MatchesFactory, MatchDetails, $timeout, $rootScope) {

	let ctrl = this;

	ctrl.data = {
		matches: [],
		distanceUnit: 'km',
		withPhoto: false,
		inContact: false,
		favourite: false,
		compatibilitySlider: {
			min: 0.5,
			max: 1,
			options: {
				floor: 0,
				ceil: 1,
				step: 0.01,
				stepsArray: [0.0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1],
				precision: 0.01,
				noSwitching: true,
				showSelectionBar: true,
				onEnd: _load
			}
		},
		ageSlider: {
			min: 18,
			max: 95,
			options: {
				floor: 18,
				ceil: 95,
				step: 1,
				noSwitching: true,
				showSelectionBar: true,
				onEnd: _load
			}
		},
		heightSlider: {
			min: 135,
			max: 210,
			options: {
				floor: 135,
				ceil: 210,
				step: 1,
				noSwitching: true,
				showSelectionBar: true,
				onEnd: _load
			}
		},
		distanceSlider: {
			value: 50,
			options: {
				floor: 0,
				ceil: 1000,
				step: 1,
				showSelectionBar: true,
				onEnd: _load
			}
		}
	};

	ctrl.functions = {
		load: _load
	};

	function _load() {
		MatchesFactory.getMatches(
			ctrl.data.withPhoto,
			ctrl.data.inContact,
			ctrl.data.favourite,
			ctrl.data.ageSlider.max,
			ctrl.data.ageSlider.min,
			ctrl.data.heightSlider.max,
			ctrl.data.heightSlider.min,
			ctrl.data.compatibilitySlider.max,
			ctrl.data.compatibilitySlider.min,
			ctrl.data.distanceSlider.value,
			ctrl.data.distanceUnit,
			53.801277,
			-1.548567
		).then((data) => {
			ctrl.data.matches = data.map(MatchDetails.build);
		}, (err) => {
			//do something with this
			console.error(err);
		});
	}

	function _init() {
		_load();
	}

	_init();
}

GoogleAPIAutocompleteCtrl.$inject = ['MatchesFactory', 'MatchDetails', '$timeout', '$rootScope'];

export default GoogleAPIAutocompleteCtrl;
