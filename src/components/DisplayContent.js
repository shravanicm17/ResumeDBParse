// DisplayContent.js
import React, { useEffect, useState } from 'react';
import apiUrl from '../config';
import { BrowserRouter as Router, Route, Routes, Link, Outlet } from 'react-router-dom';


const DisplayContent = ({ match }) => {
    const [textContent, setTextContent] = useState('');

    useEffect(() => {
        const fetchContent = async () => {
            try {
                const response = await fetch(`${apiUrl}/content/${match.params.id}`);
                if (response.ok) {
                    const content = await response.text();
                    setTextContent(content);
                } else {
                    console.error('Failed to fetch content');
                }
            } catch (error) {
                console.error('Error fetching content:', error);
            }
        };

        fetchContent();
    }, [match.params.id]);

    return (
        <div>
            <h2>Document Content</h2>
            <pre>{textContent}</pre>
        </div>
    );
};

export default DisplayContent;
