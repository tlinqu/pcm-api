package gov.samhsa.bhits.pcm.service.consent;

import gov.samhsa.bhits.pcm.service.dto.ConsentDto;

/**
 * The Interface PolicyIdService.
 */
public interface PolicyIdService {

	/**
	 * Generate policy id.
	 *
	 * @param consentDto
	 *            the consent dto
	 * @param mrn
	 *            the mrn
	 * @return the string
	 */
	public abstract String generatePolicyId(ConsentDto consentDto, String mrn);
}
