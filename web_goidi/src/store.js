import { createStore } from 'redux'

const initialState = {
	sidebarShow: true,
	user: { verified: true },
	loading: false,
	toast: null,
	modal: { visible: false, title: "", buttons: [], body: <></> },
}

const changeState = (state = initialState, { type, ...rest }) => {
	switch (type) {
		case 'set':
			return { ...state, ...rest }
		default:
			return state
	}
}

const store = createStore(changeState)
export default store
