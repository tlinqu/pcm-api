package gov.samhsa.c2s.pcm.service.pdf;


import gov.samhsa.c2s.pcm.config.PdfProperties;
import gov.samhsa.c2s.pcm.domain.consent.Consent;
import gov.samhsa.c2s.pcm.domain.patient.Patient;
import gov.samhsa.c2s.pcm.infrastructure.exception.PdfGenerationException;
import gov.samhsa.c2s.pcm.infrastructure.pdfbox.PdfBoxService;
import gov.samhsa.c2s.pcm.infrastructure.pdfbox.PdfBoxStyle;
import gov.samhsa.c2s.pcm.infrastructure.pdfbox.util.PdfBoxHandler;
import gov.samhsa.c2s.pcm.service.exception.PdfConfigMissingException;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;

@Service
@Slf4j
public class ConsentPdfGeneratorImpl implements ConsentPdfGenerator {
    private static final String CONSENT_PDF = "consent-pdf";
    private static final String DATE_FORMAT_PATTERN = "MM/dd/yyyy";
    private static final String SPACE_PATTERN = " ";

    private final PdfBoxService pdfBoxService;
    private final PdfProperties pdfProperties;

    @Autowired
    public ConsentPdfGeneratorImpl(PdfBoxService pdfBoxService, PdfProperties pdfProperties) {
        this.pdfBoxService = pdfBoxService;
        this.pdfProperties = pdfProperties;
    }

    @Override
    public void addConsentTitle(String pdfType, float startYCoordinate, PDPage page, PDPageContentStream contentStream) throws IOException {
        String consentTitle = pdfProperties.getPdfConfigs().stream()
                .filter(pdfConfig -> pdfConfig.type.equalsIgnoreCase(pdfType))
                .map(PdfProperties.PdfConfig::getTitle)
                .findAny()
                .orElseThrow(PdfConfigMissingException::new);

        float titleFontSize = 20f;
        PDFont titleFont = PDType1Font.TIMES_BOLD;
        Color titleColor = Color.BLACK;
        pdfBoxService.addCenteredTextAtOffset(consentTitle, titleFont, titleFontSize, titleColor, startYCoordinate, page, contentStream);
    }

    @Override
    public void addConsentReferenceNumberAndPatientInfo(Consent consent, Patient patient, float startYCoordinate, PDFont defaultFont, PDPageContentStream contentStream) throws IOException {
        String consentReferenceNumber = consent.getConsentReferenceId();
        String patientFullName = patient.getFirstName().concat(SPACE_PATTERN + patient.getLastName());
        String patientBirthDate = PdfBoxHandler.formatDate(patient.getBirthDay(), DATE_FORMAT_PATTERN);

        final Color textColor = Color.BLACK;
        final float fontSize = PdfBoxStyle.TEXT_SMALL_SIZE;
        final PDFont contentFont = PDType1Font.TIMES_BOLD;

        // Add Consent Reference Number
        final String crnLabel = "Consent Reference Number: ";
        pdfBoxService.addTextAtOffset(crnLabel, defaultFont, fontSize, textColor, PdfBoxStyle.LEFT_RIGHT_MARGINS_OF_LETTER, startYCoordinate, contentStream);
        final float crnXCoordinate = PdfBoxStyle.LEFT_RIGHT_MARGINS_OF_LETTER + PdfBoxHandler.targetedStringWidth(crnLabel, defaultFont, fontSize);
        pdfBoxService.addTextAtOffset(consentReferenceNumber, contentFont, fontSize, textColor, crnXCoordinate, startYCoordinate, contentStream);

        // Add patient name
        final float nameYCoordinate = startYCoordinate - PdfBoxStyle.XLARGE_LINE_SPACE;
        final String nameLabel = "Patient Name: ";
        pdfBoxService.addTextAtOffset(nameLabel, defaultFont, fontSize, textColor, PdfBoxStyle.LEFT_RIGHT_MARGINS_OF_LETTER, nameYCoordinate, contentStream);
        final float nameXCoordinate = PdfBoxStyle.LEFT_RIGHT_MARGINS_OF_LETTER + PdfBoxHandler.targetedStringWidth(nameLabel, defaultFont, fontSize);
        pdfBoxService.addTextAtOffset(patientFullName, contentFont, fontSize, textColor, nameXCoordinate, nameYCoordinate, contentStream);

        // Add patient DOB
        final String dobLabel = "Patient DOB: ";
        final float dobLabelXCoordinate = 300f;
        pdfBoxService.addTextAtOffset(dobLabel, defaultFont, fontSize, textColor, dobLabelXCoordinate, nameYCoordinate, contentStream);
        final float dobXCoordinate = dobLabelXCoordinate + PdfBoxHandler.targetedStringWidth(dobLabel, defaultFont, fontSize);
        pdfBoxService.addTextAtOffset(patientBirthDate, contentFont, fontSize, textColor, dobXCoordinate, nameYCoordinate, contentStream);
    }

    @Override
    public byte[] generateConsentPdf(Consent consent, Patient patient, boolean isSigned, Date attestedOn, String terms) throws IOException {
        Assert.notNull(consent, "Consent is required.");

        ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();
        // Create a new empty document
        PDDocument document = new PDDocument();

        // Create a new blank page with configured page size and add it to the document
        PDPage page = pdfBoxService.generatePage(CONSENT_PDF, document);
        log.debug("Configured page size is: " + pdfBoxService.getConfiguredPdfFont(CONSENT_PDF));

        // Set configured font
        PDFont defaultFont = pdfBoxService.getConfiguredPdfFont(CONSENT_PDF);
        log.debug("Configured font is: " + defaultFont);

        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {

            // Configure each drawing section yCoordinate in order to centralized adjust layout
            final float titleSectionStartYCoordinate = page.getMediaBox().getHeight() - PdfBoxStyle.TOP_BOTTOM_MARGINS_OF_LETTER;
            final float consentReferenceNumberSectionStartYCoordinate = 690f;

            // Title
            addConsentTitle(CONSENT_PDF, titleSectionStartYCoordinate, page, contentStream);

            // Consent Reference Number and Patient information
            addConsentReferenceNumberAndPatientInfo(consent, patient, consentReferenceNumberSectionStartYCoordinate, defaultFont, contentStream);

            // Make sure that the content stream is closed
            contentStream.close();

            // Save the document to an output stream
            document.save(pdfOutputStream);

            return pdfOutputStream.toByteArray();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new PdfGenerationException(e);
        } finally {
            pdfOutputStream.close();
            // finally make sure that the document is properly closed
            document.close();
        }
    }
}
