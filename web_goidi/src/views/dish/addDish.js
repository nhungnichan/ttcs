import React, { useEffect, useState, useCallback } from 'react'
import { useLocation, useNavigate } from "react-router-dom";
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { initializeApp } from "firebase/app";
import { getStorage, ref, uploadBytes, getDownloadURL } from "firebase/storage";
import { getDatabase, ref as databaseRef, set } from 'firebase/database';

const firebaseConfig = {
    apiKey: "AIzaSyCnRQHMhPaH0xfhA_pkXplR7ebQjy7q6j8",
    authDomain: "duan-90921.firebaseapp.com",
    databaseURL: "https://duan-90921-default-rtdb.firebaseio.com",
    projectId: "duan-90921",
    storageBucket: "duan-90921.appspot.com",
    messagingSenderId: "48897335922",
    appId: "1:48897335922:web:0b9454f73a8389e54d58eb",
    measurementId: "G-348JCBXY10"
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);
const storage = getStorage(app);

import {
    CForm,
    CFormInput,
    CFormLabel,
    CFormCheck,
    CCardHeader,
    CButton,
    CInputGroup,
    CInputGroupText,
    CCard,
    CCardBody,
    CFormFeedback
} from '@coreui/react'
import { useForm } from 'react-hook-form';

const UpdateDish = () => {
    const { state } = useLocation();
    const navigate = useNavigate();
    const { register, watch, reset, handleSubmit, setValue, getValues, formState: { errors } } = useForm({
        defaultValues: {
            imageUrl: '',
            name: '',
            price: '',
            image: null
        },
    });

    const handleFileChange = async (e) => {
        const file = e.target.files[0];
        setValue('image', file)
    };

    const saveDish = async () => {
        try {
            let downloadURL = '';
            if (watch('imageUrl')) {
                const storageRef = ref(storage, 'images/' + new Date().getTime() + '.jpg');
                await uploadBytes(storageRef, watch('image'));
                downloadURL = await getDownloadURL(storageRef);
            }
            let id = `dish${new Date().getTime()}`;
            const newData = {
                ten: watch('name'),
                gia: Number(watch('price')),
                img: downloadURL,
                id: id,
                soLuong: 0
            }
            const dbRef = databaseRef(getDatabase(), `Dish/${id}`);
            try {
                await set(dbRef, newData);
                navigate('/dish');
            } catch (error) {
                console.error('Error setting dish data:', error);
            }
        } catch (error) {
            console.error('Error uploading file:', error);
        }
    }
    const onSubmit = (data) => {
        saveDish();
    };

    return <>
        <CCard>
            <CCardHeader>
                <strong>Thêm</strong>
            </CCardHeader>
            <CCardBody>
                <CForm onSubmit={handleSubmit(onSubmit)}>
                    <div className="mb-3">
                        <CFormLabel htmlFor="exampleFormControlInput1">Tên</CFormLabel>
                        <CFormInput {...register('name', { required: "Vui lòng nhập tên" })} type="text" placeholder="Lau rieu cua" />
                        {errors.name && <CFormFeedback className='mt-1' style={{ color: 'red' }}>{errors.name.message}</CFormFeedback>}
                    </div>
                    <div className="mb-3">
                        <CFormLabel htmlFor="exampleFormControlInput1">Giá</CFormLabel>
                        <CFormInput {...register('price', { required: "Vui lòng nhập giá" })} type="text" placeholder="100" />
                        {errors.price && <CFormFeedback className='mt-1' style={{ color: 'red' }}>{errors.price.message}</CFormFeedback>}
                    </div>
                    <div className="mb-3">
                        <CFormLabel htmlFor="exampleFormControlInput1">Ảnh</CFormLabel>
                        <CFormInput {...register('imageUrl')} type="file" accept="image/*" onChange={handleFileChange} required />
                    </div>
                    <div className='mb-2 mt-4' style={{ textAlign: 'right' }}>
                        <CButton className='mt-1 text-light' type='submit' color="success" style={{ width: 100 }}>Save</CButton>
                    </div>
                </CForm>
            </CCardBody>
        </CCard >
    </>
}
export default UpdateDish;