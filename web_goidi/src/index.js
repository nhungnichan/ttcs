import 'react-app-polyfill/stable';
import 'core-js';
import React from 'react';
import { createRoot } from 'react-dom/client';
import { Provider } from 'react-redux';
import { GoogleReCaptchaProvider } from 'react-google-recaptcha-v3';
import store from './store';
import App from './App';

createRoot(document.getElementById('root')).render(
	<Provider store={store}>
		<GoogleReCaptchaProvider>
			<App />
		</GoogleReCaptchaProvider>
	</Provider>
);
