import React, { useState } from 'react';
import { useNavigate, } from 'react-router-dom';
import { Form, Button } from 'react-bootstrap';
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const Login = () => {
	const navigate = useNavigate();
	const [username, setUsername] = useState('');
	const [password, setPassword] = useState('');

	const handleLogin = async () => {
		try {
			const response = await fetch('http://localhost:8000/login', {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json',
				},
				body: JSON.stringify({
					username,
					password,
				}),
			});
			const data = await response.json();
			if (data.success) {
				localStorage.setItem('token', data.token);
				toast.success(data.message, {
					autoClose: 1000,
					onClose: () => navigate('/dashboard'),
				});
			} else {
				toast.error(data.message);
				return;
			}
		} catch (error) {
			// Hiển thị toast khi đăng nhập thất bại
			toast.error('Sai thông tin đăng nhập');
		}
	};

	return (
		<div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '40vh' }}>
			<ToastContainer />
			<Form onSubmit={handleLogin} style={{ width: 300, margin: 20, padding: 20, boxShadow: '0 5px 10px rgba(0, 0, 0, 0.2)', borderRadius: 10 }}>
				<Form.Group controlId="formPassword">
					<Form.Label style={{ marginLeft: 80 }}>Tên tài khoản</Form.Label>
					<Form.Control
						type="text"
						value={username}
						onChange={(e) => setUsername(e.target.value)}
						style={{ marginTop: 10 }}
					/>
					<Form.Label style={{ marginLeft: 80 }}>Mật khẩu</Form.Label>
					<Form.Control
						type="password"
						value={password}
						onChange={(e) => setPassword(e.target.value)}
						style={{ marginTop: 10 }}
					/>
				</Form.Group>

				<Button onClick={handleLogin} variant="primary" style={{ marginTop: 30, marginLeft: 80 }}>
					Đăng nhập
				</Button>
			</Form>
		</div>
	);
};

export default Login;
