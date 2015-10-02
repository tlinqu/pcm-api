package gov.va.ds4p.cas.providers;

import static org.junit.Assert.*;
import static org.custommonkey.xmlunit.XMLAssert.assertXMLEqual;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import gov.va.ds4p.policy.reference.ActUSPrivacyLaw;
import gov.va.ds4p.policy.reference.ObligationPolicy;
import gov.va.ds4p.policy.reference.OrgObligationPolicyDocument;
import gov.va.ds4p.policy.reference.OrganizationPolicy;
import gov.va.ds4p.policy.reference.OrganizationTaggingRules;
import gov.va.ds4p.policy.reference.RefrainPolicy;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

public class XACMLPolicyProviderForCDATest {
	
	XACMLPolicyProviderForCDA xACMLPolicyProviderForCDA;
	
	@Before
	public void setUp(){
		xACMLPolicyProviderForCDA=new XACMLPolicyProviderForCDA();
	}
	
	@Test
	public void testCreatePatientConsentXACMLPolicy_when_all_list_are_empty() throws SAXException, IOException {
		String result=xACMLPolicyProviderForCDA.createPatientConsentXACMLPolicy(new OrganizationPolicy(), null, null, new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>());
		//Subject to change when xacml schema changes. If that happens you may generate the xacml file and use online xml escaper to change this part.
		assertXMLEqual(result,"<?xml version=\"1.0\" encoding=\"UTF-8\"?><PolicySet xmlns=\"urn:oasis:names:tc:xacml:2.0:policy:schema:os\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:oasis:names:tc:xacml:2.0:policy:schema:os http://docs.oasis-open.org/xacml/access_control-xacml-2.0-policy-schema-os.xsd\" PolicySetId=\"null-null\" PolicyCombiningAlgId=\"urn:oasis:names:tc:xacml:1.0:policy-combining-algorithm:deny-overrides\"><Target/><!-- DISCLOSURE AUTHORIZATION POLICY --><Policy PolicyId=\"urn:oasis:names:tc:xspa:1.0:nwhin:exchange:query\" RuleCombiningAlgId=\"urn:oasis:names:tc:xacml:1.0:rule-combining-algorithm:deny-overrides\"><Description>Denies the request if authorization does not exist.</Description><Target><Resources><Resource><ResourceMatch MatchId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">DocumentQuery</AttributeValue><ResourceAttributeDesignator AttributeId=\"urn:gov:hhs:fha:nhinc:service-type\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></ResourceMatch></Resource><Resource><ResourceMatch MatchId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">DocumentRetrieve</AttributeValue><ResourceAttributeDesignator AttributeId=\"urn:gov:hhs:fha:nhinc:service-type\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></ResourceMatch></Resource></Resources></Target><Rule RuleId=\"urn:gov:hhs:fha:nhinc:docquery:notauthorized\" Effect=\"Permit\"><Description>If request is to disclose then permit.</Description><Target/><Condition><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">Disclose</AttributeValue><ResourceAttributeDesignator AttributeId=\"urn:oasis:names:tc:xspa:2.0:resource:patient:authorization\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></Apply></Condition></Rule><Rule RuleId=\"urn:gov:hhs:fha:nhinc:docquery:allowedpous\" Effect=\"Deny\"><Description>If request in not in list then deny.</Description><Target/><Condition><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:not\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-at-least-one-member-of\"><SubjectAttributeDesignator AttributeId=\"urn:oasis:names:tc:xspa:1.0:subject:purposeofuse\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-bag\"></Apply></Apply></Apply></Condition></Rule><Rule RuleId=\"urn:gov:hhs:fha:nhinc:docquery:allowedrecipients\" Effect=\"Deny\"><Description>If request in not in list then deny.</Description><Target/><Condition><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:not\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-at-least-one-member-of\"><SubjectAttributeDesignator AttributeId=\"urn:oasis:names:tc:xacml:2.0:subject:subject-id\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-bag\"></Apply></Apply></Apply></Condition></Rule><Rule RuleId=\"urn:gov:hhs:fha:nhinc:docquery:requiredpermissions\" Effect=\"Deny\"><Description>If request in not in list then deny.</Description><Target/><Condition><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:not\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-subset\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-bag\"></Apply><SubjectAttributeDesignator AttributeId=\"urn:oasis:names:tc:xspa:2.0:subject:sensitivity:privileges\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></Apply></Apply></Condition></Rule></Policy><!-- EMERGENCY TREATMENT OVERRIDE POLICY --><Policy PolicyId=\"urn:gov:hhs:fha:nhinc:anyServiceType:EmergencyPolicy\" RuleCombiningAlgId=\"urn:oasis:names:tc:xacml:1.0:rule-combining-algorithm:permit-overrides\"><Target/><Rule RuleId=\"urn:gov:hhs:fha:nhinc:anyServiceType:EmergencyRule\" Effect=\"Permit\"><Description>Purpose of Use is Emergency so Permit All</Description><Target/><Condition><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">ETREAT</AttributeValue><SubjectAttributeDesignator AttributeId=\"urn:oasis:names:tc:xspa:1.0:subject:purposeofuse\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></Apply></Condition></Rule></Policy></PolicySet>");
	}
	
