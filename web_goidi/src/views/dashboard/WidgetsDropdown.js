import React from 'react'
import {
	CRow,
	CCol,
	CDropdown,
	CDropdownMenu,
	CDropdownItem,
	CDropdownToggle,
	CWidgetStatsA,
} from '@coreui/react'
import CIcon from '@coreui/icons-react'
import { cilArrowBottom, cilArrowTop, cilOptions } from '@coreui/icons'
import chart from './chart';


const WidgetsDropdown = () => {

	const dropdownRefresh = <CDropdown alignment="end">
		<CDropdownToggle color="transparent" caret={false} className="p-0">
			<CIcon icon={cilOptions} className="text-high-emphasis-inverse" />
		</CDropdownToggle>
		<CDropdownMenu>
			<CDropdownItem>Làm mới</CDropdownItem>
		</CDropdownMenu>
	</CDropdown>;
	return (
		<CRow>
			<CCol sm={6} lg={3}>
				<CWidgetStatsA
					className="mb-4"
					color="primary"
					title="hello1"
					action={dropdownRefresh}
					chart={chart.chart1}
				/>
			</CCol>
			<CCol sm={6} lg={3}>
				<CWidgetStatsA
					className="mb-4"
					color="info"
					title="hello2"
					action={dropdownRefresh}
					chart={chart.chart2}
				/>
			</CCol>
			<CCol sm={6} lg={3}>
				<CWidgetStatsA
					className="mb-4"
					color="warning"
					title="hello3"
					action={dropdownRefresh}
					chart={chart.chart3}
				/>
			</CCol>
			<CCol sm={6} lg={3}>
				<CWidgetStatsA
					className="mb-4"
					color="danger"
					title="hello4"
					action={dropdownRefresh}
					chart={chart.chart4}
				/>
			</CCol>
		</CRow>
	)
}

export default WidgetsDropdown
