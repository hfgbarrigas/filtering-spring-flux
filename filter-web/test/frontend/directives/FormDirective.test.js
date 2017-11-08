'use strict';

describe('directive:formDirective tests', function () {

	var $compile, scope, $rootScope, deferred, $q, MatchesFactory, MatchDetails;

	beforeEach(module('Filtering'));

	beforeEach(inject(function (_$compile_, _$rootScope_, _$q_, _MatchesFactory_, _MatchDetails_) {
		$compile = _$compile_;
		$rootScope = _$rootScope_;
		scope = $rootScope.$new();
		$q = _$q_;
		deferred = $q.defer();
		MatchesFactory = _MatchesFactory_;
		MatchDetails = _MatchDetails_;
	}));

	it('should have form directive', function () {
		//prepare
		spyOn(MatchesFactory, 'getMatches').and.callFake(function () {
			return deferred.promise;
		});

		spyOn(MatchDetails, 'build').and.callFake(function () {
			return {};
		});

		//act
		var directive = $compile(angular.element('<filtering-form></filtering-form>'))(scope);
		scope.$digest();

		//assert
		var controller = directive.controller;

		expect(directive).not.toBeNull();
		expect(controller).not.toBeNull();
		expect(MatchesFactory.getMatches).toHaveBeenCalled();
		deferred.resolve([{}]);
		scope.$apply();
		expect(scope.matchesCtrl.data.matches).toEqual([{}]);
	});

});