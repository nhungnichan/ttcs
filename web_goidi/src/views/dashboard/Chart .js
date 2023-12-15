import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { CCard, CCardBody, CCardHeader, CCol, CRow, CProgressBar } from '@coreui/react';
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from 'chart.js';
import { Pie } from 'react-chartjs-2';
import { collection, query, where, getDocs, startAt, orderBy, endAt, deleteDoc, doc } from 'firebase/firestore/lite';
import db from 'src/config';

ChartJS.register(ArcElement, Tooltip, Legend);

const fetchDataFromFirestore = async (collectionName, queryConfig) => {
    const collectionRef = collection(db, collectionName);
    const q = query(collectionRef, ...queryConfig);
    const snapshot = await getDocs(q);
    return snapshot.docs.map(doc => doc.data());
};

const GenderChart = () => {
    const [listUser, setListUser] = useState([]);
    const [loading, setLoading] = useState(true);
    const [totalUser, setTotalUser] = useState(0);
    const [totalUserActive, setTotalUserActive] = useState(0);
    const [totalChatsItem, setTotalChatsItem] = useState(0);

    useEffect(() => {
        const fetchData = async () => {
            try {
                await Promise.allSettled([
                    getUsers(),
                    getChatsItemCount(),
                ]);
            } catch (error) {
                console.error('Error fetching data', error);
            } finally {
                setLoading(false);
            }
        };
        fetchData();
    }, []);

    const getUsers = async (value) => {
        try {
            const userCol = 'Users';
            const q = value
                ? [orderBy('fullName'), startAt(value), endAt(value + '\uf8ff')]
                : [];

            const [userSnapshot, activeUsersSnapshot] = await Promise.all([
                fetchDataFromFirestore(userCol, q),
                fetchDataFromFirestore(userCol, [where('activeStatus', '==', true)]),
            ]);

            initialState(userSnapshot);

            const totalActiveUsers = activeUsersSnapshot.length;
            setTotalUserActive(totalActiveUsers);
        } catch (e) {
            console.log(e);
        }
    };

    const getChatsItemCount = async () => {
        try {
            const chatsItemCol = 'ChatsItem';
            const chatsItemSnapshot = await fetchDataFromFirestore(chatsItemCol, []);
            const totalChatsItem = chatsItemSnapshot.length;
            setTotalChatsItem(totalChatsItem);
        } catch (error) {
            console.error('Error getting ChatsItem count', error);
        }
    };

    const initialState = (userSnapshot) => {
        let userList = userSnapshot.docs.map(doc => doc.data());
        setListUser(userList);
        setTotalUser(userList.length);
        console.log("User List:", userList);
    }

    const calculateGenderRatio = () => {
        const maleCount = listUser.filter(user => user.gender === 0).length;
        const percent_maleCount = (maleCount / totalUser * 100).toFixed(1);
        const femaleCount = listUser.filter(user => user.gender === 1).length;
        const percent_femaleCount = (femaleCount / totalUser * 100).toFixed(1);
        return {
            labels: ['Male', 'Female'],
            datasets: [
                {
                    data: [percent_maleCount, percent_femaleCount],
                    backgroundColor: ['#36A2EB', '#FF6384'],
                    hoverBackgroundColor: ['#36A2EB', '#FF6384'],
                },
            ],
        };
    };

    const calculateAgeDistribution = () => {
        const ageDistribution = {
            '18-25': 0,
            '26-32': 0,
            '32+': 0,
        };

        // Tính tuổi và đếm phân phối
        listUser.forEach(user => {
            const birthDate = new Date(user.birthday);
            const age = new Date().getFullYear() - birthDate.getFullYear();
            // Chia độ tuổi thành các khoảng cụ thể
            if (age >= 18 && age <= 25) {
                ageDistribution['18-25']++;
            } else if (age > 25 && age <= 32) {
                ageDistribution['26-32']++;
            } else if (age > 32) {
                ageDistribution['32+']++;
            }
        });

        // Chuyển đổi số lượng thành phần trăm
        const ageDistributionInPercentage = {};
        Object.keys(ageDistribution).forEach(key => {
            ageDistributionInPercentage[key] = ((ageDistribution[key] / totalUser) * 100).toFixed(1);
        });

        return {
            labels: Object.keys(ageDistributionInPercentage),
            datasets: [
                {
                    data: Object.values(ageDistributionInPercentage),
                    backgroundColor: ['#FF6384', '#36A2EB', '#FFCE56'],
                    hoverBackgroundColor: ['#FF6384', '#36A2EB', '#FFCE56'],
                },
            ],
        };
    };

    if (loading)
        return <CCardBody>
            <CProgressBar></CProgressBar>
        </CCardBody>
    else
        return <>
            <div style={{ height: 180, boxShadow: '0px 0px 10px rgba(0, 0, 0, 0.1)', borderRadius: 10, padding: 20, backgroundColor: '#fff' }}>
                <h4 style={{ marginBottom: 10 }}>Statistics</h4>
                <hr />
                <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
                    <Link to="/user" style={{ textDecoration: 'none', color: 'black' }}>
                        <div style={{ width: 250, height: 70, boxShadow: '0px 0px 10px rgba(0, 0, 0, 0.3)', borderRadius: 10, textAlign: 'center', paddingTop: 10, cursor: 'pointer' }} className='hover-overlay'>
                            Total number of users <br />
                            <p style={{ fontWeight: 'bold' }}>{totalUser}</p>
                        </div>
                    </Link>
                    <div style={{ width: 250, height: 70, color: 'black', boxShadow: '0px 0px 10px rgba(0, 0, 0, 0.3)', borderRadius: 10, textAlign: 'center', paddingTop: 10, marginLeft: 80 }}>
                        Number of active users<br />
                        <p style={{ fontWeight: 'bold' }}>{totalUserActive}</p>
                    </div>
                    <div style={{ width: 250, height: 70, color: 'black', boxShadow: '0px 0px 10px rgba(0, 0, 0, 0.3)', borderRadius: 10, textAlign: 'center', paddingTop: 10, marginLeft: 80 }}>
                        Number of successful matches <br />
                        <p style={{ fontWeight: 'bold' }}>{totalChatsItem}</p>
                    </div>
                </div >
            </div>
            <div style={{ height: 500, marginTop: 30, boxShadow: '0px 0px 10px rgba(0, 0, 0, 0.1)', borderRadius: 10, padding: 20, backgroundColor: '#fff' }}>
                <h4 style={{ marginBottom: 10 }}>Chart</h4>
                <hr />
                <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
                    <div style={{ width: 360, height: 360, textAlign: 'center', marginRight: 200 }}>
                        <h5>Distribution by gender (%)</h5>
                        <Pie
                            data={calculateGenderRatio()}
                            options={{
                                tooltips: {
                                    enabled: true,
                                    callbacks: {
                                        label: (tooltipItem, data) => {
                                            const dataset = data.datasets[tooltipItem.datasetIndex];
                                            const currentValue = dataset.data[tooltipItem.index];
                                            return currentValue + '%';
                                        },
                                    },
                                },
                            }}
                        />
                    </div>
                    <div style={{ width: 360, height: 360, textAlign: 'center' }}>
                        <h5>  Distribution by age (%)</h5>
                        <Pie
                            data={calculateAgeDistribution()}
                            options={{
                                tooltips: {
                                    enabled: true,
                                    callbacks: {
                                        label: (tooltipItem, data) => {
                                            const dataset = data.datasets[tooltipItem.datasetIndex];
                                            const currentValue = dataset.data[tooltipItem.index];
                                            return currentValue + '%';
                                        },
                                        title: () => '', // Trả về chuỗi trống để ẩn tiêu đề
                                    },
                                },
                            }}
                        />
                    </div>
                </div>
            </div>
        </>;
}

export default GenderChart;
