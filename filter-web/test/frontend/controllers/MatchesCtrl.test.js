'use strict';

describe('Matches Controller Tests', function () {

	beforeEach(module('Filtering'));

	var $controller, MatchesFactory, MatchDetails, $q, deferred, $rootScope;

	beforeEach(inject(function (_$controller_, _$q_, _MatchesFactory_, _MatchDetails_, _$rootScope_) {
		$controller = _$controller_;
		$q = _$q_;
		deferred = $q.defer();

		MatchesFactory = _MatchesFactory_;
		MatchDetails = _MatchDetails_;
		$rootScope = _$rootScope_;
	}));

	describe('MatchesCtrl Tests', function () {

		it('On Controller loading, with api success call, there should be a list of matches', function () {
			//prepare
			var mocks = {
				MatchesFactory: MatchesFactory,
				MatchesDetails: MatchDetails
			};

			spyOn(MatchesFactory, 'getMatches').and.callFake(function () {
				return deferred.promise;
			});

			spyOn(MatchDetails, 'build').and.callFake(function () {
				return {};
			});

			//act
			var vm = $controller('MatchesCtrl', mocks);

			//assert
			expect(vm).not.toBeNull();
			expect(MatchesFactory.getMatches).toHaveBeenCalled();
			deferred.resolve([{}]);
			$rootScope.$apply();
			expect(vm.data.matches).toEqual([{}]);
		});

		it('On Controller loading, with api error call, there should be an empty list of matches', function () {
			//prepare
			var mocks = {
				MatchesFactory: MatchesFactory,
				MatchesDetails: MatchDetails
			};

			spyOn(MatchesFactory, 'getMatches').and.callFake(function () {
				return deferred.promise;
			});

			spyOn(MatchDetails, 'build').and.callFake(function () {
				return {};
			});

			//act
			var vm = $controller('MatchesCtrl', mocks);

			//assert
			expect(vm).not.toBeNull();
			expect(MatchesFactory.getMatches).toHaveBeenCalled();
			deferred.reject({});
			$rootScope.$apply();
			expect(MatchDetails.build).not.toHaveBeenCalled();
			expect(vm.data.matches).toEqual([]);
		});

		it('Update matches on loading function', function () {
			//prepare
			var mocks = {
				MatchesFactory: MatchesFactory,
				MatchesDetails: MatchDetails
			};

			spyOn(MatchesFactory, 'getMatches').and.callFake(function () {
				return deferred.promise;
			});

			spyOn(MatchDetails, 'build').and.callFake(function () {
				return {randomData: 'randomData'};
			});

			//act
			var vm = $controller('MatchesCtrl', mocks);
			vm.functions.load();

			//assert
			expect(vm).not.toBeNull();
			expect(vm.data.matches).toEqual([]);
			expect(MatchesFactory.getMatches).toHaveBeenCalled();
			expect(MatchesFactory.getMatches.calls.count()).toBe(2);
			deferred.resolve([{randomData: 'randomData'}]);
			$rootScope.$apply();
			expect(MatchDetails.build).toHaveBeenCalled();
			expect(vm.data.matches).toEqual([{randomData: 'randomData'}]);
		});

	});
});