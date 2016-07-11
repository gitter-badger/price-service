import {Person} from './person';
describe('Person', () => {
	it('has date of Birth', () => {
		let person:Person = {dateOfBirth: new Date(), gender: 'male', zip: '8610'};
		expect(person.dateOfBirth.getDate()).toEqual(new Date().getDate());
	});
	it('has gender', () => {
		let person:Person = {dateOfBirth: new Date(), gender: 'male', zip: '8610'};
		expect(person.gender).toEqual('male');
	});
	it('has zip', () => {
		let person:Person = {dateOfBirth: new Date(), gender: 'male', zip: '8610'};
		expect(person.zip).toEqual('8610');
	});
});
