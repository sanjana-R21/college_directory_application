import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Navbar from './navbar'; // Import Navbar component
import './StudentDashboard.css'; // Custom styling for student dashboard

const StudentDashboard = () => {
    const [profile, setProfile] = useState(null);
    const [searchTerm, setSearchTerm] = useState('');
    const [searchResults, setSearchResults] = useState([]);

    const userId = localStorage.getItem('userId');  

    useEffect(() => {
        const fetchProfile = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/api/student/profile?userId=${userId}`);
                setProfile(response.data);
            } catch (error) {
                console.error('Error fetching profile:', error);
            }
        };
        fetchProfile();
    }, [userId]);

    const handleSearch = async () => {
        try {
            const response = await axios.get(`http://localhost:8080/api/student/search?name=${searchTerm}`);
            setSearchResults(response.data);
        } catch (error) {
            console.error('Error searching students:', error);
        }
    };

    return (
        <div>
            <Navbar /> {/* Include the Navbar */}
            <div className="dashboard-container">
                
                {/* Student Details Section */}
                <div className="student-details-container">
                    {profile && profile.user ? (
                        <>
                            <div className="profile-section">
                                <img src="/pic2.png" alt="Profile" className="profile-icon" />
                                <ul>
                                    <li><strong>Name:</strong> {profile.user.name}</li>
                                    <li><strong>Email:</strong> {profile.user.email}</li>
                                    <li><strong>User ID:</strong> {profile.userId}</li>
                                    <li><strong>Year:</strong> {profile.year}</li>
                                    <li><strong>Role:</strong> {profile.user.role}</li>
                                </ul>
                            </div>
                        </>
                    ) : (
                        <div>Loading profile or user information is missing...</div>
                    )}
                </div>

                {/* Enrolled Courses Section */}
                {profile && profile.user && (
                    <div className="courses-container">
                        <h3>Enrolled Courses</h3>
                        <table className="course-table">
                            <thead>
                                <tr>
                                    <th>Department Name</th>
                                    <th>Department ID</th>
                                    <th>Department Description</th>
                                    <th>Grade</th>
                                    <th>Attendance</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>{profile.department.name}</td>
                                    <td>{profile.department.id}</td>
                                    <td>{profile.department.description}</td>
                                    <td>B</td> {/* Placeholder grade */}
                                    <td>65%</td> {/* Placeholder attendance */}
                                </tr>
                            </tbody>
                        </table>
                    </div>
                )}

                {/* Search Section */}
                <div className="search-section">
                    <h2>Search for Other Students</h2>
                    <input
                        type="text"
                        value={searchTerm}
                        onChange={(e) => setSearchTerm(e.target.value)}
                        placeholder="Search by name"
                        className="search-input"
                    />
                    <button onClick={handleSearch} className="search-button">Search</button>

                    {searchResults.length > 0 && (
                        <ul className="search-results">
                            {searchResults.map(student => (
                                <li key={student.userId}>
                                    {student.user.name} - {student.department.name} - {student.year}
                                </li>
                            ))}
                        </ul>
                    )}
                </div>
            </div>
        </div>
    );
};

export default StudentDashboard;
