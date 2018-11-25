package nl.yasmijn.state.tax;

import nl.yasmijn.utils.RangeKey;

public class Tester {

	public static void main(String[] args) {
		
		TaxSystem ts = new TaxSystem();
		/*
		 *             defaultList.Add(CreateNotionalRentalValue(0, 12500, new decimal(0.0), 0));
            defaultList.Add(CreateNotionalRentalValue(12501, 25000, new decimal(0.2), 0));
            defaultList.Add(CreateNotionalRentalValue(25001, 50000, new decimal(0.3), 0));
            defaultList.Add(CreateNotionalRentalValue(50001, 75000, new decimal(0.4), 0));
            defaultList.Add(CreateNotionalRentalValue(75001, 1010000, new decimal(0.55), 0));
            defaultList.Add(CreateNotionalRentalValue(1010001, Decimal.MaxValue, new decimal(0.8), 5555));

		 */
		ts.addNotionalRentalTaxTariff(0, 12500, 0.00);
		ts.addNotionalRentalTaxTariff(12501, 25000, 0.002);
		ts.addNotionalRentalTaxTariff(25001, 50000, 0.003);
		ts.addNotionalRentalTaxTariff(50001, 75000, 0.004);
		ts.addNotionalRentalTaxTariff(75001, 1010000, 0.0055);
		ts.addNotionalRentalTaxTariff(1010001, Integer.MAX_VALUE, 0.008, 5555);
		System.out.println(ts.getNotionalRentalValue(222000));
		System.out.println(ts.getNotionalRentalValue(12500));
		System.out.println(ts.getNotionalRentalValue(12501));
		System.out.println(ts.getNotionalRentalValue(22500));
		System.out.println(ts.getNotionalRentalValue(32500));
		System.out.println(ts.getNotionalRentalValue(32501));
		System.out.println(ts.getNotionalRentalValue(1010000));
		System.out.println(ts.getNotionalRentalValue(1010001));
		//            List<TaxSliceDto> defaultList = new List<TaxSliceDto>();
//        defaultList.Add(CreateTaxSlice(1, 0, 18218, 33.45M, 15.55M));
//        defaultList.Add(CreateTaxSlice(2, 18218, 32738, 41.95M, 24.05M));
//        defaultList.Add(CreateTaxSlice(3, 32738, 54367, 42, 42));
//        defaultList.Add(CreateTaxSlice(4, 54367, Decimal.MaxValue, 52, 52));
//        return defaultList;

		// tarieven 2010
//		ts.addTaxBracket(0, 18218, 0.3345, 0.1555);
//		ts.addTaxBracket(18218, 32738, 0.4195, 0.2405);
//		ts.addTaxBracket(32738, 54367, 0.42, 0.42);
//		ts.addTaxBracket(54367, Integer.MAX_VALUE, 0.52, 0.52);
//		System.out.println(ts.getIncomeTax(18218));
//		System.out.println(ts.getIncomeTax(32738));
//		System.out.println(ts.getIncomeTax(60000));
		//tarieven 2011
		ts = new TaxSystem();
		ts.addTaxBracket(0, 18628, 0.33, 0.151);
		ts.addTaxBracket(18628, 33436, 0.4195, 0.2405);
		ts.addTaxBracket(33436, 55694, 0.42, 0.42);
		ts.addTaxBracket(55694, Integer.MAX_VALUE, 0.52, 0.52);
		System.out.println(ts.getIncomeTax(1000));
//		System.out.println(ts.getIncomeTax(18628));
//		System.out.println(ts.getIncomeTax(18629));
//		System.out.println(ts.getIncomeTax(28628));
//		System.out.println(ts.getIncomeTax(33436));
//		System.out.println(ts.getIncomeTax(33437));
//		System.out.println(ts.getIncomeTax(34436));
//		System.out.println(ts.getIncomeTax(55694));
//		System.out.println(ts.getIncomeTax(55695));
		System.out.println(ts.getIncomeTax(56694));
		RangeKey r1 = new RangeKey(1, 10);
		RangeKey r2 = new RangeKey(11, 12);
		RangeKey r3 = new RangeKey(13, 19);
		RangeKey v = new RangeKey(11);
		RangeKey v2 = new RangeKey(11);
		System.out.println(v.compareTo(r1));
		System.out.println(v.compareTo(r2));
		System.out.println(v.compareTo(r3));
		System.out.println(r1.compareTo(v));
		System.out.println(r2.compareTo(v));
		System.out.println(r3.compareTo(v));
		System.out.println(v.compareTo(v2));
	}
}
