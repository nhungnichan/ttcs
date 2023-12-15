import React from 'react'
import { CNavItem, CNavTitle } from '@coreui/react'
import { IoNotifications } from "react-icons/io5";
import { FaUser, FaChartBar } from "react-icons/fa";
import { IoIosHome } from "react-icons/io";
const _nav = [
	{
		component: CNavItem,
	},
	{
		component: CNavTitle,
		name: 'App',
	},
	// {
	// 	component: CNavItem,
	// 	name: 'Home',
	// 	to: '/dashboard',
	// 	icon: <IoIosHome size={24} className="nav-icon" />,
	// },
	// {
	// 	component: CNavItem,
	// 	name: 'Users',
	// 	to: '/user',
	// 	icon: <FaUser size={24} className="nav-icon" />,
	// },
	// {
	// 	component: CNavItem,
	// 	name: 'Notification',
	// 	to: '/notification',
	// 	icon: <IoNotifications size={24} className="nav-icon" />,
	// },
	// {
	// 	component: CNavItem,
	// 	name: 'Revenue',
	// 	to: '/revenue',
	// 	icon: <FaChartBar size={24} className="nav-icon" />,
	// },
	{
		component: CNavItem,
		name: 'Dishs',
		to: '/dish',
		icon: <FaChartBar size={24} className="nav-icon" />,
	},

]

export default _nav
