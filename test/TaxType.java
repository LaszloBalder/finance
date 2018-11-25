package test;

public enum TaxType {
	None(0), IncomeTax(1), IncomeTaxAddition(2), Deductable(3);
	
	private int typeId;
	private TaxType (int typeId){
		this.typeId = typeId;
	}
	public int getTypeId(){
		return typeId;
	}

}
