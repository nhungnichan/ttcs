import React from 'react'
import { useSelector, useDispatch } from 'react-redux'
import {
	CContainer,
	CHeader,
	CHeaderDivider,
	CHeaderNav,
	CHeaderToggler,
	CNavItem,
	CNavLink,
} from '@coreui/react'
import { TiThMenu } from "react-icons/ti";

import { AppBreadcrumb } from './index'
import { AppHeaderDropdown } from './header/index'
import { formatMoney, getRandomInt } from 'src/utils/number.util'
import { NavLink } from 'react-router-dom';

const AppHeader = () => {
	const dispatch = useDispatch()
	const user = useSelector(state => state.user);
	const sidebarShow = useSelector((state) => state.sidebarShow)

	return (
		<CHeader position="sticky" className="mb-4" >
			<CContainer fluid>
				<CHeaderToggler
					style={{ paddingBottom: 10 }}
					onClick={() => dispatch({ type: 'set', sidebarShow: !sidebarShow })}
				>
					<TiThMenu size={24} className="nav-icon" />
				</CHeaderToggler>
				
				{/* <CHeaderNav style={{ cursor: 'pointer' }}>
					<CNavItem>
						<CNavLink className='position-relative'>
							<CPopover content='Tài khoản chính' trigger='hover' placement="bottom">
								<div>
									<CIcon icon={cilMoney} size="sm" className="text-primary" />
									{formatMoney(user.money?.main || 0)}đ
								</div>
							</CPopover>
						</CNavLink>
					</CNavItem>
					<CNavItem className='d-none d-sm-block'>
						<CNavLink className='position-relative'>
							<CPopover content='Tài khoản phụ' trigger='hover' placement="bottom">
								<div>
									<CIcon icon={cilMoney} size="sm" className="text-danger" />
									{formatMoney(user.money?.test || 0)}đ
								</div>
							</CPopover>
						</CNavLink>
					</CNavItem>
					<CNavItem className='d-none d-sm-block'>
						<CNavLink className='position-relative'>
							<CPopover content='Tài khoản giới thiệu' trigger='hover' placement="bottom">
								<div>
									<CIcon icon={cilMoney} size="sm" className="text-warning" />
									{formatMoney(user.money?.ref || 0)}đ
								</div>
							</CPopover>
						</CNavLink>
					</CNavItem>
					<CNavItem className='d-none d-sm-block'>
						<CNavLink className='position-relative'>
							<CIcon icon={cilBell} size="lg" />
		
						</CNavLink>
					</CNavItem>
					<CNavItem className='d-none d-sm-block'>
						<CNavLink >
							<CIcon icon={cilList} size="lg" />
						</CNavLink>
					</CNavItem>
					<CNavItem>
						<CNavLink className='position-relative'>
							<CIcon icon={cilEnvelopeOpen} size="lg" />
					x	
						</CNavLink>
					</CNavItem>
				</CHeaderNav> */}
				<CHeaderNav>
					<AppHeaderDropdown />
				</CHeaderNav>
			</CContainer>
			<CHeaderDivider />
		</CHeader>
	)
}

export default AppHeader
