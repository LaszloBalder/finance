package nl.yasmijn.state.allowance;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import nl.yasmijn.borrowingcapacity.HouseholdTypeKey;

public class HousingAllowance {
	private static final double lowMaxRent = 361.61;
	private static final double highMaxRent = 652.52;
	private static final double discountLimit = 361.66;
	private static final double service1Limit = 12.0;
	private static final double service2Limit = 12.0;
	private static final double service3Limit = 12.0;
	private static final double service4Limit = 12.0;
	private static final double smallHousehold = 517.64;
	private static final double largeHousehold = 554.76;
	private static final double ONEHUNDRED = 100.0;

	private static SortedMap<HouseholdTypeKey, HousingAllowanceParameters> parameters = new TreeMap<HouseholdTypeKey, HousingAllowanceParameters>();

	public HousingAllowance() {
		if (parameters.size() == 0) {
			initializeParameters();
		}
	}

	public double getAllowance(List<Beneficiary> persons, double monthlyRent, double service1, double service2, double service3,
			double service4, boolean oldAllowanceRights) {
		if (persons.size() == 0) {
			throw new IllegalArgumentException("Allowance calculations needs persons with income");
		}
		boolean multipleHousehold = persons.size() > 1;
		double toppingLimit = persons.size() > 1 ? largeHousehold : smallHousehold;

//		HouseholdInfo hi = new HouseholdInfo(persons);
		Date calcDate = Calendar.getInstance().getTime();
		int totalIncome = 0;
		int above65Income = 0;
		int maxAge = 0;
		int minAge = Integer.MAX_VALUE;
		boolean hasPensioner = false;
		for (Beneficiary person : persons) {
			maxAge = Math.max(maxAge, person.getAge(calcDate));
			minAge = Math.min(minAge, person.getAge(calcDate));
			totalIncome += person.getIncome();
			if (person.getAge(calcDate) >= 65) {
				above65Income += person.getIncome();
				hasPensioner = true;
			}
		}

		double maxRent = lowMaxRent;
		if (maxAge >= 23 || (persons.size() == 1 && persons.get(0).getDisabilityRate() > 0.1)
				|| (persons.size() > 1 && minAge < 10)) {
			maxRent = highMaxRent;
		}

		boolean pensionerHousehold = above65Income >= (totalIncome - above65Income);
		HousingAllowanceParameters data = getData(multipleHousehold, pensionerHousehold);

		double calcRent = monthlyRent + Math.min(service1, service1Limit) + Math.min(service2, service2Limit)
				+ Math.min(service3, service3Limit) + Math.min(service4, service4Limit);
		if (calcRent > maxRent && !oldAllowanceRights) {
			return 0.0;
		}

		double baseRent = data.getMinBaseRent();
		if (totalIncome > data.getMinIncome()) {
			BigDecimal calcBaseRent = data.getFactorA().multiply(new BigDecimal(totalIncome * totalIncome)).add(
					data.getFactorB().multiply(new BigDecimal(totalIncome)));
			baseRent = roundToCents(calcBaseRent.doubleValue() + data.getTaskAmount());
		}

		double typeArent = Math.min(calcRent, discountLimit);
		// System.out.println(typeArent);
		// System.out.println(Math.max(baseRent, 0.0));
		double allowanceA = 0.0;
		if (typeArent > Math.max(baseRent, 0.0)) {
			allowanceA = typeArent - Math.max(baseRent, 0.0);
			allowanceA = roundToCents(allowanceA);
		}

		double typeBrent = 0.0;
		double allowanceB = 0.0;
		if (Math.min(calcRent, toppingLimit) > discountLimit) {
			typeBrent = roundToCents(Math.min(calcRent, toppingLimit) - discountLimit);
			allowanceB = Math.max(typeBrent, 0.0) * 0.75;
			allowanceB = roundToCents(allowanceB);
		}

		double allowanceC = 0.0;
		if ((hasPensioner || !multipleHousehold) && calcRent > Math.max(baseRent, toppingLimit)) {
			allowanceC = (calcRent - Math.max(baseRent, toppingLimit)) * 0.5;
			allowanceC = roundToCents(allowanceC);
		}
		// System.out.println(allowanceA);
		// System.out.println(allowanceB);
		// System.out.println(allowanceC);
		return allowanceA + allowanceB + allowanceC;
	}

	private double roundToCents(double amount) {
		return Math.round(ONEHUNDRED * amount) / ONEHUNDRED;
	}

	private HousingAllowanceParameters getData(boolean multiple, boolean pensioner) {
		return parameters.get(new HouseholdTypeKey(multiple, pensioner));
	}

	public static void main(String[] args) {
		HousingAllowance ha = new HousingAllowance();
		// double all = ha.getAllowance(getPersons(), 308, 14, 11, 15, 13,
		// false);
		// double all = ha.getAllowance(getPersons(), 560, 16, 20, 11, 16,
		// false);
		double all = ha.getAllowance(getPersons(), 510, 0, 0, 0, 0, false);
		System.out.println(all);
	}

