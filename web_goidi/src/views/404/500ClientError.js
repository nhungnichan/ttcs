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

const Page500ClientError = ({ error, resetErrorBoundary }) => {
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
              Trang bạn truy cập đã xảy ra lỗi :(, chúng tôi đã ghi nhận lỗi và sớm khắc phục.
            </p>
          </div>
          <CInputGroup className="input-prepend">
            <CInputGroupText>
              <CIcon icon={cilMagnifyingGlass} />
            </CInputGroupText>
            <CFormInput type="text" placeholder="Về trang chủ" />
            <CButton color="info" onClick={_ => location.href = '/'}>Về trang chủ</CButton>
          </CInputGroup>
        </CCol>
      </CRow>
    </CContainer>
  )
}

export default Page500ClientError
