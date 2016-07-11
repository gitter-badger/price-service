import {Injectable} from '@angular/core';
import {Headers, Http} from '@angular/http';
import 'rxjs/add/operator/toPromise';

import {Person} from './person';

@Injectable()
export class PriceService {
	private priceUrl = 'app/price';  // URL to web api

	constructor(private http:Http) {
	}

	getPrice(person:Person):Promise<Person> {
		return this.post(person);
	}

	private post(person:Person):Promise<Person> {
		let headers = new Headers({
			'Content-Type': 'application/json'
		});

		return this.http
			.post(this.priceUrl, JSON.stringify(person), {headers: headers})
			.toPromise()
			.then(res => res.json().data)
			.catch(this.handleError);
	}

	private handleError(error:any) {
		console.error('An error occurred', error);
		return Promise.reject(error.message || error);
	}
}

