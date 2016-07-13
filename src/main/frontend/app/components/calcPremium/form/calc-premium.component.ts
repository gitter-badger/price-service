import {Component} from '@angular/core'
import {NgForm}    from '@angular/forms';

import {PriceService} from "../shared/price.service";
import {Person} from "../shared/person";


@Component({
	selector: 'calc-premium',
	templateUrl: 'calc-premium.component.html',
	styleUrls: ['calc-premium.component.scss']
})
export class CalcPremiumComponent {
	title = 'Prämie berechnen';
	model = new Person();

	constructor(private priceService:PriceService) {
	}

	submit() {
		this.priceService.getPrice(this.model);
	}
}