	public static List<Beneficiary> getPersons() {
		List<Beneficiary> persons = new ArrayList<Beneficiary>();
		PersonImpl person = new PersonImpl();
		person.setIncome(20000);
		person.setAge(30);
		person.setDisabilityRate(0);
		persons.add(person);
		person = new PersonImpl();
		person.setIncome(5000);
		person.setAge(27);
		person.setDisabilityRate(0);
		persons.add(person);
		person = new PersonImpl();
		person.setIncome(5000);
		person.setAge(33);
		person.setDisabilityRate(0);
		// persons.add(person);

		return persons;
	}

	private void initializeParameters() {
		// single
		HousingAllowanceParameters data = new HousingAllowanceParameters();
		data.setFactorA(new BigDecimal("0.000000674894"));
		data.setFactorB(new BigDecimal("0.002076557038"));
		data.setMinIncome(15700);
		data.setTypeLimits(21625);
		data.setTaskAmount(17.91);
		data.setMinNormRent(194.33);
		data.setMinBaseRent(212.24);
		parameters.put(new HouseholdTypeKey(false, false), data);

		// multiple
		data = new HousingAllowanceParameters();
		data.setFactorA(new BigDecimal("0.000000382988"));
		data.setFactorB(new BigDecimal("0.002013352663"));
		data.setMinIncome(20350);
		data.setTypeLimits(29350);
		data.setTaskAmount(17.91);
		data.setMinNormRent(194.33);
		data.setMinBaseRent(212.24);
		parameters.put(new HouseholdTypeKey(true, false), data);

		// single 65+
		data = new HousingAllowanceParameters();
		data.setFactorA(new BigDecimal("0.000001182582"));
		data.setFactorB(new BigDecimal("-0.006695504780"));
		data.setMinIncome(16100);
		data.setTypeLimits(20325);
		data.setTaskAmount(17.91);
		data.setMinNormRent(192.51);
		data.setMinBaseRent(210.42);
		parameters.put(new HouseholdTypeKey(false, true), data);

		// multiple 65+
		data = new HousingAllowanceParameters();
		data.setFactorA(new BigDecimal("0.000000670694"));
		data.setFactorB(new BigDecimal("-0.005468796372"));
		data.setMinIncome(21725);
		data.setTypeLimits(27750);
		data.setTaskAmount(17.91);
		data.setMinNormRent(190.70);
		data.setMinBaseRent(208.61);
		parameters.put(new HouseholdTypeKey(true, true), data);

		// /*
		// * Factor a 0,000000674894 0,000000382988 0,000001182582
		// 0,000000670694
		// * Factor b 0,002076557038 0,002013352663 -0,006695504780
		// -0,005468796372
		// * Overige bedragen (in Euro)
		// * Minimuminkomensgrens 15.700 20.350 16.100 21.725
		// * Doelgroepgrens 21.625 29.350 20.325 27.750
		// * Taakstellingsbedrag 17,91 17,91 17,91 17,91
		// * Minimumnormhuur 194,33 194,33 192,51 190,70
		// * Minimumbasishuur 212,24 212,24 210,42 208,61
		// */
	}
}

class PersonImpl implements Beneficiary {

	Date dateOfBirth;
	double disabilityRate;
	int income;
	int age;
	boolean underCareLaw = true;

