import {disableDeprecatedForms, provideForms} from '@angular/forms';


// The usual bootstrapping imports
import {bootstrap}    from '@angular/platform-browser-dynamic';
import {HTTP_PROVIDERS} from '@angular/http';
import {enableProdMode} from '@angular/core';


import {AppComponent} from './components/app.component';
import 'styles/all.scss';

if (process.env.ENV === 'production') {
	enableProdMode();
}

bootstrap(AppComponent, [
	disableDeprecatedForms(),
	provideForms(),
	HTTP_PROVIDERS
]);
