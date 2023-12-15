import React from 'react';
import {
	CAvatar,
	CDropdown,
	CDropdownItem,
	CDropdownMenu,
	CDropdownToggle,
} from '@coreui/react';
import { cilAccountLogout } from '@coreui/icons';
import CIcon from '@coreui/icons-react';
import { useNavigate } from 'react-router-dom';
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const AppHeaderDropdown = () => {
	const navigate = useNavigate();

	const handleLogout = async () => {
		const authToken = localStorage.getItem('token');

		if (authToken) {
			try {
				const response = await fetch('http://localhost:8000/logout', {
					method: 'POST',
					headers: {
						'Content-Type': 'application/json',
						'Authorization': `Bearer ${authToken}`,
					},
				});

				const data = await response.json();

				if (data.success) {
					// Xóa token khỏi localStorage khi đăng xuất
					localStorage.removeItem('token');
					navigate('/login')
				} else {
					console.error('Đăng xuất thất bại');
				}
			} catch (error) {
				console.error('Lỗi khi gọi API đăng xuất', error);
			}
		} else {
			console.error('Không có token để gửi yêu cầu đăng xuất');
		}
	};

	return (

		<CDropdown variant="nav-item" style={{ cursor: 'pointer' }}>
			<ToastContainer />
			<CDropdownToggle placement="bottom-end" className="py-0" caret={false} style={{ cursor: 'pointer' }}>
				<img src='https://cdn-icons-png.flaticon.com/128/4980/4980623.png' style={{ width: 45, height: 45 }}></img>
			</CDropdownToggle>
			<CDropdownMenu className="pt-0" placement="bottom-end">
				<CDropdownItem onClick={handleLogout}>
					<CIcon icon={cilAccountLogout} className="me-2" />
					Log out
				</CDropdownItem>
			</CDropdownMenu>
		</CDropdown>
	);
};

export default React.memo(AppHeaderDropdown);
