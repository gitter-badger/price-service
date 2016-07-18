var calcPremiumPage = require('../pageobjects/calcPremiums');

describe('check calcPremiums', function () {
    it('should check Title', function () {
        browser.get(calcPremiumPage.getUrl());
        calcPremiumPage.getTitle().getText().then(function (text) {
            expect(text).toEqual('Prämie berechnen');
        });
    });
});
