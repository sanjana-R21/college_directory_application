import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Bar } from 'react-chartjs-2';
import {
    Chart as ChartJS,
    CategoryScale,
    LinearScale,
    BarElement,
    Title,
    Tooltip,
    Legend,
} from 'chart.js';
import Navbar from './navbar'; // Assuming you have a Navbar component
import './AdminDashboard.css'; // CSS file for styling

// Register ChartJS components explicitly
ChartJS.register(CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend);

const AdminDashboard = () => {
    const [students, setStudents] = useState([]);
    const [faculty, setFaculty] = useState([]);
    const [newRecord, setNewRecord] = useState({ type: 'student', name: '', email: '' });
    const [chartData, setChartData] = useState({});
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [submitLoading, setSubmitLoading] = useState(false);

    const token = localStorage.getItem('token');

    const axiosConfig = {
        headers: {
            Authorization: `Bearer ${token}`,
        },
    };

    // Fetch all students and faculty
    const fetchRecords = async () => {
        try {
            setLoading(true);
            const studentResponse = await axios.get('http://localhost:8080/api/admin/students', axiosConfig);
            const facultyResponse = await axios.get('http://localhost:8080/api/admin/faculty', axiosConfig);
            setStudents(studentResponse.data);
            setFaculty(facultyResponse.data);
            generateChartData(studentResponse.data); // Pass the student data to generate chart
        } catch (error) {
            setError('Failed to fetch student or faculty records');
        } finally {
            setLoading(false);
        }
    };

    const handleDeleteRecord = async (id, type) => {
        try {
            if (type === 'student') {
                setStudents((prevStudents) => prevStudents.filter((student) => student.userId !== id));
            } else {
                setFaculty((prevFaculty) => prevFaculty.filter((facultyMember) => facultyMember.userId !== id));
            }
        } catch (error) {
            console.error('Error deleting record:', error);
            setError(`Failed to delete ${type}`);
        }
    };

    const generateChartData = (studentData) => {
        const departmentCounts = {};
        studentData.forEach(student => {
            const deptName = student.department?.name || 'Unknown Department';
            if (departmentCounts[deptName]) {
                departmentCounts[deptName]++;
            } else {
                departmentCounts[deptName] = 1;
            }
        });

        setChartData({
            labels: Object.keys(departmentCounts),
            datasets: [
                {
                    label: 'Number of Students',
                    data: Object.values(departmentCounts),
                    backgroundColor: 'rgba(75, 192, 192, 0.6)',
                },
            ],
        });
    };

    useEffect(() => {
        fetchRecords();
    }, []);

    const handleAddRecord = (e) => {
        e.preventDefault();

        if (newRecord.type === 'student') {
            setStudents(prev => [
                ...prev,
                {
                    userId: prev.length + 1,
                    user: {
                        name: newRecord.name || 'John Doe',
                        email: newRecord.email || 'john.doe@example.com',
                    },
                    department: { id: 1, name: 'Computer Science' },
                },
            ]);
        } else {
            setFaculty(prev => [
                ...prev,
                {
                    userId: prev.length + 1,
                    user: {
                        name: newRecord.name || 'Jane Doe',
                        email: newRecord.email || 'jane.doe@example.com',
                    },
                    officeHours: 'Monday 9am-11am',
                    department: { id: 1, name: 'Computer Science' },
                },
            ]);
        }
        setNewRecord({ type: 'student', name: '', email: '' });
        setSubmitLoading(false);
    };

    return (
        <div>
            <Navbar /> {/* Include Navbar */}
            <div className="dashboard-container">
                <div className="tables-section">
                    <h2>Students</h2>
                    <table className="students-table">
                        <thead>
                            <tr>
                                <th>User</th>
                                <th>Email</th>
                                <th>Department</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            {students.map((student) => (
                                <tr key={student.userId}>
                                    <td>
                                        <img src="/pic2.png" alt="User Icon" className="user-icon" />
                                        {student.user?.name || 'Unknown Student'}
                                    </td>
                                    <td>{student.user?.email || 'unknown@example.com'}</td>
                                    <td>{student.department?.name || 'Unknown Department'}</td>
                                    <td>
                                        <button onClick={() => handleDeleteRecord(student.userId, 'student')}>
                                            Delete
                                        </button>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>

                    <h2>Faculty</h2>
                    <table className="faculty-table">
                        <thead>
                            <tr>
                                <th>User</th>
                                <th>Email</th>
                                <th>Department</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            {faculty.map((facultyMember) => (
                                <tr key={facultyMember.userId}>
                                    <td>
                                        <img src="/pic2.png" alt="User Icon" className="user-icon" />
                                        {facultyMember.user?.name || 'Unknown Faculty'}
                                    </td>
                                    <td>{facultyMember.user?.email || 'unknown@example.com'}</td>
                                    <td>{facultyMember.department?.name || 'Unknown Department'}</td>
                                    <td>
                                        <button onClick={() => handleDeleteRecord(facultyMember.userId, 'faculty')}>
                                            Delete
                                        </button>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>

                <div className="add-record-section">
                    <h2>Add New Record</h2>
                    {error && <p style={{ color: 'red' }}>{error}</p>}
                    <form onSubmit={handleAddRecord}>
                        <div>
                            <label>Type:</label>
                            <select
                                value={newRecord.type}
                                onChange={(e) => setNewRecord({ ...newRecord, type: e.target.value })}
                            >
                                <option value="student">Student</option>
                                <option value="faculty">Faculty</option>
                            </select>
                        </div>
                        <div>
                            <label>Name:</label>
                            <input
                                type="text"
                                value={newRecord.name}
                                onChange={(e) => setNewRecord({ ...newRecord, name: e.target.value })}
                                required
                            />
                        </div>
                        <div>
                            <label>Email:</label>
                            <input
                                type="email"
                                value={newRecord.email}
                                onChange={(e) => setNewRecord({ ...newRecord, email: e.target.value })}
                                required
                            />
                        </div>
                        <button type="submit" disabled={submitLoading}>
                            {submitLoading ? 'Adding...' : 'Add Record'}
                        </button>
                    </form>
                </div>
            </div>

            <div className="chart-section">
                <h2>Number of Students by Department</h2>
                {loading ? (
                    <p>Loading chart...</p>
                ) : (
                    <Bar
    data={chartData}
    options={{
        responsive: false, 
        scales: {
            y: {
                beginAtZero: true,
            },
        },
    }}
    height={300}  // Adjust the height here
    width={400}   // Adjust the width here
/>

                )}
            </div>
        </div>
    );
};

export default AdminDashboard;
