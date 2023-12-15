import React from 'react'
import {
  CButton,
  CCol,
  CContainer,
  CFormInput,
  CInputGroup,
  CInputGroupText,
  CRow,
} from '@coreui/react'
import CIcon from '@coreui/icons-react'
import { cilMagnifyingGlass } from '@coreui/icons'

const Page404 = () => {
  return (
    <CContainer>
      <CRow className="justify-content-center">
        <CCol md={6}>
          <div className="clearfix">
            <h1 className="float-start display-3 me-4"></h1>
            <h4 className="pt-3">
            <img src="assets/img/details-6.webp" className="img-fluid" alt="" />
              {/* Có vẻ như trang đó không tồn tại. <br></br>Vui lòng kiểm tra URL và thử lại ! */}
            </h4>
            <p className="text-medium-emphasis float-start">
              Không tìm thấy trang bạn đang tìm kiếm.
            </p>
          </div>
          <CInputGroup className="input-prepend">
            <CInputGroupText>
              <CIcon icon={cilMagnifyingGlass} />
            </CInputGroupText>
            <CFormInput type="text" placeholder="Đặt câu hỏi về chúng tôi?" />
            <CButton color="info">Tìm kiếm</CButton>
          </CInputGroup>
        </CCol>
      </CRow>
    </CContainer>
  )
}

export default Page404
