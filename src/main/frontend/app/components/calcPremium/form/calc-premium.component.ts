import {Component} from '@angular/core'
import {NgForm}    from '@angular/forms';

import {PriceService} from "../shared/price.service";
import {Person} from "../shared/person";


@Component({
	selector: 'calc-premium',
	templateUrl: 'app/components/calcPremium/form/calc-premium.component.html',
	styleUrls: ['app/components/calcPremium/form/calc-premium.component.css']
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
