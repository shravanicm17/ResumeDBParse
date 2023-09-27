import React, { useState, useEffect } from 'react';
import apiUrl from './config';
const FileList = () => {
  const [files, setFiles] = useState([]);

  useEffect(() => {
    fetch('http://localhost:8080/api/files')
      .then((response) => response.json())
      .then((data) => setFiles(data))
      .catch((error) => console.error('Error fetching files:', error));
  }, []);

  return (
    <div>
      <h2>Uploaded Files</h2>
      <ul>
        {files.map((file) => (
          <li key={file.id}>{file.fileName}</li>
        ))}
      </ul>
    </div>
  );
};

export default ResumeList;
