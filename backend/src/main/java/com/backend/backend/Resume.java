package com.backend.backend;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

// @Entity
// public class Resume {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     @Lob
//     private byte[] data;

//     public void setFileName(String originalFilename) {
//     }

//     public void setData(byte[] bytes) {
//     }

//     public String getFileName() {
//         return null;
//     }

//     public byte[] getData() {
//         return null;
//     }

//     public String getId() {
//         return null;
//     }

//     // getters and setters
// }
// import javax.persistence.Entity;
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
// import javax.persistence.Id;
// import javax.persistence.Lob;

@Entity
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_name")
    private String fileName;

    @Lob
    private byte[] data;

    public void setFileName(String originalFilename) {
        this.fileName = originalFilename;
    }

    public void setData(byte[] bytes) {
    }

    public String getId() {
        return null;
    }

    public byte[] getData() {
        return null;
    }

    public String getFileName() {
        return null;
    }

    // Getters and setters
}
