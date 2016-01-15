package gov.samhsa.bhits.vss.service.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class MedicalSectionDto extends AbstractNodeDto{
	
	private String description;
	
	
	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	@Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }	

}
