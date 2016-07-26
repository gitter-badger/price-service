export const zipService = (function () {
    let defaults = {
        debug: false,	// true sollte nur in Dev-Umgebung für weitere Debug-Infos (z.B. Status des Ajax-Calls) gesetzt werden, Infos erscheinen in der Konsole
        service: {
            url: 'http://www.helsana-u2-test.hel.kko.ch/hlsapp/gateway/helsana/plzOrtSearcher/plzOrtSearcher.cfc' // Pfad zur CFC (nur in Rücksprache mit IFF / Roland Ricklin ändern)
        }
    };


    return {
        getZip: getZip,
    };


    /**
     * @hint public getPlzOrt function which you can call directly from your page, returns response AS JSON
     * @searchStr beliebiger search string (PLZ, Ort)
     * @searchType0 = kein spezifischer searchType, es wird das PLZ-Verzeichnis der Schweiz durchsucht; weitere searchTypes siehe im Header oder den Docs (https://alma-wiki.hel.kko.ch/display/WEB/PLZ-Ort-Suche)
     * @maxNrOfResults maximale Anzahl an Resultaten, welche retourniert werden
     * @isPremiumRelevant ob die Abfrage prämienrelevant sein soll oder nicht; true: Gemeinden und Gemeinde-Nummern sind im Resultset enthalten; hat nur Einfluss, wenn searchType 0 ist
     * @returnPostalCodeAndLocality true: es werden PLZ UND Ort retourniert; false: retourniert nur Orte, vorausgesetzt der Suchstring ist nicht numerisch; hat nur Einfluss, wenn searchType 0 ist; wird ignoriert, wenn isPremiumRelevant true ist
     */
    function getZip(searchStr, searchType, maxNrOfResults, isPremiumRelevant, returnPostalCodeAndLocality) {
        let data = {
            method: 'getPlzOrt',
            searchStr: encodeURIComponent(searchStr),
            searchType: searchType,
            maxNrOfResults: maxNrOfResults || 7,
            isPremiumRelevant: isPremiumRelevant,
            returnPostalCodeAndLocality: returnPostalCodeAndLocality
        };

        return new Promise(function (resolve, reject) {
            $.ajax({
                crossDomain: true,
                dataType: "json",
                url: defaults.service.url,
                data: data
            })
                .then(success, failed);

            function success(response) {
                resolve(response);
            }

            function failed() {
                reject('failed to get ZIP');
            }
        });
    }
})();