	@Test
	public void testCreatePatientConsentXACMLPolicy_when_redactActions_is_not_empty() throws SAXException, IOException {
		List<String> redactActions=new ArrayList<String>();
		redactActions.add("treatment");
		redactActions.add("diagnose");
		String result=xACMLPolicyProviderForCDA.createPatientConsentXACMLPolicy(new OrganizationPolicy(), null, null, new ArrayList<String>(), new ArrayList<String>(), redactActions, new ArrayList<String>());
		//Subject to change when xacml schema changes. If that happens you may generate the xacml file and use online xml escaper to change this part.
		assertXMLEqual(result,"<?xml version=\"1.0\" encoding=\"UTF-8\"?><PolicySet xmlns=\"urn:oasis:names:tc:xacml:2.0:policy:schema:os\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:oasis:names:tc:xacml:2.0:policy:schema:os http://docs.oasis-open.org/xacml/access_control-xacml-2.0-policy-schema-os.xsd\" PolicySetId=\"null-null\" PolicyCombiningAlgId=\"urn:oasis:names:tc:xacml:1.0:policy-combining-algorithm:deny-overrides\"><Target/><!-- DISCLOSURE AUTHORIZATION POLICY --><Policy PolicyId=\"urn:oasis:names:tc:xspa:1.0:nwhin:exchange:query\" RuleCombiningAlgId=\"urn:oasis:names:tc:xacml:1.0:rule-combining-algorithm:deny-overrides\"><Description>Denies the request if authorization does not exist.</Description><Target><Resources><Resource><ResourceMatch MatchId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">DocumentQuery</AttributeValue><ResourceAttributeDesignator AttributeId=\"urn:gov:hhs:fha:nhinc:service-type\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></ResourceMatch></Resource><Resource><ResourceMatch MatchId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">DocumentRetrieve</AttributeValue><ResourceAttributeDesignator AttributeId=\"urn:gov:hhs:fha:nhinc:service-type\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></ResourceMatch></Resource></Resources></Target><Rule RuleId=\"urn:gov:hhs:fha:nhinc:docquery:notauthorized\" Effect=\"Permit\"><Description>If request is to disclose then permit.</Description><Target/><Condition><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">Disclose</AttributeValue><ResourceAttributeDesignator AttributeId=\"urn:oasis:names:tc:xspa:2.0:resource:patient:authorization\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></Apply></Condition></Rule><Rule RuleId=\"urn:gov:hhs:fha:nhinc:docquery:allowedpous\" Effect=\"Deny\"><Description>If request in not in list then deny.</Description><Target/><Condition><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:not\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-at-least-one-member-of\"><SubjectAttributeDesignator AttributeId=\"urn:oasis:names:tc:xspa:1.0:subject:purposeofuse\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-bag\"></Apply></Apply></Apply></Condition></Rule><Rule RuleId=\"urn:gov:hhs:fha:nhinc:docquery:allowedrecipients\" Effect=\"Deny\"><Description>If request in not in list then deny.</Description><Target/><Condition><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:not\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-at-least-one-member-of\"><SubjectAttributeDesignator AttributeId=\"urn:oasis:names:tc:xacml:2.0:subject:subject-id\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-bag\"></Apply></Apply></Apply></Condition></Rule><Rule RuleId=\"urn:gov:hhs:fha:nhinc:docquery:requiredpermissions\" Effect=\"Deny\"><Description>If request in not in list then deny.</Description><Target/><Condition><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:not\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-subset\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-bag\"></Apply><SubjectAttributeDesignator AttributeId=\"urn:oasis:names:tc:xspa:2.0:subject:sensitivity:privileges\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></Apply></Apply></Condition></Rule></Policy><!-- DATA REDACTION POLICY --><Policy PolicyId=\"urn:gov:hhs:onc:ds4p:redact\" RuleCombiningAlgId=\"urn:oasis:names:tc:xacml:1.0:rule-combining-algorithm:deny-overrides\"><Target><Resources><Resource><ResourceMatch MatchId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">DS4PRedactAuthorization</AttributeValue><ResourceAttributeDesignator AttributeId=\"urn:gov:hhs:fha:nhinc:service-type\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></ResourceMatch></Resource></Resources></Target><Rule RuleId=\"urn:gov:hhs:onc:ds4p:anyServiceType:RedactAllowRule\" Effect=\"Permit\"><Description>Determine is Redaction is allowed for specific data sensitivity</Description><Target/><Condition><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-at-least-one-member-of\"><ResourceAttributeDesignator AttributeId=\"urn:oasis:names:tc:xspa:2.0:resource:redactauthorization\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-bag\"><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">treatment</AttributeValue><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">diagnose</AttributeValue></Apply></Apply></Condition></Rule><Rule RuleId=\"urn:gov:hhs:onc:ds4p:anyServiceType:RedactDenyRule\" Effect=\"Deny\"><Description>Determine is Redaction is not allowed for specific data sensitivity</Description><Target/><Condition><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:not\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-at-least-one-member-of\"><ResourceAttributeDesignator AttributeId=\"urn:oasis:names:tc:xspa:2.0:resource:redactauthorization\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-bag\"><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">treatment</AttributeValue><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">diagnose</AttributeValue></Apply></Apply></Apply></Condition></Rule></Policy><!-- EMERGENCY TREATMENT OVERRIDE POLICY --><Policy PolicyId=\"urn:gov:hhs:fha:nhinc:anyServiceType:EmergencyPolicy\" RuleCombiningAlgId=\"urn:oasis:names:tc:xacml:1.0:rule-combining-algorithm:permit-overrides\"><Target/><Rule RuleId=\"urn:gov:hhs:fha:nhinc:anyServiceType:EmergencyRule\" Effect=\"Permit\"><Description>Purpose of Use is Emergency so Permit All</Description><Target/><Condition><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">ETREAT</AttributeValue><SubjectAttributeDesignator AttributeId=\"urn:oasis:names:tc:xspa:1.0:subject:purposeofuse\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></Apply></Condition></Rule></Policy></PolicySet>");
	}
	
