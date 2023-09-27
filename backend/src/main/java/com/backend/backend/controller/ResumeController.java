package com.backend.backend.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
// import java.net.http.HttpHeaders;
import java.util.List;

// import org.apache.tomcat.util.http.parser.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.backend.backend.Resume;
import com.backend.backend.service.ResumeService;

import jakarta.persistence.EntityNotFoundException;

// import org.springframework.http.MediaType;
// import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.HttpHeaders;



// @RestController
// @RequestMapping("/api/resumes")
// public class ResumeController {

//     @Autowired
//     private ResumeService resumeService;

//     @GetMapping
//     public List<Resume> getAllResumes() {
//         return resumeService.getAllResumes();
//     }


//     /**
//      * @param id
//      * @return
//      */
//     @GetMapping("/{id}")
//     public ResponseEntity<InputStreamResource> getResumeById(@PathVariable Long id) {
//         Resume resume = resumeService.getResumeById(id);
//         return ResponseEntity.ok()
//                 .contentType(MediaType.APPLICATION_PDF) // Adjust the content type as needed
//                 .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resume.getFileName() + "\"")
//                 .body(new InputStreamResource(new ByteArrayInputStream(resume.getData())));
//     }

//     @PostMapping("/upload")
//     public ResponseEntity<String> uploadResume(@RequestParam("file") MultipartFile file) {
//         try {
//             Resume storedResume = resumeService.storeResume(file);
//             return ResponseEntity.status(HttpStatus.CREATED).body("Resume uploaded successfully with ID: " + storedResume.getId());
//         } catch (IOException e) {
//             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload resume");
//         }
//     }

// }
@RestController
@RequestMapping("/api/resume")
public class ResumeController {

    @Autowired
    private ResumeService resumeService;

    @GetMapping
    public List<Resume> getAllResumes() {
        // Retrieve and return all resumes
        return resumeService.getAllResumes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<InputStreamResource> getResumeById(@PathVariable Long id) {
        try {
            // Retrieve the resume by ID
            Resume resume = resumeService.getResumeById(id);

            // Return the resume data as a response with appropriate headers
            return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF) // Adjust the content type as needed
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resume.getFileName() + "\"")
                .body(new InputStreamResource(new ByteArrayInputStream(resume.getData())));
        } catch (EntityNotFoundException e) {
            // Handle the case where the resume is not found by returning a 404 response
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadResume(@RequestParam("file") MultipartFile file) {
        try {
            // Validate the file type and size here before processing
            if (!isValidFileType(file)) {
                return ResponseEntity.badRequest().body("Invalid file type. Please upload a PDF or DOC file.");
            }

            if (file.getSize() > 10 * 1024 * 1024) { // 10MB
                return ResponseEntity.badRequest().body("File size exceeds the maximum allowed limit (10MB).");
            }

            Resume storedResume = resumeService.storeResume(file);
            return ResponseEntity.status(HttpStatus.CREATED).body("Resume uploaded successfully with ID: " + storedResume.getId());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload resume");
        }
    }

    @GetMapping("/content/{id}")
    public ResponseEntity<String> getResumeContentById(@PathVariable Long id) {
    try {
        String content = resumeService.getTextContent(id);
        return ResponseEntity.ok(content);
    } catch (EntityNotFoundException e) {
        return ResponseEntity.notFound().build();
    } catch (IOException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve content");
    } catch (UnsupportedOperationException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}


    // Validate if the uploaded file is of a valid type (PDF or DOC)
        private boolean isValidFileType(MultipartFile file) {
            String contentType = file.getContentType();
            return contentType != null && (contentType.equals("application/pdf") || contentType.equals("application/msword") || contentType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document"));
    }
}
