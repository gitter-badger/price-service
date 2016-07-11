import {Component} from '@angular/core'

import {PriceService} from "./calcPremium/shared/price.service";
import {CalcPremiumComponent} from "./calcPremium/form/calc-premium.component";


@Component({
	selector: 'my-app',
	templateUrl: 'app/components/app.component.html',
	directives: [
		CalcPremiumComponent
	],
	providers: [
		PriceService
	]
})
export class AppComponent {

}
