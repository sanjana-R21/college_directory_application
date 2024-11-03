import React, { useState, useEffect } from 'react';
import axios from 'axios';
import FacultyProfileForm from './FacultyProfileForm'; // Assuming FacultyProfileForm is in the same directory
import Navbar from './navbar'; // Import Navbar component
import './FacultyDashboard.css'; // Custom styling for faculty dashboard

const FacultyDashboard = () => {
    const [profile, setProfile] = useState({
        user: {
            name: '',
            email: '',
            phone: ''
        },
        officeHours: '',
        department: {
            name: ''
        },
    });
    
    const [classList, setClassList] = useState([]); 
    const [error, setError] = useState(null);
    const [showForm, setShowForm] = useState(false); // To toggle the form visibility

    const userId = localStorage.getItem('userId'); 

    // Fetch the faculty profile
    const fetchProfile = async () => {
        try {
            const response = await axios.get(`http://localhost:8080/api/faculty/profile?userId=${userId}`);
            setProfile(response.data);
            console.log(response.data);
        } catch (error) {
            console.error('Error fetching profile:', error);
            setError('Failed to fetch profile');
        }
    };

    useEffect(() => {
        fetchProfile();
    }, [userId]);

    // Fetch the class list
    useEffect(() => {
        const fetchClassList = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/api/faculty/classes?facultyId=${profile?.userId}`);
                setClassList(response.data);
                console.log(response.data);
            } catch (error) {
                console.error('Error fetching class list:', error);
                setError('Failed to fetch class list');
            }
        };

        if (profile?.userId) {
            fetchClassList();
        }
    }, [profile]);

    const handleProfileUpdate = () => {
        fetchProfile(); // Refresh the profile after successful update
        setShowForm(false); // Close the form after update
    };

    return (
        <div>
            <Navbar /> {/* Include Navbar */}
            <div className="dashboard-container">
                {/* Faculty Profile Section */}
                <div className="faculty-details-container">
                    <div className="profile-section">
                        <img src="/pic2.png" alt="Profile" className="profile-icon" />
                        <ul>
                            <li><strong>Name:</strong> {profile.user.name || 'Faculty'}</li>
                            <li><strong>Email:</strong> {profile.user.email || 'N/A'}</li>
                            <li><strong>Phone:</strong> {profile.user.phone || 'N/A'}</li>
                            <li><strong>Office Hours:</strong> {profile.officeHours || 'N/A'}</li>
                            <li><strong>Department:</strong> {profile.department.name || 'N/A'}</li>
                        </ul>
                        <button onClick={() => setShowForm(!showForm)} className="update-button">
                            {showForm ? 'Close Update Form' : 'Update Profile'}
                        </button>
                    </div>
                </div>

                {/* Class List Section */}
                <div className="class-list-container">
                    <h3>Class List</h3>
                    <table className="class-table">
                        <thead>
                            <tr>
                                <th>Name</th>
                                <th>Email</th>
                            </tr>
                        </thead>
                        <tbody>
                            {classList.length > 0 ? (
                                classList.map((student, index) => (
                                    <tr key={index}>
                                        <td>{student.user.name || 'N/A'}</td>
                                        <td>{student.user.email || 'N/A'}</td>
                                    </tr>
                                ))
                            ) : (
                                <tr>
                                    <td colSpan="2">No students found</td>
                                </tr>
                            )}
                        </tbody>
                    </table>
                </div>

                {/* Profile Update Modal */}
                {showForm && (
                    <div className="profile-update-modal">
                        <FacultyProfileForm 
                            facultyId={profile.userId} 
                            initialProfile={profile} 
                            onProfileUpdate={handleProfileUpdate} 
                        />
                    </div>
                )}
            </div>
        </div>
    );
};

export default FacultyDashboard;