	@Test
	public void testCreatePatientConsentXACMLPolicy_when_maskingActions_is_not_empty() throws SAXException, IOException {
		List<String> maskingActions=new ArrayList<String>();
		maskingActions.add("actionA");
		maskingActions.add("actionB");
		String result=xACMLPolicyProviderForCDA.createPatientConsentXACMLPolicy(new OrganizationPolicy(), null, null, new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>(), maskingActions);
		//Subject to change when xacml schema changes. If that happens you may generate the xacml file and use online xml escaper to change this part.
		assertXMLEqual(result,"<?xml version=\"1.0\" encoding=\"UTF-8\"?><PolicySet xmlns=\"urn:oasis:names:tc:xacml:2.0:policy:schema:os\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:oasis:names:tc:xacml:2.0:policy:schema:os http://docs.oasis-open.org/xacml/access_control-xacml-2.0-policy-schema-os.xsd\" PolicySetId=\"null-null\" PolicyCombiningAlgId=\"urn:oasis:names:tc:xacml:1.0:policy-combining-algorithm:deny-overrides\"><Target/><!-- DISCLOSURE AUTHORIZATION POLICY --><Policy PolicyId=\"urn:oasis:names:tc:xspa:1.0:nwhin:exchange:query\" RuleCombiningAlgId=\"urn:oasis:names:tc:xacml:1.0:rule-combining-algorithm:deny-overrides\"><Description>Denies the request if authorization does not exist.</Description><Target><Resources><Resource><ResourceMatch MatchId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">DocumentQuery</AttributeValue><ResourceAttributeDesignator AttributeId=\"urn:gov:hhs:fha:nhinc:service-type\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></ResourceMatch></Resource><Resource><ResourceMatch MatchId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">DocumentRetrieve</AttributeValue><ResourceAttributeDesignator AttributeId=\"urn:gov:hhs:fha:nhinc:service-type\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></ResourceMatch></Resource></Resources></Target><Rule RuleId=\"urn:gov:hhs:fha:nhinc:docquery:notauthorized\" Effect=\"Permit\"><Description>If request is to disclose then permit.</Description><Target/><Condition><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">Disclose</AttributeValue><ResourceAttributeDesignator AttributeId=\"urn:oasis:names:tc:xspa:2.0:resource:patient:authorization\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></Apply></Condition></Rule><Rule RuleId=\"urn:gov:hhs:fha:nhinc:docquery:allowedpous\" Effect=\"Deny\"><Description>If request in not in list then deny.</Description><Target/><Condition><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:not\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-at-least-one-member-of\"><SubjectAttributeDesignator AttributeId=\"urn:oasis:names:tc:xspa:1.0:subject:purposeofuse\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-bag\"></Apply></Apply></Apply></Condition></Rule><Rule RuleId=\"urn:gov:hhs:fha:nhinc:docquery:allowedrecipients\" Effect=\"Deny\"><Description>If request in not in list then deny.</Description><Target/><Condition><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:not\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-at-least-one-member-of\"><SubjectAttributeDesignator AttributeId=\"urn:oasis:names:tc:xacml:2.0:subject:subject-id\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-bag\"></Apply></Apply></Apply></Condition></Rule><Rule RuleId=\"urn:gov:hhs:fha:nhinc:docquery:requiredpermissions\" Effect=\"Deny\"><Description>If request in not in list then deny.</Description><Target/><Condition><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:not\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-subset\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-bag\"><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">actionA</AttributeValue><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">actionB</AttributeValue></Apply><SubjectAttributeDesignator AttributeId=\"urn:oasis:names:tc:xspa:2.0:subject:sensitivity:privileges\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></Apply></Apply></Condition></Rule></Policy><!-- DATA MASKING POLICY --><Policy PolicyId=\"urn:gov:hhs:onc:ds4p:redact\" RuleCombiningAlgId=\"urn:oasis:names:tc:xacml:1.0:rule-combining-algorithm:deny-overrides\"><Target><Resources><Resource><ResourceMatch MatchId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">DS4PMaskAuthorization</AttributeValue><ResourceAttributeDesignator AttributeId=\"urn:gov:hhs:fha:nhinc:service-type\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></ResourceMatch></Resource></Resources></Target><Rule RuleId=\"urn:gov:hhs:onc:ds4p:anyServiceType:MaskAllowRule\" Effect=\"Permit\"><Description>Determine is Masking is allowed for specific data sensitivity</Description><Target/><Condition><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-at-least-one-member-of\"><ResourceAttributeDesignator AttributeId=\"urn:oasis:names:tc:xspa:2.0:resource:maskauthorization\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-bag\"><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">actionA</AttributeValue><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">actionB</AttributeValue></Apply></Apply></Condition></Rule><Rule RuleId=\"urn:gov:hhs:onc:ds4p:anyServiceType:MaskDenyRule\" Effect=\"Deny\"><Description>Determine is Masking is not allowed for specific data sensitivity</Description><Target/><Condition><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:not\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-at-least-one-member-of\"><ResourceAttributeDesignator AttributeId=\"urn:oasis:names:tc:xspa:2.0:resource:maskauthorization\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-bag\"><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">actionA</AttributeValue><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">actionB</AttributeValue></Apply></Apply></Apply></Condition></Rule></Policy><!-- EMERGENCY TREATMENT OVERRIDE POLICY --><Policy PolicyId=\"urn:gov:hhs:fha:nhinc:anyServiceType:EmergencyPolicy\" RuleCombiningAlgId=\"urn:oasis:names:tc:xacml:1.0:rule-combining-algorithm:permit-overrides\"><Target/><Rule RuleId=\"urn:gov:hhs:fha:nhinc:anyServiceType:EmergencyRule\" Effect=\"Permit\"><Description>Purpose of Use is Emergency so Permit All</Description><Target/><Condition><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">ETREAT</AttributeValue><SubjectAttributeDesignator AttributeId=\"urn:oasis:names:tc:xspa:1.0:subject:purposeofuse\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></Apply></Condition></Rule></Policy></PolicySet>");
		
//		System.out.println(result);
	}
	
