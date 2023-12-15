// authMiddleware.js
import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

const AuthMiddleware = ({ children }) => {
    const navigate = useNavigate();

    useEffect(() => {
        const token = localStorage.getItem('token'); // Lấy token từ localStorage hoặc cookie

        if (!token) {
            navigate('/login'); // Nếu không có token, chuyển hướng đến trang đăng nhập
        }
    }, [navigate]);

    return <>{children}</>;
};

export default AuthMiddleware;
