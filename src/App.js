import React from 'react';
import './App.css';
import { BrowserRouter as Router, Route, Routes, Link, Outlet } from 'react-router-dom';
import FileUpload from "./components/FileUpload";
import DisplayContent from './components/DisplayContent';
function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<FileUpload />} />
        <Route path="/display/:id" element={<DisplayContent />} />
      </Routes>
    </Router>
  );
}

export default App;
