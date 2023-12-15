import React, { Suspense } from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import './scss/style.scss';
import AuthMiddleware from './views/auth/AuthMiddleware ';
const Login = React.lazy(() => import('./views/auth/Login'));

// Containers
const DefaultLayout = React.lazy(() => import('./layout/DefaultLayout'));

const App = () => {
	return (
		<Router>
			<Routes>
				<Route
					path="/login"
					element={
						<Suspense fallback={<div>Loading...</div>}>
							<Login />
						</Suspense>
					}
				/>
				<Route
					path="*"
					element={
						<Suspense fallback={<div>Loading...</div>}>
							<AuthMiddleware>
								<DefaultLayout />
							</AuthMiddleware>
						</Suspense>
					}
				/>
			</Routes>
		</Router>
	);
};

export default App;
