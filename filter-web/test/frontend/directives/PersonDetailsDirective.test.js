'use strict';

describe('directive:personDetailsDirective tests', function () {

	var $compile, scope, $rootScope;

	beforeEach(module('Filtering'));

	beforeEach(inject(function (_$compile_, _$rootScope_) {
		$compile = _$compile_;
		$rootScope = _$rootScope_;
		scope = $rootScope.$new();
	}));

	it('should have directive with proper attributes', function () {
		scope.personDetails = {
			test: 'test'
		};

		var directive = $compile(angular.element('<personDetails person-details="personDetails"></personDetails>'))(scope);
		scope.$digest();

		var isolatedScope = directive.isolateScope();
		expect(directive).not.toBeNull();
		expect(isolatedScope.personDetails).toBeDefined();
		expect(isolatedScope.personDetails.test).toEqual('test');
	});

});