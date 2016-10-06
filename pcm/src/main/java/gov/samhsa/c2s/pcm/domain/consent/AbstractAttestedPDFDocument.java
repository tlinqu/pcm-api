/*******************************************************************************
 * Open Behavioral Health Information Technology Architecture (OBHITA.org)
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package gov.samhsa.c2s.pcm.domain.consent;

import org.hibernate.envers.Audited;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@MappedSuperclass
@Audited(withModifiedFlag = true)
public abstract class AbstractAttestedPDFDocument implements BinaryContentAccessible{
	@NotNull
	@Column(name = "consent_reference_id")
	private String consentReferenceId;

	@NotNull
	@Column(name = "attester_email")
	private String attesterEmail;

	@NotNull
	@Column(name = "attester_last_name")
	private String attesterLastName;

	@NotNull
	@Column(name = "attester_first_name")
	private String attesterFirstName;

	@NotNull
	@Column(name = "attester_middle_name")
	private String attesterMiddleName;

	@NotNull
	@Column(name = "attester_by_user")
	private String attesterByUser;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "MM/dd/yyyy hh:mm:ss")
	@Column(name = "attested_date_time")
	private Date attestedDateTime;

	@NotNull
    @Column(name = "patient_guid")
    private String patientGuid;

	@NotNull
    @Column(name = "attester_ip_address")
    private String attesterIpAddress;


	public String getConsentReferenceId() {
		return consentReferenceId;
	}

	public void setConsentReferenceId(String consentReferenceId) {
		this.consentReferenceId = consentReferenceId;
	}

	public String getAttesterEmail() {
		return attesterEmail;
	}

	public void setAttesterEmail(String attesterEmail) {
		this.attesterEmail = attesterEmail;
	}

	public String getAttesterLastName() {
		return attesterLastName;
	}

	public void setAttesterLastName(String attesterLastName) {
		this.attesterLastName = attesterLastName;
	}

	public String getAttesterFirstName() {
		return attesterFirstName;
	}

	public void setAttesterFirstName(String attesterFirstName) {
		this.attesterFirstName = attesterFirstName;
	}

	public String getAttesterMiddleName() {
		return attesterMiddleName;
	}

	public void setAttesterMiddleName(String attesterMiddleName) {
		this.attesterMiddleName = attesterMiddleName;
	}

	public String getAttesterByUser() {
		return attesterByUser;
	}

	public void setAttesterByUser(String attesterByUser) {
		this.attesterByUser = attesterByUser;
	}

	public Date getAttestedDateTime() {
		return attestedDateTime;
	}

	public void setAttestedDateTime(Date attestedDateTime) {
		this.attestedDateTime = attestedDateTime;
	}

    public String getPatientGuid() {
        return patientGuid;
    }

    public void setPatientGuid(String patientGuid) {
        this.patientGuid = patientGuid;
    }

    public String getAttesterIpAddress() {
        return attesterIpAddress;
    }

    public void setAttesterIpAddress(String attesterIpAddress) {
        this.attesterIpAddress = attesterIpAddress;
    }
}
