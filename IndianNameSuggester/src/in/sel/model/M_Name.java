package in.sel.model;

public class M_Name {

	String name_ma;
	String name_en;
	String frequency;
	int id;

	public M_Name(String name_ma, String name_en, String frequency) {
		super();
		this.name_ma = name_ma;
		this.name_en = name_en;
		this.frequency = frequency;
	}
	
	public M_Name(String name_ma, String name_en, String frequency, int id) {
		super();
		this.name_ma = name_ma;
		this.name_en = name_en;
		this.frequency = frequency;
		this.id = id;
	}

	public String getName_ma() {
		return name_ma;
	}

	public void setName_ma(String name_ma) {
		this.name_ma = name_ma;
	}

	public String getName_en() {
		return name_en;
	}

	public void setName_en(String name_en) {
		this.name_en = name_en;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
