import React, { useEffect, useState } from 'react';
import { CCard, CCardBody, CCardHeader, CCol, CRow, CProgressBar } from '@coreui/react';
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from 'chart.js';
import { Pie } from 'react-chartjs-2';
import { collection, query, startAt, orderBy, endAt, getDocs } from 'firebase/firestore/lite';
import db from 'src/config';

ChartJS.register(ArcElement, Tooltip, Legend);

const BirthdayChart = () => {
    const [listUser, setListUser] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        getUsers();
    }, []);

    const getUsers = async (value) => {
        try {
            let userCol = collection(db, 'Users');
            let q;
            let userSnapshot;
            if (value) {
                q = query(userCol, orderBy('fullName'), startAt(value), endAt(value + '\uf8ff'));
            } else {
                q = userCol;
            }
            userSnapshot = await getDocs(q);
            initialState(userSnapshot);
            console.log("query: ", q);
        } catch (e) {
            console.log(e);
        }
    }

    const initialState = (userSnapshot) => {
        let userList = userSnapshot.docs.map(doc => doc.data());
        setListUser(userList);
        setLoading(false);
        console.log("User List:", userList);
    }

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

        return {
            labels: Object.keys(ageDistribution),
            datasets: [
                {
                    data: Object.values(ageDistribution),
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
            <div style={{ width: 360, height: 360, textAlign: 'center' }}>
                <h5>Biểu đồ phân phối theo độ tuổi </h5>
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
                            },
                        },
                    }}
                />
            </div>
        </>;
}

export default BirthdayChart; 