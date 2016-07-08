import {Component} from '@angular/core'
import {NgForm}    from '@angular/forms';

import {PriceService} from "./shared/price.service";
import {Person} from "./shared/person";


@Component({
	selector: 'my-app',
	templateUrl: 'app/components/app.component.html',
	styleUrls: ['app/components/app.component.css'],
	providers: [
		PriceService
	]
})
export class AppComponent {
	title = 'Prämie berechnen';
	model = new Person();

	constructor(private priceService:PriceService) {
	}

	submit() {
		this.priceService.getPrice(this.model);
	}
}
