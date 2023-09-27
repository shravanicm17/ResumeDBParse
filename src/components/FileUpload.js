import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import apiUrl from '../config'; // Import the apiUrl constant

const allowedFileTypes = ['application/pdf', 'application/msword', 'application/vnd.openxmlformats-officedocument.wordprocessingml.document'];

const FileUpload = () => {
    const [selectedFile, setSelectedFile] = useState(null);
    const [fileId, setFileId] = useState(null);
    const handleFileChange = (e) => {
        const file = e.target.files[0];
        if (file && allowedFileTypes.includes(file.type)) {
            setSelectedFile(file);
        } else {
            alert('Please select a valid PDF or DOC file.');
            setSelectedFile(null);
        }
    };

    const handleFileUpload = async () => {
        if (selectedFile) {
            const formData = new FormData();
            formData.append('file', selectedFile);

            try {
                const response = await fetch(`${apiUrl}/upload`, {
                    method: 'POST',
                    body: formData,
                });

                if (response.ok) {
                    alert('File uploaded successfully.');
                    setSelectedFile(null); // Clear the selected file input
                } else {
                    alert('File upload failed.');
                }
            } catch (error) {
                console.error('Error uploading file:', error);
            }
        } else {
            alert('Please select a file to upload.');
        }
    };

    return (
      <div>
          <input type="file" accept=".pdf,.doc,.docx" onChange={handleFileChange} />
          <button onClick={handleFileUpload}>Upload File</button>
          {selectedFile && (
              <Link to={`/display/${fileId}`}>View Content</Link>
          )}
      </div>
    );
};

export default FileUpload;
