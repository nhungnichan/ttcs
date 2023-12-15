import React from "react";

const Loading = ({ visible }) => {
	return visible && <div className="spinner-container"><div className="loading-spinner"></div></div>
}

export default Loading;