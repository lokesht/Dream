package in.sel.model;

import java.util.Collection;
import java.util.Comparator;

public class M_Name implements Comparator<M_Name> {

	String name_ma;
	String name_en;
	int frequency;
	int id;
	int description;

	public M_Name(String name_en, String name_ma, int frequency) {
		super();
		this.name_en = name_en;
		this.name_ma = name_ma;
		this.frequency = frequency;
	}

	public M_Name(String name_ma, String name_en, int frequency, int id,
			int description) {
		super();
		this.name_ma = name_ma;
		this.name_en = name_en;
		this.frequency = frequency;
		this.id = id;
		this.description = description;
	}

	public int getDescription() {
		return description;
	}

	public void setDescription(int description) {
		this.description = description;
	}

	public M_Name(String name_ma, String name_en, int frequency, int id) {
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

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public int compare(M_Name lhs, M_Name rhs) {
		return lhs.getFrequency()-rhs.getFrequency();
	}
}