	@Override
	public int getAge(Date calcDate) {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public double getDisabilityRate() {
		return disabilityRate;
	}

	@Override
	public int getIncome() {
		return income;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public void setDisabilityRate(double disabilityRate) {
		this.disabilityRate = disabilityRate;
	}

	public void setIncome(int income) {
		this.income = income;
	}
	
	public void setUnderCareLaw(boolean underCareLaw){
		this.underCareLaw = underCareLaw;
	}

	@Override
	public boolean isUnderCareLaw() {
		return false;
	}
}

/*
Wie is uw toeslagpartner?
Klik om dit helpvenster te sluiten.
Duurzaam gescheiden leven

Kies 'nee' als u duurzaam gescheiden leeft van uw echtgenoot of geregistreerde partner. Wij beschouwen u dan als ongetrouwd.
Ander adres

Kies 'ja' als uw echtgenoot of geregistreerde partner op een ander adres woont, maar u niet duurzaam gescheiden leeft. Uw partner woont en werkt bijvoorbeeld tijdelijk in het buitenland.

Hebt u huurtoeslag en staat uw echtgenoot of geregistreerde partner op een ander adres ingeschreven? Kies dan 'nee'.
Verblijf in verpleeghuis

Kies 'ja' als uw echtgenoot of geregistreerde partner in een verpleeghuis woont.

Hebt u huurtoeslag en staat uw partner ingeschreven op het adres van het verpleeghuis? Kies dan 'nee'.
Meer informatie

Lees meer bij Toeslagpartner. Deze pagina opent in een nieuw venster.
Klik om dit helpvenster te sluiten.
Huisgenoot

Een huisgenoot is iemand die bij u in huis woont (op hetzelfde adres staat ingeschreven), bijvoorbeeld uw kind, uw ouder(s) of een vriend. Een huisgenoot telt alleen voor de huurtoeslag mee. Die noemen we dan medebewoner.
Onderhuurder

Een onderhuurder is iemand met wie u een schriftelijk huurcontract hebt gesloten.
Klik om dit helpvenster te sluiten.
Fiscale partner

Is uw huisgenoot uw fiscale partner voor de inkomstenbelasting? Dan is die ook uw toeslagpartner.
Klik om dit helpvenster te sluiten.
Eerder toeslagpartner geweest

Kies 'ja' als u in een eerder jaar huur-, zorg-, kinder- of kinderopvangtoeslag hebt gehad en u daarbij 1 van uw huisgenoten als toeslagpartner hebt opgegeven.

Kies 'ja' als u in een eerder jaar huur-, zorg-, kinder- of kinderopvangtoeslag of kindgebonden budget hebt gehad en u daarbij 1 van uw huisgenoten als toeslagpartner hebt opgegeven.
Klik om dit helpvenster te sluiten.
Huishouden

In een huishouden kiezen de bewoners er bewust voor, voor elkaar te zorgen. Er is een verdeling van alle huishoudelijke taken. De een doet bijvoorbeeld de was en de ander doet de boodschappen of kookt.
Klik om dit helpvenster te sluiten.
Gezamenlijke huishouding

U hebt een gezamenlijke huishouding met uw huisgenoot als u allebei uw hoofdverblijf in dezelfde woning hebt en u voor elkaar zorgt. U deelt bijvoorbeeld de kosten van de huishouding en doet allebei huishoudelijke taken.

U voert meestal geen gezamenlijke huishouding als u als student of religieuze samenwoont met iemand anders. Of als u in een verzorgingshuis of in een ander samenleefverband woont. U woont dan namelijk niet samen met het doel om voor elkaar te zorgen. Kies in deze situaties 'nee'.
Klik om dit helpvenster te sluiten.
Naaste familie

Naaste familieleden zijn: broers en zussen, ouders, grootouders, kinderen en kleinkinderen.
Klik om dit helpvenster te sluiten.
Meerderjarige broer of zus

Kies alleen 'ja' als het gaat om een broer of zus die 18 jaar of ouder is.
Klik om dit helpvenster te sluiten.
Naaste familie

Kies 'ja' als deze huisgenoot een grootouder, kind of kleinkind van u is.
Klik om dit helpvenster te sluiten.
Toeslagpartner van iemand anders

De toeslagpartner van een ander kan niet uw toeslagpartner zijn.

Hebt u een echtgenoot of geregistreerde partner?
Let op! Lees eerst de toelichting. ja nee nee Klik hier voor help over deze vraag.

Staan er behalve uzelf ook andere mensen ingeschreven op uw woonadres? Hierna noemen wij deze personen huisgenoten.
Let op! Onderhuurders tellen niet mee. ja nee ja Klik hier voor help over deze vraag.

Is 1 van uw huisgenoten uw fiscale partner? ja nee nee Klik hier voor help over deze vraag.

Hebt u met 1 van uw huisgenoten een samenlevingscontract gesloten bij de notaris? ja nee nee

Hebt u samen met 1 van uw huisgenoten een kind? ja nee nee

Is 1 van uw huisgenoten in een eerder jaar uw toeslagpartner geweest? ja nee nee Klik hier voor help over deze vraag.

Is 1 van uw huisgenoten partner voor uw pensioen? ja nee nee

Woont u met 1 van uw huisgenoten in een woning waarvan u samen eigenaar bent? En bent u allebei aansprakelijk voor de hypotheekschuld van deze woning? ja nee nee

Hoeveel meerderjarige huisgenoten hebt u?

Woont u bij uw ouders? ja nee nee
Huisgenoot 1

Verwacht u dat deze huisgenoot in 2011 6 maanden of langer onafgebroken ingeschreven staat op uw woonadres? ja nee nee

Voert u met deze huisgenoot in dezelfde periode een gezamenlijke huishouding? ja nee Klik hier voor help over deze vraag.

Is deze huisgenoot een naast familielid van u? ja nee Klik hier voor help over deze vraag.

Is deze huisgenoot uw broer of zus? ja nee Klik hier voor help over deze vraag.

Is deze huisgenoot een ander naast familielid van u? ja nee Klik hier voor help over deze vraag.

Is deze huisgenoot de toeslagpartner van iemand anders dan uzelf? ja nee Klik hier voor help over deze vraag.

Deze huisgenoot is niet uw toeslagpartner.
Huisgenoot 2

Verwacht u dat deze huisgenoot in 2011 6 maanden of langer onafgebroken ingeschreven staat op uw woonadres? ja nee 
*/