package com.backend.backend.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.backend.backend.Resume;
import com.backend.backend.repository.ResumeRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;

// import org.apache.poi.hwpf.usermodel.Range;


@Service
public class ResumeService {

    @Autowired
    private ResumeRepository resumeRepository;

    public List<Resume> getAllResumes() {
        return resumeRepository.findAll();
    }

    public Resume getResumeById(Long id) {
        return resumeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Resume not found with id: " + id));
    }
    @Transactional
    public Resume storeResume(MultipartFile file) throws IOException {
        Resume resume = new Resume();
        resume.setFileName(file.getOriginalFilename());
        resume.setData(file.getBytes());
        return resumeRepository.save(resume);
    }
    



    // TO DETECT TEXT FROM DOCUMENT
    public String getTextContent(Long id) throws IOException {
        Resume resume = getResumeById(id);
        String contentType = "application/pdf"; // You can determine the content type based on the file extension

        if (contentType.equals("application/pdf")) {
            // Handle PDF
            try (PDDocument document = PDDocument.load(resume.getData())) {
                PDFTextStripper pdfStripper = new PDFTextStripper();
                return pdfStripper.getText(document);
            }
        } else if (contentType.equals("application/msword") || contentType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")) {
            // Handle DOC or DOCX
            try (InputStream inputStream = new ByteArrayInputStream(resume.getData())) {
                HWPFDocument doc = new HWPFDocument(inputStream);
                WordExtractor extractor = new WordExtractor(doc);
                return String.join("\n", extractor.getParagraphText());
            }
        } else {
            // Unsupported file type
            throw new UnsupportedOperationException("Unsupported file type: " + contentType);
        }
    }
    
}