	@Test
	public void testCreatePatientConsentXACMLPolicy_when_orgLaw_refrainPolicy_and_documentHandling_is_not_empty() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException, SAXException, IOException {
		List<String> maskingActions=new ArrayList<String>();
		maskingActions.add("actionA");
		maskingActions.add("actionB");
		
		OrganizationPolicy orgPolicy=new OrganizationPolicy();
		Field organizationTaggingRules = orgPolicy.getClass().getDeclaredField("organizationTaggingRules");
		organizationTaggingRules.setAccessible(true);
		List<OrganizationTaggingRules> organizationTaggingRulesValue=new ArrayList<OrganizationTaggingRules>();
		
		OrganizationTaggingRules OrganizationTaggingRulesEntry=new OrganizationTaggingRules();
		ActUSPrivacyLaw actUSPrivacyLawValue=new ActUSPrivacyLaw();
		actUSPrivacyLawValue.setCode("actUSPrivacyLaw1");
		OrganizationTaggingRulesEntry.setActUSPrivacyLaw(actUSPrivacyLawValue);
		
		RefrainPolicy refrainPolicyValue=new RefrainPolicy();
		refrainPolicyValue.setCode("refrainPolicy1");
		OrganizationTaggingRulesEntry.setRefrainPolicy(refrainPolicyValue);
		
		OrgObligationPolicyDocument orgObligationPolicyDocumentValue=new OrgObligationPolicyDocument();
		ObligationPolicy obligationPolicy=new ObligationPolicy();
		obligationPolicy.setCode("obligationPolicy1");
		orgObligationPolicyDocumentValue.setObligationPolicy(obligationPolicy);
		OrganizationTaggingRulesEntry.setOrgObligationPolicyDocument(orgObligationPolicyDocumentValue);
		
		organizationTaggingRulesValue.add(OrganizationTaggingRulesEntry);
		
		organizationTaggingRules.set(orgPolicy, organizationTaggingRulesValue);
		
		String result=xACMLPolicyProviderForCDA.createPatientConsentXACMLPolicy(orgPolicy, null, null, new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>(), maskingActions);
		//Subject to change when xacml schema changes. If that happens you may generate the xacml file and use online xml escaper to change this part.
		assertXMLEqual(result,"<?xml version=\"1.0\" encoding=\"UTF-8\"?><PolicySet xmlns=\"urn:oasis:names:tc:xacml:2.0:policy:schema:os\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:oasis:names:tc:xacml:2.0:policy:schema:os http://docs.oasis-open.org/xacml/access_control-xacml-2.0-policy-schema-os.xsd\" PolicySetId=\"null-null\" PolicyCombiningAlgId=\"urn:oasis:names:tc:xacml:1.0:policy-combining-algorithm:deny-overrides\"><Target/><!-- DISCLOSURE AUTHORIZATION POLICY --><Policy PolicyId=\"urn:oasis:names:tc:xspa:1.0:nwhin:exchange:query\" RuleCombiningAlgId=\"urn:oasis:names:tc:xacml:1.0:rule-combining-algorithm:deny-overrides\"><Description>Denies the request if authorization does not exist.</Description><Target><Resources><Resource><ResourceMatch MatchId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">DocumentQuery</AttributeValue><ResourceAttributeDesignator AttributeId=\"urn:gov:hhs:fha:nhinc:service-type\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></ResourceMatch></Resource><Resource><ResourceMatch MatchId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">DocumentRetrieve</AttributeValue><ResourceAttributeDesignator AttributeId=\"urn:gov:hhs:fha:nhinc:service-type\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></ResourceMatch></Resource></Resources></Target><Rule RuleId=\"urn:gov:hhs:fha:nhinc:docquery:notauthorized\" Effect=\"Permit\"><Description>If request is to disclose then permit.</Description><Target/><Condition><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">Disclose</AttributeValue><ResourceAttributeDesignator AttributeId=\"urn:oasis:names:tc:xspa:2.0:resource:patient:authorization\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></Apply></Condition></Rule><Rule RuleId=\"urn:gov:hhs:fha:nhinc:docquery:allowedpous\" Effect=\"Deny\"><Description>If request in not in list then deny.</Description><Target/><Condition><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:not\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-at-least-one-member-of\"><SubjectAttributeDesignator AttributeId=\"urn:oasis:names:tc:xspa:1.0:subject:purposeofuse\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-bag\"></Apply></Apply></Apply></Condition></Rule><Rule RuleId=\"urn:gov:hhs:fha:nhinc:docquery:allowedrecipients\" Effect=\"Deny\"><Description>If request in not in list then deny.</Description><Target/><Condition><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:not\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-at-least-one-member-of\"><SubjectAttributeDesignator AttributeId=\"urn:oasis:names:tc:xacml:2.0:subject:subject-id\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-bag\"></Apply></Apply></Apply></Condition></Rule><Rule RuleId=\"urn:gov:hhs:fha:nhinc:docquery:requiredpermissions\" Effect=\"Deny\"><Description>If request in not in list then deny.</Description><Target/><Condition><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:not\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-subset\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-bag\"><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">actionA</AttributeValue><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">actionB</AttributeValue></Apply><SubjectAttributeDesignator AttributeId=\"urn:oasis:names:tc:xspa:2.0:subject:sensitivity:privileges\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></Apply></Apply></Condition></Rule></Policy><!-- DATA MASKING POLICY --><Policy PolicyId=\"urn:gov:hhs:onc:ds4p:redact\" RuleCombiningAlgId=\"urn:oasis:names:tc:xacml:1.0:rule-combining-algorithm:deny-overrides\"><Target><Resources><Resource><ResourceMatch MatchId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">DS4PMaskAuthorization</AttributeValue><ResourceAttributeDesignator AttributeId=\"urn:gov:hhs:fha:nhinc:service-type\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></ResourceMatch></Resource></Resources></Target><Rule RuleId=\"urn:gov:hhs:onc:ds4p:anyServiceType:MaskAllowRule\" Effect=\"Permit\"><Description>Determine is Masking is allowed for specific data sensitivity</Description><Target/><Condition><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-at-least-one-member-of\"><ResourceAttributeDesignator AttributeId=\"urn:oasis:names:tc:xspa:2.0:resource:maskauthorization\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-bag\"><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">actionA</AttributeValue><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">actionB</AttributeValue></Apply></Apply></Condition></Rule><Rule RuleId=\"urn:gov:hhs:onc:ds4p:anyServiceType:MaskDenyRule\" Effect=\"Deny\"><Description>Determine is Masking is not allowed for specific data sensitivity</Description><Target/><Condition><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:not\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-at-least-one-member-of\"><ResourceAttributeDesignator AttributeId=\"urn:oasis:names:tc:xspa:2.0:resource:maskauthorization\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-bag\"><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">actionA</AttributeValue><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">actionB</AttributeValue></Apply></Apply></Apply></Condition></Rule></Policy><!-- ORGANIZATION US PRIVACY LAW POLICY --><Policy PolicyId=\"urn:gov:hhs:onc:ds4p:usprivacylaw\" RuleCombiningAlgId=\"urn:oasis:names:tc:xacml:1.0:rule-combining-algorithm:deny-overrides\"><Target><Resources><Resource><ResourceMatch MatchId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">DS4PUSPrivacyLaw</AttributeValue><ResourceAttributeDesignator AttributeId=\"urn:gov:hhs:fha:nhinc:service-type\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></ResourceMatch></Resource></Resources></Target><Rule RuleId=\"urn:gov:hhs:onc:ds4p:anyServiceType:USPrivacyLawAllowRule\" Effect=\"Permit\"><Description>Determine if US Privacy is required</Description><Target/><Condition><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-at-least-one-member-of\"><ResourceAttributeDesignator AttributeId=\"urn:oasis:names:tc:xspa:2.0:resource:org:us-privacy-law\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-bag\"><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">actUSPrivacyLaw1</AttributeValue></Apply></Apply></Condition></Rule><Rule RuleId=\"urn:gov:hhs:onc:ds4p:anyServiceType:USPrivacyLawDenyRule\" Effect=\"Deny\"><Description>Determine if US Privacy Law is not required</Description><Target/><Condition><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:not\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-at-least-one-member-of\"><ResourceAttributeDesignator AttributeId=\"urn:oasis:names:tc:xspa:2.0:resource:org:us-privacy-law\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-bag\"><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">actUSPrivacyLaw1</AttributeValue></Apply></Apply></Apply></Condition></Rule></Policy><!-- ORGANIZATION REFRAIN POLICY --><Policy PolicyId=\"urn:gov:hhs:onc:ds4p:refrainpolicy\" RuleCombiningAlgId=\"urn:oasis:names:tc:xacml:1.0:rule-combining-algorithm:deny-overrides\"><Target><Resources><Resource><ResourceMatch MatchId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">DS4PRefrainPolicy</AttributeValue><ResourceAttributeDesignator AttributeId=\"urn:gov:hhs:fha:nhinc:service-type\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></ResourceMatch></Resource></Resources></Target><Rule RuleId=\"urn:gov:hhs:onc:ds4p:anyServiceType:RefrainPolicyAllowRule\" Effect=\"Permit\"><Description>Determine refrain policy allowed</Description><Target/><Condition><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-at-least-one-member-of\"><ResourceAttributeDesignator AttributeId=\"urn:oasis:names:tc:xspa:2.0:resource:org:refrain-policy\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-bag\"><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">refrainPolicy1</AttributeValue></Apply></Apply></Condition></Rule><Rule RuleId=\"urn:gov:hhs:onc:ds4p:anyServiceType:RefrainPolicyDenyRule\" Effect=\"Deny\"><Description>Determine refrain policy is denied</Description><Target/><Condition><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:not\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-at-least-one-member-of\"><ResourceAttributeDesignator AttributeId=\"urn:oasis:names:tc:xspa:2.0:resource:org:us-privacy-law\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-bag\"><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">refrainPolicy1</AttributeValue></Apply></Apply></Apply></Condition></Rule></Policy><!-- ORGANIZATION DOCUMENT HANDLING POLICY --><Policy PolicyId=\"urn:gov:hhs:onc:ds4p:refrainpolicy\" RuleCombiningAlgId=\"urn:oasis:names:tc:xacml:1.0:rule-combining-algorithm:deny-overrides\"><Target><Resources><Resource><ResourceMatch MatchId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">DS4PDocumentHandling</AttributeValue><ResourceAttributeDesignator AttributeId=\"urn:gov:hhs:fha:nhinc:service-type\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></ResourceMatch></Resource></Resources></Target><Rule RuleId=\"urn:gov:hhs:onc:ds4p:anyServiceType:RefrainPolicyAllowRule\" Effect=\"Permit\"><Description>Determine document handling policy is allowed</Description><Target/><Condition><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-at-least-one-member-of\"><ResourceAttributeDesignator AttributeId=\"urn:oasis:names:tc:xspa:2.0:resource:org:document-handling\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-bag\"><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">obligationPolicy1</AttributeValue></Apply></Apply></Condition></Rule><Rule RuleId=\"urn:gov:hhs:onc:ds4p:anyServiceType:RefrainPolicyDenyRule\" Effect=\"Deny\"><Description>Determine document handling policy is denied</Description><Target/><Condition><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:not\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-at-least-one-member-of\"><ResourceAttributeDesignator AttributeId=\"urn:oasis:names:tc:xspa:2.0:resource:org:document-handling\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-bag\"><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">obligationPolicy1</AttributeValue></Apply></Apply></Apply></Condition></Rule></Policy><!-- EMERGENCY TREATMENT OVERRIDE POLICY --><Policy PolicyId=\"urn:gov:hhs:fha:nhinc:anyServiceType:EmergencyPolicy\" RuleCombiningAlgId=\"urn:oasis:names:tc:xacml:1.0:rule-combining-algorithm:permit-overrides\"><Target/><Rule RuleId=\"urn:gov:hhs:fha:nhinc:anyServiceType:EmergencyRule\" Effect=\"Permit\"><Description>Purpose of Use is Emergency so Permit All</Description><Target/><Condition><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">ETREAT</AttributeValue><SubjectAttributeDesignator AttributeId=\"urn:oasis:names:tc:xspa:1.0:subject:purposeofuse\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></Apply></Condition></Rule></Policy></PolicySet>");
		
	}
	
}
