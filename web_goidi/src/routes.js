import React from 'react'

const Dashboard = React.lazy(() => import('./views/dashboard/Dashboard'))
const Login = React.lazy(() => import('./views/auth/Login'))
const Dish = React.lazy(() => import('./views/dish/dish'))
const AddDish = React.lazy(() => import('./views/dish/addDish'))
const UpdateDish = React.lazy(() => import('./views/dish/updateDish'))

const routes = [
	{ path: '/login', name: 'Đăng nhập', element: Login },
	{ path: '/dashboard', element: Dashboard },
	{ path: '/dish', name: 'Món ăn', element: Dish },
	{ path: '/dish/add', name: 'Thêm Món ăn', element: AddDish },
	{ path: '/dish/:id', name: 'Cập Nhật Món Ăn', element: UpdateDish },
]

export default routes	