import React, { useState, useEffect } from "react";
import axios from "axios";
import './FacultyProfileForm.css'; // Custom styling for the form

const FacultyProfileForm = ({ facultyId, initialProfile, onProfileUpdate }) => {
    const [officeHours, setOfficeHours] = useState(initialProfile.officeHours);
    const [email, setEmail] = useState('');
    const [phone, setPhone] = useState('');

    useEffect(() => {
        if (initialProfile.user) {
            setEmail(initialProfile.user.email || '');
            setPhone(initialProfile.user.phone || '');
        }
    }, [initialProfile]);

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await axios.put(`http://localhost:8080/api/faculty/update-profile`, null, {
                params: {
                    facultyId: facultyId,
                    officeHours: officeHours,
                    email: email,
                    phone: phone,
                },
            });
            alert("Profile updated successfully");
            onProfileUpdate(); 
        } catch (error) {
            console.error("Error updating profile", error);
            alert("Failed to update profile");
        }
    };

    return (
        <div className="profile-form-container">
            <h2>Update Profile</h2>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Office Hours:</label>
                    <input
                        type="text"
                        value={officeHours}
                        onChange={(e) => setOfficeHours(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label>Email:</label>
                    <input
                        type="email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label>Phone:</label>
                    <input
                        type="text"
                        value={phone}
                        onChange={(e) => setPhone(e.target.value)}
                        required
                    />
                </div>
                <button type="submit" className="submit-button">Update Profile</button>
            </form>
        </div>
    );
};

export default FacultyProfileForm;
